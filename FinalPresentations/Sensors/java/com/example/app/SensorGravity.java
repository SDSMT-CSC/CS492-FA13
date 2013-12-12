package com.example.app;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Created by mcrypt on 12/8/13.
 */
public class SensorGravity extends Activity implements SensorEventListener {

    private SensorManager _sensorManager;
    private Sensor _gravity;
    private TextView xaxisg_reading;
    private TextView yaxisg_reading;
    private TextView zaxisg_reading;
    private double[] _gravityArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gravity);

        _sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        _gravity = _sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);

        xaxisg_reading = (TextView)findViewById(R.id.textView_Xaxis_G);
        yaxisg_reading = (TextView)findViewById(R.id.textView_Yaxis_G);
        zaxisg_reading = (TextView)findViewById(R.id.textView_Zaxis_G);

        _gravityArray = new double[3];
        if(_gravity == null)
        {
            xaxisg_reading.setText(R.string.textView_NoSensorOnDevice);
            yaxisg_reading.setText(R.string.textView_NoSensorOnDevice);
            zaxisg_reading.setText(R.string.textView_NoSensorOnDevice);
        }
        else
        {
            xaxisg_reading.setText("0");
            xaxisg_reading.setText("0");
            xaxisg_reading.setText("0");
        }
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

       double alpha = 0.8;

        _gravityArray[0] = alpha * _gravityArray[0] + (1 - alpha) * sensorEvent.values[0];
        _gravityArray[1] = alpha * _gravityArray[1] + (1 - alpha) * sensorEvent.values[1];
        _gravityArray[2] = alpha * _gravityArray[2] + (1 - alpha) * sensorEvent.values[2];

        xaxisg_reading.setText(String.format("%.5f",_gravityArray[0]) + " (m/s^2)");
        yaxisg_reading.setText(String.format("%.5f",_gravityArray[1]) + " (m/s^2)");
        zaxisg_reading.setText(String.format("%.5f",_gravityArray[2]) + " (m/s^2)");

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        _sensorManager.registerListener(this,_gravity, _sensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        _sensorManager.unregisterListener(this);
    }
}