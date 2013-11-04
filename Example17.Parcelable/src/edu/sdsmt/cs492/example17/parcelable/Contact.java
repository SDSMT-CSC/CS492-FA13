package edu.sdsmt.cs492.example17.parcelable;

import android.os.Parcel;
import android.os.Parcelable;

public class Contact implements Parcelable
{

	public long ContactID = -1;
	public String Name;

	public Contact()
	{
		ContactID = -1;
		Name = "Initialized";
	}

	private Contact(Parcel parcel)
	{
		// Private constructor used by Creator specific to Parcelable interface.
		ContactID = parcel.readLong();
		Name = parcel.readString();
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
}
