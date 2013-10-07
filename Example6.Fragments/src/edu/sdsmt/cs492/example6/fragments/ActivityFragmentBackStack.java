package edu.sdsmt.cs492.example6.fragments;

import android.os.Bundle;
import android.app.Activity;
import android.app.FragmentManager;
import android.view.Menu;
import android.widget.FrameLayout;

public class ActivityFragmentBackStack extends Activity
{

	private FragmentManager _fragmentManager;
	private FragmentList _listFragment;
	private FragmentDetail _detailFragment;
	
	private FrameLayout _fragmentContainerFrameLayout;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_fragment_back_stack);
		
		// Get a reference to the detail frame layout.
		_fragmentContainerFrameLayout = (FrameLayout) findViewById(R.id.fragmentContainer);
	}


}
