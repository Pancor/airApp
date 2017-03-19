package pl.pancor.android.air.utils.location;

import android.content.Intent;

public interface Location {

    interface Service extends BaseLocation<Receiver>{

        void onStart();

        void onStop();

        void getLastKnownLocation();

        void onActivityResult(int requestCode, int resultCode);
    }

    interface Receiver{

        void lastKnownLocation(double latitude, double longitude);

        void failedToConnectGoogleApiClient();

        void userRefusedToSendLocation();

        void unableToObtainLocation();
    }
}
