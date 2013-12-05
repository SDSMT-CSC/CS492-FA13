package edu.sdsmt.ThuryWulff.sentireaerissimia;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import edu.sdsmt.ThuryWulff.forecastsforfun.R;

/***
 * Fragment for storing information that is used in the Color
 * selection pop out box
 * 
 * @author Alex Wulff
 * 
 */
public class Color_Selection_Dialog_Fragment extends DialogFragment{

	private CheckBox _Black;
	private CheckBox _Blue;
	private CheckBox _Brown;
	private CheckBox _Green;
	private CheckBox _Grey;
	private CheckBox _Orange;
	private CheckBox _Pink;
	private CheckBox _Purple;
	private CheckBox _Red;
	private CheckBox _White;
	private CheckBox _Yellow;	
	
	/***
	 * Constructor
	 * 
	 * @author Alex Wulff
	 */
    public Color_Selection_Dialog_Fragment() {
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
        View view = inflater.inflate(R.layout.color_selection_popout, container);
        _Black = (CheckBox) view.findViewById(R.id.BlackCheckBox);        
        _Blue = (CheckBox) view.findViewById(R.id.BlueCheckBox);
        _Brown = (CheckBox) view.findViewById(R.id.BrownCheckBox);
        _Green = (CheckBox) view.findViewById(R.id.GreenCheckBox);
        _Grey = (CheckBox) view.findViewById(R.id.GreyCheckBox);
        _Orange = (CheckBox) view.findViewById(R.id.OrangeCheckBox);
        _Pink = (CheckBox) view.findViewById(R.id.PinkCheckBox);
        _Purple = (CheckBox) view.findViewById(R.id.PurpleCheckBox);
        _Red = (CheckBox) view.findViewById(R.id.RedCheckBox);
        _White = (CheckBox) view.findViewById(R.id.WhiteCheckBox);
        _Yellow = (CheckBox) view.findViewById(R.id.YellowCheckBox);
        
        // build the values back into what is selected
        IMenuControlListener baseActivity = (IMenuControlListener) getActivity();
        Clothing target = baseActivity.getFindMe();
        
    	_Black.setChecked(target.CanBlack);
    	_Blue.setChecked(target.CanBlue);
    	_Brown.setChecked(target.CanBrown);
    	_Green.setChecked(target.CanGreen);
    	_Grey.setChecked(target.CanGrey);
    	_Orange.setChecked(target.CanOrange);
    	_Pink.setChecked(target.CanPink);
    	_Purple.setChecked(target.CanPurple);
    	_Red.setChecked(target.CanRed);
    	_White.setChecked(target.CanWhite);
    	_Yellow.setChecked(target.CanYellow);
        
        // set listeners for clicks        
        _Black.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// call listener to notify status of each selected check box
				IMenuControlListener baseActivity = (IMenuControlListener) getActivity();
				baseActivity.onColorsAction(_Black.isChecked(), _Blue.isChecked(), _Brown.isChecked(), 
						_Green.isChecked(), _Grey.isChecked(), _Orange.isChecked(), _Pink.isChecked(),
						_Purple.isChecked(), _Red.isChecked(), _White.isChecked(), _Yellow.isChecked());
			}
		});
        
        _Blue.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// call listener to notify status of each selected check box
				IMenuControlListener baseActivity = (IMenuControlListener) getActivity();
				baseActivity.onColorsAction(_Black.isChecked(), _Blue.isChecked(), _Brown.isChecked(), 
						_Green.isChecked(), _Grey.isChecked(), _Orange.isChecked(), _Pink.isChecked(),
						_Purple.isChecked(), _Red.isChecked(), _White.isChecked(), _Yellow.isChecked());
			}
		});
        
        _Brown.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// call listener to notify status of each selected check box
				IMenuControlListener baseActivity = (IMenuControlListener) getActivity();
				baseActivity.onColorsAction(_Black.isChecked(), _Blue.isChecked(), _Brown.isChecked(), 
						_Green.isChecked(), _Grey.isChecked(), _Orange.isChecked(), _Pink.isChecked(),
						_Purple.isChecked(), _Red.isChecked(), _White.isChecked(), _Yellow.isChecked());
			}
		});
        
        _Green.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// call listener to notify status of each selected check box
				IMenuControlListener baseActivity = (IMenuControlListener) getActivity();
				baseActivity.onColorsAction(_Black.isChecked(), _Blue.isChecked(), _Brown.isChecked(), 
						_Green.isChecked(), _Grey.isChecked(), _Orange.isChecked(), _Pink.isChecked(),
						_Purple.isChecked(), _Red.isChecked(), _White.isChecked(), _Yellow.isChecked());
			}
		});
        
        _Grey.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// call listener to notify status of each selected check box
				IMenuControlListener baseActivity = (IMenuControlListener) getActivity();
				baseActivity.onColorsAction(_Black.isChecked(), _Blue.isChecked(), _Brown.isChecked(), 
						_Green.isChecked(), _Grey.isChecked(), _Orange.isChecked(), _Pink.isChecked(),
						_Purple.isChecked(), _Red.isChecked(), _White.isChecked(), _Yellow.isChecked());
			}
		});
        
        _Orange.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// call listener to notify status of each selected check box
				IMenuControlListener baseActivity = (IMenuControlListener) getActivity();
				baseActivity.onColorsAction(_Black.isChecked(), _Blue.isChecked(), _Brown.isChecked(), 
						_Green.isChecked(), _Grey.isChecked(), _Orange.isChecked(), _Pink.isChecked(),
						_Purple.isChecked(), _Red.isChecked(), _White.isChecked(), _Yellow.isChecked());
			}
		});
        
        _Pink.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// call listener to notify status of each selected check box
				IMenuControlListener baseActivity = (IMenuControlListener) getActivity();
				baseActivity.onColorsAction(_Black.isChecked(), _Blue.isChecked(), _Brown.isChecked(), 
						_Green.isChecked(), _Grey.isChecked(), _Orange.isChecked(), _Pink.isChecked(),
						_Purple.isChecked(), _Red.isChecked(), _White.isChecked(), _Yellow.isChecked());
			}
		});
        
        _Purple.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// call listener to notify status of each selected check box
				IMenuControlListener baseActivity = (IMenuControlListener) getActivity();
				baseActivity.onColorsAction(_Black.isChecked(), _Blue.isChecked(), _Brown.isChecked(), 
						_Green.isChecked(), _Grey.isChecked(), _Orange.isChecked(), _Pink.isChecked(),
						_Purple.isChecked(), _Red.isChecked(), _White.isChecked(), _Yellow.isChecked());
			}
		});
        
        _Red.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// call listener to notify status of each selected check box
				IMenuControlListener baseActivity = (IMenuControlListener) getActivity();
				baseActivity.onColorsAction(_Black.isChecked(), _Blue.isChecked(), _Brown.isChecked(), 
						_Green.isChecked(), _Grey.isChecked(), _Orange.isChecked(), _Pink.isChecked(),
						_Purple.isChecked(), _Red.isChecked(), _White.isChecked(), _Yellow.isChecked());
			}
		});
        
        _White.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// call listener to notify status of each selected check box
				IMenuControlListener baseActivity = (IMenuControlListener) getActivity();
				baseActivity.onColorsAction(_Black.isChecked(), _Blue.isChecked(), _Brown.isChecked(), 
						_Green.isChecked(), _Grey.isChecked(), _Orange.isChecked(), _Pink.isChecked(),
						_Purple.isChecked(), _Red.isChecked(), _White.isChecked(), _Yellow.isChecked());
			}
		});
        
        _Yellow.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// call listener to notify status of each selected check box
				IMenuControlListener baseActivity = (IMenuControlListener) getActivity();
				baseActivity.onColorsAction(_Black.isChecked(), _Blue.isChecked(), _Brown.isChecked(), 
						_Green.isChecked(), _Grey.isChecked(), _Orange.isChecked(), _Pink.isChecked(),
						_Purple.isChecked(), _Red.isChecked(), _White.isChecked(), _Yellow.isChecked());
			}
		});
        
        
        getDialog().setTitle("Colors");

        return view;
    }
}
