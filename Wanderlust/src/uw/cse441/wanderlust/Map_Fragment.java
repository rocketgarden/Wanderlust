package uw.cse441.wanderlust;

import uw.cse441.wanderlust.utility.Meetup;
import uw.cse441.wanderlust.utility.POI;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class Map_Fragment extends MapFragment implements OnMarkerClickListener {
	// This class extends the "real" MapFragment class to let us add our own
	static final String TAG = "Map_Fragment";
	private Location currentLocation;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		currentLocation = new Location("default");
		currentLocation.setLatitude(47.671369);
		currentLocation.setLongitude(-122.342603);
				
		// Add pois to map
		
	}
	
	@Override
	public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		super.onCreateView(inflater, container, savedInstanceState);
		View view = super.onCreateView(inflater, container, savedInstanceState);
		getMap().setMyLocationEnabled(true);
		
		// load top bar but set to hidden
  		getActivity().setContentView(R.layout.map_detail_bar);
  		LinearLayout topBar = (LinearLayout) getActivity().findViewById(R.id.topBar);
  		topBar.setVisibility(View.GONE);
		
		return view;
		
	}

	public void onPause() {
		super.onPause();
		currentLocation = getMap().getMyLocation();
		
	}

	@Override
	public void onResume() {
		super.onResume();
		CameraUpdate cu = CameraUpdateFactory.newLatLngZoom(new LatLng(
				currentLocation.getLatitude(), currentLocation.getLongitude()),
				14.0f);
		getMap().moveCamera(cu);
		
		for(POI p : ((MainActivity) getActivity()).getPlaceDataProvider().getPOIList()){
	        LatLng loc = new LatLng(p.getLocation().first, p.getLocation().second);
	        getMap().addMarker(new MarkerOptions().position(loc)
	  	          .title('p' + Integer.toString(p.getId())));
		}
		
		// Add meetups to map
		for(Meetup m : ((MainActivity) getActivity()).getPlaceDataProvider().getMeetupList()){
	        LatLng loc = new LatLng(m.getLocation().first, m.getLocation().second);
	        getMap().addMarker(new MarkerOptions().position(loc)
	  	          .title('m' + Integer.toString(m.getId())));
		}
		
		getMap().setOnMarkerClickListener(this);
	}
	
	@Override
	public boolean onMarkerClick(Marker marker) {
		LinearLayout topBar = (LinearLayout) getActivity().findViewById(R.id.topBar);
		Button closeButton = (Button) getActivity().findViewById(R.id.closeButton);
		topBar.setVisibility(View.VISIBLE);
		topBar.bringToFront();
		String title = marker.getTitle();
		if (title.charAt(0) == 'm') { // marker represents a maeetup
			Button meetupButton = (Button) getActivity().findViewById(R.id.meetUpButton);
			meetupButton.setVisibility(View.GONE);
			Meetup m = ((MainActivity) getActivity()).getPlaceDataProvider().getMeetup(Integer.parseInt(title.substring(1)));
			((TextView) getActivity().findViewById(R.id.topBar_address)).setText(m.getAddress());
			((TextView) getActivity().findViewById(R.id.topBar_title)).setText(m.getTitle());
			topBar.setBackgroundColor(Color.parseColor("#19FF19"));
			closeButton.setBackgroundResource(R.drawable.hide2);
		} else { // marker represents a poi
			Button meetupButton = (Button) getActivity().findViewById(R.id.meetUpButton);
			meetupButton.setVisibility(View.VISIBLE);
			POI p = ((MainActivity) getActivity()).getPlaceDataProvider().getPOI(Integer.parseInt(title.substring(1)));
			((TextView) getActivity().findViewById(R.id.topBar_address)).setText(p.getAddress());
			((TextView) getActivity().findViewById(R.id.topBar_title)).setText(p.getTitle());
			topBar.setBackgroundColor(Color.parseColor("#33B5E5"));
			closeButton.setBackgroundResource(R.drawable.hide1);
		}

		return true;
	 }	
	
	
}
