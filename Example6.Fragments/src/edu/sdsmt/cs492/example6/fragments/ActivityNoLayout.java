package edu.sdsmt.cs492.example6.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;
import edu.sdsmt.cs492.example6.fragments.FragmentNoLayout.IOnCounterUpdateListener;

public class ActivityNoLayout extends Activity implements IOnCounterUpdateListener
{
	private static final String FRAGMENT_NO_LAYOUT = "FragmentNoLayout";
	private static final String BUNDLE_COUNTER_KEY = "CounterKey";
	
	private FragmentManager _fragmentManager;
	private Fragment _fragmentNoLayout;
	
	private Button _startButton;
	private ProgressBar _progressBar;
	
	private int _currentProgress;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		// Assign layout to the host activity.
		setContentView(R.layout.activity_no_layout);
		
		// Get a reference to the butter and progress bar to update it. 
		_startButton = (Button)	findViewById(R.id.buttonStart);
		_progressBar = (ProgressBar) findViewById(R.id.progressBar);
		
		_progressBar.setMax(500);
		
		_startButton.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				((FragmentNoLayout) _fragmentNoLayout).startThread();
			}
		});
		
		// Get a reference to the Fragment Manager in order to perform
		// fragment transactions, i.e. add, remove, etc.
		_fragmentManager = getFragmentManager();
		
		// Determine if an instance of the Fragment already exists.
		_fragmentNoLayout = (FragmentNoLayout) _fragmentManager.findFragmentByTag(FRAGMENT_NO_LAYOUT);
		
		// If Fragment was not found, instantiate it and add it
		// to the hosting Activity.
		if (_fragmentNoLayout == null)
		{
			// Create a new Fragment object to be passed in
			// FragmentTransaction.
			_fragmentNoLayout = new FragmentNoLayout();

			// Add the "no layout" fragment to the host Activity.
			_fragmentManager.beginTransaction()
							.add(_fragmentNoLayout, FRAGMENT_NO_LAYOUT)
							.commit();
		}
		
		if (savedInstanceState != null)
		{
			// Get the value out of the bundle.
			_currentProgress = savedInstanceState.getInt(BUNDLE_COUNTER_KEY);	
			
			_progressBar.setProgress(_currentProgress);
		}
		
	}

	@Override
	protected void onSaveInstanceState(Bundle outState)
	{
		super.onSaveInstanceState(outState);
		
		// Save the current counter value to the bundle for
		// later retrieval when the activity is restarted.
		outState.putInt(BUNDLE_COUNTER_KEY, _currentProgress);
	}

	@Override
	public void onCounterUpdate(int currentProgress)
	{
		_progressBar.setProgress(currentProgress);
		
		// Assign current counter value to a member
		// which will be added to the saved instance
		// bundle on Activity creation.
		_currentProgress = currentProgress;
	}
}
