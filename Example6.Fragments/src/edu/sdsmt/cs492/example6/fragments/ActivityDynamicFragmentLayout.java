package edu.sdsmt.cs492.example6.fragments;

import edu.sdsmt.cs492.example6.fragments.FragmentList.IOnClassSelectedListener;
import android.app.Activity;
import android.app.FragmentManager;
import android.os.Bundle;

public class ActivityDynamicFragmentLayout extends Activity implements IOnClassSelectedListener
{

	private FragmentList _listFragment;
	private FragmentDetail _detailFragment;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_dynamic_fragment_layout);
		
		FragmentManager _fragmentManager = getFragmentManager();
		
		if (savedInstanceState == null)
		{
			_listFragment = new FragmentList();
			_detailFragment = new FragmentDetail();
			
			_fragmentManager.beginTransaction()
							.add(R.id.frameList, _listFragment)
							.add(R.id.frameDetail, _detailFragment)
							.commit();
		}
		else
		{
			_listFragment = (FragmentList) _fragmentManager.findFragmentById(R.id.frameList);
			_detailFragment = (FragmentDetail) _fragmentManager.findFragmentById(R.id.frameDetail);
		}
	}

	@Override
	public void onClassSelected(int classID)
	{
		_detailFragment.displayClassDescription(classID);
	}

}
