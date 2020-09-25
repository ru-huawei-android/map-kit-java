package com.huawei.hms.mapkit.direction.utils;

import android.util.Log;

import com.huawei.hms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class Request {

    private static final String TAG = "Request";

    public static RequestBody requestBody(LatLng origin, LatLng destination) {

        JSONObject json = new JSONObject();
        JSONObject originJson = new JSONObject();
        JSONObject destinationJson = new JSONObject();

        MediaType type = MediaType.parse("application/json; charset=utf-8");

        try {
            originJson.put("lat", origin.latitude);
            originJson.put("lng", origin.longitude);

            destinationJson.put("lat", destination.latitude);
            destinationJson.put("lng", destination.longitude);

            json.put("origin", originJson);
            json.put("destination", destinationJson);
        } catch (JSONException e) {
            Log.e(TAG, Objects.requireNonNull(e.getMessage()));
        }

        Log.d(TAG, json.toString());
        return RequestBody.create(type, json.toString());
    }

    public static RequestBody requestBody(LatLng origin, LatLng destination, boolean alternatives, int[] avoid) {

        JSONObject json = new JSONObject();
        JSONObject originJson = new JSONObject();
        JSONObject destinationJson = new JSONObject();
        JSONArray avoidJson = new JSONArray();

        MediaType type = MediaType.parse("application/json; charset=utf-8");

        try {
            originJson.put("lat", origin.latitude);
            originJson.put("lng", origin.longitude);

            destinationJson.put("lat", destination.latitude);
            destinationJson.put("lng", destination.longitude);

            for (int item : avoid) {
                if (item == 1 || item == 2) avoidJson.put(item);
            }

            json.put("origin", originJson);
            json.put("destination", destinationJson);
            json.put("alternatives", alternatives);
            json.put("avoid", avoidJson);
        } catch (JSONException e) {
            Log.e(TAG, Objects.requireNonNull(e.getMessage()));
        }

        Log.d(TAG, json.toString());
        return RequestBody.create(type, json.toString());
    }
}
