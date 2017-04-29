package pl.pancor.android.air.data;

import android.support.annotation.NonNull;

import javax.inject.Inject;

import io.realm.Realm;
import pl.pancor.android.air.models.Polluter;
import pl.pancor.android.air.models.Station;
import pl.pancor.android.air.models.User;

public class RealmService implements RealmInterface {

    private Realm realm;

    @Inject RealmService(Realm realm){

        this.realm = realm;
    }

    @Override
    public void close() {

        realm.close();
    }

    public void addStation(@NonNull Station station){

        this.realm.executeTransactionAsync(
                realm -> realm.copyToRealmOrUpdate(station),
                () -> {}, Throwable::printStackTrace);

    }

    public Station getStation(){

        return realm.where(Station.class).findFirst();
    }

    public User getUser(){

        return realm.where(User.class).findFirst();
    }

    public void addUser(Double lat, Double lng){

        User user = new User(1, lat, lng);

        this.realm.executeTransactionAsync(realm -> realm.copyToRealmOrUpdate(user),
                () -> {}, Throwable::printStackTrace);
    }
}
