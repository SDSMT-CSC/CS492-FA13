package edu.sdsmt.ThuryWulff.sentireaerissimia;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import edu.sdsmt.ThuryWulff.forecastsforfun.R;

/***
 * Class for use with the main menu fragment
 * 
 * @author Alex Wulff
 */
public class MainMenu extends Fragment {

	private static String SERVER_IP = "208.107.166.177";//"http://smfo.no-ip.org";
	private IMenuControlListener _Listener;

	/***
	 * Over ridden OnCreate method
	 * 
	 * @author Alex Wulff
	 * 
	 * @param argumentsBundle Bundle for use with saved state
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
		
    /***
     * Over ridden oncreate view used for creating the view and setting up the button handlers
     * 
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
    	//inflates the new view
    	View rootView = inflater.inflate(R.layout.mainmenufragmentview, null);
		Button MatchMe = (Button) rootView.findViewById(R.id.ButtonMatchMe);
		Button DressUp = (Button) rootView.findViewById(R.id.ButtonDressUp);
		Button Json = (Button) rootView.findViewById(R.id.TestJSON);
		
		MatchMe.setOnClickListener(
				new View.OnClickListener()
				{
					// Set on click
					public void onClick(View view)
					{
						OnButtonPressMatchMe();
					}
				});
		
		DressUp.setOnClickListener(
				new View.OnClickListener()
				{
					// Set on click
					public void onClick(View view)
					{
						OnButtonPressDressUp();
					}
				});
		Json.setOnClickListener(
				new View.OnClickListener()
				{
					// Set on click
					public void onClick(View view)
					{
						OnButtonPressJSON();
					}
				});
		return rootView;
	}

	/***
	 * Attaches this fragment to the main activity
	 * 
	 * @author Alex Wulff
	 * 
	 * @return none
	 * @param activity the activity to which this fragment is attatched
	 */
	 @Override
     public void onAttach(Activity activity)
     {
             try
             {
                     // Assign listener reference from hosting activity.
                     _Listener = (IMenuControlListener) activity;
             }
             catch (ClassCastException e)
             {
                     throw new ClassCastException(activity.toString());
             }
             
             super.onAttach(activity);
     }
	 

//	public boolean onCreateOptionsMenu(Menu menu) {
//		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.main, menu);
//		return true;
//	}
	
    /***
     * Function that is called when button is pressed
     * 
     * @author Alex Wulff
     * 
     * @return none
     * @param none
     */
	private void OnButtonPressMatchMe()
	{
		_Listener.showMatchMe();
	}
	
    /***
     * Function that is called when button is pressed
     * 
     * @author Alex Wulff
     * 
     * @return none
     * @param none
     */
	private void OnButtonPressDressUp()
	{
		_Listener.showDressUp();
	}
	
    /***
     * Function that is called when button is pressed
     * 
     * @author Alex Wulff
     * 
     * @return none
     * @param none
     */
	private void OnButtonPressJSON()
	{
		//Gson g = new Gson();
		ClientSender cs = new ClientSender(getActivity());
		cs.execute("NO <EOF>");
	}

}

