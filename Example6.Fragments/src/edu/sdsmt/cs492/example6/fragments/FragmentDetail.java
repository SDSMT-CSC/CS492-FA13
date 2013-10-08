package edu.sdsmt.cs492.example6.fragments;

import android.app.Fragment;
import android.graphics.Color;
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
		
		// Retrieve a list of class description from array string resource.
		_listClassDescriptions = getResources().getStringArray(R.array.arrayListClassDescription);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		// Inflate the appropriate fragment layout in order to assign it to
		// the holding activity.
		View rootView = inflater.inflate(R.layout.fragment_detail, container, false);
		
		// Set color to help distinguish details fragment.
		rootView.setBackgroundColor(Color.GREEN);
		
		// Get a reference to the text view for assigning the 
		// select class description.
		_classDescTextView = (TextView) rootView.findViewById(R.id.textViewDescription);
		_classDescTextView.setTextColor(Color.WHITE);
		
		return rootView;
	}

	@Override
	public void onResume()
	{
		// This is necessary for some reason because when the call to 
		// displayClassDescription method from the activity is made,
		// the text is not updated unless it is call from onResume().
		// There are SO discussions on the topic with no good explanation.
		displayClassDescription(_currentClassID);
		
		super.onResume();
	}

	public void displayClassDescription(int classID)
	{
		// Store the selected index or classID for assignment
		// after configuration change.
		_currentClassID = classID;
		
		if (classID >= 0)
		{
			_classDescTextView.setText(_listClassDescriptions[classID]);
		}
		else
		{
			_classDescTextView.setText("");
		}
	}

}
