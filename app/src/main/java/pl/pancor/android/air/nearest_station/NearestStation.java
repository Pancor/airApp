package pl.pancor.android.air.nearest_station;


import pl.pancor.android.air.base.BaseView;

public interface NearestStation {

    interface View extends BaseView<Presenter>{

        void setLoadingIndicator(boolean active);


    }

    interface Presenter{

        void findNearestStation();
    }
}
