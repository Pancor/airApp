package pl.pancor.android.air.base;


import android.app.Application;

import com.squareup.leakcanary.LeakCanary;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import pl.pancor.android.air.net.DaggerNetComponent;
import pl.pancor.android.air.net.NetComponent;
import pl.pancor.android.air.net.NetModule;
import pl.pancor.android.air.net.WebLinks;

public class App extends Application {

    private static final int REALM_VERSION = 0;

    private NetComponent mNetComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        if (!LeakCanary.isInAnalyzerProcess(this))
            LeakCanary.install(this);

        //TODO setupRealm();

        mNetComponent = DaggerNetComponent.builder()
                .appModule(new AppModule(this))
                .netModule(new NetModule(WebLinks.ROOT))
                .build();
    }

    public NetComponent getNetComponent(){

        return mNetComponent;
    }

    private void setupRealm(){

        RealmConfiguration conf = new RealmConfiguration.Builder()
                .name(Realm.DEFAULT_REALM_NAME)
                .schemaVersion(REALM_VERSION)
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(conf);
    }
}
