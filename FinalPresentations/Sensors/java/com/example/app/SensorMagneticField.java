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
public class SensorMagneticField extends Activity implements SensorEventListener {

    private SensorManager _sensorManager;
    private Sensor _magneticField;
    private TextView _xaxis_reading;
    private TextView _yaxis_reading;
    private TextView _zaxis_reading;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_magnetic_field);

        _sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        _magneticField = _sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

        _xaxis_reading = (TextView)findViewById(R.id.textView_xaxis_mg);
        _yaxis_reading = (TextView)findViewById(R.id.textView_yaxis_mg);
        _zaxis_reading = (TextView)findViewById(R.id.textView_zaxis_mg);

        if(_magneticField == null)
        {
            _xaxis_reading.setText(R.string.textView_NoSensorOnDevice);
            _yaxis_reading.setText(R.string.textView_NoSensorOnDevice);
            _zaxis_reading.setText(R.string.textView_NoSensorOnDevice);
        }
        else
        {
            _xaxis_reading.setText("0 (μT)");
            _yaxis_reading.setText("0 (μT)");
            _zaxis_reading.setText("0 (μT)");
        }
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

        _xaxis_reading.setText(String.valueOf(sensorEvent.values[0]) + " (μT)");
        _yaxis_reading.setText(String.valueOf(sensorEvent.values[1]) + " (μT)");
        _zaxis_reading.setText(String.valueOf(sensorEvent.values[2]) + " (μT)");

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        _sensorManager.registerListener(this,_magneticField, _sensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        _sensorManager.unregisterListener(this);
    }
}

