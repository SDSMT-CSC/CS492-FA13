package edu.sdsmt.cs492.example2.callbacks;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.EditText;

public class MainActivity extends Activity
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// Assign view to variable.
		EditText editTextView = (EditText) findViewById(R.id.editTextView);
		
		// Instantiate and pass reference to EditText to Model1.
		Model1 model1 = new Model1(editTextView);
		Log.d(Model1.TAG, model1.toString());
		
		// Instantiate and pass reference to EditText to Model2.
		Model2 model2 = new Model2(editTextView);
		Log.d(Model2.TAG, model2.toString());
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
