package pl.pancor.android.air.net;

import javax.inject.Singleton;

import dagger.Component;
import pl.pancor.android.air.base.AppModule;
import retrofit2.Retrofit;

@Singleton
@Component(modules = {AppModule.class, NetModule.class})
public interface NetComponent {

    Retrofit getRetrofit();
}
