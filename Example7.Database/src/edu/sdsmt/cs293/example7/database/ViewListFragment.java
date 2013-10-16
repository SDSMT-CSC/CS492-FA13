package edu.sdsmt.cs293.example7.database;


import android.app.Activity;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import edu.sdsmt.cs293.example7.database.Model.Course;

public class ViewListFragment extends ListFragment
{
	//IMPORTANT NOTE: There are no public members.
	
	private ICourseControlListener _listener;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		// Insert Sample Courses
		Model model = new Model(getActivity());
		model.insertSampleCourses();
		
		// Tell the host activity that an options menu is
		// associated with this fragment.
		setHasOptionsMenu(true);
		
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflator)
	{
		// Inflate the Menu resource to be associated with
		// this activity.
		getActivity().getMenuInflater().inflate(R.menu.menu_list, menu);

		// Call super to give the inflated menu back to the host activity.
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
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch (item.getItemId())
		{
			case R.id.action_add_course:
			{
				_listener.insertCourse();
			}
			default:
			{
				return super.onOptionsItemSelected(item);
			}
		}
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

	private void refreshCourseList()
	{
		// Assign the adapter.
		setListAdapter(_listener.getCourseArrayAdapter());	
	}
}