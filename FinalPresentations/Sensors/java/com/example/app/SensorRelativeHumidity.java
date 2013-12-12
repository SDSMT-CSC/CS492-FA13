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
public class SensorRelativeHumidity extends Activity implements SensorEventListener {

    private SensorManager _sensorManager;
    private Sensor _relativeHumidity;
    private TextView _humidity_reading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relative_humidity);

        _sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        _relativeHumidity = _sensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY);

        _humidity_reading = (TextView) findViewById(R.id.textView_humidity_data);

        if(_relativeHumidity == null)
        {
            _humidity_reading.setText(R.string.textView_NoSensorOnDevice);
        }
        else
        {
            _humidity_reading.setText("0%");
        }
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        _humidity_reading.setText(String.valueOf(sensorEvent.values[0]) + "0%");
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        _sensorManager.registerListener(this,_relativeHumidity, _sensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        _sensorManager.unregisterListener(this);
    }
}

