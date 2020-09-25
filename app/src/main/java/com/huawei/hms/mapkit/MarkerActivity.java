package com.huawei.hms.mapkit;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.huawei.hms.mapkit.utils.Utils;
import com.huawei.hms.maps.CameraUpdateFactory;
import com.huawei.hms.maps.HuaweiMap;
import com.huawei.hms.maps.MapView;
import com.huawei.hms.maps.OnMapReadyCallback;
import com.huawei.hms.maps.model.BitmapDescriptorFactory;
import com.huawei.hms.maps.model.LatLng;
import com.huawei.hms.maps.model.Marker;
import com.huawei.hms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class MarkerActivity extends AppCompatActivity implements OnMapReadyCallback, View.OnClickListener {

    private static final String TAG = "MarkerActivity";

    private static final LatLng SAINT_PETERSBURG = new LatLng(59.939095, 30.315868);
    private static final LatLng MOSCOW = new LatLng(55.755814, 37.617635);
    private static final LatLng SOCHI = new LatLng(43.585525, 39.723062);
    private static final LatLng START_POINT = new LatLng(54.0, 33.0);

    //HUAWEI map
    private HuaweiMap map;
    private MapView mapView;

    //List for markers
    private List<Marker> markerList = new ArrayList<>();

    private boolean isRestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marker);

        mapView = findViewById(R.id.mapView);

        isRestore = savedInstanceState != null;

        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(Utils.MAPVIEW_BUNDLE_KEY);
        }

        mapView.onCreate(mapViewBundle);
        mapView.getMapAsync(this);

        findViewById(R.id.button_add_markers).setOnClickListener(this);
        findViewById(R.id.button_remove_markers).setOnClickListener(this);
    }

    @Override
    public void onMapReady(HuaweiMap huaweiMap) {
        Log.d(TAG, "onMapReady: ");
        map = huaweiMap;

        map.setMyLocationEnabled(true); // Enable the my-location overlay.
        map.getUiSettings().setMyLocationButtonEnabled(true); // Enable the my-location icon.
        map.setMarkersClustering(true); // Enable the clustering.
        map.setInfoWindowAdapter(new CustomInfoWindowAdapter()); //Custom info window, not necessary

        addMarkerListener();
        addMapListener();

        if (!isRestore) {
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(START_POINT, 4f));
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
            case R.id.button_add_markers:
                Log.i(TAG, "onClick: add markers");
                addCites();
                break;
            case R.id.button_remove_markers:
                Log.i(TAG, "onClick: remove markers");
                for (Marker marker : markerList) marker.remove(); //map?.clear() - also can be used
                markerList.clear();
                break;
            default:
                break;
        }
    }

    /**
     * Set the listener associated with the marker
     */
    private void addMarkerListener() {

        map.setOnInfoWindowClickListener(marker -> Toast.makeText(getApplicationContext(), "onInfoWindowClickListener", Toast.LENGTH_SHORT).show());
        map.setOnInfoWindowCloseListener(marker -> Toast.makeText(getApplicationContext(), "onInfoWindowClose", Toast.LENGTH_SHORT).show());
        map.setOnInfoWindowLongClickListener(marker -> Toast.makeText(getApplicationContext(), "onInfoWindowLongClick", Toast.LENGTH_SHORT).show());
        map.setOnMarkerDragListener(new HuaweiMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDragStart(Marker marker) {
                Log.i(TAG, "onMarkerDragStart: " + marker.getId());
            }

            @Override
            public void onMarkerDrag(Marker marker) {
                Log.i(TAG, "onMarkerDrag: " + marker.getId());
            }

            @Override
            public void onMarkerDragEnd(Marker marker) {
                Log.i(TAG, "onMarkerDragEnd: " + marker.getId());
            }
        });
    }

    /**
     * Set the listener associated with the map
     */
    private void addMapListener() {
        map.setOnMapLongClickListener(latLng -> {
            addAddressMarker(latLng);
            Log.i(TAG, getString(R.string.marker_list_size) + markerList.size());
        });
    }

    private void addAddressMarker(LatLng latLng) {
        Marker marker = map.addMarker(new MarkerOptions()
                .position(latLng)
                .title(getString(R.string.marker_name))
                .snippet(getString(R.string.snippet))
                .draggable(true)
                .clusterable(true));
        markerList.add(marker);
    }

    private void addCites() {
            Marker marker = map.addMarker(new MarkerOptions()
                    .position(SAINT_PETERSBURG)
                    .title(getString(R.string.SPB))
                    .snippet(getString(R.string.russia))
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA))
                    .clusterable(true));
            markerList.add(marker);
            marker = map.addMarker(new MarkerOptions()
                    .position(MOSCOW)
                    .title(getString(R.string.moscow))
                    .snippet(getString(R.string.russia))
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE))
                    .clusterable(true));
            markerList.add(marker);
            marker = map.addMarker(new MarkerOptions()
                    .position(SOCHI)
                    .title(getString(R.string.sochi))
                    .snippet(getString(R.string.russia))
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                    .clusterable(true));
            markerList.add(marker);
        }

    /**
     * Custom Info Window
     */
     class CustomInfoWindowAdapter implements HuaweiMap.InfoWindowAdapter {
        private View windowView = getLayoutInflater().inflate(R.layout.custom_info_window, null);
        private View contentsView = getLayoutInflater().inflate(R.layout.custom_info_contents, null);

        private void render(Marker marker, View view) {
            setMarkerBadge(marker, view);
            setMarkerTitle(marker, view);
            setMarkerSnippet(marker, view);
        }

        private void setMarkerBadge(Marker marker, View view) {
            //TODO
            ((ImageView) view.findViewById(R.id.imgv_badge)).setImageResource(R.drawable.ic_baseline_map_24);
        }

        private void setMarkerTitle(Marker marker, View view) {
            String markerTitle = marker.getTitle();

            if (markerTitle == null) {
                ((TextView) view.findViewById(R.id.txtv_title)).setText("");
            } else {
                SpannableString titleText = new SpannableString(markerTitle);
                titleText.setSpan(new ForegroundColorSpan(Color.BLUE), 0, titleText.length(), 0);
                ((TextView) view.findViewById(R.id.txtv_title)).setText(titleText);
            }
        }

        private void setMarkerSnippet(Marker marker, View view) {
            String markerSnippet = marker.getSnippet();
            if (markerSnippet != null && markerSnippet.length() != 0) {
                SpannableString snippetText = new SpannableString(markerSnippet);
                snippetText.setSpan(new ForegroundColorSpan(Color.RED), 0, markerSnippet.length(), 0);
                ((TextView) view.findViewById(R.id.txtv_snippet)).setText(snippetText);
            } else {
                ((TextView) view.findViewById(R.id.txtv_snippet)).setText("");
            }
        }

        @Override
        public View getInfoContents(Marker marker) {
            Log.d(TAG, "getInfoContents");
            render(marker, windowView);
            return contentsView;
        }

        @Override
        public View getInfoWindow(Marker marker) {
            Log.d(TAG, "getInfoWindow");
            render(marker, windowView);
            return windowView;
        }
    }
}

