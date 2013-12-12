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
 * Created by mcrypt on 12/10/13.
 */
public class SensorLinearAcceleration extends Activity implements SensorEventListener {

    private SensorManager _sensorManager;
    private Sensor _accelerometer;

    private TextView xaxis_reading;
    private TextView yaxis_reading;
    private TextView zaxis_reading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_linear_acceleration);

        //get sensor information for accelerometer
        _sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        _accelerometer = _sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);

        xaxis_reading = (TextView)findViewById(R.id.textView_Xaxis_data);
        yaxis_reading = (TextView)findViewById(R.id.textView_Yaxis_data);
        zaxis_reading = (TextView)findViewById(R.id.textView_Zaxis_data);


        if(_accelerometer == null)
        {
            xaxis_reading.setText(R.string.textView_NoSensorOnDevice);
            yaxis_reading.setText(R.string.textView_NoSensorOnDevice);
            zaxis_reading.setText(R.string.textView_NoSensorOnDevice);
        }
        else
        {
            xaxis_reading.setText("0");
            xaxis_reading.setText("0");
            xaxis_reading.setText("0");
        }

    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

        xaxis_reading.setText(String.format("%.5f",sensorEvent.values[0]) + " (m/s^2)");
        yaxis_reading.setText(String.format("%.5f",sensorEvent.values[1]) + " (m/s^2)");
        zaxis_reading.setText(String.format("%.5f",sensorEvent.values[2]) + " (m/s^2)");

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        _sensorManager.registerListener(this,_accelerometer, _sensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        _sensorManager.unregisterListener(this);
    }
}

