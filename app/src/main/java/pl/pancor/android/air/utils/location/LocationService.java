package pl.pancor.android.air.utils.location;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;

import javax.inject.Inject;

import pl.pancor.android.air.base.FragmentScope;

@FragmentScope
public class LocationService implements Location.Service,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
        ActivityCompat.OnRequestPermissionsResultCallback, LocationListener {

    private static final int PERMISSIONS_REQUEST = 13;

    private GoogleApiClient mGoogleApiClient;

    private Activity mActivity;

    private android.location.Location mLastLocation;

    private Location.Receiver mReceiver;

    @Inject
    LocationService(Activity activity) {

        mActivity = activity;
    }

    @Override
    public void getLastKnownLocation() {

        if (isPermissionsGranted(true))
            getLocation();
    }

    @Override
    public void onLocationChanged(android.location.Location location) {

        if (mLastLocation == null) {

            mLastLocation = location;
            sendLatLngToReceiver();
        }

        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

        requestLocation();
    }

    @Override
    public void onConnectionSuspended(int i) {


    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

        mReceiver.failedToConnectGoogleApiClient();
    }

    @Override
    public void setupReceiver(Location.Receiver receiver) {

        mReceiver = receiver;
    }

    @Override
    public void onStart() {

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

    private void getLocation() {

        if (mGoogleApiClient != null)
            mLastLocation = LocationServices.FusedLocationApi
                    .getLastLocation(mGoogleApiClient);

        sendLatLngToReceiver();
    }

    private void sendLatLngToReceiver(){

        if (mLastLocation != null) {

            mReceiver.lastKnownLocation(mLastLocation.getLatitude(),
                    mLastLocation.getLongitude());
        } else {

            requestLocation();
        }
    }

    private void requestLocation(){

        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {

            LocationRequest locationRequest = new LocationRequest();
            locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
            locationRequest.setFastestInterval(1000);
            locationRequest.setInterval(2 * 1000);
            locationRequest.setNumUpdates(1);

            LocationServices.FusedLocationApi.requestLocationUpdates(
                    mGoogleApiClient, locationRequest, this);
        } else {

            mReceiver.failedToConnectGoogleApiClient();
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
}
