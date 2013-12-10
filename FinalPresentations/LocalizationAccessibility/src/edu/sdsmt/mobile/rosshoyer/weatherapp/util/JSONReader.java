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
package edu.sdsmt.mobile.rosshoyer.weatherapp.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

/**
 * The basic class used to read a json html request
 * and return a json object
 * 
 * @author Ross Hoyer
 * 
 */
public class JSONReader {

	/**
	 * The Singular method in the class
	 * it gets a json object from a url
	 * @author Ross Hoyer
	 * @param URL
	 * 	THe url at where the object resides
	 */
	public static JSONObject getJSONFromUrl(String URL) {

		//Initilize variables to negilable values
		InputStream is = null;
		JSONObject jObj = null;
		String json = "";

		// Making HTTP request
		try {
			// defaultHttpClient
			DefaultHttpClient httpClient = new DefaultHttpClient();

			//Post the question to the url
			//Gets the answer from the url
			//creates an http entity
			HttpGet httpPost = new HttpGet(URL);
			HttpResponse httpResponse = httpClient.execute(httpPost);
			HttpEntity httpEntity = httpResponse.getEntity();
			is = httpEntity.getContent();

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			//create a buffered reader that streams and pases the data line by line
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					is, "iso-8859-1"), 8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			is.close();
			//creates a string builder object to a string
			json = sb.toString();
		} catch (Exception e) {
			Log.e("Buffer Error", "Error converting result " + e.toString());
		}

		// try parse the string to a JSON object
		try {
			jObj = new JSONObject(json);
		} catch (JSONException e) {
			Log.e("JSON Parser", "Error parsing data " + e.toString());
		}

		// return JSON String
		return jObj;
	}

}
