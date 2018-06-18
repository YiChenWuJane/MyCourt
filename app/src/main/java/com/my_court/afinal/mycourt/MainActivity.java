package com.my_court.afinal.mycourt;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import helpers.MqttHelper;

/**
 * An activity that displays a Google map with a marker (pin) to indicate a particular location.
 */
public class MainActivity extends AppCompatActivity
        implements OnMapReadyCallback, GoogleMap.OnInfoWindowClickListener {
    private LatLng NTHU = new LatLng(24.795726, 120.990707);
    private LatLng Court_A = new LatLng(24.793820, 120.990920);
    private LatLng Court_B = new LatLng(24.796373, 120.990330);
    private LatLng Court_New = new LatLng(24.793492, 120.991577);
    private LatLng Court_Friend = new LatLng(24.795596, 120.989865);
    private GoogleMap mMap;
    private Marker Marker_Court_A;
    private Marker Marker_Court_B;
    private Marker Marker_Court_New;
    private Marker Marker_Court_Friend;
    private Button rctbtn;
    private MqttHelper mqttHelper;
    private String message, m1, m2, m3;

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
        try {
            startMqtt();
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    private void startMqtt() throws MqttException {
        mqttHelper = new MqttHelper(getApplicationContext());
        mqttHelper.setCallback(new MqttCallbackExtended() {
            @Override
            public void connectComplete(boolean b, String s) {
                Log.w("Debug", "connection in main");
            }

            @Override
            public void connectionLost(Throwable throwable) {


            }

            @Override
            public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception {
                Log.w("Debug", mqttMessage.toString()+"message arrive in main");
                message = mqttMessage.toString();
                if(message.indexOf("C1H{")!=-1 && message.indexOf("C1E}")!=-1){
                    m1 = message.substring(message.indexOf("C1H{") + 4, message.indexOf("C1E}"));
                    Marker_Court_A.setSnippet(m1);
                    //tv_court_a_d1.setText(m1);
                }
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        // Add a marker in Sydney, Australia,
        // and move the map's camera to the same location.
        mMap = googleMap;
        Log.w("Debug", "map ready msg");
        CustomInfoWindowAdapter adapter = new CustomInfoWindowAdapter(MainActivity.this);
        mMap.setInfoWindowAdapter(adapter);

        Marker_Court_A = mMap.addMarker(new MarkerOptions().position(Court_A)
                .title("室外排球場").snippet(getString(R.string.no_data)));
        Marker_Court_B = mMap.addMarker(new MarkerOptions().position(Court_B)
                .title("室內網球場").snippet(getString(R.string.ComSon)));
        Marker_Court_New = mMap.addMarker(new MarkerOptions().position(Court_New)
                .title("新體").snippet(getString(R.string.ComSon)));
        Marker_Court_Friend = mMap.addMarker(new MarkerOptions().position(Court_Friend)
                .title("校體").snippet(getString(R.string.ComSon)));
        mMap.setOnInfoWindowClickListener(this);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setCompassEnabled(true);
        mMap.getUiSettings().setMapToolbarEnabled(true);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(NTHU));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(16));
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        if (marker.equals(Marker_Court_A)) {
            Intent intent = new Intent(MainActivity.this, CourtInfoa.class);
            MainActivity.this.startActivity(intent);
        }
    }
}