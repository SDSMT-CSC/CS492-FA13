package edu.sdsmt.cs492.example10.contentprovider.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class ContactModel extends SQLiteOpenHelper
{

	private static final String TAG = "AddressBookFragmentApp";

	private static final String DATABASE_NAME = "AddressBook.db";
	private static final int DATABASE_VERSION = 1;
	

	private static final String CREATE_TABLE_CONTACT =
			        "CREATE TABLE " +
					Contact.TABLE_NAME +
					"(" + Contact.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
					Contact.COLUMN_CONTACT_NAME + " TEXT, " +
					Contact.COLUMN_PHONE + " TEXT, " +
					Contact.COLUMN_EMAIL + " TEXT, " +
					Contact.COLUMN_STREET + " TEXT, " +
					Contact.COLUMN_CITY + " TEXT);";

	private SQLiteDatabase _db;
	private static ContactModel _instance;
	
	private Context _context;

	public ContactModel(Context context)
	{
		
		// Call the parent class and pass the actual name and version of the
		// database to be created. The version will be used in the future for
		// determine whether onUpgrade() is called from the SQLiteOpenHelper
		// extension.
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		
		_context = context;
	}

	@Override
	public void onCreate(SQLiteDatabase db)
	{
		// Execute the CREATE TABLE statement defined as a const.
		db.execSQL(CREATE_TABLE_CONTACT);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
	{
		// If there is ever a need to upgrade the database and/or table.
		// Compare old and new versions to determine if modifications
		// to the database are necessary. Typically, this will be done with
		// ALTER TABLE or CREATE TABLE SQL statements depending on the
		// change required.

		if (newVersion == 2)
		{
			// No version 2 upgrade process yet.
		}
	}
	
	public static synchronized ContactModel getInstance(Context context)
	{
		// Used to synchronize access and force singleton on the 
		// database helper object.
		if (_instance == null)
		{
			_instance = new ContactModel(context);
		}
		
		return _instance;
	}

	public int insertDummyContacts(boolean largeQuantity)
	{
		Contact contact;
		int count = 0;
		
		contact = new Contact();
		contact.Name = "Brian Butterfield";
		contact.Phone = "605-390-0395";
		contact.Email = "brianb@innovsys.com";
		contact.Street = "123 Main Street";
		contact.City = "Rapid City, SD";
		insertContact(contact);
		count++;
		
		contact = new Contact();
		contact.Name = "Toni Logar";
		contact.Phone = "605-555-1212";
		contact.Email = "toni@sdsmt.edu";
		contact.Street = "123 Main Street";
		contact.City = "Somewhere, HI";
		insertContact(contact);
		count++;
		
		contact = new Contact();
		contact.Name = "David Springhetti";
		contact.Phone = "605-555-1212";
		contact.Email = "davids@innovsys.com";
		contact.Street = "123 Main Street";
		contact.City = "Somewhere, SD";
		insertContact(contact);
		count++;
		
		if (largeQuantity == true)
		{
			for (int i = 1; i <= 300; i++)
			{
				contact = new Contact();
				contact.Name = String.valueOf(i) + " Name";
				contact.Phone = String.valueOf(i) + "605-555-1212";
				contact.Email = String.valueOf(i) + "email@domain.com";
				contact.Street = String.valueOf(i) + "123 Main Street";
				contact.City = String.valueOf(i) + "Somewhere, HI";
				insertContact(contact);
				
				count++;
			}
		}
		
		return count;
	}

	public long insertContact(Contact contact)
	{
		// Take parameters and pass to method to populate the
		// ContentValues data structure.
		ContentValues values = contact.getContentValues();
	
		// Open the database connect, keep it close to the actual operation.
		openDBConnection();
	
		// Execute query to update the specified contact.
		long id = _db.insert(Contact.TABLE_NAME, null, values);
	
		Log.d(TAG, "ContactID inserted = " + String.valueOf(id));
	
		// Close the database connection as soon as possible.
		closeDBConnection();
		
		if (id > 0)
		{
			// Notify any providers that an update has occurred.
			notifyProviderOnContactChanges();
		}
		
		return id;
	}

	public int updateContact(Contact contact)
	{
		// Take parameters and pass to method to populate the
		// ContentValues data structure.
		ContentValues values = contact.getContentValues();

		// Open the database connect, keep it close to the actual operation.
		openDBConnection();

		// Execute query to update the specified contact.
		int rowsAffected = _db.update(Contact.TABLE_NAME,
							values,
							Contact.COLUMN_ID + " = ?",
							new String[] { String.valueOf(contact.ContactID) });

		// Close the database connection as soon as possible.
		closeDBConnection();

		if (rowsAffected > 0)
		{
			// Notify any providers that an update has occurred.
			notifyProviderOnContactChanges();
		}
		
		return rowsAffected;
	}

	public int deleteContact(Contact contact)
	{
		// Open the database connect, keep it close to the actual operation.
		openDBConnection();

		// Execute query to delete the specified contact.
		int rowsAffected = _db.delete(Contact.TABLE_NAME,
				Contact.COLUMN_ID + " = ?",
				new String[] { String.valueOf(contact.ContactID) });

		// Close the database connection as soon as possible.
		closeDBConnection();

		if (rowsAffected > 0)
		{
			// Notify any providers that an update has occurred.
			notifyProviderOnContactChanges();
		}
		
		return rowsAffected;
	}

	public int deleteAllContacts()
	{
		// Open the database connect, keep it close to the actual operation.
		openDBConnection();

		// Execute query to delete the specified contact.
		int rowsAffected = _db.delete(Contact.TABLE_NAME, null, null);

		// Close the database connection as soon as possible.
		closeDBConnection();

		if (rowsAffected > 0)
		{
			// Notify any providers that an update has occurred.
			notifyProviderOnContactChanges();
		}
		
		return rowsAffected;
	}
	
	public Contact getContact(long contactID)
	{
		// Used specifically by MainActivity so the ViewContactFragment
		// only has to be aware of the Contact class, not Model or Cursors.
		Cursor cursor = null;
		Contact contact = null;
		
		cursor = queryContact(contactID);
		
		if (cursor.moveToFirst() == true)
		{
			contact = new Contact(cursor);
		}
		
		cursor.close();
		cursor = null;
		
		closeDBConnection();
		
		return contact;
	}

	public Cursor queryContact(long contactID)
	{
		Cursor cursor = null;
		
		openDBConnection();
		
		// Return the specific contact row based on ID passed.
		cursor = _db.query(Contact.TABLE_NAME,
						   Contact.COLUMN_NAMES,
						   Contact.COLUMN_ID + "=" + contactID,
						   null,
						   null,
						   null,
						   null);

		return cursor;
	}
	
	public synchronized Cursor queryContacts(String sortOrder)
	{
		Cursor cursor = null;
		
		openDBConnection();
		
		cursor = _db.query(Contact.TABLE_NAME,
						   Contact.COLUMN_NAMES,
						   null,
						   null,
						   null,
						   null,
						   sortOrder);
		
		return cursor;
	}

	private void openDBConnection()
	{
		// Opens connection to the database for writing specifically.
		_db = getWritableDatabase();
	}

	private void closeDBConnection()
	{
		if (_db != null && _db.isOpen() == true)
		{
			// Close connection to database if open.
			_db.close();
		}
	}
	
	private void notifyProviderOnContactChanges()
	{
		// Tell the provider the underlying data has changed.
		_context.getContentResolver().notifyChange(ContactProvider.URI_CONTACTS, null);
	}
}
