package pl.pancor.android.air.nearest_station;


import dagger.Module;
import dagger.Provides;

@Module
public class NearestStationModule {

    private NearestStation.View mView;

    public NearestStationModule(NearestStation.View view){

        mView = view;
    }

    @Provides
    NearestStation.View provideView(){

        return mView;
    }
}
