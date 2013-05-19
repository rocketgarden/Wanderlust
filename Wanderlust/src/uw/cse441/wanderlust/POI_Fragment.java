package uw.cse441.wanderlust;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import uw.cse441.wanderlust.utility.POI;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class POI_Fragment extends ListFragment {
	/*
	 * Notes on lifecycle:
	 * onAttach: Ensure that parent activity implements any required listeners (if any)
	 * onCreateView: draw and return a view for the parent activity
	 * 
	 */
	static final String TAG = "POI_Fragment";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.poilist, container, false);
		return view;
	}
	
	@Override
	public void onResume() {
		super.onResume();
    
	    // Fill in list view
		ListView lv = getListView();
	    List<Map<String, String>> data = new ArrayList<Map<String, String>>();
	    for (POI p: MainActivity.getPlaceDataProvider().getPOIList()) {
	        Map<String, String> datum = new HashMap<String, String>(2);
	        datum.put("title", p.getTitle());
	        datum.put("address", p.getAddress());
	        data.add(datum);
	    }
	    SimpleAdapter adapter = new SimpleAdapter(getActivity(), data,
	                                              android.R.layout.simple_list_item_2,
	                                              new String[] {"title", "address"},
	                                              new int[] {android.R.id.text1,
	                                                         android.R.id.text2});
	    lv.setAdapter(adapter);
	    lv.setTextFilterEnabled(true);
	}
	
	

}
