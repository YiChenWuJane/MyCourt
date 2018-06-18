package com.my_court.afinal.mycourt;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import helpers.MqttHelper;

public class CourtInfoa extends AppCompatActivity {

    private Button btn_return;
    private TextView tv_court_a_d1;
    private MqttHelper mqttHelper;
    private String message;
    private String m1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_court_infoa);

        btn_return = findViewById(R.id.btn_rtn_a);
        btn_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mqttHelper.mqttAndroidClient.close();
                finish();
            }
        });
        tv_court_a_d1 = findViewById(R.id.CourtAData1);
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
                Log.w("Debug", "connection in a");
            }

            @Override
            public void connectionLost(Throwable throwable) {
                tv_court_a_d1.setText(R.string.connlost);
            }

            @Override
            public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception {
//                Log.w("Debug", mqttMessage.toString()+topic+"message arrive in activity");
//                if (topic.equals("CourtDataBase")) {
//                    //Log.w("Debug", "successfully goin if");
//                    message = mqttMessage.toString();
//                    if(message.indexOf("C1H{")!=-1 && message.indexOf("C1E}")!=-1){
//                        m1 = message.substring(message.indexOf("C1H{") + 4, message.indexOf("C1E}"));
//                        tv_court_a_d1.setText(m1);
//                    }
//                    else{
//                        tv_court_a_d1.setText("data error");
//                    }
//                }
            }

                    @Override
                    public void deliveryComplete (IMqttDeliveryToken iMqttDeliveryToken){

                    }
                });

            }
        }
