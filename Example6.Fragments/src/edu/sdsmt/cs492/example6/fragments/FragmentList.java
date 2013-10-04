package edu.sdsmt.cs492.example6.fragments;

import java.util.ArrayList;

import android.app.Activity;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class FragmentList extends ListFragment
{
	private IOnClassSelectedListener _onClassSelected;
	private String[] _listClasses;
	
	private int _currentPosition = -1;

	/**
	 * Define listener interface that the hosting Activity needs 
	 * to implement.
	 */
	public interface IOnClassSelectedListener
	{
		public void onClassSelected(int classID);
	}

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		// Maintain fragment instance during hosting Activity's recreation
		setRetainInstance(true);
		
		// Retrieve array of string values, ex. class list.
		_listClasses = getResources().getStringArray(R.array.arrayListClasses);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		return super.onCreateView(inflater, container, savedInstanceState);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		// Only allow one item to be selected, i.e. not multi-selection.
		getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);

		// Populate an array of classes from the string resource
		// file to be used to populate the listview.
		ArrayList<String> arrayClasses = new ArrayList<String>();
		for (String itemClass : _listClasses)
		{
			arrayClasses.add(itemClass);
		}
		
		// Assign list adapter to the array of classes.
		setListAdapter(new ArrayAdapter<String>(getActivity().getBaseContext(),
					   android.R.layout.simple_list_item_activated_1, 
					   arrayClasses));
		
		// During a configuration change, a position might have been 
		// selected and needs to be re-selected when fragment is visible
		// again.
		if (_currentPosition != -1)
		{
			getListView().setItemChecked(_currentPosition, true);
			_onClassSelected.onClassSelected(_currentPosition);
		}
		
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onAttach(Activity activity)
	{
		try
		{
			// Assign listener reference from hosting activity.
			_onClassSelected = (IOnClassSelectedListener) activity;
		}
		catch (ClassCastException e)
		{
			throw new ClassCastException(activity.toString());
		}
		
		super.onAttach(activity);
	}

	@Override
	public void onDetach()
	{
		super.onDetach();
	}

	@Override
	public void onListItemClick(ListView listview, View view, int position, long id)
	{
		// Assign current position to member variable to be 
		// returned during configuration changes, i.e. device rotation.
		_currentPosition = position;
		
		// Make sure list item selected is highlighted.
		listview.setItemChecked(position, true);
		
		// Hosting activity callback to specify class that was
		//selected.
		_onClassSelected.onClassSelected(position);
	}

}
