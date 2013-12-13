package com.brinkcarp.addressbook;

import java.util.List;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import com.brinkcarp.addressbook.Model.Contact;

/*
 * The main activity of the app - uses an AddressFragment as its UI
 */
public class MainActivity extends Activity implements IContactControlListener {

	private final static String FRAGMENT_LIST_TAG = "ContactListTag";
	private final static String FRAGMENT_DETAIL_TAG = "ContactViewTag";
	
	private FragmentManager _fragmentManager;
	private AddressFragment _fragmentList;
	private DetailFragment _fragmentDetail;
	
	private Model _model;
	private ArrayAdapter<Contact> _adapter;
	private List<Contact> _contacts;
	private Contact _contact;
	
	@Override
    protected void onCreate(Bundle savedInstanceState)
    {
		Log.d("AddressBook", "In onCreate");
		
        super.onCreate(savedInstanceState);
        
        // Assign the layout to the activity.
        setContentView(R.layout.activity_main);
        
        // Get a reference to the fragment manager to
        // be used for adding/replacing fragments.
        _fragmentManager = getFragmentManager();
        
        // If the fragment is not found, create it.
        _fragmentList = (AddressFragment) _fragmentManager.findFragmentByTag(FRAGMENT_LIST_TAG);
        if (_fragmentList == null)
        {
                _fragmentList = new AddressFragment();
        }
        
        // If the fragment is not found, create it.
        _fragmentDetail = (DetailFragment) _fragmentManager.findFragmentByTag(FRAGMENT_DETAIL_TAG);
        if (_fragmentDetail == null)
        {
                _fragmentDetail = new DetailFragment();
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
        
        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())){
        	Log.d("AddressBook", "Starting with search");
        	String query = intent.getStringExtra(SearchManager.QUERY);
        	doSearch(query);
        }
        else{
        	refreshArrayAdapter();
        }
        
