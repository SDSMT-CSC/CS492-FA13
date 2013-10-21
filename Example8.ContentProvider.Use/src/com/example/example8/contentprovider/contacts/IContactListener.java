package com.example.example8.contentprovider.contacts;

import android.widget.SimpleCursorAdapter;

public interface IContactListener
{
	
	public void onContactSelected(String lookupKey);
	public SimpleCursorAdapter getCursorAdapter();
	public String getContactName();
	public String getPhoneNumber();
	public String getPhoneNumberType();

}
