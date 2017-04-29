package pl.pancor.android.air.base;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.rx.RealmObservableFactory;
import pl.pancor.android.air.net.DaggerNetComponent;
import pl.pancor.android.air.net.NetComponent;
import pl.pancor.android.air.net.NetModule;

public class App extends Application {

    private static final int REALM_VERSION = 0;

    private NetComponent mNetComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        if (!LeakCanary.isInAnalyzerProcess(this))
            LeakCanary.install(this);

        setupNetComponent();
        setupRealm();
    }

    public NetComponent getNetComponent(){

        return mNetComponent;
    }

    private void setupNetComponent(){

        mNetComponent = DaggerNetComponent.builder()
                .appModule(new AppModule(this))
                .netModule(new NetModule())
                .build();
    }

    private void setupRealm(){

        Realm.init(this);
        RealmConfiguration conf = new RealmConfiguration.Builder()
                .name(Realm.DEFAULT_REALM_NAME)
                .schemaVersion(REALM_VERSION)
                .deleteRealmIfMigrationNeeded()
                .rxFactory(new RealmObservableFactory())
                .build();
        Realm.setDefaultConfiguration(conf);

        //Realm.deleteRealm(conf);
    }
}
