package edu.sdsmt.ThuryWulff.sentireaerissimia;


import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.JsonReader;
import android.util.Log;
import edu.sdsmt.ThuryWulff.forecastsforfun.R;

/***
 * This class is what each clothing item is stored to to
 * Tell you about the clothing
 * 
 * @author Alex Mulff
 *
 */
public class Clothing implements Parcelable
{
	// Networking
//	public final String URL = "http://smfo.no-ip.org";
//    public final int Port = 11000;
	// base model properties
	public long ClothingID;
	public int Type;
	public String Description;
	// Colors
	public boolean CanBlack , IsBlack;    
	public boolean CanBlue , IsBlue;
    public boolean CanGreen , IsGreen;
	public boolean CanGrey , IsGrey;      
	public boolean CanOrange , IsOrange;  
	public boolean CanPink , IsPink;
	public boolean CanPurple , IsPurple;
	public boolean CanRed , IsRed;
	public boolean CanWhite , IsWhite;
	public boolean CanYellow , IsYellow;
	public boolean CanBrown , IsBrown;
	// Settings
	public boolean IsCasual;
	public boolean IsRelaxed;
	public boolean IsBusiness;
	public boolean IsFormal;
	public long IdealTemp;
//    public int ImageHeight;
//    public int ImageWidth;
//    public byte[] RawImage;
//    public Bitmap Image;
    
    // Members
    
//    private String TAG = "";
    
	/***
	 * Constuctor
	 * 
	 * @author Alex Wulff
	 * 
	 */
    public Clothing()
    {
    	ClothingID = -1;
    }

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
//		dest.writeParcelable(Image, 0);
	}	
}
