package com.example.example8.contentprovider.contacts;

import com.example.example8.contentprovider.use.R;

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

public class MainActivity extends Activity implements IContactListener
{

	private final static String FRAGMENT_LIST_TAG = "ContactListTag";
	private final static String FRAGMENT_DETAIL_TAG = "ContactViewTag";
	
	private FragmentManager _fragmentManager;
	private ContactListFragment _fragmentList;
	private ContactDetailFragment _fragmentDetail;
	
	private ContentResolver _contentResolver;
	private SimpleCursorAdapter _adapter;
	
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
		refreshAdapter();
		
		// Only add/replace the list fragment if the bundle is empty; otherwise,
		// the activity is being re-created so keep the fragment that is already
		// displayed.
		if (savedInstanceState == null)
		{
			_fragmentManager.beginTransaction()
			                .replace(R.id.fragmentContainerFrame, _fragmentList, FRAGMENT_LIST_TAG)
			                .commit();
		}
		

	}


	@Override
	public void onContactSelected(String lookupKey)
	{
		
		Cursor cursor = getContact(lookupKey); 
		
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
	}


	@Override
	public SimpleCursorAdapter getCursorAdapter()
	{
		return _adapter;
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


	private void refreshAdapter()
	{
		
		Cursor cursor = getContacts();
		
		_adapter = new SimpleCursorAdapter(this, 
										   R.layout.contact_list_row, 
										   cursor, 
										   new String[] { ContactsContract.Contacts.DISPLAY_NAME }, 
										   new int[] { R.id.textViewName }, 
										   CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
	}
	
	private Cursor getContacts()
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
		
		return _contentResolver.query(uri, projection, selection, selectionArgs, sortOrder);
	}
	
	private Cursor getContact(String lookupKey)
	{
		Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
		
		String[] projection = new String[]
		{
			ContactsContract.Contacts.DISPLAY_NAME,
			ContactsContract.CommonDataKinds.Phone.NUMBER,
			ContactsContract.CommonDataKinds.Phone.TYPE
		};

		String selection = ContactsContract.CommonDataKinds.Phone.LOOKUP_KEY + " = ?";
		
		String[] selectionArgs = new String[] { lookupKey };
		
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
