package edu.sdsmt.cs293.example7.database;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import edu.sdsmt.cs293.example7.database.Model.Course;

public class MainActivity extends Activity implements ICourseControlListener
{
	
	private final static String FRAGMENT_LIST_TAG = "ContactListTag";
	private final static String FRAGMENT_DETAIL_TAG = "ContactViewTag";
	
	private FragmentManager _fragmentManager;
	private ViewListFragment _fragmentList;
	private ViewDetailFragment _fragmentDetail;
	
	private Model _model;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_main);
		
		// Get a reference to the fragment manager to 
		// be used for adding/replacing fragments.
		_fragmentManager = getFragmentManager();
		
		_fragmentList = (ViewListFragment) _fragmentManager.findFragmentByTag(FRAGMENT_LIST_TAG);
		if (_fragmentList == null)
		{
			_fragmentList = new ViewListFragment();
		}
		
		_fragmentDetail = (ViewDetailFragment) _fragmentManager.findFragmentByTag(FRAGMENT_DETAIL_TAG);
		if (_fragmentDetail == null)
		{
			_fragmentDetail = new ViewDetailFragment();
		}
		
		if (savedInstanceState == null)
		{
			_fragmentManager.beginTransaction()
			                .replace(R.id.fragmentContainerFrame, _fragmentList, FRAGMENT_LIST_TAG)
			                .commit();
		}
		
		// Get single instance to the model to handle
		// all database activity.
		_model = Model.getInstance(this);
	}

	@Override
	public void selectCourse(Course course)
	{
		showDetailFragment(course);
	}

	@Override
	public void insertCourse()
	{
		Course course = new Course();
		showDetailFragment(course);
	}

	@Override
	public void insertCourse(Course course)
	{
		_model.insertCourse(course);
		_fragmentManager.popBackStackImmediate();
	}

	@Override
	public void deleteCourse(Course course)
	{
		_model.deleteCourse(course);
		_fragmentManager.popBackStackImmediate();
	}

	@Override
	public void updateCourse(Course course)
	{
		_model.updateCourse(course);
		_fragmentManager.popBackStackImmediate();
	}

	private void showDetailFragment(Course course)
	{
	
		_fragmentDetail._course = course;
		
		_fragmentManager.beginTransaction()
				        .replace(R.id.fragmentContainerFrame, _fragmentDetail, FRAGMENT_DETAIL_TAG)
				        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
				        .addToBackStack(null)
				        .commit();
	}

}
