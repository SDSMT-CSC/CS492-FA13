package edu.sdsmt.cs492.example6.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentManager.OnBackStackChangedListener;
import android.app.FragmentTransaction;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import edu.sdsmt.cs492.example6.fragments.FragmentList.IOnClassSelectedListener;

public class ActivityRuntimeFragmentLayout extends Activity implements IOnClassSelectedListener
{

	private FragmentManager _fragmentManager;
	private FragmentList _listFragment;
	private FragmentDetail _detailFragment;
	
	private FrameLayout _listFrameLayout;
	private FrameLayout _detailFrameLayout;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_runtime_fragment_layout);
		
		_listFrameLayout = (FrameLayout) findViewById(R.id.frameList);
		_detailFrameLayout = (FrameLayout) findViewById(R.id.frameDetail);
		
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
							.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
							.commit();
		}
		else
		{
			// Fragments have already been added, just find them.
			_listFragment = (FragmentList) _fragmentManager.findFragmentById(R.id.frameList);
			_detailFragment = (FragmentDetail) _fragmentManager.findFragmentById(R.id.frameDetail);
			
			if (_detailFragment == null)
			{
				_detailFragment = new FragmentDetail();
			}
			
			setLayout();
		}
		
		_fragmentManager.addOnBackStackChangedListener(new OnBackStackChangedListener()
		{
			@Override
			public void onBackStackChanged()
			{
				// Register for the back button and reset the layout
				// by removing the detail fragment.
				setLayout();
			}
		});
	}

	@Override
	public void onClassSelected(int classID)
	{
		// Handle calllback from list fragment to display the 
		// appropriate class description in the detail fragment.
		
		if (_detailFragment != null)
		{
			// If the detail fragment has not yet been added, make it happen here.
			if (_detailFragment.isAdded() == false)
			{
				_fragmentManager.beginTransaction()
				                .replace(R.id.frameDetail, _detailFragment)
				                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
				                .addToBackStack(null)
				                .commit();
				
				// Removes potential delay when calling commit() is async.
				_fragmentManager.executePendingTransactions();
			}
			
			// Now populate with the selected class description.
			_detailFragment.displayClassDescription(classID);
		}
		
	}
	
	private void setLayout()
	{
		if (_detailFragment != null)
		{
			// Descide to either display or not display the detail fragment.
		
			if (_detailFragment.isAdded() == false)
			{
				_detailFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(0, LayoutParams.MATCH_PARENT, 0f));
			}
			else
			{
				_detailFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(0, LayoutParams.MATCH_PARENT, 2f));
			}
		}
	}
}
