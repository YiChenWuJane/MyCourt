package com.my_court.afinal.mycourt;

import android.content.Intent;
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
        implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener, GoogleMap.OnInfoWindowClickListener{
    private LatLng NTHU = new LatLng(24.793498, 120.991717);
    private LatLng Court_A = new LatLng(24.793820, 120.990920);
    private LatLng Court_B = new LatLng(24.796373, 120.990330);
    private GoogleMap mMap;
    private Marker Marker_Court_A;
    private Button rctbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        rctbtn = findViewById(R.id.rctbtn);
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

        CustomInfoWindowAdapter adapter = new CustomInfoWindowAdapter(MainActivity.this);
        mMap.setInfoWindowAdapter(adapter);

        Marker_Court_A = mMap.addMarker(new MarkerOptions().position(Court_A)
                .title("室外排球場").snippet("這邊有三個球場這邊有三個球場這邊有三個球場這邊有三個球場這邊有三個球場這邊有三個球場"));

        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setCompassEnabled(true);
        mMap.getUiSettings().setMapToolbarEnabled(true);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(NTHU));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(16));
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        /*if (marker.equals(Marker_Court_A))
        {

        }*/
        return false;
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        if (marker.equals(Marker_Court_A))
        {
            Intent intent = new Intent(MainActivity.this, CourtAActivity.class);
            startActivity(intent);
        }
        Intent intent = new Intent(MainActivity.this, CourtAActivity.class);
        MainActivity.this.startActivity(intent);
    }
}