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
public class SensorGyroscope extends Activity implements SensorEventListener {

    private SensorManager _sensorManager;
    private Sensor _gyroscope;
    private TextView _xaxis_data;
    private TextView _yaxis_data;
    private TextView _zaxis_data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gyroscope);

        _sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        _gyroscope = _sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);

        _xaxis_data = (TextView)findViewById(R.id.textView_xaxis_gyro);
        _yaxis_data = (TextView)findViewById(R.id.textView_yaxis_gyro);
        _zaxis_data = (TextView)findViewById(R.id.textView_zaxis_gyro);

        if(_gyroscope == null)
        {
            _xaxis_data.setText(R.string.textView_NoSensorOnDevice);
            _yaxis_data.setText(R.string.textView_NoSensorOnDevice);
            _zaxis_data.setText(R.string.textView_NoSensorOnDevice);
        }
        else
        {
            _xaxis_data.setText("0 (radians/s)");
            _yaxis_data.setText("0 (radians/s)");
            _zaxis_data.setText("0 (radians/s)");

        }
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        _xaxis_data.setText(String.format("%.4f",sensorEvent.values[0]) + " (radians/s)");
        _yaxis_data.setText(String.format("%.4f",sensorEvent.values[1] )+ " (radians/s)");
        _zaxis_data.setText(String.format("%.4f",sensorEvent.values[2]) + " (radians/s)");

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        _sensorManager.registerListener(this,_gyroscope, _sensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        _sensorManager.unregisterListener(this);
    }
}