package com.example.example8.contentprovider.contacts;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.CursorAdapter;
import android.widget.SimpleCursorAdapter;

import com.example.example8.contentprovider.use.R;

public class MainActivity extends Activity implements IContactListener
{

	private final static String FRAGMENT_LIST_TAG = "ContactListTag";
	private final static String FRAGMENT_DETAIL_TAG = "ContactViewTag";
	
	private final static String KEY_CONTACT_NAME = "Name";
	private final static String KEY_CONTACT_PHONE = "Phone";
	private final static String KEY_CONTACT_PHONE_TYPE = "Type";
	
	private FragmentManager _fragmentManager;
	private ContactListFragment _fragmentList;
	private ContactDetailFragment _fragmentDetail;
	
	private ContentResolver _contentResolver;
	private SimpleCursorAdapter _adapter;
	private Cursor _cursor;
	
	private String _contactName;
	private String _phoneNumber;
	private String _phoneType;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		// Assign the layout to the activity.
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
		
		// Get a reference to a ContentResolver and query the Contacts.
		_contentResolver = getContentResolver();
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
			// Device was rotated, so get the saved state.
			_contactName = savedInstanceState.getString(KEY_CONTACT_NAME);
			_phoneNumber = savedInstanceState.getString(KEY_CONTACT_PHONE);
			_phoneType = savedInstanceState.getString(KEY_CONTACT_PHONE_TYPE);
		}
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState)
	{
		// Store the values that are necessary for the detail
		// fragment display so rotation is covered.
		outState.putString(KEY_CONTACT_NAME, _contactName);
		outState.putString(KEY_CONTACT_PHONE, _phoneNumber);
		outState.putString(KEY_CONTACT_PHONE_TYPE, _phoneType);
		
		super.onSaveInstanceState(outState);
	}
	
	@Override
	protected void onDestroy()
	{
		// With this implementation, the responsibility for cursor
		// life management belongs to this activity.
		if (_cursor != null)
		{
			_cursor.close();
		}
		_cursor = null;
		
		super.onDestroy();
	}

	@Override
	public void onContactSelected(long id)
	{
		
		Cursor cursor = getContact(id); 
		
		if (cursor.moveToFirst() == true)
		{
			_contactName = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
			
			_phoneNumber = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
			
			int phoneType = cursor.getInt(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.TYPE));
		
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
			
			showDetailFragment();
		}
		
		cursor.close();
		cursor = null;
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
		
		// Use the content resolver to get a list of
		// contacts from the People app content provider
		// and assign member cursor.
		getContacts();
		
		// Create the cursor adapter and assign cursor from above.
		_adapter = new SimpleCursorAdapter(this, 
										   R.layout.contact_list_row, 
										   _cursor, 
										   new String[] { ContactsContract.Contacts.DISPLAY_NAME }, 
										   new int[] { R.id.textViewName }, 
										   CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
		
		// Assign adapter to list fragment.
		_fragmentList.setListAdapter(_adapter);
		
	}
	
	private void getContacts()
	{
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
		
		_cursor = _contentResolver.query(uri, projection, selection, selectionArgs, sortOrder);
	}
	
	private Cursor getContact(long id)
	{
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
		
		return _contentResolver.query(uri, projection, selection, selectionArgs, sortOrder);
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
