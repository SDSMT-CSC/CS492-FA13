package edu.sdsmt.cs492.example9.cursorloader;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ContactDetailFragment extends Fragment
{

	private IContactListener _listener;
	
	private TextView _textViewName;
	private TextView _textViewPhoneNumber;
	private TextView _textViewPhoneNumberType;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		// Inflate the UI.
		View rootView = inflater.inflate(R.layout.contact_detail_fragment, container, false);

		// Assign instances of Views from the Layout Resource.
		_textViewName = (TextView) rootView.findViewById(R.id.textViewContactName);
		_textViewPhoneNumber = (TextView) rootView.findViewById(R.id.textViewPhone);
		_textViewPhoneNumberType = (TextView) rootView.findViewById(R.id.textViewPhoneType);

		return rootView;
	}


	@Override
	public void onAttach(Activity activity)
	{
		try
		{
			// Assign listener reference from host activity.
			_listener = (IContactListener) activity;
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

		displayDetail();
	}
	
	private void displayDetail()
	{
		_textViewName.setText(_listener.getContactName());
		_textViewPhoneNumber.setText(_listener.getPhoneNumber());
		_textViewPhoneNumberType.setText(_listener.getPhoneNumberType());
	}

}
