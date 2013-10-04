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
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
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
	
	public void displayClassDescription(int classID)
	{
		_classDescTextView.setText(_listClassDescriptions[classID]);
	}

}
