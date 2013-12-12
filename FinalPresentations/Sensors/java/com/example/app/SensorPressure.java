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
public class SensorPressure extends Activity implements SensorEventListener {

    private SensorManager _sensorManager;
    private Sensor _pressure;
    private TextView _airPressure_reading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pressure);

        //get air pressure sensor information from device
        _sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        _pressure = _sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE);

        _airPressure_reading =  (TextView) findViewById(R.id.textView_airPressure_data);

        if(_pressure == null)
        {
            _airPressure_reading.setText(R.string.textView_NoSensorOnDevice);
        }
        else
        {
            _airPressure_reading.setText("0 (hPa)");
        }
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

        _airPressure_reading.setText(String.valueOf(sensorEvent.values[0]) + " (hPa)");
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        _sensorManager.registerListener(this,_pressure, _sensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        _sensorManager.unregisterListener(this);
    }
}

