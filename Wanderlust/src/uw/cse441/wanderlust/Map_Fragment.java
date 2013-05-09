package uw.cse441.wanderlust;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;

public class Map_Fragment extends MapFragment {
//This class extends the "real" MapFragment class to let us add our own
	public void onResume() {
		super.onResume();
		getMap().moveCamera(
				CameraUpdateFactory.newLatLngZoom(new LatLng(47.671369,
						-122.342603), 14.0f)); //TODO Change this to right location
	}
}
