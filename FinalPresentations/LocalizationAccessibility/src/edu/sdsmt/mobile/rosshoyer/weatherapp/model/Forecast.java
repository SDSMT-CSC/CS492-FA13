/**
 *	Assignment #3
 *	CS 492 - Mobile Computing
 *	Fall 2013
 *	Weather App
 *	11/17/2013
 * 
 * @author 1819398 - Ross Hoyer
 * 
 */
package edu.sdsmt.mobile.rosshoyer.weatherapp.model;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import edu.sdsmt.mobile.rosshoyer.weatherapp.R;
import edu.sdsmt.mobile.rosshoyer.weatherapp.util.IListeners;
import edu.sdsmt.mobile.rosshoyer.weatherapp.util.JSONReader;

/**
 * The model class for the forcast object 
 * 
 * @author Ross Hoyer
 * 
 */
public class Forecast implements Parcelable {
	
	//Key used to debug
	private static final String TAG = "Forecast";
	//used to identify the data
	public static final String KEY_FORECAST = "key_forecast";
	
	// http://developer.weatherbug.com/docs/read/WeatherBug_API_JSON
	// NOTE: See example JSON in doc folder.
	private static String _URL = "http://i.wxbug.net/REST/Direct/GetForecastHourly.ashx?zip="
			+ "%s" + "&ht=t&ht=i&ht=cp&ht=fl&ht=h" + "&api_key=%s";

	// http://developer.weatherbug.com/docs/read/List_of_Icons
	private String _imageURL = "http://img.weather.weatherbug.com/forecast/icons/localized/500x420/en/trans/%s.png";

	//The mebers that define a forcast including an image
	public String _Temp;
	public String _Humidity;
	public String _ChanceOfPrecip;
	public String _AsOfTime;
	public String _FeelsLike;
	public byte[] _bitmap;

	//Variables used for parcable creation and retreval
	private static final int _ittTemp = 1;
	private static final int _ittHumidity = 0;
	private static final int _ittChanceOfPrecip = 2;
	private static final int _ittAsOfTime = 3;
	private static final int _ittFeelsLike = 4;
	
	/**
	 * A Abstaract creator class for parcable objects
	 * dosent do much just has the plug ins needed for
	 * the objects
	 * 
	 * @author Ross Hoyer
	 * 
	 */
	public static final Parcelable.Creator<Forecast> CREATOR = new Parcelable.Creator<Forecast>() {
		
		//If creates from a parceable what should it do 
        @Override
		public Forecast createFromParcel(Parcel in) {
            return new Forecast(in); 
        }

        //how to create a parcable
        @Override
		public Forecast[] newArray(int size) {
            return new Forecast[size];
        }
    };
    
    /**
     * Compares 2 different forcast objects
     * 
     * @author Ross Hoyer
     * @param o
     * 	The object to compare to
     * 
     */
	@Override
	public boolean equals(Object o) {
		//converts the object into a forcast object
		Forecast input = (Forecast) o;	
		
		//These statements are as followed for each object
		//contained in the class compare if on is null of the other
		//it then compare the 2 members if any member differs in nullatity or
		//value return false else keep going
		if(_AsOfTime == null || input._AsOfTime == null){
			if(_AsOfTime != input._AsOfTime)
				return false;
		}
		else if(!input._AsOfTime.equals(_AsOfTime))
			return input._AsOfTime.equals(_AsOfTime);
		
		if(_ChanceOfPrecip == null || input._ChanceOfPrecip == null){
			if(_ChanceOfPrecip != input._ChanceOfPrecip)
				return false;
		}
		else if(!input._ChanceOfPrecip.equals(_ChanceOfPrecip))
			return input._ChanceOfPrecip.equals(_ChanceOfPrecip);
		
		if(_Humidity == null || input._Humidity == null){
			if(_Humidity != input._Humidity)
				return false;
		}
		else if(!input._Humidity.equals(_Humidity))
			return input._Humidity.equals(_Humidity);
		
		if(_Temp == null || input._Temp == null){
			if(_Temp != input._Temp)
				return false;
		}
		else if(!input._Temp.equals(_Temp))
			return input._Temp.equals(_Temp);
		
		if(_FeelsLike == null || input._FeelsLike == null){
			if(_FeelsLike != input._FeelsLike)
				return false;
		}
		else if(!input._FeelsLike.equals(_FeelsLike))
			return input._FeelsLike.equals(_FeelsLike);
		
		if(_bitmap == null || input._bitmap == null){
			if(_bitmap != input._bitmap)
				return false;
		}
		else if(!input._bitmap.equals(_bitmap))
			return input._bitmap.equals(_bitmap);
		
		return true;		
	}

	
	/**
	 * The default crator creats a instanec of every member and intilizes them to null
	 * 
	 * @author Ross Hoyer
	 * 
	 */
	public Forecast() {
		_Temp = null;
		_Humidity = null;
		_ChanceOfPrecip = null;
		_AsOfTime = null;
		_FeelsLike = null;
	}

