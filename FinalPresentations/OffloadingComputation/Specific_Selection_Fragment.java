package edu.sdsmt.ThuryWulff.sentireaerissimia;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import edu.sdsmt.ThuryWulff.forecastsforfun.R;

/***
 * Class for using when inflating the Specific Selection Fragment
 * 
 * @author Daniel Thury
 * @author Alex Wulff
 */
public class Specific_Selection_Fragment extends Fragment
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
    	View rootView = inflater.inflate(R.layout.specific_selection_fragment, null);
    	
    	//Setting up buttons
    	Button SelectSetting = (Button) rootView.findViewById(R.id.ButtonSelectSetting);
    	Button SelectColors = (Button) rootView.findViewById(R.id.ButtonSelectColors);
    	Button SelectType = (Button) rootView.findViewById(R.id.ButtonSelectType);
    	Button TypeLeft = (Button) rootView.findViewById(R.id.ButtonTypeLeft);
    	Button TypeRight = (Button) rootView.findViewById(R.id.ButtonTypeRight);
    	Button Select = (Button) rootView.findViewById(R.id.ButtonSelect);
    	
    	
    	//Setting on Click Listeners
    	SelectSetting.setOnClickListener(
				new View.OnClickListener()
				{
					// Set on click
					public void onClick(View view)
					{
						OnButtonClickSelectSetting();
					}
				});
		
    	SelectColors.setOnClickListener(
				new View.OnClickListener()
				{
					// Set on click
					public void onClick(View view)
					{
						OnButtonClickSelectColors();
					}
				});
		
    	SelectType.setOnClickListener(
				new View.OnClickListener()
				{
					// Set on click
					public void onClick(View view)
					{
						OnButtonClickSelectType();
					}
				});
		
    	TypeLeft.setOnClickListener(
				new View.OnClickListener()
				{
					// Set on click
					public void onClick(View view)
					{
						OnButtonClickTypeLeft();
					}
				});
		
    	TypeRight.setOnClickListener(
				new View.OnClickListener()
				{
					// Set on click
					public void onClick(View view)
					{
						OnButtonClickTypeRight();
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
     * Function that is called when button is pressed
     * 
     * @author Daniel Thury
     * 
     * @return none
     * @param none
     */
    public void OnButtonClickSelectSetting()
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
    public void OnButtonClickSelectColors()
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
    public void OnButtonClickSelectType()
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
    public void OnButtonClickTypeLeft()
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
    public void OnButtonClickTypeRight()
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
