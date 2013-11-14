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

public class ContactModelTest extends AndroidTestCase
{

	private static final String TAG = ContactModelTest.class.getSimpleName();
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
	public final void testContactModel()
	{
		assertEquals("Database name incorrect!", "AddressBook.db", _contactModel.getDatabaseName());
	}

	@Test
	public final void testGetInstance()
	{
		assertEquals(_contactModel, ContactModel.getInstance(_context));
	}
	
	@Test
	public final void testInsertContact()
	{
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
	public final void testUpdateContact()
	{
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
	public final void testDeleteContact()
	{
		int rowsAffected = -1;
		
		// *******************************************************************************
		// DELETE
		// *******************************************************************************
		rowsAffected = _contactModel.deleteContact(getContact());
	
		assertTrue("Delete expected:1 | actual:" + rowsAffected, rowsAffected == 1);
	
	}

	@Test
	public final void testGetContact()
	{

		// *******************************************************************************
		// READ
		// *******************************************************************************
		Contact updatedContact = _contactModel.getContact(getContact().ContactID);
	
		assertNotNull("Read returned NULL", updatedContact);

	}
	
	@Test
	public final void testQueryContact()
	{

		Cursor cursor = null;
		
		// *******************************************************************************
		// QUERY Single
		// *******************************************************************************
		cursor = _contactModel.queryContact(getContact().ContactID);

		assertTrue("Query returned no rows", cursor.moveToFirst());
		
		cursor.close();
	}

	@Test
	public final void testQueryContacts()
	{
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
		
		cursor = _contactModel.queryContacts(Contact.COLUMN_CONTACT_NAME);
		cursor.moveToLast();
		contactID = cursor.getLong(cursor.getColumnIndex(Contact.COLUMN_ID));
		
		cursor.close();
		
		return _contactModel.getContact(contactID);
		
	}
}
