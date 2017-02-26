package pl.pancor.android.air.nearest_station;


import javax.inject.Inject;

import pl.pancor.android.air.base.FragmentScope;

@FragmentScope
public class NearestStationPresenter implements NearestStation.Presenter {

    private static final String TAG = NearestStationPresenter.class.getSimpleName();

    private NearestStation.View mView;

    @Inject
    NearestStationPresenter(NearestStation.View view){

        mView = view;
    }

    @Inject
    void setupListeners(){

        mView.setPresenter(this);
    }


    @Override
    public void findNearestStation() {

        mView.setLoadingIndicator(true);


    }
}
