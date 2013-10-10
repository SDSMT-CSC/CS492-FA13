package edu.sdsmt.cs492.example6.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.SeekBar;
import edu.sdsmt.cs492.example6.fragments.FragmentNoLayout.IOnCounterUpdateListener;

public class ActivityNoLayout extends Activity implements IOnCounterUpdateListener
{
	private static final String FRAGMENT_NO_LAYOUT = "FragmentNoLayout";
	
	private FragmentManager _fragmentManager;
	private Fragment _fragmentNoLayout;
	
	private Button _startButton;
	private SeekBar _progressBar;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		// Assign layout to the host activity.
		setContentView(R.layout.activity_no_layout);
		
		// Get a reference to the butter and progress bar to update it. 
		_startButton = (Button)	findViewById(R.id.buttonStart);
		_progressBar = (SeekBar) findViewById(R.id.progressBar);
		
		_startButton.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				((FragmentNoLayout) _fragmentNoLayout).startThread();
				
				_startButton.setEnabled(false);
			}
		});
		
		_progressBar.setMax(FragmentNoLayout.PROGRESS_MAX);
		
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
	}

	@Override
	public void onPause()
	{
		super.onPause();
		
		// Force Activity to finish on Home or Back button use.
		if (isChangingConfigurations() == false)
		{
			finish();
		}
	}

	@Override
	public void onCounterUpdate(int currentProgress)
	{
		_progressBar.setProgress(currentProgress);
	}
}
