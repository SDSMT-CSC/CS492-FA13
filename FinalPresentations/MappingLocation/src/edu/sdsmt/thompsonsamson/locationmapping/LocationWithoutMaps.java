package edu.sdsmt.thompsonsamson.locationmapping;

import android.app.Activity;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class LocationWithoutMaps extends Activity 
{
	private LocationManager lm;
	private LocationListener locListenD;
	public TextView tvLatitude;
	public TextView tvLongitude;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_location_without_maps);
		
		// find the TextViews
		tvLatitude = (TextView)findViewById(R.id.tvLatitude);
		tvLongitude = (TextView)findViewById(R.id.tvLongitude);
		
		// get handle for LocationManager
		lm =(LocationManager) getSystemService(Context.LOCATION_SERVICE);
		
		// connect to the GPS location service
		Criteria criteria = new Criteria();
		criteria.setAccuracy(Criteria.ACCURACY_FINE);
		criteria.setPowerRequirement(Criteria.POWER_HIGH);
		String provider = lm.getBestProvider(criteria, true);
		
		Location loc = lm.getLastKnownLocation(provider);
		
		if( loc == null )
		{
			Toast.makeText(this, "Unable to get location:", Toast.LENGTH_SHORT).show();
		}
		else
		{
			// fill in the TextViews
			tvLatitude.setText(Double.toString(loc.getLatitude()));
			tvLongitude.setText(Double.toString(loc.getLongitude()));
			
			// ask the Location Manager to send us location updates
			locListenD = new DispLocListener();
			lm.requestLocationUpdates("gps", 100L, 0.01f, locListenD);
		}
	}

	@Override
	protected void onPause() 
	{
		super.onPause();
		
		if( locListenD != null )
		{
			lm.removeUpdates(locListenD);
		}
	}
	
	private class DispLocListener implements LocationListener 
	{
		@Override
		public void onLocationChanged(Location location) 
		{
			// make some toast
			Toast.makeText(getApplicationContext(), "Location Updated", Toast.LENGTH_SHORT).show();
			
			// update TextViews
			tvLatitude.setText(Double.toString(location.getLatitude()));
			tvLongitude.setText(Double.toString(location.getLongitude()));
		}
		
		@Override
		public void onProviderDisabled(String provider) 
		{
		}
		
		@Override
		public void onProviderEnabled(String provider) 
		{
		}
		
		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) 
		{
		}
	
	}
	

}