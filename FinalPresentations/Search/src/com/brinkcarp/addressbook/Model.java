package com.brinkcarp.addressbook;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class Model extends SQLiteOpenHelper
{
	public static final String KEY_ID = "ContactID";
	public static final String KEY_NAME = "Name";
	public static final String KEY_PHONE = "Phone";
	public static final String KEY_EMAIL =  "Email";
	public static final String KEY_STREET = "Street";
	public static final String KEY_CITY = "City";
	
	private static final String TAG = "AddressBook";
	
	private static final String DATABASE_NAME = "AddressBook.db";
	private static final int DATABASE_VERSION = 1;
	private static final String TABLE_MYCONTACTS = "MyContacts";
	
	private static final String TABLE_CREATE_MYCONTACTS = 
			"CREATE TABLE " +
			TABLE_MYCONTACTS +
			"(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
				KEY_NAME + " TEXT," +
				KEY_PHONE + " TEXT," +
				KEY_EMAIL + " TEXT," +
				KEY_STREET + " TEXT," +
				KEY_CITY + " TEXT)";
	
	private SQLiteDatabase _db;
	private static Model _instance;
	
	/**
	 * Call the parent class and pass the actual name and version of the database to be created.
	 * @param context
	 */
	public Model(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	/**
	 * Execute the CREATE_TABLE statement defined as a const
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {
		Log.d(TAG, "In Model.onCreate()");
		db.execSQL(TABLE_CREATE_MYCONTACTS);
	}

	/**
	 * If there is ever a need to upgrade the database compare oldVersion and newVersion to determine 
	 * if modification to the database are necessary. Usually ALTER TABLE or CREATE TABLE statements.
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) 
	{
		//No upgrade process yet
	}
	
	/**
	 * Used to synchronize access and force singleton on the database helper object
	 * @param context
	 * @return singleton instance of the Model class
	 */
	public static synchronized Model getInstance(Context context)
	{
		if(_instance == null)
		{
			_instance = new Model(context);
		}
		
		return _instance;
	}

	/**
	 * Contact model class. Includes comparator for sorting contacts
	 * @author Minda Carpenter
	 *
	 */
	public static class Contact implements Comparator<Contact>
	{
		/**
		 * constructor for empty contact
		 */
		public Contact()
		{
			ContactID = -1;
		}
		/**
		 * constructor for contact with ID
		 * @param ID the ContactID for the contact
		 */
		public Contact(long ID)
		{
			ContactID = ID;
		}
		
		/**
		 * primary key for database
		 */
		public long ContactID;

		/**
		 * full name of contact
		 */
		public String ContactName;
		
		/**
		 * phone number of contact
		 */
		public String PhoneNumber;
		
		/**
		 * email of contact
		 */
		public String Email;
		
		/**
		 * house number, street name and apartment number of contact's address
		 */
		public String StreetAddress;
		
		/**
		 * city, state and zip code of contacts address; 
		 */
		public String City;
		
		/**
		 * returns and positive number if arg1 goes before agr0, returns a negative number if arg0 goes before arg1, 
		 * and returns zero if they are equal. This method is case insensitive.
		 * @param arg0 the first contact to compare
		 * @param arg1 the second contact to compare
		 */
		@Override
		public int compare(Contact arg0, Contact arg1)
		{
			return arg0.ContactName.compareToIgnoreCase(arg1.ContactName);
		}
		
		/**
		 * Returns a string describing this contact
		 * Currently uses ContactName
		 * @return
		 */
		@Override
		public String toString()
		{
			return this.ContactName;
		}
		
	}
	
	/**
	 * opens connection to the database for writing specifically
	 */
	private void openDBConnection()
	{
		_db = getWritableDatabase();
	}
	
	/**
	 * close connection to database if open
	 */
	private void closeDBConnection()
	{
		if(_db != null && _db.isOpen() == true)
		{
			_db.close();
		}
	}

	/**
	 * Converts a cursor object into a Contact
	 * Fetches column indexes using column labels
	 * @param cursor The Cursor pointing to the contact
	 * @return
	 */
	private Contact cursorToContact(Cursor cursor)
	{
		Contact contact = new Contact(cursor.getLong(cursor.getColumnIndex(KEY_ID)));
		contact.ContactName = cursor.getString(cursor.getColumnIndex(KEY_NAME));
		contact.PhoneNumber = cursor.getString(cursor.getColumnIndex(KEY_PHONE));
		contact.Email = cursor.getString(cursor.getColumnIndex(KEY_EMAIL));
		contact.StreetAddress = cursor.getString(cursor.getColumnIndex(KEY_STREET));
		contact.City = cursor.getString(cursor.getColumnIndex(KEY_CITY));
		return contact;
	}
	
	/**
	 * used to populate the ContentValues to be used in SQL insert or update methods
	 * @param contact contains values to be used
	 * @return populated ContentValues
	 */
	private ContentValues populateContentValues(Contact contact)
	{
		ContentValues values = new ContentValues();
		values.put(KEY_NAME, contact.ContactName);
		values.put(KEY_PHONE, contact.PhoneNumber);
		values.put(KEY_EMAIL, contact.Email);
		values.put(KEY_STREET, contact.StreetAddress);
		values.put(KEY_CITY, contact.City);
		
		return values;
	}
	
	/**
	 * Inserts a contact into the database.
	 * @param contact The Contact to be inserted
	 */
	public void insertContact(Contact contact)
	{
		Log.d(TAG, "In Model.insertContact()");
		ContentValues values = populateContentValues(contact);
		openDBConnection();
		long id = _db.insert(TABLE_MYCONTACTS, null, values);
		Log.d(TAG, "ContactID inserted = " + String.valueOf(id));
		closeDBConnection();
	}
	
	/**
	 * updates a contact already in the database
	 * @param contact to be updated
	 */
	public void updateContact(Contact contact)
	{
		Log.d(TAG, "In Model.updateContact()");
		ContentValues values = populateContentValues(contact);
		openDBConnection();
		int rowsAffected = _db.update(TABLE_MYCONTACTS, values, KEY_ID + " = ?", new String[] {String.valueOf(contact.ContactID)});
		closeDBConnection();
		if (rowsAffected == 0)
		{
			Log.d(TAG, "Contact not updated!");
		}
	}
	
	/**
	 * delete a contact from the database code
	 * @param contact to be deleted from database
	 */
	public void deleteContact(Contact contact)
	{
		openDBConnection();
		int rowsAffected = _db.delete(TABLE_MYCONTACTS, KEY_ID + " = ?", new String[] {String.valueOf(contact.ContactID)});
		closeDBConnection();
		if (rowsAffected == 0)
		{
			Log.d(TAG, "Contact not deleted!");
		}
	}
	
	/**
	 * gets a contact from the database
	 * @param id to lookup the contact
	 * @return the contact found
	 */
	public Contact getContact(long id)
	{
		Contact contact = null;
		openDBConnection();
		Cursor cursor = _db.query(TABLE_MYCONTACTS, new String[] {KEY_ID,KEY_NAME,KEY_PHONE,KEY_EMAIL,KEY_STREET,KEY_CITY}, 
				KEY_ID + " = " + id , null, null, null, KEY_NAME);
		if(cursor.moveToFirst())
		{
			contact = cursorToContact(cursor);
		}
		
		cursor.close();
		closeDBConnection();
		
		return contact;
	}
	
	/**
	 * Gets all the contacts in the database
	 * @return a list of all contacts
	 */
	public List<Contact> getContacts()
	{
		List<Contact> contacts = new ArrayList<Contact>();
		openDBConnection();
		Cursor cursor = _db.query(TABLE_MYCONTACTS, new String[] {KEY_ID,KEY_NAME,KEY_PHONE,KEY_EMAIL,KEY_STREET,KEY_CITY}, 
				null, null, null, null, KEY_NAME);
		cursor.moveToFirst();
		
		while(cursor.isAfterLast() == false)
		{
			Contact contact = cursorToContact(cursor);
			contacts.add(contact);
			cursor.moveToNext();
		}
		
		cursor.close();
		closeDBConnection();
		
		return contacts;
	}
	
	/**
	 * Gets a list of all contacts in the database matching a given query
	 * @param query
	 * @return
	 */
	public List<Contact> getContacts(String query)
	{
		List<Contact> contacts = new ArrayList<Contact>();
		openDBConnection();
		Cursor cursor = _db.query(TABLE_MYCONTACTS, new String[] {KEY_ID,KEY_NAME,KEY_PHONE,KEY_EMAIL,KEY_STREET,KEY_CITY}, 
				"Name LIKE ?", new String[]{"%"+query+"%"}, null, null, KEY_NAME);
		cursor.moveToFirst();
		
		while(cursor.isAfterLast() == false)
		{
			Contact contact = cursorToContact(cursor);
			contacts.add(contact);
			cursor.moveToNext();
		}
		
		cursor.close();
		closeDBConnection();
		
		return contacts;
	}
}