	/**
	 * returns the string of the forcatst json request attached with the key
	 * found in resources
	 * 
	 * @author Ross Hoyer
	 * 
	 */
	private static String getForecastURL(String ZipCode, Context context) {
		//Create the string reading from the resources file
		return String.format(_URL, ZipCode,
				context.getResources().getString(R.string.key));
	}

	/**
	 * The creator if created from a parcel
	 * 
	 * @author Ross Hoyer
	 * 
	 */
	public Forecast(Parcel parcel) {
		String[] parcelArray = new String[10];
		parcel.readStringArray(parcelArray);
		
		//read the data from the parcel array and initlize the members
		_Temp = parcelArray[_ittTemp];
		_Humidity = parcelArray[_ittHumidity];
		_ChanceOfPrecip = parcelArray[_ittChanceOfPrecip];
		_AsOfTime = parcelArray[_ittAsOfTime];
		_FeelsLike = parcelArray[_ittFeelsLike];
	}

	/**
	 * The creator if created from a JSONObject
	 * 
	 * @author Ross Hoyer
	 * @param input
	 * 		A JSON object for converting
	 */
	public Forecast(JSONObject input) {

		//Create a new JSONObject
		JSONObject obj = new JSONObject();
		
		//If the object is null we can't create anything
		if (input == null)
			return;	

		try {
			//Grab an inner object of the array
			obj = (JSONObject) input.getJSONArray("forecastHourlyList").get(0);
			
			//Grabs the parts to create the object
			_Temp = obj.getString("temperature");
			_Humidity = obj.getString("humidity");
			_ChanceOfPrecip = obj.getString("chancePrecip");
			_bitmap = convertBitmapToByteArray(readIconBitmap(
					obj.getString("icon"), -1));
			_AsOfTime = obj.getString("dateTime");
			_FeelsLike = obj.getString("feelsLike");
			
			//Take the String asOfTime which is in unix milliseconds 
			//And converts it to a more human readable format
			Date convertedDate = new Date(Long.parseLong(_AsOfTime));
			DateFormat dateFormat = new SimpleDateFormat("h:00 a", Locale.US);
			dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
			_AsOfTime = dateFormat.format(convertedDate);			
		} catch (JSONException e) {
			Log.e(TAG, e.toString());
		}
	}

	/**
	 *	NOT USED
	 * 
	 * @author Ross Hoyer
	 * 
	 */
	@Override
	public int describeContents() {
		return 0;
	}

	/**
	 *	used so this class could be a parcable object
	 * 
	 * @author Ross Hoyer
	 * @param dest
	 * 		the destination Parcel where the data is to be stored
	 * @param flags
	 * 		NOT USED
	 */
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		String[] parcelArray = new String[10];
		//Convert the memebers of the class to an array of members
		parcelArray[_ittTemp] = _Temp;
		parcelArray[_ittHumidity] = _Humidity;
		parcelArray[_ittChanceOfPrecip] = _ChanceOfPrecip;
		parcelArray[_ittAsOfTime] = _AsOfTime;
		parcelArray[_ittFeelsLike] = _FeelsLike;
		
