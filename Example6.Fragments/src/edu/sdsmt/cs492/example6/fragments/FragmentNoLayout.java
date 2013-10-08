package edu.sdsmt.cs492.example6.fragments;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;

public class FragmentNoLayout extends Fragment
{

	/**
	 * Define listener interface that the hosting Activity needs 
	 * to implement.
	 */
	public interface IOnCounterUpdateListener
	{
		public void onCounterUpdate(int counter);
	}

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onDestroy()
	{
		super.onDestroy();
	}

	public class CounterAsync extends AsyncTask<Void, String, String>
	{

		@Override
		protected String doInBackground(Void... params)
		{
			return null;
		}

		@Override
		protected void onProgressUpdate(String... values)
		{
		}

		@Override
		protected void onPostExecute(String result)
		{
		}
		
	}
	
	
}
