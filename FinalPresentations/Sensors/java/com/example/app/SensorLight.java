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
public class SensorLight extends Activity implements SensorEventListener {

    private SensorManager _sensorManager;
    private Sensor _light;
    private TextView _lightReading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_light);

        //get information for light sensor from device
        _sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        _light = _sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);

        _lightReading = (TextView) findViewById(R.id.textView_lightlevel_data);
        if(_light == null)
        {
            _lightReading.setText(R.string.textView_NoSensorOnDevice);
        }
        else
        {
            _lightReading.setText("0 (lx)");
        }
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

        _lightReading.setText(String.valueOf(sensorEvent.values[0]) + " (lx)");
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {


    }

    @Override
    protected void onResume() {
        super.onResume();
        _sensorManager.registerListener(this,_light, _sensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        _sensorManager.unregisterListener(this);
    }
}
