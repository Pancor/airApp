package pl.pancor.android.air.nearest_station;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import pl.pancor.android.air.base.FragmentScope;
import pl.pancor.android.air.data.RealmService;
import pl.pancor.android.air.models.DataResponse;
import pl.pancor.android.air.models.Station;
import pl.pancor.android.air.models.User;
import pl.pancor.android.air.net.NetService;
import pl.pancor.android.air.utils.OtherUtils;
import pl.pancor.android.air.utils.location.Location;
import pl.pancor.android.air.utils.location.LocationService;


import pl.pancor.android.air.utils.location.LocationUtils;
import retrofit2.Retrofit;

@FragmentScope
public class NearestStationPresenter implements NearestStation.Presenter,
        Location.Receiver {

    private static final String TAG = NearestStationPresenter.class.getSimpleName();
    private static String mRequestToken;

    private NearestStation.View mView;

    private Retrofit mRetrofit;

    private LocationService mLocationService;

    private RealmService realmService;

    private Disposable disposable;

    @Inject
    NearestStationPresenter(NearestStation.View view, Retrofit retrofit,
                            LocationService service, RealmService realmService){

        mView = view;
        mRetrofit = retrofit;
        mLocationService = service;
        this.realmService = realmService;
    }

    @Inject
    void setupListeners(){

        mView.setPresenter(this);
        mLocationService.setupReceiver(this);
    }

    @Override
    public void findNearestStation(@NonNull String token) {

        mView.setLoadingIndicator(true);
        mRequestToken = token;
        mLocationService.getLastKnownLocation();
    }

    @Override
    public void getStation(Double latitude, Double longitude) {

        mView.setLoadingIndicator(true);
        getNearestStation(latitude, longitude);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode) {
        mLocationService.onActivityResult(requestCode, resultCode);
    }

    @Override
    public void onStart() {
        mLocationService.onStart();
    }

    @Override
    public void onStop() {
        mLocationService.onStop();
        realmService.close();

        if (disposable != null && !disposable.isDisposed())
            disposable.dispose();
    }

    @Override
    public void lastKnownLocation(double latitude, double longitude) {

        getNearestStation(latitude, longitude);
    }

    @Override
    public void userRefusedToSendLocation() {

        mView.setLoadingIndicator(false);
        mView.userRefusedToGiveLocation();
    }

    @Override
    public void unableToObtainLocation() {

    }

    private void getNearestStation(Double lat, Double lng){

        User user = realmService.getUser();
        if (user != null) {

            Station station = realmService.getStation();

            if (LocationUtils.getDistance(lat, lng,
                    user.getLatitude(), user.getLongitude()) < 0.3 &&
                station != null &&
                OtherUtils.isTimeUpToDate(station.getUpdateTime())){

                mView.setStation(station);
                mView.setLoadingIndicator(false);
            } else {
                getDataFromServer(lat, lng);
            }
        } else {
            realmService.addUser(lat, lng);
            getDataFromServer(lat, lng);
        }
    }

    private void getDataFromServer(Double lat, Double lng){

        disposable = mRetrofit.create(NetService.class)
                .getNearestStation(mRequestToken, lat, lng)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(dataResponse -> handleData(dataResponse, lat, lng),
                        throwable ->  {throwable.printStackTrace();mView.onConnectionError();},
                        () -> mView.setLoadingIndicator(false));
    }
    private void handleData(DataResponse<Station> dataResponse, Double lat, Double lng){

        if (!dataResponse.isError()) {

            Station station = dataResponse.getData().get(0);
            station.setDistance(LocationUtils.getDistance(lat, lng,
                    station.getLatitude(), station.getLongitude()));

            realmService.addStation(station);
            mView.setStation(station);
        } else
            mView.onConnectionError();
    }
}
