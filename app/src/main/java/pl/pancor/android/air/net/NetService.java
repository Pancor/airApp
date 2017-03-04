package pl.pancor.android.air.net;


import java.util.List;

import pl.pancor.android.air.models.station.Station;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface NetService {

    @GET("feed/geo:{lat};{lng}/")
    Call<Station> createStation(@Path("lat") double latitude,
                                @Path("lng") double longitude,
                                @Query("token") String token);
}
