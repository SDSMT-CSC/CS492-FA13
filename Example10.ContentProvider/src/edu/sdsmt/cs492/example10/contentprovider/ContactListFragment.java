package edu.sdsmt.cs492.example10.contentprovider;


import android.app.Activity;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

public class ContactListFragment extends ListFragment
{

	private IContactControlListener _listener;
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		setHasOptionsMenu(true);

	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflator)
	{
		// Inflate the Menu resource to be associated with
		// this activity.
		getActivity().getMenuInflater().inflate(R.menu.menu_contact_list, menu);

		super.onCreateOptionsMenu(menu, menuInflator);
	}


	@Override
	public void onAttach(Activity activity)
	{
		try
		{
			// Assign listener reference from hosting activity.
			_listener = (IContactControlListener) activity;
		}
		catch (ClassCastException e)
		{
			throw new ClassCastException(activity.toString());
		}
		
		super.onAttach(activity);
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id)
	{
		_listener.contactSelect(getListAdapter().getItemId(position));		
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch (item.getItemId())
		{
			case R.id.action_add_contact:
			{
				_listener.contactInsert();
			}
			default:
			{
				return super.onOptionsItemSelected(item);
			}
		}
	}
}