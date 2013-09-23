package edu.sdsmt.cs492.example2.callbacks;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.TextView;

// NOTE:  Model1 knows nothing about the TextWatcher interface.

public class Model1
{
	public static final String TAG = "Example2.Callbacks (Model.1)";
	
	public Model1(TextView editTextView)
	{
		// Provided anonymous inner class TextWatcher.
		editTextView.addTextChangedListener(new TextWatcher() 
		{
			@Override
			public void afterTextChanged(Editable s)
			{
				handleAfterTextChangeEvent(s);
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after)
			{
				// Do nothing.
			}
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count)
			{
				// Do nothing.
			}
		});
	}
	
	@Override
	public String toString()
	{
		super.toString();
		return "Model1 Class";
	}

	private void handleAfterTextChangeEvent(Editable s)
	{
		Log.d(TAG, "After Text Changed:  " + s.toString());
	}
}
