package uw.cse441.wanderlust;

import uw.cse441.wanderlust.utility.Meetup;
import uw.cse441.wanderlust.utility.POI;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

public class New_Meetup_Fragment extends Fragment {
	static final String TAG = "Meetup_Fragment";
	private int id;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (getArguments() != null) {
			id = getArguments().getInt("id", -1);
		} else {
			id = -1;
		}
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.newmeetup, container, false);
		return view;
	}
	
	@Override
	public void onResume() {
		super.onResume();
		
		if (id != -1) {
			POI p = ((MainActivity) getActivity()).getPlaceDataProvider().getPOI(id);
			
			if (p != null) {
			    ((EditText) getView().findViewById(R.id.poi_field)).setText(p.getTitle());
			    
			    ((EditText) getView().findViewById(R.id.address_text)).setText(p.getAddress());
			}
	
		}

	}
}