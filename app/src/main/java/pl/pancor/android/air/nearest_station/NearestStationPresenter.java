package pl.pancor.android.air.nearest_station;


import android.support.annotation.NonNull;

import javax.inject.Inject;

import pl.pancor.android.air.base.FragmentScope;
import pl.pancor.android.air.models.station.Station;
import pl.pancor.android.air.net.NetService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

@FragmentScope
public class NearestStationPresenter implements NearestStation.Presenter {

    private static final String TAG = NearestStationPresenter.class.getSimpleName();

    private NearestStation.View mView;

    private Retrofit mRetrofit;

    @Inject
    NearestStationPresenter(NearestStation.View view, Retrofit retrofit){

        mView = view;
        mRetrofit = retrofit;
    }

    @Inject
    void setupListeners(){

        mView.setPresenter(this);
    }

    @Override
    public void findNearestStation(@NonNull String token) {

        mView.setLoadingIndicator(true);
        getNearestStation(token);
    }

    private void getNearestStation(String token){

        Call<Station> station = mRetrofit.create(NetService.class)
                .createStation(54.56792193396939, 18.38378917425871, token);//TODO: change me

        station.enqueue(new Callback<Station>() {
            @Override
            public void onResponse(Call<Station> call, Response<Station> response) {

                if (response.isSuccessful()){

                    mView.setStation(response.body().getData());
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
}
