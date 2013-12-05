package edu.sdsmt.ThuryWulff.sentireaerissimia;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import edu.sdsmt.ThuryWulff.forecastsforfun.R;

/***
 * Class for using when inflating the Match Me Fragment
 * 
 * @author Daniel Thury
 * @author Alex Wulff
 *
 */
public class Match_Me_Fragment extends Fragment
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
    	View rootView = inflater.inflate(R.layout.match_me_fragment, null);
    	
    	//setting up the buttons
		Button SelectColors = (Button) rootView.findViewById(R.id.ButtonSelectColors);
		Button SpecificSelection = (Button) rootView.findViewById(R.id.ButtonSpecificSelection);
		Button SelectSetting = (Button) rootView.findViewById(R.id.ButtonSelectSetting);
		Button Go = (Button) rootView.findViewById(R.id.ButtonGo);
    	
		//setting up the on click listeners
		SelectColors.setOnClickListener(
				new View.OnClickListener()
				{
					// Set on click
					public void onClick(View view)
					{
						OnButtonPressSelectColors();
					}
				});
		
		SpecificSelection.setOnClickListener(
				new View.OnClickListener()
				{
					// Set on click
					public void onClick(View view)
					{
						OnButtonPressSpecificSelection();
					}
				});
		
		SelectSetting.setOnClickListener(
				new View.OnClickListener()
				{
					// Set on click
					public void onClick(View view)
					{
						OnButtonPressSelectSetting();
					}
				});
		
		Go.setOnClickListener(
				new View.OnClickListener()
				{
					// Set on click
					public void onClick(View view)
					{
						OnButtonPressGo();
					}
				});
		
    	return rootView;
    }
    
    /***
     * Function that is called when button is pressed
     * 
     * @author Daniel Thury
     * @author Alex Wulff
     * 
     * @return none
     * @param none
     */
    public void OnButtonPressSelectColors()
    {
    	IMenuControlListener baseActivity = (IMenuControlListener) getActivity();
    	baseActivity.showSelectColors();
    }
    
    /***
     * Function that is called when button is pressed
     * 
     * @author Daniel Thury
     * @author Alex Wulff
     * 
     * @return none
     * @param none
     */
    public void OnButtonPressSpecificSelection()
    {
    	IMenuControlListener baseActivity = (IMenuControlListener) getActivity();
    	baseActivity.showSpecificSelection();
    }
    
    /***
     * Function that is called when button is pressed
     * 
     * @author Daniel Thury
     * @author Alex Wulff
     * 
     * @return none
     * @param none
     */
    public void OnButtonPressSelectSetting()
    {
    	IMenuControlListener baseActivity = (IMenuControlListener) getActivity();
    	baseActivity.showSelectSetting();
    }
    
    /***
     * Function that is called when button is pressed
     * 
     * @author Daniel Thury
     * @author Alex Wulff
     * 
     * @return none
     * @param none
     */
    public void OnButtonPressGo()
    {
    	IMenuControlListener baseActivity = (IMenuControlListener) getActivity();
    	baseActivity.goClicked();
    }
}
