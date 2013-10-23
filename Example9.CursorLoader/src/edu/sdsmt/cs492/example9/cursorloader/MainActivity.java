package edu.sdsmt.cs492.example9.cursorloader;

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
import android.provider.ContactsContract;
import android.widget.SimpleCursorAdapter;

public class MainActivity extends Activity implements LoaderCallbacks<Cursor>, 
                                                      IContactListener
{
	private final static String FRAGMENT_LIST_TAG = "ContactListTag";
	private final static String FRAGMENT_DETAIL_TAG = "ContactViewTag";
	
	private final static int LOADER_TAG = 1;
	
	private final static String KEY_CONTACT_NAME = "Name";
	private final static String KEY_CONTACT_PHONE = "Phone";
	private final static String KEY_CONTACT_PHONE_TYPE = "Type";
	
	private LoaderManager _loaderManager;
	private CursorLoader _cursorLoader;
	private SimpleCursorAdapter _adapter;
	
	private FragmentManager _fragmentManager;
	private ContactListFragment _fragmentList;
	private ContactDetailFragment _fragmentDetail;
	
	private String _contactName;
	private String _phoneNumber;
	private String _phoneType;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// Get a reference to the fragment manager to 
		// be used for adding/replacing fragments.
		_fragmentManager = getFragmentManager();
		
		// If the fragment is not found, create it.
		_fragmentList = (ContactListFragment) _fragmentManager.findFragmentByTag(FRAGMENT_LIST_TAG);
		if (_fragmentList == null)
		{
			_fragmentList = new ContactListFragment();
		}
		
		// If the fragment is not found, create it.
		_fragmentDetail = (ContactDetailFragment) _fragmentManager.findFragmentByTag(FRAGMENT_DETAIL_TAG);
		if (_fragmentDetail == null)
		{
			_fragmentDetail = new ContactDetailFragment();
		}
		
		// Use the LoaderManager to init a cursor loader to load the
		// contact list.
		assignListAdapter();
		
		// Only add/replace the list fragment if the bundle is empty; otherwise,
		// the activity is being re-created so keep the fragment that is already
		// displayed.
		if (savedInstanceState == null)
		{
			_fragmentManager.beginTransaction()
			                .replace(R.id.fragmentContainerFrame, _fragmentList, FRAGMENT_LIST_TAG)
			                .commit();
		}
		else
		{
			// Restore the detail display values.
			_contactName = savedInstanceState.getString(KEY_CONTACT_NAME);
			_phoneNumber = savedInstanceState.getString(KEY_CONTACT_PHONE);
			_phoneType = savedInstanceState.getString(KEY_CONTACT_PHONE_TYPE);
		}
				
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState)
	{
		// Just because, save the detail display items for
		// retrieval on configuration change.
		outState.putString(KEY_CONTACT_NAME, _contactName);
		outState.putString(KEY_CONTACT_PHONE, _phoneNumber);
		outState.putString(KEY_CONTACT_PHONE_TYPE, _phoneType);
		
		super.onSaveInstanceState(outState);
	}

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args)
	{

		// Event resulting from call to initLoader.
		// Perform the standard query on contact provider
		// to return a cursor to the loader.
		
		Uri uri = ContactsContract.Contacts.CONTENT_URI;
		
		String[] projection = new String[]
		{
			ContactsContract.Contacts._ID,
			ContactsContract.Contacts.LOOKUP_KEY,
			ContactsContract.Contacts.DISPLAY_NAME
		};

		String selection = ContactsContract.Contacts.IN_VISIBLE_GROUP + " = ?";
		
		String[] selectionArgs = new String[] { "1" };
		
		String sortOrder = ContactsContract.Contacts.DISPLAY_NAME + " ASC";
		
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
	public void onContactSelected(long id)
	{
		// Just retrieve the phone number for the single
		// contact that was selected from the list.
		Cursor cursor = getContact(id); 
		
		if (cursor.moveToFirst() == true)
		{
			_contactName = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
			
			_phoneNumber = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
			
			int phoneType = cursor.getInt(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.TYPE));
		
			// Provide user-friendly representation for the type of phone.
			if (phoneType == 1)
			{
				_phoneType = "(Home)";
			}
			else if (phoneType == 2)
			{
				_phoneType = "(Mobile)";
			}
			else if (phoneType == 3)
			{
				_phoneType = "(Work)";
			}
			else{
				_phoneType = "(Other)";
			}
			
			// Display the detail fragment.
			showDetailFragment();
		}
	}

	@Override
	public String getContactName()
	{
		return _contactName;
	}

	@Override
	public String getPhoneNumber()
	{
		return _phoneNumber;
	}

	@Override
	public String getPhoneNumberType()
	{
		return _phoneType;
	}
	
	private void assignListAdapter()
	{
		// Get a reference to the loader manager.
		_loaderManager = getLoaderManager();
		
		// Build the adapter, passing null in for the cursor.
		_adapter = new SimpleCursorAdapter(this, 
										   R.layout.contact_list_row, 
										   null, 
										   new String[] { ContactsContract.Contacts.DISPLAY_NAME }, 
										   new int[] { R.id.textViewName }, 
										   0);
		
		// Assign adapter to the ListFragment underlying listview.
		_fragmentList.setListAdapter(_adapter);
		
		// Initialize the loader providing an identifier.
		_loaderManager.initLoader(LOADER_TAG, null, this);
	}
	
	private Cursor getContact(long id)
	{
		// Does not use Loader because we really do not want the detail
		// display to change if underlying contact changes.  Also, we 
		// are only loading one contact knowing it is being done on 
		// UI thread is acceptable.
		
		Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;

		String[] projection = new String[]
		{
			ContactsContract.Contacts.DISPLAY_NAME,
			ContactsContract.CommonDataKinds.Phone.NUMBER,
			ContactsContract.CommonDataKinds.Phone.TYPE
		};

		String selection = ContactsContract.CommonDataKinds.Phone.RAW_CONTACT_ID + " = ?";
		
		String[] selectionArgs = new String[] { String.valueOf(id) };
		
		String sortOrder = null;
		
		return getContentResolver().query(uri, projection, selection, selectionArgs, sortOrder);
	}

	private void showDetailFragment()
	{
		
		// Perform the fragment transaction to display the details fragment.
		_fragmentManager.beginTransaction()
				        .replace(R.id.fragmentContainerFrame, _fragmentDetail, FRAGMENT_DETAIL_TAG)
				        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
				        .addToBackStack(null)
				        .commit();
	}

}
