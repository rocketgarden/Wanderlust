package uw.cse441.wanderlust;

import java.util.ArrayList;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class Profile_Fragment extends Fragment {
	static final String TAG = "Profile_Fragment";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.profile, container, false);
		return view;
	}
	
	@Override
	public void onResume() {
		super.onResume();
    
	    // Fill in poi list
	    final ListView poiList = (ListView) getView().findViewById(R.id.listedpois);
	    String[] pois = new String[] { "Orange Statue", "IMA" };

	    ArrayList<String> list1 = new ArrayList<String>();
	    for (int i = 0; i < pois.length; ++i) {
	      list1.add(pois[i]);
	    }
   
	    ArrayAdapter<String> aa1 =  new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, list1);
	    poiList.setAdapter(aa1);
	    
	    // Fill in meetups list
	    final ListView meetupList = (ListView) getView().findViewById(R.id.listedmeetups);
	    String[] meetups = new String[] { "Diner" };

	    ArrayList<String> list2 = new ArrayList<String>();
	    for (int i = 0; i < meetups.length; ++i) {
	      list2.add(meetups[i]);
	    }
   
	    ArrayAdapter<String> aa2 =  new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, list2);
	    meetupList.setAdapter(aa2);
	}
}
