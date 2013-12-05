package edu.sdsmt.ThuryWulff.sentireaerissimia;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import edu.sdsmt.ThuryWulff.forecastsforfun.R;

/***
 * Fragment for storing information that is used in the Setting
 * selection pop out box
 * 
 * @author Alex Wulff
 * 
 */
public class Settings_Selection_Dialog_Fragment extends DialogFragment{

	private RadioButton _IsRelaxed;
	private RadioButton _IsCasual;
	private RadioButton _IsBusiness;
	private RadioButton _IsFormal;

	/***
	 * Constructor
	 * 
	 * @author Alex Wulff
	 */
    public Settings_Selection_Dialog_Fragment() {
        // Empty constructor required for DialogFragment
    }

    /***
     * Overridden on create view to setup all of the checkboxes
     * 
     * @author Alex Wulff
     * 
     * @return View The view to be used for this fragment
     * @param inflater the inflator for inflating the view
     * @param container ViewGroup to be used
     * @param savedInstanceState the bundle to be used
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.setting_selection_popout, container);
                
        RadioGroup rg = (RadioGroup) view.findViewById(R.id.radioGroup1);
        
        // set the existing values
        _IsBusiness = (RadioButton) view.findViewById(R.id.BusinessRadioButton);
        _IsCasual = (RadioButton) view.findViewById(R.id.CasualRadioButton);
        _IsRelaxed = (RadioButton) view.findViewById(R.id.RelaxedRadioButton);
        _IsFormal = (RadioButton) view.findViewById(R.id.FormalRadioButton);
        
        IMenuControlListener baseActivity = (IMenuControlListener) getActivity();
        Clothing target = baseActivity.getFindMe();
        if(target.IsBusiness)
        {
        	_IsBusiness.toggle();
        }
        else if (target.IsCasual)
        {
        	_IsCasual.toggle();
        }
        else if (target.IsRelaxed)
        {
        	_IsRelaxed.toggle();
        }
        else
        {
        	_IsFormal.toggle();
        }
        
        
        // set listeners 
        rg.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				if (checkedId == R.id.BusinessRadioButton)
				{
					// toggle business on clothing type
					IMenuControlListener baseActivity = (IMenuControlListener) getActivity();
					baseActivity.onSettingsAction(true, false, false, false);
				}
				else if (checkedId == R.id.CasualRadioButton)
				{
					// toggle casual on clothing type
					IMenuControlListener baseActivity = (IMenuControlListener) getActivity();
					baseActivity.onSettingsAction(false, true, false, false);
				}
				else if (checkedId == R.id.RelaxedRadioButton)
				{
					// toggle relaxed on clothing type
					IMenuControlListener baseActivity = (IMenuControlListener) getActivity();
					baseActivity.onSettingsAction(false, false, true, false);
				}
				else
				{
					// toggle relaxed on clothing type
					IMenuControlListener baseActivity = (IMenuControlListener) getActivity();
					baseActivity.onSettingsAction(false, false, false, true);
				}
				
			}
		});
        
        getDialog().setTitle("Setting");

        return view;
    }
    
}
