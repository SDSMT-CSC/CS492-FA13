package edu.sdsmt.cs293.example7.database;


import java.util.List;

import edu.sdsmt.cs293.example7.database.Model.Course;

import android.app.Activity;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ViewListFragment extends ListFragment
{
	
	private ICourseControlListener _listener;
	private List<Course> _courses;
	private ArrayAdapter<Course> _adapter;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		// Insert Sample Courses
		Model model = new Model(getActivity());
		model.insertSampleCourses();
		
		setHasOptionsMenu(true);
		
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflator)
	{
		// Inflate the Menu resource to be associated with
		// this activity.
		getActivity().getMenuInflater().inflate(R.menu.menu_list, menu);

		super.onCreateOptionsMenu(menu, menuInflator);
	}


	@Override
	public void onAttach(Activity activity)
	{
		try
		{
			// Assign listener reference from hosting activity.
			_listener = (ICourseControlListener) activity;
		}
		catch (ClassCastException e)
		{
			throw new ClassCastException(activity.toString());
		}
		
		super.onAttach(activity);
	}


	@Override
	public void onResume()
	{
		super.onResume();

		// Just get the list of courses from the database again.
		refreshCourseList();
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id)
	{
		Course course = null;
		
		course = (Course) getListAdapter().getItem(position);
		if (course != null)
		{
			_listener.selectCourse(course);
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch (item.getItemId())
		{
			case R.id.action_add_class:
			{
				_listener.insertCourse();
			}
			default:
			{
				return super.onOptionsItemSelected(item);
			}
		}
	}

	private void refreshCourseList()
	{
		// Get an Array List of Contact objects.
		_courses = Model.getInstance(getActivity()).getCourses();
		
		// Assign list to ArrayAdapter to be used with assigning
		// to the ListFragment list adapter.
		_adapter = new ArrayAdapter<Course>(getActivity(),
		        						    android.R.layout.simple_list_item_1, 
		        						    _courses);
		
		// Assign the adapter.
		setListAdapter(_adapter);	
	}
}