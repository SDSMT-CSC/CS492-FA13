package edu.sdsmt.ThuryWulff.sentireaerissimia;

import java.util.LinkedList;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;

import com.google.gson.Gson;

import edu.sdsmt.ThuryWulff.forecastsforfun.R;

/***
 * The MainActivity of the App
 * 
 * @author Daniel Thury
 * @author Alex Wulff
 *
 */
public class MainActivity extends Activity implements IMenuControlListener
{
	
    private FragmentManager _FragmentManager;
    private Clothing _FindMe;
    private ClothingImage _Template;
    private LinkedList<ClothingImage> list;
    
    /***
     * The OnCreate method for this activity, is called when the activity is created,
     * Sets up the fragment to use
     * 
	 * @author Daniel Thury
	 * @author Alex Wulff
	 *  
	 * @return none
	 * @param savedInstanceState holds the saved state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        _FindMe = new Clothing();
        _Template = new ClothingImage();
        setContentView(R.layout.activity_main);
        _FragmentManager = getFragmentManager();
     
        list = new LinkedList<ClothingImage>();
        
        showMainMenu();
    }

    /***
     * Setts up the fragment for displaying the weather data
     * 
	 * @author Daniel Thury
	 * @author Alex Wulff
     * 
     * @return none
     * @param zipCode the zip code that is used for this app
     */
    private void showMainMenu()
    {
    	MainMenu mm;
    	
    	mm = (MainMenu) _FragmentManager.findFragmentById(R.layout.mainmenufragmentview);
    	
    	if (mm == null)
    	{
    		mm = new MainMenu();
        	_FragmentManager.beginTransaction()
    					.replace(R.id.FragmentContainer, mm, null)
    					.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
    					.addToBackStack(null)        					
    					.commit();
    	}
    }
    
    /***
     * Function to show the Match me fragment
     * 
     * @author Alex Wulff
     * 
     * @param none
     * @return none
     */
    @Override
    public void showMatchMe()
    {
    	Match_Me_Fragment mmf;
    	mmf = (Match_Me_Fragment) _FragmentManager.findFragmentById(R.layout.match_me_fragment);
    	if (mmf == null)
    	{
    		mmf = new Match_Me_Fragment();
    		_FragmentManager.beginTransaction()
			.replace(R.id.FragmentContainer, mmf, null)
			.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
			.addToBackStack(null)        					
			.commit();
    	}
    }
    
    /***
     * Function to show the Specific fragment
     * 
     * @author Alex Wulff
     * 
     * @param none
     * @return none
     */
    @Override
    public void showSpecificSelection()
    {
    	Specific_Selection_Fragment ssf;
    	ssf = (Specific_Selection_Fragment) _FragmentManager.findFragmentById(R.layout.specific_selection_fragment);
    	if (ssf == null)
    	{
    		ssf = new Specific_Selection_Fragment();
    		_FragmentManager.beginTransaction()
			.replace(R.id.FragmentContainer, ssf, null)
			.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
			.addToBackStack(null)        					
			.commit();
    	}
    }
    
    /***
     * Function to show the Dress up fragment
     * 
     * @author Alex Wulff
     * 
     * @param none
     * @return none
     */
    @Override
    public void showDressUp()
    {
    	Dress_Up_Fragment duf;
    	duf = (Dress_Up_Fragment) _FragmentManager.findFragmentById(R.layout.dress_up_fragment);
    	if (duf == null)
    	{
    		duf = new Dress_Up_Fragment();
    		_FragmentManager.beginTransaction()
			.replace(R.id.FragmentContainer, duf, null)
			.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
			.addToBackStack(null)        					
			.commit();
    	}
    }
    
    /***
     * Function that is called when there is a setting change in one of the fragments
     * 
     * @author Alex Wulff
     * 
     * @param business tells if business setting is selected
     * @param casual tells if casual setting is selected
     * @param relaxed tells if relaxed setting is selected
     * @param formal tells if formal setting is selected
     * @return none
     */
    public void onSettingsAction(boolean business, boolean casual, boolean relaxed, boolean formal)
	{
    	_FindMe.IsBusiness = business;
    	_FindMe.IsCasual = casual;
    	_FindMe.IsRelaxed = relaxed;
    	_FindMe.IsFormal = formal;
	}
    
