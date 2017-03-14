package pl.pancor.android.air.utils.location;


import android.app.Activity;

import dagger.Module;
import dagger.Provides;

@Module
public class LocationModule {

    private Activity mActivity;

    public LocationModule(Activity activity){

        mActivity = activity;
    }

    @Provides
    LocationService provideLocationService(){

        return new LocationService(mActivity);
    }
}
