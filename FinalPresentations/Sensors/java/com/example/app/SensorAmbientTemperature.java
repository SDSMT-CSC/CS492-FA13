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
public class SensorAmbientTemperature extends Activity implements SensorEventListener {

    private SensorManager _sensorManager;
    private Sensor _ambientTemperature;
    private TextView _tempc;
    private TextView _tempf;
    private TextView _tempk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ambient_temp);

        //get temperature sensor information
        _sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        _ambientTemperature = _sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);

        _tempc = (TextView)findViewById(R.id.textView_Temp_C);
        _tempf = (TextView)findViewById(R.id.textView_Temp_F);
        _tempk = (TextView)findViewById(R.id.textView_Temp_K);

        if(_ambientTemperature == null)
        {
            _tempc.setText(R.string.textView_NoSensorOnDevice);
            _tempf.setText(R.string.textView_NoSensorOnDevice);
            _tempk.setText(R.string.textView_NoSensorOnDevice);
        }
        else
        {
            _tempc.setText("0°C");
            _tempf.setText("0°F");
            _tempk.setText("0°K");
        }
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

        float temperatureC = sensorEvent.values[0];
        _tempc.setText(String.format("%.5f",temperatureC) + " °C");

        float temperatureF = (temperatureC * 9/5) + 32;
        _tempf.setText(String.format("%.5f",temperatureF) + " °F");

        double temperatureK = temperatureC + 273.15;
        _tempk.setText(String.format("%.5f",temperatureK) + " °K");
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        _sensorManager.registerListener(this,_ambientTemperature, _sensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        _sensorManager.unregisterListener(this);
    }
}
