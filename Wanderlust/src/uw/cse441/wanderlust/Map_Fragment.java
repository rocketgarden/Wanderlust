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
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class Map_Fragment extends MapFragment implements OnMarkerClickListener {
	// This class extends the "real" MapFragment class to let us add our own
	static final String TAG = "Map_Fragment";
	private Location currentLocation;
	private CameraPosition cameraPos;

	LinearLayout mTopBar;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		cameraPos = CameraPosition.fromLatLngZoom(new LatLng(47.671369, -122.342603), 14.0f);

		currentLocation = new Location("default");
		currentLocation.setLatitude(47.671369);
		currentLocation.setLongitude(-122.342603);

		// Add pois to map

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = super.onCreateView(inflater, container, savedInstanceState);
		mTopBar = (LinearLayout) inflater.inflate(R.layout.map_detail_bar, (ViewGroup) v)
				.findViewById(R.id.topBar);
		mTopBar.setVisibility(View.GONE);

		// load top bar but set to hidden
		// getActivity().setContentView(R.layout.map_detail_bar);
		// LinearLayout topBar = (LinearLayout)
		// getActivity().findViewById(R.id.topBar);
		// topBar.setVisibility(View.GONE);

		return v;

	}

	public void onStart() {
		super.onStart();
		CameraUpdate cu = CameraUpdateFactory.newCameraPosition(cameraPos);
		getMap().moveCamera(cu);

		getMap().setMyLocationEnabled(true);
		getMap().setOnMapClickListener(new OnMapClickListener() {

			@Override
			public void onMapClick(LatLng arg0) {
				mTopBar.setVisibility(View.GONE);
			}
		});
	}

	public void onStop() {
		super.onStop();
	}

	public void onPause() {
		super.onPause();
		mTopBar.setVisibility(View.GONE);
		cameraPos = getMap().getCameraPosition();

	}

	@Override
	public void onResume() {
		super.onResume();

		for (POI p : ((MainActivity) getActivity()).getPlaceDataProvider().getPOIList()) {
			LatLng loc = new LatLng(p.getLocation().first, p.getLocation().second);
			getMap().addMarker(
					new MarkerOptions()
							.position(loc)
							.title('p' + Integer.toString(p.getId()))
							.icon(BitmapDescriptorFactory
									.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
		}

		// Add meetups to map
		for (Meetup m : ((MainActivity) getActivity()).getPlaceDataProvider().getMeetupList()) {
			LatLng loc = new LatLng(m.getLocation().first, m.getLocation().second);
			getMap().addMarker(
					new MarkerOptions()
							.position(loc)
							.title('m' + Integer.toString(m.getId()))
							.icon(BitmapDescriptorFactory
									.defaultMarker(45f)));
		}

		getMap().setOnMarkerClickListener(this);
	}

	@Override
	public boolean onMarkerClick(Marker marker) {
		mTopBar.setVisibility(View.VISIBLE);
		mTopBar.bringToFront();
		String title = marker.getTitle();
		((TextView) getActivity().findViewById(R.id.markerId)).setText(title);
		if (title.charAt(0) == 'm') { // marker represents a meetup
			Button meetupButton = (Button) getActivity().findViewById(R.id.meetUpButton);
			meetupButton.setVisibility(View.GONE);
			Meetup m = ((MainActivity) getActivity()).getPlaceDataProvider().getMeetup(
					Integer.parseInt(title.substring(1)));
			((TextView) getActivity().findViewById(R.id.topBar_address)).setText(m.getAddress());
			((TextView) getActivity().findViewById(R.id.topBar_title)).setText(m.getTitle());
			mTopBar.setBackgroundColor(Color.parseColor("#FFD033"));
		} else { // marker represents a poi
			Button meetupButton = (Button) getActivity().findViewById(R.id.meetUpButton);
			meetupButton.setVisibility(View.VISIBLE);
			POI p = ((MainActivity) getActivity()).getPlaceDataProvider().getPOI(
					Integer.parseInt(title.substring(1)));
			((TextView) getActivity().findViewById(R.id.topBar_address)).setText(p.getAddress());
			((TextView) getActivity().findViewById(R.id.topBar_title)).setText(p.getTitle());
			mTopBar.setBackgroundColor(Color.parseColor("#33B5E5"));
		}

		return true;
	}

}
