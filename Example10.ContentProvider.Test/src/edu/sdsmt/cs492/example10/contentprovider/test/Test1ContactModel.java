package edu.sdsmt.cs492.example10.contentprovider.test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import android.database.Cursor;
import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;
import android.test.suitebuilder.annotation.Suppress;
import android.util.Log;
import edu.sdsmt.cs492.example10.contentprovider.model.Contact;
import edu.sdsmt.cs492.example10.contentprovider.model.ContactModel;

public class Test1ContactModel extends AndroidTestCase
{

	private static final String TAG = Test1ContactModel.class.getSimpleName();
	private static final String NEW_NAME = "Brian G. Butterfield";
	
	private ContactModel _contactModel;
	private RenamingDelegatingContext _context;

	private int _numberOfContacts = 0;
	
	@Before
	public void setUp() throws Exception
	{
		Log.i(TAG, "setUp() called");
		
		super.setUp();

		// http://stackoverflow.com/questions/8499554/android-junit-test-for-sqliteopenhelper
		// Provides a test specific context.
		_context = new RenamingDelegatingContext(getContext(), "test_");
		_contactModel = ContactModel.getInstance(_context);
		
		_numberOfContacts = _contactModel.insertDummyContacts(false);
	}

	@After
	public void tearDown() throws Exception
	{
		Log.i(TAG, "tearDown() called");
		
		Log.d(TAG, "Deleted: " + _contactModel.deleteAllContacts());
		
		_contactModel = null;
		_context = null;

		super.tearDown();
	}
	
	@Override @Suppress
	public void testAndroidTestCaseSetupProperly()
	{
		
	}

	@Test
	public final void test0ContactModel()
	{
		assertEquals("Database name incorrect!", "AddressBook.db", _contactModel.getDatabaseName());
	}

	@Test
	public final void test1GetInstance()
	{
	    Log.i(TAG, "test1GetInstance() called");
	    
		assertEquals(_contactModel, ContactModel.getInstance(_context));
	}
	
	@Test
	public final void test2InsertContact()
	{
	    
	    Log.i(TAG, "test2InsertContact() called");
	    
		Contact contact = new Contact();

		long contactID = -1;

		// *******************************************************************************
		// INSERT
		// *******************************************************************************
		contact.Name = "Brian Butterfield";
		contact.Phone = "605-390-0395";
		contact.Email = "brianb@innovsys.com";
		contact.Street = "123 Main Street";
		contact.City = "Rapid City, SD";
		contactID = _contactModel.insertContact(contact);

		assertTrue("Insert expected:>0 | actual:" + contactID, contactID > 0);
		
	}
	
	@Test
	public final void test3UpdateContact()
	{
	    Log.i(TAG, "test3UpdateContact() called");
	    
		Contact contact = new Contact();
		int rowsAffected = -1;
		
		// *******************************************************************************
		// UPDATE
		// *******************************************************************************
		contact.ContactID = -99;
		contact.Name = NEW_NAME;
		rowsAffected = _contactModel.updateContact(contact);

		assertTrue("Update expected:0 | actual:" + rowsAffected, rowsAffected == 0);

		contact.ContactID = getContact().ContactID;
		contact.Name = NEW_NAME;
		rowsAffected = _contactModel.updateContact(contact);

		assertTrue("Update expected:1 | actual:" + rowsAffected, rowsAffected == 1);
	}

	@Test
	public final void test4DeleteContact()
	{
	    Log.i(TAG, "test4DeleteContact() called");
	    
		int rowsAffected = -1;
		
		// *******************************************************************************
		// DELETE
		// *******************************************************************************
		rowsAffected = _contactModel.deleteContact(getContact());
	
		assertTrue("Delete expected:1 | actual:" + rowsAffected, rowsAffected == 1);
	
	}

	@Test
	public final void test5GetContact()
	{

	    Log.i(TAG, "test5GetContact() called");
	    
		// *******************************************************************************
		// READ
		// *******************************************************************************
		Contact updatedContact = _contactModel.getContact(getContact().ContactID);
	
		assertNotNull("Read returned NULL", updatedContact);

	}
	
	@Test
	public final void test6QueryContact()
	{

	    Log.i(TAG, "test6QueryContact() called");
	    
		Cursor cursor = null;
		
		// *******************************************************************************
		// QUERY Single
		// *******************************************************************************
		cursor = _contactModel.queryContact(getContact().ContactID);

		assertTrue("Query returned no rows", cursor.moveToFirst());
		
		cursor.close();
	}

	@Test
	public final void test7QueryContacts()
	{
	    Log.i(TAG, "test7QueryContacts() called");
	    
		Cursor cursor = null;
		
		// *******************************************************************************
		// QUERY All
		// *******************************************************************************
		cursor = _contactModel.queryContacts(Contact.COLUMN_CONTACT_NAME);

		assertTrue("Query all expected:" + _numberOfContacts + " | actual:" + cursor.getCount(), cursor.getCount() == _numberOfContacts);

		cursor.close();
	}
	
	private Contact getContact()
	{
		Cursor cursor = null;
		long contactID = 0;
		
		// *******************************************************************************
		// GET <Contact>
		// *******************************************************************************
		
		cursor = _contactModel.queryContacts(Contact.COLUMN_CONTACT_NAME);
		cursor.moveToLast();
		contactID = cursor.getLong(cursor.getColumnIndex(Contact.COLUMN_ID));
		
		cursor.close();
		
		return _contactModel.getContact(contactID);
		
	}
}
