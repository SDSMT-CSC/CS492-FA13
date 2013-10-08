package edu.sdsmt.cs492.example6.fragments;

import edu.sdsmt.cs492.example6.fragments.FragmentList.IOnClassSelectedListener;
import android.app.Activity;
import android.app.FragmentManager;
import android.os.Bundle;

public class ActivityDualPaneProgrammaticLayout extends Activity implements IOnClassSelectedListener
{

	private FragmentManager _fragmentManager;
	private FragmentList _listFragment;
	private FragmentDetail _detailFragment;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		// Assign the appropriate layout to the container Activity.
		setContentView(R.layout.activity_dual_pane_programmatic_layout);
		
		// Get a reference to the Fragment Manager to be used for
		// dynamically adding fragments to this hosting Activity.
		_fragmentManager = getFragmentManager();
		
		if (savedInstanceState == null)
		{
			// New instance, so create fragments and add to the activity.
			_listFragment = new FragmentList();
			_detailFragment = new FragmentDetail();
			
			// Add fragments to the appropriate frame layout containers.
			_fragmentManager.beginTransaction()
							.add(R.id.frameList, _listFragment)
							.add(R.id.frameDetail, _detailFragment)
							.commit();
		}
		else
		{
			// Fragments have already been added, just find them.
			_listFragment = (FragmentList) _fragmentManager.findFragmentById(R.id.frameList);
			_detailFragment = (FragmentDetail) _fragmentManager.findFragmentById(R.id.frameDetail);
		}
	}

	@Override
	public void onClassSelected(int classID)
	{
		// Handle calllback from list fragment to display the 
		// appropriate class description in the detail fragment.
		_detailFragment.displayClassDescription(classID);
	}


}
