package pl.pancor.android.air.nearest_station;

import dagger.Component;
import pl.pancor.android.air.base.FragmentScope;
import pl.pancor.android.air.data.RealmModule;
import pl.pancor.android.air.net.NetComponent;
import pl.pancor.android.air.utils.location.LocationModule;

@FragmentScope
@Component(dependencies = NetComponent.class, modules = {NearestStationModule.class,
        LocationModule.class, RealmModule.class})
public interface NearestStationComponent {

    void inject(NearestStationActivity activity);
}
