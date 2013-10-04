package edu.sdsmt.cs492.example6.fragments;

import android.app.Activity;
import android.os.Bundle;
import edu.sdsmt.cs492.example6.fragments.FragmentList.IOnClassSelectedListener;

public class ActivityStaticFragmentLayout extends Activity implements IOnClassSelectedListener
{
	private FragmentDetail _detailFragment;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_static_fragment_layout);
		
		// Get a reference to the fragment in order to make a method
		// call to select the correct class description.
		_detailFragment = (FragmentDetail) getFragmentManager().findFragmentById(R.id.fragmentDetail);
	
	}

	@Override
	public void onClassSelected(int classID)
	{
		_detailFragment.displayClassDescription(classID);
	}

}
