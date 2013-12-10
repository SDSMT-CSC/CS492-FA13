/**
 *	Assignment #3
 *	CS 492 � Mobile Computing
 *	Fall 2013
 *	Weather App
 *	11/17/2013
 * 
 * @author 1819398 - Ross Hoyer
 * 
 */
package edu.sdsmt.mobile.rosshoyer.weatherapp.model;


import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import edu.sdsmt.mobile.rosshoyer.weatherapp.R;
import edu.sdsmt.mobile.rosshoyer.weatherapp.util.IListeners;
import edu.sdsmt.mobile.rosshoyer.weatherapp.util.JSONReader;

/**
 * This class is the model solution to grab a location given a  
 * certain zip code
 * 
 * 
 * @author Ross Hoyer
 * 
 */
public class ForecastLocation implements Parcelable {

	//Used for debugging and storing data to parcables and bundles
	private static final String TAG = "ForcastLocation";
	public static final String KEY_LOCATION = "key_location";
	
	//The url to find a location given a zip code
	private final static String _URL = "http://i.wxbug.net/REST/Direct/GetLocation.ashx?zip=%s&api_key=%s";
	
	//The members needed to describe a Location
	public String _ZipCode;
	public String _City;
	public String _State;
	public String _Country;
	
	//Used to not get lost in parcable arrays.
	private static final int _ittCity = 1;
	private static final int _ittZipCode = 0;
	private static final int _ittState = 2;
	private static final int _ittCountry = 3;
	
	/**
	 * Used for the creation of a parcable object
	 * 
	 * @author Ross Hoyer
	 * 
	 */
	public static final Parcelable.Creator<ForecastLocation> CREATOR = new Parcelable.Creator<ForecastLocation>() {
		
		/**
		 * How to create from a parcaable
		 * @author Ross Hoyer
		 * 
		 */
        @Override
		public ForecastLocation createFromParcel(Parcel in) {
            return new ForecastLocation(in); 
        }

        /**
         * How to create a new array attached
         * 
         * @author Ross Hoyer
         * 
         */
        @Override
		public ForecastLocation[] newArray(int size) {
            return new ForecastLocation[size];
        }
    };
    
    /**
     * Used to equivilize between two objects
     * @author Ross Hoyer
     * 
     * @param o
     * 	THe object to compare to
     */
	@Override
	public boolean equals(Object o) {
		
		//Turn the object into a ForcastLocation
		ForecastLocation input = (ForecastLocation) o;		
		
		
		//These statements are as followed for each object
		//contained in the class compare if on is null of the other
		//it then compare the 2 members if any member differs in nullatity or
		//value return false else keep going
		if(_ZipCode == null || input._ZipCode == null){
			if(_ZipCode != input._ZipCode)
				return false;
		}
		else if(!input._ZipCode.equals(_ZipCode))
			return input._ZipCode.equals(_ZipCode);
		
		if(_City == null || input._City == null){
			if(_City != input._City)
				return false;
		}
		else if(!input._City.equals(_City))
			return input._City.equals(_City);
		
		if(_State == null || input._State == null){
			if(_State != input._State)
				return false;
		}
		else if(!input._State.equals(_State))
			return input._State.equals(_State);
		
		if(_Country == null || input._Country == null){
			if(_Country != input._Country)
				return false;
		}
		else if(!input._Country.equals(_Country))
			return input._Country.equals(_Country);		 

		return true;
	}

	/**
	 * The most basic of crerators initilizes the memers to null
	 * 
	 * 
	 * @author Ross Hoyer
	 * 
	 */
	public ForecastLocation() {
		_ZipCode = null;
		_City = null;
		_State = null;
		_Country = null;
	}

