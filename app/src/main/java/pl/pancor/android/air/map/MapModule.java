package pl.pancor.android.air.map;

import dagger.Module;
import dagger.Provides;

@Module
public class MapModule {

    private MapContract.View view;

    public MapModule(MapContract.View view){

        this.view = view;
    }

    @Provides
    MapContract.View providesView(){

        return view;
    }
}
