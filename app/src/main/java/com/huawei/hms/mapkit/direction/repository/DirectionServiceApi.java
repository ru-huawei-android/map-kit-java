package com.huawei.hms.mapkit.direction.repository;

import com.huawei.hms.mapkit.direction.model.Direction;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface DirectionServiceApi {

    @POST("driving")
    Call<Direction> getDrivingDirection(@Body RequestBody body, @Query("key") String apiKey);

    @POST("bicycling")
    Call<Direction> getBicyclingDirection(@Body RequestBody body, @Query("key") String apiKey);

    @POST("walking")
    Call<Direction> getWalkingDirection(@Body RequestBody body, @Query("key") String apiKey);
}
