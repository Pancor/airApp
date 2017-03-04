package pl.pancor.android.air.utils.location;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;

import javax.inject.Inject;

import pl.pancor.android.air.base.FragmentScope;

@FragmentScope
public class LocationService implements Location.Service,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private GoogleApiClient mGoogleApiClient;

    private Location.LocationReceiver mReceiver;
    private Context mContext;

    @Inject LocationService(Location.LocationReceiver receiver, Context context){

        mReceiver = receiver;
        mContext = context;
    }

    @Override
    public void getLastKnownLocation() {

        android.location.Location location = null;

        if (mGoogleApiClient.isConnected()) {

            location = LocationServices.FusedLocationApi.getLastLocation(
                    mGoogleApiClient);
        }

        if (location != null){

            LatLng latLng = new LatLng(
                    location.getLatitude(), location.getLongitude());
            mReceiver.lastKnownLocation(latLng);
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onStart() {

        if (mGoogleApiClient != null){

            mGoogleApiClient.connect();
        } else {

            mGoogleApiClient = getGoogleApiClient();
        }
    }

    @Override
    public void onStop() {

        if (mGoogleApiClient != null)
            mGoogleApiClient.disconnect();
    }

    private GoogleApiClient getGoogleApiClient(){

        return new GoogleApiClient.Builder(mContext)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }
}
