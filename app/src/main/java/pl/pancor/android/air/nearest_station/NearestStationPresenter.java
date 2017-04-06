package pl.pancor.android.air.nearest_station;


import android.support.annotation.NonNull;

import java.util.List;

import javax.inject.Inject;

import pl.pancor.android.air.R;
import pl.pancor.android.air.base.FragmentScope;
import pl.pancor.android.air.models.station.City;
import pl.pancor.android.air.models.station.Station;
import pl.pancor.android.air.net.NetService;
import pl.pancor.android.air.utils.location.Location;
import pl.pancor.android.air.utils.location.LocationService;
import pl.pancor.android.air.utils.location.LocationUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

@FragmentScope
public class NearestStationPresenter implements NearestStation.Presenter, Location.Receiver {

    private static final String TAG = NearestStationPresenter.class.getSimpleName();
    private static String mRequestToken;

    private NearestStation.View mView;

    private Retrofit mRetrofit;

    private LocationService mLocationService;

    @Inject
    NearestStationPresenter(NearestStation.View view, Retrofit retrofit, LocationService service){

        mView = view;
        mRetrofit = retrofit;
        mLocationService = service;
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

    private void getNearestStation(final double lat, final double lng){

        Call<Station> station = mRetrofit.create(NetService.class)
                .createStation(lat, lng, mRequestToken);

        station.enqueue(new Callback<Station>() {
            @Override
            public void onResponse(Call<Station> call, Response<Station> response) {

                if (response.isSuccessful()){

                    City city = response.body().getData().getCity();
                    city.setDistance(LocationUtils.getDistance(lat, lng,
                            city.getGeo().get(0), city.getGeo().get(1)));
                    city.setGeo(checkCoordinates(city));

                    mView.setStation(response.body().getData(), lat, lng);
                    mView.setLoadingIndicator(false);
                } else {

                    mView.setLoadingIndicator(false);
                    mView.onConnectionError();
                }
            }

            @Override
            public void onFailure(Call<Station> call, Throwable t) {

                t.printStackTrace();
                mView.setLoadingIndicator(false);
                mView.onConnectionError();
            }
        });
    }

    /**
     * Coordinates are insert in List<Double>. From time to time latitude and longitude are mixed.
     * This function check that, if first item is smaller than second, then they are inverted.
     * This prevent to calculation of wrong distance in Poland. Check {@link LocationUtils} to see,
     * how distance is calculated.
     * @param city object, that hold coordinates of weather station
     * @return List<Double> with coordinates
     */
    private List<Double> checkCoordinates(City city){

        List<Double> geo = city.getGeo();

        if (geo.get(0) < geo.get(1)){

            Double lat = geo.get(1);
            geo.set(1, geo.get(0));
            geo.set(0, lat);
            return geo;
        }
        return geo;
    }
}