		//write those members to a parcable
		dest.writeArray(parcelArray);
	}


	/**
	 *	Not needed but that was realized after the fact so it is 
	 *  being used this function converts bitmaps to byte arrays
	 * 
	 * @author Ross Hoyer
	 * @param image
	 * 		the Bitmap to be converted to a byte array
	 */
	private byte[] convertBitmapToByteArray(Bitmap image) {
		
		//The stream used to outbut the byte array
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		//so the byte array isnt absolutly massive
		image.compress(Bitmap.CompressFormat.PNG, 100, stream);
		
		//the storage container for the image
		byte[] byteArray = stream.toByteArray();

		try {
			//were done so close the stream
			stream.close();
		} catch (IOException e) {
			Log.e(TAG, e.toString());
		} finally {
			stream = null;
		}

		//return the image converted to the array
		return byteArray;
	}

	/**
	 *	Not needed but that was realized after the fact so it is 
	 *  being used this function converts byte arrays to Bitmaps
	 * 
	 * @author Ross Hoyer
	 * @param image
	 * 		the Bitmap to be converted to a byte array
	 */
	public static Bitmap convertByteArrayToBitmap(byte[] data) {
		//super simple built in method
		return BitmapFactory.decodeByteArray(data, 0, data.length);
	}
	
	/**
	 *	The innerclass that is the async task a super simple method 
	 *  that uses supplied data and creates a http call 
	 *  and then translet the return on that call to a json object 
	 *
	 * @author Ross Hoyer
	 *
	 */	
	public class LoadForecast extends AsyncTask<String, Void, Forecast> {
		//Used for callbacks
		private IListeners _listener;
		//The calling classes context
		private Context _context;
		
		/**
		 *	The creator class for the async task connects 
		 *
		 * @author Ross Hoyer
		 * @param listener
		 * 		the callbacks to the base activity
		 * 
		 * @param context
		 * 	The activity the TaskShould be attached too
		 * 
		 */	
		public LoadForecast(Context context, IListeners listener) {
			//Initilizing variables
			_context = context;
			_listener = listener;
		}

		/**
		 *	The method to be done in the background thread
		 *
		 * @author Ross Hoyer
		 * @param params
		 * 		NOT USED
		 */	
		@Override
		protected Forecast doInBackground(String... params) {
			//Create a new oject
			Forecast forecast = new Forecast();
			//Initialize the weather object garnered from the http request
			JSONObject weather = JSONReader.getJSONFromUrl(getForecastURL(
					params[0], _context));
			
			//create a forcast object from the JSON object
			forecast = new Forecast(weather);

			return forecast;
		}

		/**
		 *	The method to be called after the background task has completed
		 *	tells the controller to initialize the view
		 *
		 * @author Ross Hoyer
		 * @param forecast
		 * 		the object created from the backfround task
		 */	
		@Override
		protected void onPostExecute(Forecast forecast) {
			//Controller callback
			_listener.onForecastLoaded(forecast);
		}
	}

	/**
	 *	The method to be called after the background task has completed
	 *	tells the controller to initialize the view
	 *
	 * @author Ross Hoyer
	 * @param forecast
	 * 		the object created from the backfround task
	 */	
	private Bitmap readIconBitmap(String conditionString, int bitmapSampleSize) {
	
		try {
			//Translate the URL
			URL weatherURL = new URL(String.format(_imageURL, conditionString));

			//Grabe and decode the bitmap
			BitmapFactory.Options options = new BitmapFactory.Options();
			if (bitmapSampleSize != -1) {
				options.inSampleSize = bitmapSampleSize;
			}

			//return the bitmap grabed from the html
			return (Bitmap) BitmapFactory.decodeStream(weatherURL.openStream(),
					null, options);
		} catch (MalformedURLException e) {
			Log.e(TAG, e.toString());
		} catch (IOException e) {
			Log.e(TAG, e.toString());
		} catch (Exception e) {
			Log.e(TAG, e.toString());
		}
		
		return null;

	}

}
