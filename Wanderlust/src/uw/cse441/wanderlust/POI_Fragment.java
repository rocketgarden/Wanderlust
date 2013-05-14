package uw.cse441.wanderlust;

import java.util.ArrayList;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class POI_Fragment extends Fragment {
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
		
		    String[] values = new String[] { "place1", "place2", "place3", "place4", "place5", "place6" };

		    ArrayList<String> myList = new ArrayList<String>();
		    for (int i = 0; i < values.length; ++i) {
		      myList.add(values[i]);
		    }
		    
//		    ListView lv = (ListView) getActivity().findViewById(R.id.poiList);
//		    ArrayAdapter<String> myarrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, myList);
//		    lv.setAdapter(myarrayAdapter);
//		    lv.setTextFilterEnabled(true);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.poilist, container, false);
		return view;
	}
	

}
