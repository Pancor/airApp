package pl.pancor.android.air.utils.location;

public interface Location {

    interface Service extends BaseLocation<Receiver>{

        void onStart();

        void onStop();

        void onActivityResult(int requestCode, int resultCode);

        void getLastKnownLocation();
    }

    interface Receiver{

        void lastKnownLocation(double latitude, double longitude);

        void userRefusedToSendLocation();

        void unableToObtainLocation();
    }
}
