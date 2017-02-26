package pl.pancor.android.air.nearest_station;

import dagger.Component;
import pl.pancor.android.air.base.FragmentScope;
import pl.pancor.android.air.net.NetComponent;

@FragmentScope
@Component(dependencies = NetComponent.class, modules = NearestStationModule.class)
public interface NearestStationComponent {

    void inject(NearestStationActivity activity);
}
