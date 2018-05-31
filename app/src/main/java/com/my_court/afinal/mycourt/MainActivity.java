package com.my_court.afinal.mycourt;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * An activity that displays a Google map with a marker (pin) to indicate a particular location.
 */
public class MainActivity extends AppCompatActivity
        implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {
    private LatLng NTHU = new LatLng(24.793498, 120.991717);
    private LatLng Court_A = new LatLng(24.793820, 120.990920);
    private GoogleMap mMap;
    private Marker Marker_Court_A;
    private Button rctbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Retrieve the content view that renders the map.
        setContentView(R.layout.activity_maps);
        // Get the SupportMapFragment and request notification
        // when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        rctbtn = (Button) findViewById(R.id.rctbtn);
        rctbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMap.moveCamera(CameraUpdateFactory.newLatLng(NTHU));
                mMap.animateCamera(CameraUpdateFactory.zoomTo(16));
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        // Add a marker in Sydney, Australia,
        // and move the map's camera to the same location.
        mMap = googleMap;
        Marker_Court_A = mMap.addMarker(new MarkerOptions().position(Court_A)
                .title("室外排球場").snippet("這邊有三個球場"));

        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setCompassEnabled(true);       // 左上角的指南針，要兩指旋轉才會出現
        mMap.getUiSettings().setMapToolbarEnabled(true);    // 右下角的導覽及開啟 Google Map功能
        mMap.moveCamera(CameraUpdateFactory.newLatLng(NTHU));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(16));
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        if (marker.equals(Marker_Court_A))
        {
            //handle click here
        }
        return false;
    }
}