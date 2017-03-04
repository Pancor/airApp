package pl.pancor.android.air.nearest_station;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class NearestStationModule {

    private NearestStation.View mView;

    public NearestStationModule(NearestStation.View view, Retrofit retrofit){

        mView = view;
    }

    @Provides
    NearestStation.View provideView(){

        return mView;
    }
}
