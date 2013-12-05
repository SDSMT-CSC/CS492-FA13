package edu.sdsmt.ThuryWulff.sentireaerissimia;

import java.util.LinkedList;

/***
 * Listener for the main activity.  This contains all the needed actions to be triggered from view level processing
 * 
 * @author Alex Wulff
 *
 */
public interface IMenuControlListener {
	public void showMatchMe();	
	public void showDressUp();
	public void showSpecificSelection();
	public void onSettingsAction(boolean business, boolean casual, boolean relaxed, boolean formal);
	public void onColorsAction(boolean black, boolean blue, boolean brown, 
			boolean green, boolean grey, boolean orange, boolean pink, 
			boolean purple, boolean red, boolean white, boolean yellow);
	public void showSelectColors();
	public void showSelectSetting();	
	public Clothing getFindMe();
	public void goClicked();
	public LinkedList<ClothingImage> getRack();
}
