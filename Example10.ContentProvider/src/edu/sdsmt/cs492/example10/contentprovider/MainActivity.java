package edu.sdsmt.cs492.example10.contentprovider;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.widget.SimpleCursorAdapter;
import edu.sdsmt.cs492.example10.contentprovider.model.Contact;
import edu.sdsmt.cs492.example10.contentprovider.model.ContactModel;
import edu.sdsmt.cs492.example10.contentprovider.model.ContactProvider;

public class MainActivity extends Activity implements LoaderCallbacks<Cursor>, 
                                                      IContactControlListener
{

	public final static String FRAGMENT_CONTACT_LIST_TAG = "ContactListTag";
	public final static String FRAGMENT_CONTACT_DETAIL_TAG = "ContactDetailTag";
	
	private final static int LOADER_TAG = 1;
	
	private FragmentManager _fragmentManager;
	private ContactListFragment _fragmentContactList;
	private ContactDetailFragment _fragmentContactDetail;
	
	private LoaderManager _loaderManager;
	private CursorLoader _cursorLoader;
	private SimpleCursorAdapter _adapter;
	
	private ContactModel _model;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		// Associate the layout with host activity.
		setContentView(R.layout.activity_main);
		
		// Get a reference to the fragment manager to 
		// be used for adding/replacing fragments.
		_fragmentManager = getFragmentManager();
		
		_fragmentContactList = (ContactListFragment) _fragmentManager.findFragmentByTag(FRAGMENT_CONTACT_LIST_TAG);
		if (_fragmentContactList == null)
		{
			_fragmentContactList = new ContactListFragment();
		}
		
		_fragmentContactDetail = (ContactDetailFragment) _fragmentManager.findFragmentByTag(FRAGMENT_CONTACT_DETAIL_TAG);
		if (_fragmentContactDetail == null)
		{
			_fragmentContactDetail = new ContactDetailFragment();
		}
		
		assignContactListFragmentAdapter();
		
		if (savedInstanceState == null)
		{
			_fragmentManager.beginTransaction()
			                .replace(R.id.fragmentContainerFrame, _fragmentContactList, FRAGMENT_CONTACT_LIST_TAG)
			                .commit();
		}
		
		// Get single instance to the model to handle
		// all database activity.
		_model = ContactModel.getInstance(this);
		
		// Insert Sample Contacts
//				ContactModel model = new ContactModel(this);
//				model.insertDummyContacts(false);
	}
	
	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args)
	{
		// Event resulting from call to initLoader.
		// Perform the standard query on contact provider
		// to return a cursor to the loader.
		
		Uri uri = ContactProvider.URI_CONTACTS;
		
		String[] projection = Contact.COLUMN_NAMES;

		String selection = null;
		
		String[] selectionArgs = null;
		
		String sortOrder = Contact.COLUMN_CONTACT_NAME + " ASC";
		
		_cursorLoader = new CursorLoader(this, uri, projection, selection, selectionArgs, sortOrder);
		
		return _cursorLoader;
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor cursor)
	{
		if (_adapter != null && cursor != null)
		{
			_adapter.swapCursor(cursor);
		}
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader)
	{
		if (_adapter != null)
		{
			_adapter.swapCursor(null);
		}
	}

	@Override
	public void contactSelect(long id)
	{
		// Display selected contact.
		showContactViewFragment(_model.getContact(id));
	}

	@Override
	public void contactInsert()
	{
		// Display empty contact.
		Contact contact = new Contact();
		showContactViewFragment(contact);
	}

	@Override
	public void contactInsert(Contact contact)
	{
		_model.insertContact(contact);
		_fragmentManager.popBackStackImmediate();
	}

	@Override
	public void contactUpdate(Contact contact)
	{
		_model.updateContact(contact);
		_fragmentManager.popBackStackImmediate();
	}

	@Override
	public void contactDelete(Contact contact)
	{
		_model.deleteContact(contact);
		_fragmentManager.popBackStackImmediate();
	}

	private void assignContactListFragmentAdapter()
	{
		// Get a reference to the loader manager.
		_loaderManager = getLoaderManager();
		
		// Build the adapter, passing null in for the cursor.
		_adapter = new SimpleCursorAdapter(this, 
										   R.layout.fragment_contact_list_item, 
										   null, 
										   new String[] { Contact.COLUMN_CONTACT_NAME, Contact.COLUMN_ID }, 
										   new int[] { R.id.textViewContactName }, 
										   0);
		
		// Assign adapter to the ListFragment underlying listview.
		_fragmentContactList.setListAdapter(_adapter);
		
		// Initialize the loader providing an identifier.
		_loaderManager.initLoader(LOADER_TAG, null, this);
	}

	private void showContactViewFragment(Contact contact)
	{
		
		Bundle arguments = new Bundle();
		arguments.putParcelable(ContactDetailFragment.BUNDLE_KEY_CONTACT, contact);
		_fragmentContactDetail.setArguments(arguments);
		
		_fragmentManager.beginTransaction()
				        .replace(R.id.fragmentContainerFrame, _fragmentContactDetail, FRAGMENT_CONTACT_DETAIL_TAG)
				        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
				        .addToBackStack(null)
				        .commit();
		
	}
}
