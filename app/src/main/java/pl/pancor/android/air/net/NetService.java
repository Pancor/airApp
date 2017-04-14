package pl.pancor.android.air.net;

import pl.pancor.android.air.models.DataResponse;
import pl.pancor.android.air.models.Station;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface NetService {

    @GET("getNearestStation")
    Call<DataResponse<Station>> getNearestStation(@Header("Authorization") String apiKey,
                                         @Query("lat") Double latitude,
                                         @Query("lng") Double longitude);
}

