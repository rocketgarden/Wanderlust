package uw.cse441.wanderlust;

import android.location.Location;
import android.os.Bundle;

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
