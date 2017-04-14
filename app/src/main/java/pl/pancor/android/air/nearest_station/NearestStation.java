package pl.pancor.android.air.nearest_station;

import pl.pancor.android.air.base.BaseView;
import pl.pancor.android.air.models.Station;


public interface NearestStation {

    interface View extends BaseView<Presenter>{

        void setLoadingIndicator(boolean active);

        void setStation(Station station, Double lat, Double lng);

        void onConnectionError();

        void userRefusedToGiveLocation();

        void couldNotObtainLocalization();
    }

    interface Presenter{

        void findNearestStation(String token);

        void getStation(Double latitude, Double longitude);

        void onActivityResult(int requestCode, int resultCode);

        void onStart();

        void onStop();
    }
}
