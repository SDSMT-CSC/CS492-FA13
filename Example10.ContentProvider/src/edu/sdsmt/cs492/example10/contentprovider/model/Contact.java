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

	public Contact(Parcel parcel)
	{
		ContactID = parcel.readLong();
		Name = parcel.readString();
		Phone = parcel.readString();
		Email = parcel.readString();
		Street = parcel.readString();
		City = parcel.readString();
	}

	public Contact(final Cursor cursor)
	{
		this.ContactID = cursor.getLong(cursor.getColumnIndex(Contact.COLUMN_ID));
		this.Name = cursor.getString(cursor.getColumnIndex(Contact.COLUMN_CONTACT_NAME));
		this.Phone = cursor.getString(cursor.getColumnIndex(Contact.COLUMN_PHONE));
		this.Email = cursor.getString(cursor.getColumnIndex(Contact.COLUMN_EMAIL));
		this.Street = cursor.getString(cursor.getColumnIndex(Contact.COLUMN_STREET));
		this.City = cursor.getString(cursor.getColumnIndex(Contact.COLUMN_CITY));
	}

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

	@Override
	public int describeContents()
	{
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags)
	{
		dest.writeLong(ContactID);
		dest.writeString(Name);
		dest.writeString(Phone);
		dest.writeString(Email);
		dest.writeString(Street);
		dest.writeString(City);
	}

	public static final Parcelable.Creator<Contact> Creator = new Parcelable.Creator<Contact>()
	{
		public Contact createFromParcel(Parcel pc)
		{
			return new Contact(pc);
		}

		public Contact[] newArray(int size)
		{
			return new Contact[size];
		}
	};
}
