package edu.sdsmt.ThuryWulff.sentireaerissimia;

import java.util.LinkedList;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import edu.sdsmt.ThuryWulff.forecastsforfun.R;

/***
 * Class for using when inflating the Dress Up Fragment
 * 
 * @author Daniel Thury
 * @author Alex Wulff
 *
 */
public class Dress_Up_Fragment extends Fragment
{

	/***
	 * Over ridden OnCreate method
	 * 
	 * @author Daniel Thury
	 * 
	 * @param argumentsBundle Bundle for use with saved state
	 */
    @Override
    public void onCreate(Bundle argumentsBundle)
    {
    	super.onCreate(argumentsBundle);
    }
    
    /***
     * Over ridden oncreate view used for creating the view and setting up the button handlers
     * 
     * @author Daniel Thury
     * @author Alex Wulff
     * 
     * @return View The view to be used for this fragment
     * @param inflater the inflator for inflating the view
     * @param container ViewGroup to be used
     * @param savedInstanceState the bundle to be used
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
    	View rootView = inflater.inflate(R.layout.dress_up_fragment, null);
    	
    	//All of the buttons of the fragment
		Button HatLeft = (Button) rootView.findViewById(R.id.ButtonHatLeft);
		Button HatRight = (Button) rootView.findViewById(R.id.ButtonHatRight);
		Button AccessoryLeft = (Button) rootView.findViewById(R.id.ButtonAccessoriesLeft);
		Button AccessoryRight = (Button) rootView.findViewById(R.id.ButtonAccessoriesRight);
		Button ExteriorLeft = (Button) rootView.findViewById(R.id.ButtonExteriorLeft);
		Button ExteriorRight = (Button) rootView.findViewById(R.id.ButtonExteriorRight);
		Button TopsLeft = (Button) rootView.findViewById(R.id.ButtonTopLeft);
		Button TopsRight = (Button) rootView.findViewById(R.id.ButtonTopRight);
		Button BeltLeft = (Button) rootView.findViewById(R.id.ButtonBeltLeft);
		Button BeltRight = (Button) rootView.findViewById(R.id.ButtonBeltRight);
		Button BottomsLeft = (Button) rootView.findViewById(R.id.ButtonBottomsLeft);
		Button BottomsRight = (Button) rootView.findViewById(R.id.ButtonBottomsRight);
		
		// images
		ImageView ImageHatAccessories = (ImageView) rootView.findViewById(R.id.ImageHatAccessories);
		
		Button Select = (Button) rootView.findViewById(R.id.ButtonSelect);
		
		IMenuControlListener baseActivity = (IMenuControlListener) getActivity();
		// get clothing
    	LinkedList<ClothingImage> rack = baseActivity.getRack();
		
    	// build lists
    	LinkedList<ClothingImage> hats = new LinkedList<ClothingImage>();
    	
    	for (int i = 0; i < rack.size(); i +=1)
    	{
    		// if hats
    		if (rack.get(i).Type == 1)
    		{
    				ClothingImage insert = rack.get(i);
    				hats.add(insert);    			
    		}
    	}
    	// set for first
    	//ImageHatAccessories.setImageBitmap(hats.get(0).MakeImage());
    	
    	//Sets up all of the button handlers
		HatLeft.setOnClickListener(
				new View.OnClickListener()
				{
					// Set on click
					public void onClick(View view)
					{
						OnButtonClickHatLeft();
					}
				});
		
		HatRight.setOnClickListener(
				new View.OnClickListener()
				{
					// Set on click
					public void onClick(View view)
					{
						OnButtonClickHatRight();
					}
				});
		
		AccessoryLeft.setOnClickListener(
				new View.OnClickListener()
				{
					// Set on click
					public void onClick(View view)
					{
						OnButtonClickAccessoryLeft();
					}
				});
		
		AccessoryRight.setOnClickListener(
				new View.OnClickListener()
				{
					// Set on click
					public void onClick(View view)
					{
						OnButtonClickAccessoryRight();
					}
				});
		
		ExteriorLeft.setOnClickListener(
				new View.OnClickListener()
				{
					// Set on click
					public void onClick(View view)
					{
						OnButtonClickExteriorLeft();
					}
				});
		
		ExteriorRight.setOnClickListener(
				new View.OnClickListener()
				{
					// Set on click
					public void onClick(View view)
					{
						OnButtonClickExteriorRight();
					}
				});
		
		TopsLeft.setOnClickListener(
				new View.OnClickListener()
				{
					// Set on click
					public void onClick(View view)
					{
						OnButtonClickTopLeft();
					}
				});
		
		TopsRight.setOnClickListener(
				new View.OnClickListener()
				{
					// Set on click
					public void onClick(View view)
					{
						OnButtonClickTopRight();
					}
				});
		
		BeltLeft.setOnClickListener(
				new View.OnClickListener()
				{
					// Set on click
					public void onClick(View view)
					{
						OnButtonClickBeltLeft();
					}
				});
		
		BeltRight.setOnClickListener(
				new View.OnClickListener()
				{
					// Set on click
					public void onClick(View view)
					{
						OnButtonClickBeltRight();
					}
				});
		
		BottomsLeft.setOnClickListener(
				new View.OnClickListener()
				{
					// Set on click
					public void onClick(View view)
					{
						OnButtonClickBottomLeft();
					}
				});
		
		BottomsRight.setOnClickListener(
				new View.OnClickListener()
				{
					// Set on click
					public void onClick(View view)
					{
						OnButtonClickBottomRight();
					}
				});
				
		Select.setOnClickListener(
				new View.OnClickListener()
				{
					// Set on click
					public void onClick(View view)
					{
						OnButtonClickSelect();
					}
				});
		
    	return rootView;
    }
    
    /***
     * Function that is called when button is pressed for rotation
     * this rack would send a rr or rl for rotate left/ right to 
     * the server and receive back a new article of clothing as needed
     * 
     * @author Daniel Thury
     * 
     * @return none
     * @param none
     */
    public void OnButtonClickHatLeft()
    {
    	
    }
    
