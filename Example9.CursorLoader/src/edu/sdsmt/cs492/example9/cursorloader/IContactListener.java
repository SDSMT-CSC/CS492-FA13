package edu.sdsmt.cs492.example9.cursorloader;


public interface IContactListener
{
	
	public void onContactSelected(long id);
	public String getContactName();
	public String getPhoneNumber();
	public String getPhoneNumberType();

}