	/**
	 * Creator from a parcable
	 * 
	 * @author Ross Hoyer
	 * 
	 * @param parcel
	 * 		The parcable to interpret the object from
	 */
	public ForecastLocation(Parcel parcel) {
		//Turn the parcaable to a string array
		String[] parcelArray = new String[10];
		parcel.readStringArray(parcelArray);
		//Interpret the string array
		_City = parcelArray[_ittCity];
		_State = parcelArray[_ittState];
		_Country = parcelArray[_ittCountry];
		_ZipCode = parcelArray[_ittZipCode];
	}

	/**
	 * Used for the creation from a JSONObject
	 * 
	 * @author Ross Hoyer
	 * 
	 * @param parent
	 * 	The JSONOBject to interpret into a ForcastLocation object
	 */
	public ForecastLocation(JSONObject parent) {
		//if  the object is null nothing to be done
		if (parent == null)
			return;

		try {
			//Grab the object from the parent
			JSONObject obj = parent.getJSONObject("location");
			//FIll the members in
			_ZipCode = obj.getString("zipCode");
			_City = obj.getString("city");
			_State = obj.getString("state");
			_Country = obj.getString("country");
		} catch (JSONException e) {
			Log.e(TAG, e.toString());
		}
	}

	/**
	 * Combines the URL with the users key to easily access the URL
	 * 
	 * 
	 * @author Ross Hoyer
	 * @param  ZipCode
	 * 		The zip code of the location
	 * @param context
	 * 		the base activities context
	 * 	
	 */
	public static String getLocationURL(String ZipCode, Context context) {
		//Create the string from the given information
		return String.format(_URL, ZipCode,
				context.getResources().getString(R.string.key));
	}

	/**
	 *	The innerclass that is the async task a super simple method 
	 *  that uses supplied data and creates a http call 
	 *  and then translates the return on that call to a json object 
	 *
	 * @author Ross Hoyer
	 *
	 */	
	public class LoadForecastLocation extends
			AsyncTask<String, Void, ForecastLocation> {
		//The callback class to attach to
		private IListeners _listener;
		//The base activities context
		private Context _context;

		/**
		 *	The creator method used to attache to the base activity and its
		 *	callback
		 *
		 * @author Ross Hoyer
		 *
		 *@param context
		 *	the calling activity the task is tied too
		 *@param listener
		 *	the callback that correspond
		 */	
		public LoadForecastLocation(Context context, IListeners listener) {
			
			_context = context;
			_listener = listener;
		}

		/**
		 *	The task to be run on the background thread it handles the acquiring of the 
		 *	JSON object
		 *
		 * @author Ross Hoyer
		 *
		 *@param params
		 *	NOT USED
		 */	
		@Override
		protected ForecastLocation doInBackground(String... params) {
			//A new null object
			ForecastLocation location = new ForecastLocation();

			//Create a local JSON Objcet
			JSONObject locationJSON = JSONReader.getJSONFromUrl(getLocationURL(
					params[0], _context));
			//creation of an object from a JSON object
			location = new ForecastLocation(locationJSON);

			return location;
		}

		/**
		 *	After the JSON object is loaded tell the view to interpretet it
		 *
		 * @author Ross Hoyer
		 *
		 *@param location
		 *	THe location object to be returned to the view
		 */	
		@Override
		protected void onPostExecute(ForecastLocation location) {
			_listener.onLocationLoaded(location);
		}
	}

	/**
	 *	NOT USED
	 *
	 * @author Ross Hoyer
	 */	
	@Override
	public int describeContents() {
		return 0;
	}

	/**
	 *	Used to write to a parcable object 
	 *
	 * @author Ross Hoyer
	 *
	 *@param dest
	 *	The parcel to hold the objects
	 *@param flags
	 *	NOT USED
	 */	
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		String[] parcelArray = new String[10];
		parcelArray[_ittCity] = _City;
		parcelArray[_ittState] = _State;
		parcelArray[_ittCountry] = _Country;
		parcelArray[_ittZipCode] = _ZipCode;
		dest.writeArray(parcelArray);
	}

}
