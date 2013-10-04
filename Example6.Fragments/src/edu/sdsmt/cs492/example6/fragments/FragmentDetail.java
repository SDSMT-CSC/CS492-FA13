package edu.sdsmt.cs492.example6.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class FragmentDetail extends Fragment
{

	private TextView _classDescTextView;
	
	private String[] _listClassDescriptions;
	private int _currentClassID = -1;
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		// Maintain fragment instance during hosting Activity's recreation
		setRetainInstance(true);
		
		_listClassDescriptions = getResources().getStringArray(R.array.arrayListClassDescription);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View parentView = inflater.inflate(R.layout.fragment_detail, container, false);
		
		_classDescTextView = (TextView) parentView.findViewById(R.id.textViewDescription);
		
		return parentView;
	}
	
	@Override
	public void onAttach(Activity activity)
	{
		super.onAttach(activity);
	}
	
	@Override
	public void onResume()
	{
		if (_currentClassID != -1)
		{
			displayClassDescription(_currentClassID);
		}
		
		super.onResume();
	}

	public void displayClassDescription(int classID)
	{
		_currentClassID = classID;
		_classDescTextView.setText(_listClassDescriptions[classID]);
	}

}