    /***
     * Function that is called when button is pressed
     * 
     * @author Daniel Thury
     * 
     * @return none
     * @param none
     */
    public void OnButtonClickHatRight()
    {
    	
    
    }
    
    /***
     * Function that is called when button is pressed
     * 
     * @author Daniel Thury
     * 
     * @return none
     * @param none
     */
    public void OnButtonClickAccessoryLeft()
    {
    	
    
    }
    
    /***
     * Function that is called when button is pressed
     * 
     * @author Daniel Thury
     * 
     * @return none
     * @param none
     */
    public void OnButtonClickAccessoryRight()
    {
    	
    
    }
    
    /***
     * Function that is called when button is pressed
     * 
     * @author Daniel Thury
     * 
     * @return none
     * @param none
     */
    public void OnButtonClickTopLeft()
    {
    	
    
    }
    
    /***
     * Function that is called when button is pressed
     * 
     * @author Daniel Thury
     * 
     * @return none
     * @param none
     */
    public void OnButtonClickTopRight()
    {
    	
    
    }
    
    /***
     * Function that is called when button is pressed
     * 
     * @author Daniel Thury
     * 
     * @return none
     * @param none
     */
    public void OnButtonClickExteriorLeft()
    {
    	
    
    }
    
    /***
     * Function that is called when button is pressed
     * 
     * @author Daniel Thury
     * 
     * @return none
     * @param none
     */
    public void OnButtonClickExteriorRight()
    {
    	
    
    }
    
    /***
     * Function that is called when button is pressed
     * 
     * @author Daniel Thury
     * 
     * @return none
     * @param none
     */
    public void OnButtonClickBeltLeft()
    {
    	
    
    }
    
    /***
     * Function that is called when button is pressed
     * 
     * @author Daniel Thury
     * 
     * @return none
     * @param none
     */
    public void OnButtonClickBeltRight()
    {
    	
    
    }
    
    /***
     * Function that is called when button is pressed
     * 
     * @author Daniel Thury
     * 
     * @return none
     * @param none
     */
    public void OnButtonClickBottomLeft()
    {
    	
    
    }
    
    /***
     * Function that is called when button is pressed
     * 
     * @author Daniel Thury
     * 
     * @return none
     * @param none
     */
    public void OnButtonClickBottomRight()
    {
    	
    
    }

    /***
     * Function that is called when button is pressed
     * 
     * @author Daniel Thury
     * 
     * @return none
     * @param none
     */
    public void OnButtonClickSelect()
    {
    	
    }
    
}