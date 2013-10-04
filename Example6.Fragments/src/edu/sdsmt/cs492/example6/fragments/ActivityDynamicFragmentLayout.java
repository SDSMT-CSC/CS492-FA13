package edu.sdsmt.cs492.example6.fragments;

import android.app.Activity;
import android.app.FragmentManager;
import android.os.Bundle;

public class ActivityDynamicFragmentLayout extends Activity
{

	private FragmentList _listFragment;
	private FragmentDetail _detailFragment;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_dynamic_fragment_layout);
		
		_listFragment = new FragmentList();
		_detailFragment = new FragmentDetail();
		
		
		FragmentManager _fragmentManager = getFragmentManager();

		_fragmentManager.beginTransaction()
						.add(R.id.frameList, _listFragment)
						.add(R.id.frameDetail, _detailFragment)
						.commit();
		
		
	}

}
