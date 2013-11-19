package edu.sdsmt.cs492.example10.contentprovider.test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import android.test.ActivityInstrumentationTestCase2;
import android.test.UiThreadTest;
import android.widget.EditText;
import edu.sdsmt.cs492.example10.contentprovider.ListContactFragment;
import edu.sdsmt.cs492.example10.contentprovider.MainActivity;
import edu.sdsmt.cs492.example10.contentprovider.R;
import edu.sdsmt.cs492.example10.contentprovider.ViewContactFragment;
import edu.sdsmt.cs492.example10.contentprovider.model.Contact;
import edu.sdsmt.cs492.example10.contentprovider.model.ContactModel;

// Reference:  http://developer.android.com/tools/testing/activity_test.html#CreateTestCaseClass
public class MainActivityTest extends ActivityInstrumentationTestCase2<MainActivity>
{
	
	private MainActivity _mainActivity;
	private ListContactFragment _fragmentContactList;
	private ViewContactFragment _fragmentViewContact;

	public MainActivityTest()
	{
		super(MainActivity.class);
	}

	@Before
	protected void setUp() throws Exception
	{
		super.setUp();
		
		setActivityInitialTouchMode(false);

	    _mainActivity = getActivity();
	    
	    _fragmentContactList = (ListContactFragment) _mainActivity.getFragmentManager().findFragmentByTag(MainActivity.FRAGMENT_CONTACT_LIST_TAG);

	}

	@After
	protected void tearDown() throws Exception
	{
		super.tearDown();
	}

	@Test
	public final void test0PreConditions()
	{
		// Good testing practice.
		// Make sure there are rows, etc.
		assertTrue("At least one Contact is required.", _fragmentContactList.getListAdapter().getCount() > 0);
	}
	
	@Test
	public final void test1ViewContact()
	{
		
		_mainActivity.runOnUiThread(new Runnable()
		{
			@Override
			public void run()
			{
				
				_fragmentContactList.getListView().requestFocus();
				_fragmentContactList.getListView().setSelection(0);
				
				boolean onItemClickListenerCalled = _fragmentContactList.getListView()
						                                                .performItemClick(_fragmentContactList.getView(), 
						                                                                  0, 
						                                                                  _fragmentContactList.getListView().getSelectedItemId());
				
				assertTrue("Contact not selected", onItemClickListenerCalled);
				
				// Make sure the detail fragment is available.
				// Fragments are lazily assigned.
				_mainActivity.getFragmentManager().executePendingTransactions();
				
			}
		});
		
		// Need to make sure the Fragment is visible.
		getInstrumentation().waitForIdleSync();
		
		// Get a reference to the view contact fragment.
		_fragmentViewContact = (ViewContactFragment) _mainActivity.getFragmentManager().findFragmentByTag(MainActivity.FRAGMENT_CONTACT_VIEW_TAG);
		
		assertNotNull("ViewContactFragment null", _fragmentViewContact);
		
		// Get a Contact that was passed to the fragment.
		Contact contact = _fragmentViewContact.getArguments().getParcelable(ViewContactFragment.BUNDLE_KEY_CONTACT);
		
		assertNotNull("Contact null", contact);
		
		// Get the contact name displayed in the text box.
		EditText editText = (EditText) _fragmentViewContact.getView().findViewById(R.id.editTextName);
		String contactName = editText.getText().toString();
		
		assertTrue("Contact name is different", contactName.equals(ContactModel.getInstance(_mainActivity).getContact(contact.ContactID).Name));
		
	}
	
	@Test
	@UiThreadTest
	public final void test2StatePause()
	{
				
		_fragmentContactList.getListView().requestFocus();
		_fragmentContactList.getListView().setSelection(0);
		
		boolean onItemClickListenerCalled = _fragmentContactList.getListView()
				                                                .performItemClick(_fragmentContactList.getView(), 
				                                                                  0, 
				                                                                  _fragmentContactList.getListView().getSelectedItemId());
		
		assertTrue("Contact not selected", onItemClickListenerCalled);
		
		// Make sure the detail fragment is available.
		// Fragments are lazily assigned.
		_mainActivity.getFragmentManager().executePendingTransactions();
				
		// Get a reference to the view contact fragment.
		_fragmentViewContact = (ViewContactFragment) _mainActivity.getFragmentManager().findFragmentByTag(MainActivity.FRAGMENT_CONTACT_VIEW_TAG);
		
		assertNotNull("ViewContactFragment null", _fragmentViewContact);
		
		// Get a Contact that was passed to the fragment.
		Contact contact = _fragmentViewContact.getArguments().getParcelable(ViewContactFragment.BUNDLE_KEY_CONTACT);
		
		assertNotNull("Contact null", contact);
		
		// Get the contact name displayed in the text box.
		EditText editText = (EditText) _fragmentViewContact.getView().findViewById(R.id.editTextName);
		String contactName = editText.getText().toString();
		
		assertTrue("Contact name | Expected: " + contactName + 
				   " Actual: " + ContactModel.getInstance(_mainActivity).getContact(contact.ContactID).Name, 
				   contactName.equals(ContactModel.getInstance(_mainActivity).getContact(contact.ContactID).Name));
		
		// ***PAUSE Activity.
		getInstrumentation().callActivityOnPause(_mainActivity);
		
		// Edit the text in the Contact Name field.
		String newContactName = contactName + _mainActivity.getLocalClassName();
		editText.setText(newContactName);
		editText = null;
		
		// *** RESUME Activity.
		getInstrumentation().callActivityOnResume(_mainActivity);
		
		// Just because, find the Fragment again and get the value
		// stored in the Contact Name field.
		_fragmentViewContact = (ViewContactFragment) _mainActivity.getFragmentManager().findFragmentByTag(MainActivity.FRAGMENT_CONTACT_VIEW_TAG);
		editText = (EditText) _fragmentViewContact.getView().findViewById(R.id.editTextName);
		contactName = editText.getText().toString();
		
		// Is it the modified version?
		assertTrue("Contact name | Expected: " + contactName + 
				   " Actual: " + newContactName, 
				   contactName.equals(newContactName));
		
	}
	
	@Test
	public final void test3StateDestroy()
	{
		// Destroy the Activity.
		_mainActivity.finish();
		
		// Restart Activity.
		_mainActivity = getActivity();
		
		// Get a reference to the list contact fragment.
		_fragmentContactList = (ListContactFragment) _mainActivity.getFragmentManager().findFragmentByTag(MainActivity.FRAGMENT_CONTACT_LIST_TAG);
		
		assertTrue("At least one Contact is required.", _fragmentContactList.getListAdapter().getCount() > 0);
	}

}
