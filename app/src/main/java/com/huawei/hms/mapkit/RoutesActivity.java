package com.huawei.hms.mapkit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.huawei.hms.mapkit.direction.model.Direction;
import com.huawei.hms.mapkit.direction.viewmodel.DirectionViewModel;
import com.huawei.hms.maps.CameraUpdateFactory;
import com.huawei.hms.maps.HuaweiMap;
import com.huawei.hms.maps.MapView;
import com.huawei.hms.maps.OnMapReadyCallback;
import com.huawei.hms.maps.model.BitmapDescriptor;
import com.huawei.hms.maps.model.BitmapDescriptorFactory;
import com.huawei.hms.maps.model.LatLng;
import com.huawei.hms.maps.model.Marker;
import com.huawei.hms.maps.model.MarkerOptions;
import com.huawei.hms.maps.model.Polyline;
import com.huawei.hms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;

import okhttp3.RequestBody;

import static com.huawei.hms.mapkit.direction.utils.Request.requestBody;
import static com.huawei.hms.mapkit.utils.Utils.MAPVIEW_BUNDLE_KEY;
import static com.huawei.hms.mapkit.utils.Utils.append;

public class RoutesActivity extends AppCompatActivity implements OnMapReadyCallback, View.OnClickListener  {

    private static final String TAG = "RoutesActivity";
    private static final LatLng START_POINT = new LatLng(59.939095, 30.315868);

    //HUAWEI map
    private HuaweiMap map;
    private MapView mapView;

    //List for markers
    private List<Marker> markerList = new ArrayList<>();

    private boolean isRestore = false;

    private DirectionViewModel directionViewModel;

    private List<Polyline> polylineList = new ArrayList<>();

    private int counter = 0;

