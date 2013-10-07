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
		
		// Retrieve array of string values, ex. class list.
		_listClasses = getResources().getStringArray(R.array.arrayListClasses);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		// Inflate the associated fragment layout in order to send
		// it to the holding layout.
		View rootView = inflater.inflate(R.layout.fragment_list, container, false);
		
		return rootView;
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
	public void onListItemClick(ListView listview, View view, int position, long id)
	{
		// Make sure list item selected is highlighted.
		listview.setItemChecked(position, true);
		
		// Hosting activity callback to specify class that was
		//selected.
		_onClassSelected.onClassSelected(position);
	}
}
