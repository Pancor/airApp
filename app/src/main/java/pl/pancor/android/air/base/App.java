package pl.pancor.android.air.base;


import android.app.Application;

import com.squareup.leakcanary.LeakCanary;

import pl.pancor.android.air.net.DaggerNetComponent;
import pl.pancor.android.air.net.NetComponent;
import pl.pancor.android.air.net.NetModule;
import pl.pancor.android.air.net.WebLinks;

public class App extends Application {

    private NetComponent mNetComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        mNetComponent = DaggerNetComponent.builder()
                .appModule(new AppModule(this))
                .netModule(new NetModule(WebLinks.ROOT))
                .build();

        if (!LeakCanary.isInAnalyzerProcess(this))
            LeakCanary.install(this);
    }

    public NetComponent getNetComponent(){

        return mNetComponent;
    }
}
