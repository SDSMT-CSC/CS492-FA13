package edu.sdsmt.cs492.example7.database;

import java.util.List;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import edu.sdsmt.cs492.example7.database.Model.Course;

public class MainActivity extends Activity implements ICourseControlListener
{
	
	private final static String FRAGMENT_LIST_TAG = "ContactListTag";
	private final static String FRAGMENT_DETAIL_TAG = "ContactViewTag";
	
	private FragmentManager _fragmentManager;
	private ViewListFragment _fragmentList;
	private ViewDetailFragment _fragmentDetail;
	
	private Model _model;
	private Course _course;
	private List<Course> _courses;
	private ArrayAdapter<Course> _adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		// Assign the layout to the activity.
		setContentView(R.layout.activity_main);
		
		// Get a reference to the fragment manager to 
		// be used for adding/replacing fragments.
		_fragmentManager = getFragmentManager();
		
		// If the fragment is not found, create it.
		_fragmentList = (ViewListFragment) _fragmentManager.findFragmentByTag(FRAGMENT_LIST_TAG);
		if (_fragmentList == null)
		{
			_fragmentList = new ViewListFragment();
		}
		
		// If the fragment is not found, create it.
		_fragmentDetail = (ViewDetailFragment) _fragmentManager.findFragmentByTag(FRAGMENT_DETAIL_TAG);
		if (_fragmentDetail == null)
		{
			_fragmentDetail = new ViewDetailFragment();
		}
		
		// Only add/replace the list fragment if the bundle is empty; otherwise,
		// the activity is being re-created so keep the fragment that is already
		// displayed.
		if (savedInstanceState == null)
		{
			_fragmentManager.beginTransaction()
			                .replace(R.id.fragmentContainerFrame, _fragmentList, FRAGMENT_LIST_TAG)
			                .commit();
		}
		
		// Get single instance to the model to handle
		// all database activity.
		_model = Model.getInstance(this);
		
		// Insert Sample Courses
		//_model.insertSampleCourses();
		
		refreshArrayAdapter();
	}

	@Override
	public void selectCourse(Course course)
	{
		// Listener callback on course selected, call to
		// display the detail fragment.
		
		_course = course;
		showDetailFragment();
	}

	@Override
	public void insertCourse()
	{
		// Listener callback on requesting a course be added.
		// Typically called from the list fragment to know when
		// to display the detail fragment.
		
		// Give the detail fragment an empty course object
		// to consume.
		_course = new Course();
		showDetailFragment();
	}

	@Override
	public void insertCourse(Course course)
	{
		// Listener callback to know when to actually insert
		// the course object data into the database.
		
		_adapter.add(course);
		_adapter.sort(course);
		_adapter.notifyDataSetChanged();
		
		_model.insertCourse(course);
		_fragmentManager.popBackStackImmediate();
	}

	@Override
	public void deleteCourse(Course course)
	{
		// Listener callback to know when a course is being 
		// deleted from the details fragment.
		
		_adapter.remove(course);
		_adapter.sort(course);
		_adapter.notifyDataSetChanged();
		
		_model.deleteCourse(course);
		_fragmentManager.popBackStackImmediate();
	}

	@Override
	public void updateCourse(Course course)
	{
		// Listener callback to drive the updating of a
		// course.
		
		_adapter.remove(course);
		_adapter.add(course);
		_adapter.sort(course);
		_adapter.notifyDataSetChanged();
		
		_model.updateCourse(course);
		_fragmentManager.popBackStackImmediate();
	}

	@Override
	public Course getCourse()
	{
		return _course;
	}

	@Override
	public ArrayAdapter<Course> getCourseArrayAdapter()
	{
		return _adapter;
	}

	private void refreshArrayAdapter()
	{
		// Get an Array List of Contact objects.
		_courses = Model.getInstance(this).getCourses();
		
		// Assign list to ArrayAdapter to be used with assigning
		// to the ListFragment list adapter.
		_adapter = new ArrayAdapter<Course>(this,
				        				    android.R.layout.simple_list_item_1, 
				        					_courses);
	}
	
	private void showDetailFragment()
	{
		
		// Perform the fragment transaction to display the details fragment.
		_fragmentManager.beginTransaction()
				        .replace(R.id.fragmentContainerFrame, _fragmentDetail, FRAGMENT_DETAIL_TAG)
				        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
				        .addToBackStack(null)
				        .commit();
	}

}
