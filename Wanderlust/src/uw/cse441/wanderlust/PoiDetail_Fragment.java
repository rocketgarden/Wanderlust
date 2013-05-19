package uw.cse441.wanderlust;

import uw.cse441.wanderlust.utility.POI;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class PoiDetail_Fragment extends Fragment {
	static final String TAG = "PoiDetail_Fragment";
	private int id;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//get id from bundle
		id = getArguments().getInt("id", -1);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.poi_detail, container, false);
		return view;
	}
	
	@Override
	public void onResume() {
		super.onResume();
		POI p = ((MainActivity) getActivity()).getPlaceDataProvider().getPOI(id);
		
		if (p == null) {
		    TextView title = (TextView) getView().findViewById(R.id.poi_title);
		    title.setText("Title");
		    
		    TextView location = (TextView) getView().findViewById(R.id.location_info);
		    location.setText("Address Text...");
		    
		    TextView story = (TextView) getView().findViewById(R.id.story_info);
		    story.setText("Extended Description...");
		} else {
		    TextView title = (TextView) getView().findViewById(R.id.poi_title);
		    title.setText(p.getTitle());
		    
		    TextView location = (TextView) getView().findViewById(R.id.location_info);
		    location.setText(p.getAddress());
		    
		    TextView story = (TextView) getView().findViewById(R.id.story_info);
		    story.setText(p.getDescription());
		}
	}

}
