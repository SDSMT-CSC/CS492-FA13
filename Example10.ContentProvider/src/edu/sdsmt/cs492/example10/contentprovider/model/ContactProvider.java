package edu.sdsmt.cs492.example10.contentprovider.model;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

public class ContactProvider extends ContentProvider
{
	
	public static final String AUTHORITY = "edu.sdsmt.cs492.example10.provider";
	public static final String SCHEME = "content://";
	
	public static final String CONTACTS = SCHEME + AUTHORITY + "/contact";
	public static final String CONTACT_BASE = CONTACTS + "/";
	
	public static final Uri URI_CONTACTS = Uri.parse(CONTACTS);
	
	public ContactProvider()
	{
	}

	@Override
	public boolean onCreate()
	{
		return true;
	}

	@Override
	public String getType(Uri uri)
	{
		// TODO: Implement this to handle requests for the MIME type of the data
		// at the given URI.
		throw new UnsupportedOperationException("Not yet implemented");
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder)
	{
		Cursor cursor = null;
		
		if (URI_CONTACTS.equals(uri) == true)
		{
			cursor = ContactModel.getInstance(getContext()).queryContacts(sortOrder);
			
			// Indicates this provider will listen for underlying
			// data changes.
			cursor.setNotificationUri(getContext().getContentResolver(), URI_CONTACTS);
		}
		else if (uri.toString().startsWith(CONTACT_BASE) == true)
		{
			final long id = Long.parseLong(uri.getLastPathSegment());
			cursor = ContactModel.getInstance(getContext()).queryContact(id);
			
			// Indicates this provider will listen for underlying
			// data changes.
			cursor.setNotificationUri(getContext().getContentResolver(), URI_CONTACTS);
		}
		else
		{
			throw new UnsupportedOperationException("Not yet implemented");
		}
		
		return cursor;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values)
	{
		// TODO: Implement this to handle requests to insert a new row.
		throw new UnsupportedOperationException("Not yet implemented");
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs)
	{
		// TODO: Implement this to handle requests to update one or more rows.
		throw new UnsupportedOperationException("Not yet implemented");
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs)
	{
		// Implement this to handle requests to delete one or more rows.
		throw new UnsupportedOperationException("Not yet implemented");
	}
}
