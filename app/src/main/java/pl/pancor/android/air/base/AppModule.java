package pl.pancor.android.air.base;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    private App mApp;

    public AppModule(App app){

        mApp = app;
    }

    @Provides
    @Singleton App provideApp(){

        return mApp;
    }
}
