package com.brinkcarp.addressbook;

import com.brinkcarp.addressbook.Model.Contact;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

public class DetailFragment extends Fragment {
	/**
	 * The object to handle all alterations to the Contact database
	 */
	private IContactControlListener _listener;
	private Contact _contact;
	private boolean _isOrientationChanging = false;

	/**
	 * Tell the host that it has an options menu
	 */
	@Override
    public void onCreate(Bundle savedInstanceState)
    {
            super.onCreate(savedInstanceState);
            
            // Tell the host activity that an options menu is
            // associated with this fragment.
            setHasOptionsMenu(true);
            
    }
	
	/**
	 * When the fragment is called by the activity, inflate the custom layout
	 * fragment_fetail, and return the resulting view to the activity.
	 */
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState) {
		Log.d("AddressBook", "In onCreateView()");
		
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail, container, false);
    }
	
	/**
	 * When the fragment is attached to the activity, get the activity as an
	 * IContactControlListener to send all Save requests to it
	 * Also tell the main activity to load the contact's fields into the fragment's
	 * EditText widgets
	 */
	@Override
    public void onAttach(Activity activity)
    {
		Log.d("AddressBook", "In onAttach()");
        try
        {
            // Assign listener reference from host activity.
            _listener = (IContactControlListener) activity;
            //MainActivity main = (MainActivity) activity;
            //main.loadContactFields();
        }
        catch (ClassCastException e)
        {
            throw new ClassCastException(activity.toString());
        }
        
        super.onAttach(activity);
    }
	
	/**
	 * When the save button is pushed, tell the listener to update the contact.
	 */
	/*
	public void addressSaveButton_OnClick(View v){
		
	}
	*/
	
	@Override
    public void onResume()
    {
        super.onResume();

        // If we are changing orientation, use the existing _course
        // member to populate the view.
        if (_isOrientationChanging == false)
        {
            // Get a reference to the course that was selected from
            // the list through the listener interface.
            _contact = _listener.getContact();
        }
        
        displayContact();
    }
    
    @Override
    public void onPause()
    {
        // Provides a mechanism by which the Fragment knows if the
        // host Activity is being re-created. If so, we will want
        // to just use the currently selected _course object to
        // populate the view which is possible because we are using
        // setRetainInstance(true).
        _isOrientationChanging = getActivity().isChangingConfigurations();
        
        super.onPause();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflator)
    {
        // Only provide the host activity with a menu if there is an
        // actual course that is being edited. Otherwise, it is a new
        // course and neither of the Update or Delete menu items should
        // be available.
        if (_contact.ContactID > 0)
        {
            // Inflate the menu; this adds items to the action bar if it is present.
            getActivity().getMenuInflater().inflate(R.menu.menu_detail, menu);
        }
        
        super.onCreateOptionsMenu(menu, menuInflator);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
    	Log.d("AddressBook", "DetailFragment options item selected");
        switch (item.getItemId())
        {
            case R.id.menu_delete:
            {
                _listener.deleteContact(_contact);
                return true;
            }
            default:
            {
                return super.onOptionsItemSelected(item);
            }
        }
    }

    private void displayContact()
    {
        if (_contact.ContactID > 0)
        {
        	//Pre-fill text fields from contactEditText nameText = (EditText) getView().findViewById(R.id.EditTextName);
        	EditText nameText = (EditText) getView().findViewById(R.id.EditTextName);
        	nameText.setText(_contact.ContactName);
    		
    		EditText phoneText = (EditText) getView().findViewById(R.id.EditTextPhone);
    		phoneText.setText(_contact.PhoneNumber);
    		
    		EditText emailText = (EditText) getView().findViewById(R.id.EditTextEmail);
    		emailText.setText(_contact.Email);
    		
    		EditText address1Text = (EditText) getView().findViewById(R.id.EditTextAddress1);
    		address1Text.setText(_contact.StreetAddress);
    		
    		EditText address2Text = (EditText) getView().findViewById(R.id.EditTextAddress2);
    		address2Text.setText(_contact.City);
        }
        else
        {
            EditText nameText = (EditText) getView().findViewById(R.id.EditTextName);
        	nameText.setText("");
        	
        	EditText phoneText = (EditText) getView().findViewById(R.id.EditTextPhone);
    		phoneText.setText("");
    		
    		EditText emailText = (EditText) getView().findViewById(R.id.EditTextEmail);
    		emailText.setText("");
    		
    		EditText address1Text = (EditText) getView().findViewById(R.id.EditTextAddress1);
    		address1Text.setText("");
    		
    		EditText address2Text = (EditText) getView().findViewById(R.id.EditTextAddress2);
    		address2Text.setText("");
        }
    }
}
