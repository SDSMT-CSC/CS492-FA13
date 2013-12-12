package com.example.app;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    private SensorManager _sensorManager;
    private Sensor _sensor;
    private TextView _textViewList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // get action bar
        ActionBar actionBar = getActionBar();
        actionBar.show();

        //get the system sensor information for this device
        _sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        _textViewList = (TextView) findViewById(R.id.textView_deviceList);
        _textViewList.setText("");

        //get a list of all the sensors on the device
        List<Sensor> sensorList = _sensorManager.getSensorList(Sensor.TYPE_ALL);

        if(sensorList.size() == 0)
        {
            _textViewList.setText(R.string.textView_NoSensorOnDevice);
        }
        else
        {
            List<String> list = new ArrayList<String>();

            //set the list of sensors on the device by their name and power used while active
            for (Sensor sensor : sensorList)
            {
                list.add( sensor.getName());

                _textViewList.append("\n" + sensor.getName());
                _textViewList.append(": " + sensor.getPower() + " (mA)");
            }
        }




    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here.
        int id = item.getItemId();

        switch (id)
        {
            case R.id.action_accelerometer:
                startActivity( new Intent(this, SensorAccelerometer.class));
                break;
            case R.id.action_linear_acceleration:
                startActivity( new Intent(this, SensorLinearAcceleration.class));
                break;
            case R.id.action_ambient_temp:
                startActivity( new Intent(this, SensorAmbientTemperature.class));
                break;
            case R.id.action_gravity:
                startActivity( new Intent(this, SensorGravity.class));
                break;
            case R.id.action_gyroscope:
                startActivity( new Intent(this, SensorGyroscope.class));
                break;
            case R.id.action_light:
                startActivity( new Intent(this, SensorLight.class));
                break;
            case R.id.action_magnetic_field:
                startActivity( new Intent(this, SensorMagneticField.class));
                break;
            case R.id.action_pressure:
                startActivity( new Intent(this, SensorPressure.class));
                break;
            case R.id.action_proximity:
                startActivity( new Intent(this, SensorProximity.class));
                break;
            case R.id.action_relative_humidity:
                startActivity( new Intent(this, SensorRelativeHumidity.class));
                break;
            case R.id.action_rotation_vector:
                startActivity( new Intent(this, SensorRotationVector.class));
                break;
            default:
                return super.onOptionsItemSelected(item);
        }

        return true;
    }





}
