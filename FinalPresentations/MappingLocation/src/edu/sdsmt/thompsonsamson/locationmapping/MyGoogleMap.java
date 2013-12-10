package edu.sdsmt.thompsonsamson.locationmapping;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class MyGoogleMap extends Activity 
{
	// some static locations
	private static final LatLng sdsmtLocation = new LatLng(44.0748167, -103.2058167);
	private static final LatLng schoolLocation = new LatLng(44.075067, -103.207017);
	private static final LatLng workLocation = new LatLng(44.07263, -103.20526);
	private static final LatLng beerLocation = new LatLng(44.08003, -103.22863);

	// map and location member
	private GoogleMap googleMap;
	private MapFragment mapFragment;
	
	// marker and icon members
	private int locIcon, schoolIcon, workIcon, beerIcon;
	private Marker schoolMarker, workMarker, beerMarker;
	
	// map elements
	private Polygon polygon1, polygon2;
	private Circle circle;
	private Polyline polyline;
	
	// camera settings
	private CameraPosition camOriginal, camIsometric;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_map);
        
        // initialize icon drawables
        locIcon = R.drawable.bluedot;
        schoolIcon = R.drawable.university;
        workIcon = R.drawable.workoffice;
        beerIcon = R.drawable.beergarden;
        
        // initialize the map
        if(googleMap == null)
        {	
        	// get the map fragment and the map object
        	mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        	googleMap = mapFragment.getMap();
        	
        	// make sure the map exists before using
        	if(googleMap != null)
			{
        		// setup the map objects
                setupObjects();
                		
                // set the location
        		googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(camOriginal));
                
                // click listener
                googleMap.setOnMapClickListener(new OnMapClickListener()
                {
					@Override
					public void onMapClick(LatLng loc) 
					{
						updateMapFromClick(loc);
					}
                });
                
			}
		}
    }
    
	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.google_map_menu, menu);
	    return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) 
	{
		// handle menu item clicks
		switch (item.getItemId()) 
		{
		    case R.id.animate:
		    	toggleCamera();
		        return true;
		    case R.id.markers:
				toggleMarkers();
		        return true;
		    case R.id.polygon:
				togglePolygon();
		        return true;
		    case R.id.circle:
		    	toggleCircle();
		        return true;
		    case R.id.path:
		    	togglePath();
		        return true;
		    default:
		        return super.onOptionsItemSelected(item);
		}
	}

	private void setupObjects()
	{
		// setup the location marker and add it to the map
		googleMap.addMarker(new MarkerOptions()
	    	.position(sdsmtLocation)
	    	.anchor((float)0.5,(float)0.5)
	    	.icon(BitmapDescriptorFactory.fromResource(locIcon)));
		
		// setup the school marker and add it to the map
		schoolMarker = googleMap.addMarker(new MarkerOptions()
	    	.position(schoolLocation)
	    	.title("McLaury Building")
	    	.icon(BitmapDescriptorFactory.fromResource(schoolIcon))
	    	.snippet("Mobile Development is the best!")
	    	.visible(false));

		// setup the work marker and add it to the map
		workMarker = googleMap.addMarker(new MarkerOptions()
	    	.position(workLocation)
	    	.title("Black Hills Business Center")
	    	.icon(BitmapDescriptorFactory.fromResource(workIcon))
	    	.snippet("I work at CHR Solutions.")
	    	.visible(false));

		// setup the indy marker and add it to the map
		beerMarker = googleMap.addMarker(new MarkerOptions()
	    	.position(beerLocation)
	    	.title("Independent Ale House")
	    	.icon(BitmapDescriptorFactory.fromResource(beerIcon))
	    	.snippet("Ok, Brian's \"other\" office!")
	    	.visible(false));
		
		// define the circle and add it to the map
		circle = googleMap.addCircle(new CircleOptions()
			.fillColor(Color.parseColor("#400000FF"))
			.strokeColor(Color.parseColor("#BE0000FF"))
			.strokeWidth((float) 2.0)
			.center(sdsmtLocation)
			.radius(100)
			.visible(false));
		
		// define the polygon and add it to the map
		polygon1 = googleMap.addPolygon(new PolygonOptions()
			.add(new LatLng(44.07615744242147, -103.20732861757278),
				 new LatLng(44.07583756928344, -103.2060518860817),
				 new LatLng(44.075434834982005, -103.2062503695488),
				 new LatLng(44.075704609458285, -103.20736885070801),
				 new LatLng(44.07591464730614, -103.20743322372437))
			.strokeWidth((float) 1.0)
			.strokeColor(Color.parseColor("#BEFF0000"))
			.fillColor(Color.parseColor("#40FF0000"))
			.visible(false));
		
		polygon2 = googleMap.addPolygon(new PolygonOptions()
			.add(new LatLng(44.075434834982005, -103.2062503695488),
				 new LatLng(44.075704609458285, -103.20736885070801), 
				 new LatLng(44.0755167, -103.2072833), 
				 new LatLng(44.0753000, -103.2063500))
			.strokeWidth((float) 1.0)
			.strokeColor(Color.parseColor("#BE0000FF"))
			.fillColor(Color.parseColor("#400000FF"))
			.visible(false));
		
		// define the path and add it to the map
		polyline = googleMap.addPolyline(new PolylineOptions()
			.color(Color.parseColor("#400000FF"))
        	.add(new LatLng(44.07626, -103.2073),
        		 new LatLng(44.076616, -103.20715),
        		 new LatLng(44.07803, -103.2104),
        		 new LatLng(44.079, -103.21425),
        		 new LatLng(44.081683, -103.230716),
        		 new LatLng(44.080616, -103.23103),
        		 new LatLng(44.080216, -103.22856))
        	.visible(false));
		
		// setup the original camera position
		camOriginal = new CameraPosition.Builder()
			.target(sdsmtLocation)
			.zoom(16)
			.tilt(0)
			.bearing(0)
			.build();
		
		// setup the isometric camera position
		camIsometric = new CameraPosition.Builder()
			.target(sdsmtLocation)
			.zoom(18)
			.tilt((float)60.0)
			.bearing((float) 330.0)
			.build();
	}
	
	// method to update map based on click
	private void updateMapFromClick(LatLng loc) 
	{
		// get current camera settings
		CameraPosition oldPos = googleMap.getCameraPosition();
		
		// create new camera position
		CameraPosition newPos = new CameraPosition.Builder()
			.target(loc)
			.zoom(oldPos.zoom)
			.tilt(oldPos.tilt)
			.bearing(oldPos.bearing)
			.build();
		
		// move the map to the new spot
		googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(newPos));	
	}
	
	// example of camera views and animation
	private void toggleCamera()
	{
		if(googleMap.getCameraPosition().bearing != 0)
		{
			googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(camOriginal));
		}
		else
		{
			googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(camIsometric));
		}
	}

	private void togglePolygon() 
	{
		polygon1.setVisible(!polygon1.isVisible());
		polygon2.setVisible(!polygon2.isVisible());
	}
	
	private void toggleCircle() 
	{
		circle.setVisible(!circle.isVisible());
	}

	private void togglePath() 
	{
		polyline.setVisible(!polyline.isVisible());
	}

	private void toggleMarkers() 
	{
		schoolMarker.setVisible(!schoolMarker.isVisible());
		workMarker.setVisible(!workMarker.isVisible());
		beerMarker.setVisible(!beerMarker.isVisible());
	}
    
}
