package com.huawei.hms.mapkit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";
    private static final int REQUEST_CODE = 100;

    private static final String[] permissions = new String[] {
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.INTERNET
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (!hasPermissions(this, permissions)) {
            ActivityCompat.requestPermissions(this, permissions, REQUEST_CODE);
        }

        findViewById(R.id.button_markers).setOnClickListener(this);
        findViewById(R.id.button_routes).setOnClickListener(this);
        findViewById(R.id.button_big_marker).setOnClickListener(this);
    }

    private boolean hasPermissions(Context context, String... permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.button_markers:
                Log.i(TAG, "onClick: markers demo");
                intent = new Intent(this, MarkerActivity.class);
                startActivity(intent);
                break;
            case R.id.button_routes:
                Log.i(TAG, "onClick: routes demo");
                intent = new Intent(this, RoutesActivity.class);
                startActivity(intent);
                break;
            case R.id.button_big_marker:
                Log.i(TAG, "onClick: big marker demo");
                intent = new Intent(this, BigMarkerActivity.class);
                startActivity(intent);
            default:
                break;
        }
    }

}