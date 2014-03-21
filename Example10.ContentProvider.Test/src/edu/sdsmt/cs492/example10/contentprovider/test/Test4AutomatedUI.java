package edu.sdsmt.cs492.example10.contentprovider.test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import android.test.ActivityInstrumentationTestCase2;

import com.jayway.android.robotium.solo.Solo;

import edu.sdsmt.cs492.example10.contentprovider.MainActivity;
import edu.sdsmt.cs492.example10.contentprovider.R;

public class Test4AutomatedUI extends ActivityInstrumentationTestCase2<MainActivity>
{
	//NOTE:  Extra calls to sleep() have been added for demonstration purposes.
    //       The genymotion emulator is too fast.
    
	private Solo _solo;

	public Test4AutomatedUI()
	{
		super(MainActivity.class);
	}

	@Before
	protected void setUp() throws Exception
	{
		super.setUp();
		
		// Instantiate the actual robotium object.
		_solo = new Solo(getInstrumentation(), getActivity());
	}

	@After
	protected void tearDown() throws Exception
	{
		// General cleanup.
		_solo.finishOpenedActivities();
		super.tearDown();
	}

	@Test
	public void test1OpenApp()
	{
		
		// Simple test to actually just open up the app and validate
		// the correct fragment is displayed, i.e. by Tag.
		if (_solo.waitForFragmentByTag(MainActivity.FRAGMENT_CONTACT_LIST_TAG, 1000) == true)
		{
			assertTrue(true);
			_solo.sleep(1000);
		}
		else
		{
			fail("Contact list fragment did not appear");
		}
	}
	
	@Test
	public void test2AddContact()
	{
		// Perform the necessary user touches to add a contact
		// to the list.
		
		_solo.clickOnActionBarItem(R.id.action_add_contact);
		
		assertTrue("Contact detail fragment did not appear.", 
				   _solo.waitForFragmentByTag(MainActivity.FRAGMENT_CONTACT_LIST_TAG, 1000));
		
		_solo.enterText(0, "Danny");
		_solo.enterText(1, "605-390-0395");
		_solo.enterText(2, "brianb@innovsys.com");
		_solo.enterText(3, "123 Main Street");
		_solo.enterText(4, "Baton Rouge, LA");
		
		_solo.sleep(2000);
		
		// Save Button
		_solo.clickOnButton(0);  
		
		_solo.sleep(2000);
		
	}
	
	@Test
	public void test3ViewContactDetail()
	{
		// Display the detail fragment.  Ultimately, you would want to 
		// validate in detail what is being displayed, not just confirm
		// the correct fragment was displayed.
		
		_solo.clickInList(0);
		
		if (_solo.waitForFragmentByTag(MainActivity.FRAGMENT_CONTACT_LIST_TAG, 1000) == true)
		{
			assert(true);
			
			_solo.sleep(2000);
			
			// Needs minimum requirements, Permissions, SDCARD, etc.
			_solo.takeScreenshot();
		}
		else
		{
			fail("Contact detail fragment did not appear.");
		}
	}
	
	@Test
	public void test4UpdateContact()
	{
		// Update the contact name and actually perform the save.
		
		_solo.clickInList(0);
		
		assertTrue("Contact detail fragment did not appear.", 
				   _solo.waitForFragmentByTag(MainActivity.FRAGMENT_CONTACT_LIST_TAG, 1000));
		
		_solo.sleep(2000);
		
		_solo.clickOnActionBarItem(R.id.action_update_contact);
		
		_solo.enterText(0, " Oh Danny!");
		
		// Save Button
		_solo.clickOnButton(0);  
		
		_solo.sleep(2000);
	}
	
	@Test
	public void test5DeleteContact()
	{
		// Now delete it.
		
		_solo.clickInList(0);
		
		assertTrue("Contact detail fragment did not appear.", 
				   _solo.waitForFragmentByTag(MainActivity.FRAGMENT_CONTACT_LIST_TAG, 1000));
		
		_solo.sleep(2000);
		
		_solo.clickOnActionBarItem(R.id.action_delete_contact);
	}
	
	@Test
	public void test6AddContact()
	{
		// Add another contact, for the enjoyment of it.
		
		_solo.clickOnActionBarItem(R.id.action_add_contact);
		
		assertTrue("Contact detail fragment did not appear.", 
				   _solo.waitForFragmentByTag(MainActivity.FRAGMENT_CONTACT_LIST_TAG, 1000));
		
		_solo.enterText(0, "Danny IS BACK!");
		_solo.enterText(1, "605-390-0395");
		_solo.enterText(2, "brianb@innovsys.com");
		_solo.enterText(3, "123 Main Street");
		_solo.enterText(4, "Baton Rouge, LA");
		
		_solo.sleep(2000);
		
		// Save Button
		_solo.clickOnButton(0);  
		
		_solo.sleep(2000);
		
	}
}
