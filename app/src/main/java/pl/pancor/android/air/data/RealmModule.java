package pl.pancor.android.air.data;


import dagger.Module;
import dagger.Provides;
import io.realm.Realm;

@Module
public class RealmModule {

    @Provides
    Realm provideRealm(){

        return Realm.getDefaultInstance();
    }

    @Provides
    RealmService provideRealmService(Realm realm){

        return new RealmService(realm);
    }
}
