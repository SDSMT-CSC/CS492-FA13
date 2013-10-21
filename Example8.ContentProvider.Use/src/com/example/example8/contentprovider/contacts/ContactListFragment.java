package com.example.example8.contentprovider.contacts;

import android.app.Activity;
import android.app.ListFragment;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

public class ContactListFragment extends ListFragment
{
	
	private IContactListener _listener;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
	
		setRetainInstance(true);
	}


	@Override
	public void onAttach(Activity activity)
	{
		
		super.onAttach(activity);
		
		try
		{
			_listener = (IContactListener) activity;
		}
		catch (ClassCastException e)
		{
			throw new ClassCastException(activity.toString() + 
					                     " must implement IContactListener");
		}
	}


	@Override
	public void onResume()
	{
		super.onResume();
		
		setListAdapter(_listener.getCursorAdapter());
	}


	@Override
	public void onListItemClick(ListView l, View v, int position, long id)
	{
		Cursor cursor = _listener.getCursorAdapter().getCursor();
		cursor.moveToPosition(position);
		
		String key = cursor.getString(1);
		
        _listener.onContactSelected(key);
	}
}