    private TextView startText;
    private ProgressBar progressBar;
    private CheckBox alternatives;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routes);

        startText = findViewById(R.id.start_text);
        progressBar = findViewById(R.id.progress_bar);
        alternatives = findViewById(R.id.alternatives);
        mapView = findViewById(R.id.mapView);

        isRestore = savedInstanceState != null;

        ViewModelProvider.Factory factory = new ViewModelProvider.NewInstanceFactory();
        directionViewModel = new ViewModelProvider(this, factory).get(DirectionViewModel.class);

        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAPVIEW_BUNDLE_KEY);
        }
        mapView.onCreate(mapViewBundle);
        mapView.getMapAsync(this);

        findViewById(R.id.button_driving_route).setOnClickListener(this);
        findViewById(R.id.button_walking_route).setOnClickListener(this);
        findViewById(R.id.button_bicycling_route).setOnClickListener(this);
        findViewById(R.id.button_remove_all).setOnClickListener(this);
    }

    @Override
    public void onMapReady(HuaweiMap huaweiMap) {
        Log.d(TAG, "onMapReady: ");
        map = huaweiMap;

        addMapListener();
        addPolylineListener();

        if (!isRestore) {
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(START_POINT, 10f));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_bicycling_route:
                Log.i(TAG, "onClick: button_bicycling_route");
                if (markerList.size() == 2) {
                    removeAllPolylines();
                    makeBicyclingRoute();
                }
                break;
            case R.id.button_driving_route:
                Log.i(TAG, "onClick: button_driving_route");
                if (markerList.size() == 2) {
                    removeAllPolylines();
                    makeDrivingRoute();
                }
                break;
            case R.id.button_walking_route:
                Log.i(TAG, "onClick: button_walking_route");
                if (markerList.size() == 2) {
                    removeAllPolylines();
                    makeWalkingRoute();
                }
                break;
            case R.id.button_remove_all:
                Log.i(TAG, "onClick: button_remove_all");
                startText.setText(text(getApplicationContext(), R.string.start_text));
                startText.setTextColor(Color.RED);
                removeAllPolylines();
                removeAllMarkers();
                break;
            default:
                break;
        }
    }

    private void makeDrivingRoute() {
        int[] avoid = new int[0];
        CheckBox avoidTollRoads = findViewById(R.id.avoid_toll_roads);
        CheckBox avoidExpressways = findViewById(R.id.avoid_expressways);
        CheckBox alternatives = findViewById(R.id.alternatives);

        if (avoidTollRoads.isChecked()) avoid = append(avoid, 1);
        if (avoidExpressways.isChecked()) avoid = append(avoid, 2);

        if (directionViewModel != null) {
            RequestBody requestBody = requestBody(
                    markerList.get(0).getPosition(),
                    markerList.get(1).getPosition(),
                    (alternatives.isChecked()),
                    avoid);

            progressBar.setVisibility(View.VISIBLE);

            directionViewModel.getDrivingDirectionLiveData(requestBody)
                    .observe(RoutesActivity.this, direction -> {
                        if (direction.getReturnDesc().equals("OK")) {
                            Toast.makeText(getApplicationContext(), "Cannot make the route.", Toast.LENGTH_SHORT)
                                    .show();
                            return;
                        }
                        Log.d(TAG, direction.toString());
                        directionViewModel.getPolylineLiveData(direction)
                                .observe(RoutesActivity.this, routes -> {
                                    startText.setText(text(getApplicationContext(), R.string.last_step_text));
                                    startText.setTextColor(Color.BLUE);

                                    routes.forEach((k, v) -> {
                                        Polyline polyline = map.addPolyline(
                                                new PolylineOptions()
                                                        .addAll(v)
                                                        .clickable(true)
                                                        .color((alternatives.isChecked()) ? Color.GRAY : Color.BLUE)
                                                        .width(3f)
                                        );
                                        polylineList.add(polyline);
                                    });
                                    progressBar.setVisibility(View.GONE);
                                });
                    });
        }
    }

    private void makeWalkingRoute() {
        if (directionViewModel != null) {
            RequestBody requestBody = requestBody(
                    markerList.get(0).getPosition(),
                    markerList.get(1).getPosition());

            progressBar.setVisibility(View.VISIBLE);

            directionViewModel.getWalkingDirectionLiveData(requestBody)
                    .observe(RoutesActivity.this, direction -> {
                        if (!direction.getReturnDesc().equals("OK")) {
                            Toast.makeText(getApplicationContext(), "Cannot make the route.", Toast.LENGTH_SHORT)
                                    .show();
                            return;
                        }
                        Log.d(TAG, direction.toString());
                        directionViewModel.getPolylineLiveData(direction)
                                .observe(RoutesActivity.this, routes -> {
                                    startText.setText(text(getApplicationContext(), R.string.last_step_text));
                                    startText.setTextColor(Color.BLUE);

                                    routes.forEach((k, v) -> {
                                        Polyline polyline = map.addPolyline(
                                                new PolylineOptions()
                                                        .addAll(v)
                                                        .color(Color.GREEN)
                                                        .width(3f)
                                        );
                                        polylineList.add(polyline);
                                    });
                                    progressBar.setVisibility(View.GONE);
                                });
                    });
        }
    }

    private void makeBicyclingRoute() {
        if (directionViewModel != null) {
            RequestBody requestBody = requestBody(
                    markerList.get(0).getPosition(),
                    markerList.get(1).getPosition());

            progressBar.setVisibility(View.VISIBLE);

            directionViewModel.getBicyclingDirectionLiveData(requestBody)
                    .observe(RoutesActivity.this, direction -> {
                        if (!direction.getReturnDesc().equals("OK")) {
                            Toast.makeText(getApplicationContext(), "Cannot make the route.", Toast.LENGTH_SHORT)
                                    .show();
                            return;
                        }
                        Log.d(TAG, direction.toString());
                        directionViewModel.getPolylineLiveData(direction)
                                .observe(RoutesActivity.this, routes -> {
                                    startText.setText(text(getApplicationContext(), R.string.last_step_text));
                                    startText.setTextColor(Color.BLUE);

                                    routes.forEach((k, v) -> {
                                        Polyline polyline = map.addPolyline(
                                                new PolylineOptions()
                                                        .addAll(v)
                                                        .color(Color.RED)
                                                        .width(3f)
                                        );
                                        polylineList.add(polyline);
                                    });
                                    progressBar.setVisibility(View.GONE);
                                });
                    });
        }
    }

    /**
     * Set the listener associated with a polyline
     */
    private void addPolylineListener() {
        map.setOnPolylineClickListener(polyline -> {
            Log.d(TAG, "Polyline is clicked");
            for (Polyline item : polylineList) {
                item.setColor(Color.GRAY);
                item.setWidth(1f);
            }
            polyline.setColor(Color.BLUE);
            polyline.setWidth(3f);
        });
    }

    /**
     * Set the listener associated with the map
     */
    private void addMapListener() {

        map.setOnMapLongClickListener(latLng -> {
            if (counter == 0) {
                removeAllMarkers();
                removeAllPolylines();
                addMarker(latLng, "Start");
                startText.setText(text(getApplicationContext(), R.string.second_step_text));
                startText.setTextColor(Color.RED);
                counter++;
            } else {
                addMarker(latLng, "Finish");
                startText.setText(text(getApplicationContext(), R.string.third_step_text));
                startText.setTextColor(Color.RED);
                counter--;
            }
        });
    }

    private void addMarker(LatLng latLng, String title) {
        BitmapDescriptor[] iconColors = {
                BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED),
                BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)
        };

        Marker marker = map.addMarker(
                new MarkerOptions()
                        .position(latLng)
                        .icon(iconColors[counter])
                        .title(title));
        markerList.add(marker);
    }

    private void removeAllMarkers() {
        for (Marker marker : markerList) marker.remove();
        markerList.clear();
    }

    private void removeAllPolylines() {
        for (Polyline polyline : polylineList) polyline.remove();
        polylineList.clear();
    }

    private CharSequence text(Context context, int text) {
        return context.getText(text);
    }
}