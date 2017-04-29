package pl.pancor.android.air.map;


import javax.inject.Inject;

public class MapPresenter {

    private MapContract.View view;

    @Inject MapPresenter(MapContract.View view){

        this.view = view;
    }
}
