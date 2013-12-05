package edu.sdsmt.ThuryWulff.sentireaerissimia;

import java.io.ByteArrayOutputStream;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;

/***
 * This class is for holding the images after theya re loaded from the server
 * 
 * @author Alex Wulff
 *
 */
public class ClothingImage {

	public String Image;
    public int ImageHeight;
    public int ImageWidth;
    public int Type;

//    public ClothingImage(Clothing c, int height, int width, byte[] image)
//    {
//        Type = c.Type;
//        ImageHeight = height;
//        ImageWidth = width;
//        if (image != null)
//        {
//            Image = image;
//        }
//        else
//        {            
//            int size =  4 * height * width;
//            Image = new byte[size];
//        }
//    }
    
    /***
     * Constructor
     * 
     * @author Alex Wulff
     * 
     */
    public ClothingImage()
    {
    	
    }
    /***
     * Function to convert the image to a Bitmap for outputting to the GUI
     * 
     * @author Alex Wulff
     * 
     * @return Bitmap a bitmap of the image to be dispayed
     * @param none
     */
    public Bitmap MakeImage()
    {    	
    	byte[] bytes = Image.getBytes();
    	Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    	ByteArrayOutputStream blob = new ByteArrayOutputStream();
    	bitmap.compress(CompressFormat.PNG, 0 /*ignored for PNG*/, blob);
    	return bitmap;
    }
}