        //Force the list to show, even if it's empty
        //_fragmentList.setListShown(true);
        
    }

	/**
	 * Like refreshArrayAdapter but with a search query
	 * @param query
	 */
	private void doSearch(String query) {
		Log.d("AddressBook", "In doSearch");
		_contacts = Model.getInstance(this).getContacts(query);
		
		_adapter = new ArrayAdapter<Contact>(this,
											 android.R.layout.simple_list_item_1,
											 _contacts);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		Log.d("AddressBook", "In onCreateOptionsMenu");
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.main, menu);
		super.onCreateOptionsMenu(menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Log.d("AddressBook", "MainActivity option selected");
	    // Handle presses on the action bar items
	    switch (item.getItemId()) {
	        case R.id.menu_add:
	        	insertContact();
	        	return true;
	        case R.id.menu_delete:
	        	deleteContact(_contact);
	        	return true;
	        case R.id.menu_search:
	        	Log.d("AddressBook", "Called search menu item");
	        	onSearchRequested();
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	
	/**
	 * When the state is saved, save the values from the Contact
	 */
	@Override
	protected void onSaveInstanceState(Bundle outState){
		//super.onSaveInstanceState(outState);

		if(_contact != null){
			outState.putLong(Model.KEY_ID, _contact.ContactID);
			outState.putString(Model.KEY_PHONE, _contact.PhoneNumber);
			outState.putString(Model.KEY_EMAIL, _contact.Email);
			outState.putString(Model.KEY_STREET, _contact.StreetAddress);
			outState.putString(Model.KEY_CITY, _contact.City);
		}
	}

	/**
	 * When the state is loaded, restore the values in Contact from the bundle
	 */
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState){
		super.onRestoreInstanceState(savedInstanceState);
		
		_contact = new Contact();
		if(savedInstanceState.containsKey(Model.KEY_ID)){
			_contact.ContactID = savedInstanceState.getLong(Model.KEY_ID);
			_contact.PhoneNumber = savedInstanceState.getString(Model.KEY_PHONE);
			_contact.Email = savedInstanceState.getString(Model.KEY_EMAIL);
			_contact.StreetAddress = savedInstanceState.getString(Model.KEY_STREET);
			_contact.City = savedInstanceState.getString(Model.KEY_CITY);
		}
	}
	
	/**
	 * Called internally to replace the list fragment with the detail fragment
	 * Also passes the selected contact to the detail fragment
	 */
	private void showDetailFragment(){
		//Replace the list fragment with the detail fragment
		_fragmentManager.beginTransaction()
						.replace(R.id.fragmentContainerFrame, _fragmentDetail, FRAGMENT_DETAIL_TAG)
						.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
						.addToBackStack(null)
						.commit();
	}
	
	/**
	 * Loads the detail fragment with a given new contact
	 */
	@Override
	public void insertContact(Contact contact) {
		_contact = contact;
		showDetailFragment();
	}
	
	/**
	 * Loads the detail fragment, creating a new contact
	 */
	@Override
	public void insertContact(){
		_contact = new Contact();
		showDetailFragment();
	}

	/**
	 * Removes the specified contact from the list and from the database,
	 * then moves back from the detail fragment to the list fragment
	 */
	@Override
	public void deleteContact(Contact contact) {
		_adapter.remove(contact);
		_adapter.sort(contact);
		_adapter.notifyDataSetChanged();
		
		_model.deleteContact(contact);
		_fragmentManager.popBackStackImmediate();
	}

	/**
	 * Updates the contact with changed information, re-sorts the list,
	 * then moves back from the detail fragment to the list fragment.
	 */
	@Override
	public void updateContact(Contact contact) {
		Log.d("AddressBook", "In updateContact");
		_adapter.remove(contact);
		_adapter.add(contact);
		_adapter.sort(contact);
		_adapter.notifyDataSetChanged();
		
		_model.updateContact(contact);
		_fragmentManager.popBackStackImmediate();
	}
	
	/**
	 * Add a new contact to the adapter and the model
	 * @param contact
	 */
	@Override
	public void addContact(Contact contact){
		_adapter.add(contact);
		_adapter.sort(contact);
		_adapter.notifyDataSetChanged();
		
		_model.insertContact(_contact);
		_fragmentManager.popBackStackImmediate();
	}

	/**
	 * Opens the detail fragment with a selected existing contact
	 */
	@Override
	public void selectContact(Contact contact) {
		_contact = contact;
		showDetailFragment();
	}

	/**
	 * Returns the currently-selected contact
	 */
	@Override
	public Contact getContact() {
		return _contact;
	}

	/**
	 * Returns the already-generated array adapter for the model
	 */
	@Override
	public ArrayAdapter<Contact> getContactArrayAdapter() {
		return _adapter;
	}
	
	/**
	 * Creates a new array adapter to bridge the list of Contact objects
	 * with the specified layout.
	 * NOTE: CURRENTLY USES HARD-CODED LAYOUT 
	 */
	private void refreshArrayAdapter(){
		Log.d("AddressBook", "In refreshArrayAdapter");
		_contacts = Model.getInstance(this).getContacts();
		
		_adapter = new ArrayAdapter<Contact>(this,
											 android.R.layout.simple_list_item_1,
											 _contacts);
	}
	
	/**
	 * When the Address Save button is pressed, save the values of the text fields,
	 * and then save the contact to the database
	 * @param v
	 */
	public void addressSaveButton_OnClick(View v){
		//_fragmentDetail.addressSaveButton_OnClick(v);
		
		//Load text fields into the contact
		EditText nameText = (EditText) findViewById(R.id.EditTextName);
		_contact.ContactName = nameText.getText().toString();
		
		EditText phoneText = (EditText) findViewById(R.id.EditTextPhone);
		_contact.PhoneNumber = phoneText.getText().toString();
		
		EditText emailText = (EditText) findViewById(R.id.EditTextEmail);
		_contact.Email = emailText.getText().toString();
		
		EditText address1Text = (EditText) findViewById(R.id.EditTextAddress1);
		_contact.StreetAddress = address1Text.getText().toString();
		
		EditText address2Text = (EditText) findViewById(R.id.EditTextAddress2);
		_contact.City = address2Text.getText().toString();
		
		
		Log.d("AddressBook", "In addressSaveButton_OnClick()");
		//Log.d("AddressBook", _contact.ToString());
		if(_contact.ContactID >= 0)
			updateContact(_contact);
		else{
			addContact(_contact);
		}
		Log.d("AddressBook", "Finished addressSaveButton_OnClick()");
	}

}