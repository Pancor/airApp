package pl.pancor.android.air.net;


import java.util.List;

import pl.pancor.android.air.models.station.Station;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NetService {

    @GET("feed/here/")
    Call<Station> createStation(@Query("token") String token);
}
