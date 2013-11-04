package edu.sdsmt.cs492.example10.contentprovider.model;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

public class Contact implements Parcelable
{

	public static final String TABLE_NAME = "Contact";

	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_CONTACT_NAME = "Name";
	public static final String COLUMN_PHONE = "Phone";
	public static final String COLUMN_EMAIL = "Email";
	public static final String COLUMN_STREET = "Street";
	public static final String COLUMN_CITY = "City";

	public static final String[] COLUMN_NAMES =
	{
		COLUMN_ID,
		COLUMN_CONTACT_NAME,
		COLUMN_PHONE,
		COLUMN_EMAIL,
		COLUMN_STREET,
		COLUMN_CITY
	};

	public long ContactID = -1;
	public String Name;
	public String Phone;
	public String Email;
	public String Street;
	public String City;

	public Contact()
	{
	}

	public Contact(final Cursor cursor)
	{
		// Public constructor used by ContactModel to create a Contact
		// object from the passed database cursor.
		this.ContactID = cursor.getLong(cursor.getColumnIndex(Contact.COLUMN_ID));
		this.Name = cursor.getString(cursor.getColumnIndex(Contact.COLUMN_CONTACT_NAME));
		this.Phone = cursor.getString(cursor.getColumnIndex(Contact.COLUMN_PHONE));
		this.Email = cursor.getString(cursor.getColumnIndex(Contact.COLUMN_EMAIL));
		this.Street = cursor.getString(cursor.getColumnIndex(Contact.COLUMN_STREET));
		this.City = cursor.getString(cursor.getColumnIndex(Contact.COLUMN_CITY));
	}

	private Contact(Parcel parcel)
	{
		// Private constructor used by Creator specific to Parcelable interface.
		ContactID = parcel.readLong();
		Name = parcel.readString();
		Phone = parcel.readString();
		Email = parcel.readString();
		Street = parcel.readString();
		City = parcel.readString();
	}

	@Override
	public int describeContents()
	{
		// There are cases where you need to use the constant CONTENTS_FILE_DESCRIPTOR.
		// Majority of the time, just return 0.
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags)
	{
		// Write values to save to Parcel.
		//
		// Parcel class supports writing of Primitive types (and String) or
		// arrays or lists of Primitive types.
		//
		// or, the Parcel class supports writing other Parcelable classes.
		dest.writeLong(ContactID);
		dest.writeString(Name);
		dest.writeString(Phone);
		dest.writeString(Email);
		dest.writeString(Street);
		dest.writeString(City);
	}

	public static final Parcelable.Creator<Contact> CREATOR = new Parcelable.Creator<Contact>()
	{
		public Contact createFromParcel(Parcel pc)
		{
			// Passes in the unmarshalled Parcel and using the private
			// contructor returns the object.
			return new Contact(pc);
		}

		public Contact[] newArray(int size)
		{
			// Supports array of parcelable objects.
			return new Contact[size];
		}
	};

	public ContentValues getContentValues()
	{
		final ContentValues values = new ContentValues();
		values.put(Contact.COLUMN_CONTACT_NAME, Name);
		values.put(Contact.COLUMN_PHONE, Phone);
		values.put(Contact.COLUMN_EMAIL, Email);
		values.put(Contact.COLUMN_STREET, Street);
		values.put(Contact.COLUMN_CITY, City);
	
		return values;
	}
}