    /***
     * Function that is called when there is a color change action
     * 
     * @author Alex Wulff
     * 
     * @param black Selects the color for matching
     * @param blue Selects the color for matching
     * @param brown Selects the color for matching
     * @param green Selects the color for matching
     * @param grey Selects the color for matching
     * @param orange Selects the color for matching
     * @param pink Selects the color for matching
     * @param purple Selects the color for matching
     * @param red Selects the color for matching
     * @param white Selects the color for matching
     * @param yellow Selects the color for matching
     * @return none
     */
    public void onColorsAction(boolean black, boolean blue, boolean brown, 
			boolean green, boolean grey, boolean orange, boolean pink, 
			boolean purple, boolean red, boolean white, boolean yellow)
    {
    	_FindMe.CanBlack = black;
    	_FindMe.CanBlue = blue;
    	_FindMe.CanBrown = brown;
    	_FindMe.CanGreen = green;
    	_FindMe.CanGrey = grey;
    	_FindMe.CanOrange = orange;
    	_FindMe.CanPink = pink;
    	_FindMe.CanPurple = purple;
    	_FindMe.CanRed = red;
    	_FindMe.CanWhite = white;
    	_FindMe.CanYellow = yellow;
    }

    /***
     * Function that is called to bring up the select colors popout menu
     * 
     * @author Alex Wulff
     * 
     * @param none
     * @return none
     */
	@Override
	public void showSelectColors() {
        Color_Selection_Dialog_Fragment csdf = new Color_Selection_Dialog_Fragment();
        csdf.show(_FragmentManager, "fragment_edit_name");
		
	}

	/***
	 * Function that is called to bring up the select setting popout
	 * 
     * @author Alex Wulff
     * 
     * @param none
     * @return none
	 */
	@Override
	public void showSelectSetting() {
		Settings_Selection_Dialog_Fragment ssdf = new Settings_Selection_Dialog_Fragment();
		ssdf.show(_FragmentManager, "fragment_edit_name");
	}

	/***
	 * Function to access the find me clothing from other classes
	 * 
	 * @author Alex Wulff
	 * 
	 * @param none
	 * @return Clothing the FindMe clothing of this class
	 */
	@Override
	public Clothing getFindMe() {
		return _FindMe;
	}
	
	/***
	 * function that is called when the Go button is pressed in the fragments
	 * 
	 * @author Alex Wulff
	 * 
	 * @param none
	 * @return none
	 */
	@Override
	public void goClicked()
	{
		ClientSender cs = new ClientSender(this);		
		String gsonData = new Gson().toJson(_FindMe);
		String wasted = new Gson().toJson(_Template);
		String transmit = new StringBuilder().append(gsonData).append("<EOF>").toString().trim();
		cs.execute(transmit);
		
		while (cs.Reply == null)
		{
			try 
			{
				Thread.sleep(500);
			}
			catch(Exception e)
			{
				
			}
		}
		
		int count = Integer.parseInt(cs.Reply.replace("\n", ""));
		
		for (int i = 0; i < count ; i +=1)
		{	
			cs = new ClientSender(this);
			cs.execute(Integer.toString(i)+"<EOF>");

			while (cs.Reply == null)
			{
				try 
				{
					Thread.sleep(500);
				}
				catch(Exception e)
				{
					
				}
			}
			
			ClothingImage temp = new Gson().fromJson(cs.Reply, _Template.getClass());
			if (temp != null)
			{
				list.add(temp);
			}
		}
		
		// now that we're done, pop the dress up tab
		showDressUp();
	}
	
	/***
	 * Exposes the list of clothingImages
	 * 
	 * @author Alex Wulff
	 * 
	 * @param none
	 * @return LinkedList<ClothingImgage> A list of the Clothing images
	 */
	@Override
	public LinkedList<ClothingImage> getRack()
	{
		return list;
	}
}
