package edu.sdsmt.cs492.example10.contentprovider;

import edu.sdsmt.cs492.example10.contentprovider.model.Contact;

/**
 * Interface defined for communication and callback handling between
 * the Fragments and Activity in this application.
 * @author brianb
 *
 */
public interface IContactControlListener
{
	public void contactSelect(long id);
	public void contactInsert();
	public void contactInsert(Contact contact);
	public void contactUpdate(Contact contact);
	public void contactDelete(Contact contact);

}
