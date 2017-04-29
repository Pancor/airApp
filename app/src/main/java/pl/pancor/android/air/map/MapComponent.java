package pl.pancor.android.air.map;

import dagger.Component;
import pl.pancor.android.air.base.FragmentScope;

@FragmentScope
@Component(modules = MapModule.class)
public interface MapComponent {

    void inject(MapActivity activity);
}
