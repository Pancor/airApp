package pl.pancor.android.air.nearest_station;


import pl.pancor.android.air.base.BaseView;
import pl.pancor.android.air.models.station.Data;
import pl.pancor.android.air.utils.location.Location;

public interface NearestStation {

    interface View extends BaseView<Presenter>{

        void setLoadingIndicator(boolean active);

        void setStation(Data station);

        void onConnectionError();
    }

    interface Presenter{

        void findNearestStation(String token);

        void onStart();

        void onStop();
    }
}
