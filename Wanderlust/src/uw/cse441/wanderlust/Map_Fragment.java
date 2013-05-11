package uw.cse441.wanderlust;

import uw.cse441.wanderlust.utility.Meetup;
import uw.cse441.wanderlust.utility.POI;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
		currentLocation = new Location("default");
		currentLocation.setLatitude(47.671369);
		currentLocation.setLongitude(-122.342603);
				
		// Add pois to map
		for(POI p : MainActivity.getPlaceDataProvider().getPOIList()){
	        LatLng loc = new LatLng(p.getLocation().first, p.getLocation().second);
	        getMap().addMarker(new MarkerOptions().position(loc)
	  	          .title('p' + Integer.toString(p.getId())));
		}
		
		// Add meetups to map
		for(Meetup m : MainActivity.getPlaceDataProvider().getMeetupList()){
	        LatLng loc = new LatLng(m.getLocation().first, m.getLocation().second);
	        getMap().addMarker(new MarkerOptions().position(loc)
	  	          .title('m' + Integer.toString(m.getId())));
		}
	}
	
	@Override
	public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		View view = super.onCreateView(inflater, container, savedInstanceState);
		getMap().setMyLocationEnabled(true);
		return view;
		
	}

	public void onPause() {
		currentLocation = getMap().getMyLocation();
		
	}

	@Override
	public void onResume() {
		super.onResume();
		CameraUpdate cu = CameraUpdateFactory.newLatLngZoom(new LatLng(
				currentLocation.getLatitude(), currentLocation.getLongitude()),
				14.0f);
		getMap().moveCamera(cu);
	}
	
	@Override
	public boolean onMarkerClick(Marker marker) {
		// TODO show top bar
		String title = marker.getTitle();
		if (title.charAt(0) == 'm') { // marker represents a marker
			// TODO hide meetup button
			Meetup m = MainActivity.getPlaceDataProvider().getMeetup(title.charAt(1));
			// TODO fill in text on bar
			// bar address = m.getAddress();
			// bar title = m.getTitle();
		} else { // marker represents a poi
			POI p = MainActivity.getPlaceDataProvider().getPOI(title.charAt(1));
			// TODO fill in text on bar
			// bar address = p.getAddress();
			// bar title = p.getTitle();
		}
		return true;
	  }
}
