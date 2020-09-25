package com.huawei.hms.mapkit;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.icu.text.Edits;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.huawei.hms.mapkit.direction.viewmodel.DirectionViewModel;
import com.huawei.hms.mapkit.utils.Utils;
import com.huawei.hms.maps.CameraUpdateFactory;
import com.huawei.hms.maps.HuaweiMap;
import com.huawei.hms.maps.MapView;
import com.huawei.hms.maps.OnMapReadyCallback;
import com.huawei.hms.maps.model.BitmapDescriptorFactory;
import com.huawei.hms.maps.model.LatLng;
import com.huawei.hms.maps.model.Marker;
import com.huawei.hms.maps.model.MarkerOptions;
import com.huawei.hms.maps.model.Polyline;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class BigMarkerActivity extends AppCompatActivity implements OnMapReadyCallback, View.OnClickListener  {

    private static final String TAG = "BigMarkerActivity";
    private static final String MAPVIEW_BUNDLE_KEY = "MapViewBundleKey";
    private static final LatLng START_POINT = new LatLng(59.939095, 30.315868);

    //HUAWEI map
    private HuaweiMap map;
    private MapView mapView;

    //Map for markers
    private Map<Long, Marker> markers = new ConcurrentHashMap<>();

    private boolean isRestore = false;

    private Button buttonClusterization;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_big_marker);

        mapView = findViewById(R.id.mapView);
        buttonClusterization = findViewById(R.id.button_clusterization);

        isRestore = savedInstanceState != null;

        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(Utils.MAPVIEW_BUNDLE_KEY);
        }

        mapView.onCreate(mapViewBundle);
        mapView.getMapAsync(this);

        findViewById(R.id.button_generate_markers).setOnClickListener(this);
        findViewById(R.id.button_remove_50_markers).setOnClickListener(this);
        findViewById(R.id.button_remove_markers_big).setOnClickListener(this);
        findViewById(R.id.button_clean_map).setOnClickListener(this);
        buttonClusterization.setOnClickListener(this);
        findViewById(R.id.button_hide_markers).setOnClickListener(this);
        findViewById(R.id.button_show_markers).setOnClickListener(this);
    }

    @Override
    public void onMapReady(HuaweiMap huaweiMap) {
        Log.d(TAG, "onMapReady: ");
        map = huaweiMap;

        map.setMyLocationEnabled(true);
        map.getUiSettings().setMyLocationButtonEnabled(true);
        map.setMarkersClustering(false);

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
            case R.id.button_generate_markers:
                Log.i(TAG, "onClick: generate markers");
                Log.d(TAG, String.valueOf(markers.size()));
                if (markers.isEmpty()) {
                    addMarkers();
                }
                break;
            case R.id.button_remove_markers_big:
                Log.i(TAG, "onClick: remove all markers");
                removeAllMarkers();
                break;
            case R.id.button_remove_50_markers:
                Log.i(TAG, "onClick: remove 50 markers");
                remove50Markers();
                break;
            case R.id.button_clean_map:
                Log.i(TAG, "onClick: clean map");
                clearMap();
                break;
            case R.id.button_clusterization:
                Log.i(TAG, "onClick: clusterization");
                clusterizationOffOn();
                break;
            case R.id.button_hide_markers:
                Log.i(TAG, "onClick: hide markers");
                hideMarkers();
                break;
            case R.id.button_show_markers:
                Log.i(TAG, "onClick: show markers");
                showMarkers();
                break;
            default:
                break;
        }
    }

    private void clusterizationOffOn() {
        if (buttonClusterization.getText().toString().endsWith("off")) {
            map.setMarkersClustering(true);
            buttonClusterization.setTextColor(Color.GREEN);
            buttonClusterization.setText(getResources().getString(R.string.clusterization_on));
        } else if (buttonClusterization.getText().toString().endsWith("on")) {
            map.setMarkersClustering(false);
            buttonClusterization.setTextColor(Color.parseColor("#FFAAAAAA"));
            buttonClusterization.setText(getResources().getString(R.string.clusterization_off));
        }
    }

    private void addMarkers() {
        Random random = new Random();
        for (int i = 0 ; i < 300; i++) {
            int randomX = random.nextInt(100);
            int randomY = random.nextInt(100);
            double x = Double.parseDouble("59." + randomX);
            double y = Double.parseDouble("30." + randomY);

            Marker marker = map.addMarker(
                    new MarkerOptions()
                            .position(new LatLng(x, y))
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA))
                            .clusterable(true));
            markers.put((long) i, marker);
        }
    }

    private void remove50Markers() {
        Set<Map.Entry<Long, Marker>> entrySet = markers.entrySet();
        Iterator<Map.Entry<Long, Marker>> markersIterator = entrySet.iterator();
        int i = 0;
        while (markersIterator.hasNext()) {
            if (i > 50) {
                break;
            }
            Map.Entry<Long, Marker> marker = markersIterator.next();
            marker.getValue().remove();
            markers.remove(marker.getKey());
            i++;
        }
    }

    private void removeAllMarkers() {
        Set<Map.Entry<Long, Marker>> entrySet = markers.entrySet();
        for (Map.Entry<Long, Marker> marker : entrySet) {
            marker.getValue().remove();
            markers.remove(marker.getKey());
        }
    }

    private void hideMarkers() {
        Set<Map.Entry<Long, Marker>> entrySet = markers.entrySet();
        for (Map.Entry<Long, Marker> marker : entrySet) {
            marker.getValue().setVisible(false);
        }
    }

    private void showMarkers() {
        Set<Map.Entry<Long, Marker>> entrySet = markers.entrySet();
        for (Map.Entry<Long, Marker> marker : entrySet) {
            marker.getValue().setVisible(true);
        }
    }

    private void clearMap() {
        map.clear();
        markers.clear();
        buttonClusterization.setTextColor(Color.parseColor("#FFAAAAAA"));
        buttonClusterization.setText(getResources().getString(R.string.clusterization_off));
    }

}