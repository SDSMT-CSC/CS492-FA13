package edu.sdsmt.cs492.example10.contentprovider;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import edu.sdsmt.cs492.example10.contentprovider.model.Contact;

public class ContactDetailFragment extends Fragment
{
	public static final String BUNDLE_KEY_CONTACT = "ContactObject";
	
	private static final String BUNDLE_KEY_UPDATEMODE = "UpdateMode";
	
	private Contact _contact = null;
	private IContactControlListener _listener;
	
	private EditText _editTextName;
	private EditText _editTextPhone;
	private EditText _editTextEmail;
	private EditText _editTextStreet;
	private EditText _editTextCity;
	private Button _buttonSaveContact;
	
	private boolean _inUpdateMode = false;
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		if (savedInstanceState != null)
		{
			// Fragment was destroyed, but state was saved.
			_contact = savedInstanceState.getParcelable(BUNDLE_KEY_CONTACT);
			_inUpdateMode = savedInstanceState.getBoolean(BUNDLE_KEY_UPDATEMODE);
		}
		else
		{
			// Contact needs be retrieved from host Activity.
			if (getArguments().containsKey(BUNDLE_KEY_CONTACT) == true)
			{
				_contact = getArguments().getParcelable(BUNDLE_KEY_CONTACT);
			}
		}
		
		// Tells the host Activity to display the appropriate
		// option menu.
		setHasOptionsMenu(true);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		// Inflate the UI.
		View rootView = inflater.inflate(R.layout.fragment_contact_detail, container, false);

		// Assign instances of Views from the Layout Resource.
		_editTextName = (EditText) rootView.findViewById(R.id.editTextName);
		_editTextPhone = (EditText) rootView.findViewById(R.id.editTextPhone);
		_editTextEmail = (EditText) rootView.findViewById(R.id.editTextEmail);
		_editTextStreet = (EditText) rootView.findViewById(R.id.editTextStreet);
		_editTextCity = (EditText) rootView.findViewById(R.id.editTextCityStateZip);
		
		_buttonSaveContact = (Button) rootView.findViewById(R.id.buttonSaveContact);

		_buttonSaveContact.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				if (_editTextName.getText().toString().isEmpty() == false)
				{
					populateContact();
					
					if (_contact.ContactID > 0)
					{
						// Update the contact in the database.
						_listener.contactUpdate(_contact);
					}
					else
					{
						// Insert the contact into the database.
						_listener.contactInsert(_contact);
					}
					
					_inUpdateMode = false;
				}
				else
				{
					// Alert the user of missing Name field.

					AlertDialog.Builder alertBuilder = new AlertDialog.Builder(getActivity());
					alertBuilder.setTitle(R.string.alert_title_missinginfo);
					alertBuilder.setMessage(R.string.alert_message_missing_name);
					alertBuilder.setPositiveButton(R.string.alert_button_OK, null);
					alertBuilder.show();
				}
				
			}
		});

		return rootView;
	}

	@Override
	public void onAttach(Activity activity)
	{
		try
		{
			// Assign listener reference from hosting activity.
			_listener = (IContactControlListener) activity;
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
		
		// Populate the View.
		displayContact();
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState)
	{
		
		populateContact();
		outState.putParcelable(BUNDLE_KEY_CONTACT, _contact);
		outState.putBoolean(BUNDLE_KEY_UPDATEMODE, _inUpdateMode);
		
		super.onSaveInstanceState(outState);
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflator)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		
		// Only provide the host activity with a menu if there is an 
		// actual course that is being edited.  Otherwise, it is a new
		// course and neither of the Update or Delete menu items should
		// be available.
		if (_contact.ContactID > 0)
		{			
			getActivity().getMenuInflater().inflate(R.menu.menu_contact_detail, menu);
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch (item.getItemId())
		{
			case R.id.action_update_contact:
			{
				// Allow for editing on the fields.
				_editTextName.setEnabled(true);
				_editTextPhone.setEnabled(true);
				_editTextEmail.setEnabled(true);
				_editTextStreet.setEnabled(true);
				_editTextCity.setEnabled(true);

				// Display the Save Contact button.
				_buttonSaveContact.setVisibility(View.VISIBLE);
				
				_inUpdateMode = true;

				return true;
			}
			case R.id.action_delete_contact:
			{
				// Do not allow editing.
				_editTextName.setEnabled(false);
				_editTextPhone.setEnabled(false);
				_editTextEmail.setEnabled(false);
				_editTextStreet.setEnabled(false);
				_editTextCity.setEnabled(false);

				_buttonSaveContact.setVisibility(View.INVISIBLE);

				// Delete Contact from the database.
				_listener.contactDelete(_contact);

				return true;
			}
			default:
			{
				return super.onOptionsItemSelected(item);
			}
		}
	}

	private void populateContact()
	{
		_contact.Name = _editTextName.getText().toString();
		_contact.Phone = _editTextPhone.getText().toString();
		_contact.Email = _editTextEmail.getText().toString();
		_contact.Street = _editTextStreet.getText().toString();
		_contact.City = _editTextCity.getText().toString();
	}

	private void displayContact()
	{
		// Use the member Contact to populate the view.
		_editTextName.setText(_contact.Name);
		_editTextPhone.setText(_contact.Phone);
		_editTextEmail.setText(_contact.Email);
		_editTextStreet.setText(_contact.Street);
		_editTextCity.setText(_contact.City);
		
		if (_contact.ContactID > 0 && _inUpdateMode == false)
		{
			// Do not allow editing.
			_editTextName.setEnabled(false);
			_editTextPhone.setEnabled(false);
			_editTextEmail.setEnabled(false);
			_editTextStreet.setEnabled(false);
			_editTextCity.setEnabled(false);

			// Do not display the Save Contact button initially,
			// since the user is only viewing the contact.
			_buttonSaveContact.setVisibility(View.INVISIBLE);
		}
	}
}
