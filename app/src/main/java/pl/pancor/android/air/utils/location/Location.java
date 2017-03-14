package pl.pancor.android.air.utils.location;


import com.google.android.gms.maps.model.LatLng;

public interface Location {

    interface Service extends BaseLocation<Receiver>{

        void onStart();

        void onStop();

        void getLastKnownLocation();
    }

    interface Receiver{

        void lastKnownLocation(double latitude, double longitude);

        void failedToConnectGoogleApiClient();

        void userRefusedToSendLocation();
    }
}
