package pl.pancor.android.air.utils.location;

import android.Manifest;
import android.app.Activity;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;

import javax.inject.Inject;

import pl.pancor.android.air.base.FragmentScope;

@FragmentScope
public class LocationService implements Location.Service,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
        ActivityCompat.OnRequestPermissionsResultCallback, LocationListener,
        ResultCallback<LocationSettingsResult>{

    private static final int PERMISSIONS_REQUEST = 13;
    private static final int SETTINGS_CHECK = 23;
    private static final int GOOGLE_API_CLIENT_ERROR = 33;

    private static final int LOCATION_EXPIRATION_TIME = 10 * 1000;
    private static final int LOCATION_INTERVAL = 2 * 1000;

    private GoogleApiClient mGoogleApiClient;

    private Activity mActivity;

    private LocationRequest mLocationRequest;
    private android.location.Location mLastLocation;

    private Location.Receiver mReceiver;

    private Handler mHandler;
    private final Runnable mExpiredLocationUpdate = new Runnable() {
        @Override
        public void run() {
            mReceiver.unableToObtainLocation();
        }
    };

    private boolean isWaitingForConnect = false;

    @Inject
    LocationService(Activity activity) {

        mActivity = activity;
    }

    @Override
    public void getLastKnownLocation() {

        if (isPermissionsGranted(true))
            checkLocationSettings();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode) {

        resolveProblems(requestCode, resultCode);
    }

    @Override
    public void onLocationChanged(android.location.Location location) {

        if (mLastLocation == null) {

            mLastLocation = location;
            sendLatLngToReceiver();
        }

        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        mHandler.removeCallbacks(mExpiredLocationUpdate);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

        if (isWaitingForConnect)
            getLastKnownLocation();
    }

    @Override
    public void onConnectionSuspended(int i) {

        //mGoogleApiClient will automatically try to reconnect
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult result) {

        if (!result.hasResolution()){
            mReceiver.unableToObtainLocation();
            GoogleApiAvailability.getInstance()
                    .getErrorDialog(mActivity, result.getErrorCode(), 0).show();
            return;
        }
        if (mActivity.hasWindowFocus()) {
            try {
                result.startResolutionForResult(mActivity, GOOGLE_API_CLIENT_ERROR);
            } catch (IntentSender.SendIntentException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void setupReceiver(Location.Receiver receiver) {

        mReceiver = receiver;
    }

    @Override
    public void onStart() {

        mHandler = new Handler();

        if (mGoogleApiClient != null){

            mGoogleApiClient.connect();
        } else {

            mGoogleApiClient = getGoogleApiClient();
            mGoogleApiClient.connect();
        }
    }

    @Override
    public void onStop() {

        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(
                    mGoogleApiClient, this);

            mGoogleApiClient.disconnect();
        }
        mHandler.removeCallbacks(mExpiredLocationUpdate);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {

        switch (requestCode){
            case PERMISSIONS_REQUEST:

                if (grantResults.length > 0 &&
                        grantResults[0] == PackageManager.PERMISSION_GRANTED){

                    getLastKnownLocation();
                } else {

                    mReceiver.userRefusedToSendLocation();
                }
        }
    }

    @Override
    public void onResult(@NonNull LocationSettingsResult result) {

        final Status status = result.getStatus();
        switch (status.getStatusCode()){
            case LocationSettingsStatusCodes.SUCCESS:

                getLocation();
                break;
            case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:

                if (mActivity.hasWindowFocus()) {
                    try {
                        status.startResolutionForResult(mActivity, SETTINGS_CHECK);
                    } catch (IntentSender.SendIntentException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case  LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:

                mReceiver.unableToObtainLocation();
                break;
        }
    }

    private void checkLocationSettings() {

        if (mGoogleApiClient != null){

            mLocationRequest = new LocationRequest()
                    .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                    .setFastestInterval(LOCATION_INTERVAL / 2)
                    .setInterval(LOCATION_INTERVAL)
                    .setNumUpdates(1);

            LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                    .addLocationRequest(mLocationRequest);

            PendingResult<LocationSettingsResult> result = LocationServices
                    .SettingsApi.checkLocationSettings(mGoogleApiClient, builder.build());
            result.setResultCallback(this);
        }
    }

    private void getLocation(){

        if (mGoogleApiClient != null)
            mLastLocation = LocationServices.FusedLocationApi
                    .getLastLocation(mGoogleApiClient);

        sendLatLngToReceiver();
    }

    private void sendLatLngToReceiver(){

        if (mLastLocation != null) {

            mReceiver.lastKnownLocation(mLastLocation.getLatitude(),
                    mLastLocation.getLongitude());
            mHandler.removeCallbacks(mExpiredLocationUpdate);
        } else {

            requestLocation();
        }
    }

    private void requestLocation(){

        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {

            LocationServices.FusedLocationApi.requestLocationUpdates(
                    mGoogleApiClient, mLocationRequest, this);
            mHandler.postDelayed(mExpiredLocationUpdate, LOCATION_EXPIRATION_TIME);
        } else {

            isWaitingForConnect = true;
        }
    }

    /**
     * @param request if permissions aren't granted and {@param request} is true,
     *                then request permissions
     * @return true if location permissions are granted
     */
    private boolean isPermissionsGranted(boolean request) {

        if (ActivityCompat.checkSelfPermission(mActivity,
                Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(mActivity,
                        Manifest.permission.ACCESS_COARSE_LOCATION) !=
                        PackageManager.PERMISSION_GRANTED) {

            if (request) {
                ActivityCompat.requestPermissions(mActivity,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.ACCESS_COARSE_LOCATION},
                        PERMISSIONS_REQUEST);
            }
            return false;
        }
        return true;
    }

    private GoogleApiClient getGoogleApiClient(){

        return new GoogleApiClient.Builder(mActivity)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    private void resolveProblems(int requestCode, int resultCode){

        switch (requestCode){
            case SETTINGS_CHECK:
                switch (resultCode){
                    case Activity.RESULT_OK:
                        getLastKnownLocation();
                        break;
                    case Activity.RESULT_CANCELED:
                        mReceiver.userRefusedToSendLocation();
                        break;
                }
                break;
            case GOOGLE_API_CLIENT_ERROR:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        mGoogleApiClient.connect();
                        break;
                    case Activity.RESULT_CANCELED:
                        mReceiver.unableToObtainLocation();
                        break;
                }
        }
    }
}
