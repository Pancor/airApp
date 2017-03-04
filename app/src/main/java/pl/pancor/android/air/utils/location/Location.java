package pl.pancor.android.air.utils.location;


import com.google.android.gms.maps.model.LatLng;

public interface Location {

    interface Service{

        void onStart();

        void onStop();

        void getLastKnownLocation();
    }

    interface LocationReceiver{

        void lastKnownLocation(LatLng latLng);
    }
}
