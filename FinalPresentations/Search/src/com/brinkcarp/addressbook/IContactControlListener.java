package com.brinkcarp.addressbook;

import android.widget.ArrayAdapter;
import com.brinkcarp.addressbook.Model.Contact;

/**
 * Interface for contact database commands 
 * @author Minda Carpenter
 *
 */
public interface IContactControlListener 
{
	public void insertContact(Contact contact);
	public void insertContact();
	public void deleteContact(Contact contact);
	public void updateContact(Contact contact);
	public void selectContact(Contact contact);
	public void addContact(Contact contact);
	public Contact getContact();
	public ArrayAdapter<Contact> getContactArrayAdapter();
}
