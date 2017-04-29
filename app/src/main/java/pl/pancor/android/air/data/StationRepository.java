package pl.pancor.android.air.data;


import javax.inject.Inject;

import pl.pancor.android.air.models.Station;
import pl.pancor.android.air.utils.OtherUtils;

public class StationRepository implements StationData {

    private RealmService realmService;

    @Inject StationRepository(RealmService realmService){

        this.realmService = realmService;
    }

    @Override
    public void getStation(Station station) {

    }

    private void getStationFromRealm(){


    }

    private void station(Station station){

        if (OtherUtils.isTimeUpToDate(station.getUpdateTime())){


        }
    }
}
