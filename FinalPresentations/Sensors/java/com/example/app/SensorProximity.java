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
public class SensorProximity extends Activity implements SensorEventListener {

    private SensorManager _sensorManager;
    private Sensor _proximity;
    private TextView _distance_reading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proximity);

        _sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        _proximity = _sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);

        _distance_reading = (TextView) findViewById(R.id.textView_distance_data);
        if(_proximity == null)
        {
            _distance_reading.setText(R.string.textView_NoSensorOnDevice);
        }
        else
        {
            _distance_reading.setText("0 (cm)");
        }
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

        _distance_reading.setText(String.valueOf(sensorEvent.values[0]) + " (cm)");

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        _sensorManager.registerListener(this,_proximity, _sensorManager.SENSOR_DELAY_FASTEST);
    }

    @Override
    protected void onPause() {
        super.onPause();
        _sensorManager.unregisterListener(this);
    }
}

