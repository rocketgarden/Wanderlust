package uw.cse441.wanderlust;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import uw.cse441.wanderlust.utility.Meetup;
import uw.cse441.wanderlust.utility.POI;
import android.app.Fragment;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class Meetup_Fragment extends Fragment {
	static final String TAG = "Meetup_Fragment";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.meetuplist, container, false);
		return view;
	}
	
	@Override
	public void onResume() {
		super.onResume();
    
	    // Fill in list view
	    ListView lv = (ListView) getView().findViewById(R.id.meetuplist);
	    List<Map<String, String>> data = new ArrayList<Map<String, String>>();
	    for (Meetup m: MainActivity.getPlaceDataProvider().getMeetupList()) {
	        Map<String, String> datum = new HashMap<String, String>(2);
	        datum.put("title", m.getTitle());
	        datum.put("date", m.getDate());
	        data.add(datum);
	    }
	    SimpleAdapter adapter = new SimpleAdapter(getActivity(), data,
	                                              android.R.layout.simple_list_item_2,
	                                              new String[] {"title", "date"},
	                                              new int[] {android.R.id.text1,
	                                                         android.R.id.text2});
	    lv.setAdapter(adapter);
	    lv.setTextFilterEnabled(true);
	}
}
