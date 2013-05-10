package uw.cse441.wanderlust;

import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;

public class Map_Fragment extends MapFragment {
	// This class extends the "real" MapFragment class to let us add our own
	static final String TAG = "Map_Fragment";
	private Location currentLocation;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		currentLocation = new Location("default");
		currentLocation.setLatitude(47.671369);
		currentLocation.setLongitude(-122.342603);
		
		
		//TODO Add map markers here
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
}
