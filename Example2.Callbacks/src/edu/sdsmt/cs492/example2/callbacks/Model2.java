package edu.sdsmt.cs492.example2.callbacks;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.TextView;

// NOTE:  Model2 class implements specifically the TextWatcher class.

public class Model2 implements TextWatcher
{
	public static final String TAG = "Callback (Model.2)";
	
	// Simply takes TextView reference and wires up addTextChangedListener.
	public Model2(TextView editTextView)
	{
		editTextView.addTextChangedListener(this);
	}
	
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
	
	@Override
	public String toString()
	{
		super.toString();
		return "Model2 Class";
	}

	private void handleAfterTextChangeEvent(Editable s)
	{
		Log.d(TAG, "After Text Changed:  " + s.toString());
	}
}
