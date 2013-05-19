package uw.cse441.wanderlust;

import java.io.IOException;
import java.util.List;

import uw.cse441.wanderlust.utility.POI;
import uw.cse441.wanderlust.utility.PlaceDataProvider;
import android.app.Fragment;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

public class New_POI_Fragment extends Fragment {
	static final String TAG = "Meetup_Fragment";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.newpoi, container, false);
		return view;
	}
	
	@Override
	public void onResume() {
		super.onResume();
	}
	
	public void addPOI(View v) {
		String name = ((EditText)getView().findViewById(R.id.name_field)).getText().toString();
		String address = ((EditText)getView().findViewById(R.id.address_field)).getText().toString();
		String description = ((EditText)getView().findViewById(R.id.description_field)).getText().toString();
		
		PlaceDataProvider pdp = ((MainActivity) getActivity()).getPlaceDataProvider();
		
		POI p = new POI(address, 
        		addressToLocation(address), 
        		name, description, pdp.getNextPoiId(), null);
        pdp.addPOI(p);

	}
	
	private Pair<Float, Float> addressToLocation(String streetAddress){
		Geocoder coder = new Geocoder(getActivity());
	    List<Address> address;
	    Pair<Float,Float> latLong = null;
        try {
			address = coder.getFromLocationName(streetAddress,5);
		    if (address != null && address.size() != 0) {
		        Address location = address.get(0);
		        latLong = new Pair<Float, Float>((float)location.getLatitude(), (float)location.getLongitude());
		    }
        } catch (IOException e) {
        	latLong = new Pair<Float, Float>((float)0, (float)0);
			e.printStackTrace();
		}
		return latLong;
	}
}