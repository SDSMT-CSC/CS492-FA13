package edu.sdsmt.cs492.example9.cursorloader;

import android.app.Activity;
import android.app.ListFragment;
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
	public void onListItemClick(ListView l, View v, int position, long id)
	{
        _listener.onContactSelected(id);
	}
}
