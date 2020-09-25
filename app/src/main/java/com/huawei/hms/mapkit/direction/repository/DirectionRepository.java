package com.huawei.hms.mapkit.direction.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.huawei.hms.mapkit.direction.model.Direction;
import com.huawei.hms.mapkit.direction.model.Polyline;
import com.huawei.hms.mapkit.direction.model.Route;
import com.huawei.hms.mapkit.direction.model.Step;
import com.huawei.hms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class DirectionRepository {

    private static final String TAG = "HuaweiDetector";
    private static final String BASE_URL = "https://mapapi.cloud.huawei.com/mapApi/v1/routeService/";
    private static final String API_KEY = "CgB6e3x9HQFRJOUV0KDIQLrNmQcQC7M8rxo5wvvSa2GozzXB3p9NbwPurIsys8M1jYYyB762er5ZmRdwJb+dbaYP";


    private DirectionServiceApi api = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(DirectionServiceApi.class);

    public LiveData<Direction> getDrivingDirection(RequestBody request) {
        final MutableLiveData<Direction> data = new MutableLiveData();
        api.getDrivingDirection(request, API_KEY)
                .enqueue(new Callback<Direction>() {
                    @Override
                    public void onResponse(Call<Direction> call, Response<Direction> response) {
                        assert response.body() != null;
                        Log.d(TAG, "onResponse: " + response.body().toString());
                        Log.d(TAG, call.request().url().toString());
                        if (response.isSuccessful()) data.setValue(response.body());
                    }

                    @Override
                    public void onFailure(Call<Direction> call, Throwable t) {
                        Log.e(TAG, "onFailure: " + t);
                        data.setValue(null);
                    }


                });
        return data;
    }

    public LiveData<Direction> getWalkingDirection(RequestBody request) {
        final MutableLiveData<Direction> data = new MutableLiveData();
        api.getWalkingDirection(request, API_KEY)
                .enqueue(new Callback<Direction>() {
                    @Override
                    public void onResponse(Call<Direction> call, Response<Direction> response) {
                        Log.d(TAG, "onResponse: " + Objects.requireNonNull(response.body()).toString());
                        if (response.isSuccessful()) data.setValue(response.body());
                    }

                    @Override
                    public void onFailure(Call<Direction> call, Throwable t) {
                        Log.e(TAG, "onFailure: " + t);
                        data.setValue(null);
                    }
                });
        return data;
    }

    public LiveData<Direction> getBicyclingDirection(RequestBody request) {
        final MutableLiveData<Direction> data = new MutableLiveData();
        api.getBicyclingDirection(request, API_KEY)
                .enqueue(new Callback<Direction>() {
                    @Override
                    public void onResponse(Call<Direction> call, Response<Direction> response) {
                        Log.d(TAG, "onResponse: " + Objects.requireNonNull(response.body()).toString());
                        if (response.isSuccessful()) data.setValue(response.body());
                    }

                    @Override
                    public void onFailure(Call<Direction> call, Throwable t) {
                        Log.e(TAG, "onFailure: " + t);
                        data.setValue(null);
                    }
        });
        return data;
    }

    public LiveData<Map<Integer, List<LatLng>>> getPolyline(Direction direction) {
        MutableLiveData<Map<Integer, List<LatLng>>> routes = new MutableLiveData<>();
        Map<Integer, List<LatLng>> result = new HashMap();
        int counter = 0;

        if (direction.getReturnDesc().equals("OK")) {
            for (Route route : direction.getRoutes()) {
                List<LatLng> polylinePoints = new ArrayList();
                List<Step> steps = route.getPaths().get(0).getSteps();
                for (Step step : steps) {
                    for (Polyline polylineItem : step.getPolyline()) {
                        polylinePoints.add(new LatLng(polylineItem.getLat(), polylineItem.getLng()));
                    }
                }
                result.put(counter++, polylinePoints);
            }
        }
        routes.setValue(result);
        return routes;
    }
}
