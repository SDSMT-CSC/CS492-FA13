package edu.sdsmt.cs492.example6.fragments;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentManager.OnBackStackChangedListener;
import android.app.FragmentTransaction;
import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import edu.sdsmt.cs492.example6.fragments.FragmentList.IOnClassSelectedListener;

public class ActivityConfigChangeDynamicLayout extends Activity implements IOnClassSelectedListener
{

	private static final String SELECTED_CLASS_ID = "SELECTED_CLASS_ID";
	
	private FragmentManager _fragmentManager;
	private FragmentList _listFragment;
	private FragmentDetail _detailFragment;
	
	private FrameLayout _listFrameLayout;
	private FrameLayout _detailFrameLayout;
	
	private int _selectedClassID = -1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_dynamic_layout);
		
		// Get a reference to the Fragment Manager to be used for
		// dynamically adding fragments to this hosting Activity.
		_fragmentManager = getFragmentManager();
		
		// Get references to the frame layouts in order to set appropriate
		// layout_weight parameter.
		_listFrameLayout = (FrameLayout) findViewById(R.id.frameList);
		_detailFrameLayout = (FrameLayout) findViewById(R.id.frameDetail);
		
		// Do we have any saved state, i.e. selectedClassID?
		if (savedInstanceState == null)
		{
			_listFragment = new FragmentList();
			_detailFragment = new FragmentDetail();
		}
		else
		{
			// Retrieve the saved ClassID index that was selected in the list.
			_selectedClassID = savedInstanceState.getInt(SELECTED_CLASS_ID);
			
			// Primarily used when rotating from portrait detail fragment visible
			// to landscape to simply apply the correct fragment to the frame layout.
			// The detail fragment was used to replace the list fragment in the list
			// frame layout for purposes of demonstrating the back stack.
			_fragmentManager.popBackStackImmediate();
			
			_listFragment = (FragmentList) _fragmentManager.findFragmentById(R.id.frameList);
			_detailFragment = (FragmentDetail) _fragmentManager.findFragmentById(R.id.frameDetail);
			
			if (_detailFragment == null)
			{
				_detailFragment = new FragmentDetail();
			}
		}
			
		// Determine orientation of the device to display single pane vs. dual pane layout.
		if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
		{
			_fragmentManager.beginTransaction()
							.replace(R.id.frameList, _listFragment)
							.replace(R.id.frameDetail, _detailFragment)
							.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
							.commit();
		}
		else
		{
			_fragmentManager.beginTransaction()
							.replace(R.id.frameList, _listFragment)
							.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
							.commit();
		}
		
		// Set layout_weight for the given orientation.
		setLayout();
		
		_fragmentManager.addOnBackStackChangedListener(new OnBackStackChangedListener()
		{
			@Override
			public void onBackStackChanged()
			{
				if (_listFragment.isAdded() == true)
				{
					_listFragment.getListView().clearChoices();
				}
			}
		});
	}

	@Override
	protected void onSaveInstanceState(Bundle outState)
	{
		
		// Saved the selected classID to the bundle for later
		// retrieval on activity re-creation.
		outState.putInt(SELECTED_CLASS_ID, _selectedClassID);
		
		super.onSaveInstanceState(outState);
	}
	
	@Override
	protected void onResume()
	{
		// De-select both the list and remove detail text when migrating from landscape
		// to portrait.  It did not make sense to keep the class selected when only
		// the list is shown.
		if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
		{
			_listFragment.getListView().setSelection(_selectedClassID);
		}
		else
		{
			_listFragment.getListView().clearChoices();
			_selectedClassID = -1;
		}
		
		// Either display or not the
		if (_detailFragment.isAdded() == true)
		{
			_detailFragment.displayClassDescription(_selectedClassID);
		}
		
		super.onResume();
	}

	@Override
	public void onClassSelected(int classID)
	{
		_selectedClassID = classID;
		
		if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
		{
			_fragmentManager.beginTransaction()
							.replace(R.id.frameDetail, _detailFragment)
							.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
							.commit();
		}
		else
		{
			// NOTE:  See the detail fragment is being shown in list frame layout.
			_fragmentManager.beginTransaction()
							.replace(R.id.frameList, _detailFragment)
							.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
							.addToBackStack(null)
							.commit();
		}
		
		// Need to force execution of pending commits in order to 
		// call the detail fragment to display the selected text.
		_fragmentManager.executePendingTransactions();
		
		// Now populate with the selected class description.
		_detailFragment.displayClassDescription(classID);
	}

	private void setLayout()
	{
		// Decide to either display or not display the detail fragment.
		if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
		{
			_listFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(0, LayoutParams.MATCH_PARENT, 1f));
			_detailFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(0, LayoutParams.MATCH_PARENT, 2f));
		}
		else
		{
			_listFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(0, LayoutParams.MATCH_PARENT, 1f));
			_detailFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(0, LayoutParams.MATCH_PARENT, 0f));
		}
	}

}
