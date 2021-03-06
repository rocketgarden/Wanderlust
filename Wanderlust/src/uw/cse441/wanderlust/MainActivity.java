package uw.cse441.wanderlust;

import java.io.IOException;
import java.util.List;

import uw.cse441.wanderlust.utility.Meetup;
import uw.cse441.wanderlust.utility.POI;
import uw.cse441.wanderlust.utility.PlaceDataProvider;
import uw.cse441.wanderlust.utility.SQLPlaceProvider;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

/**
 * This is the main, and probably only activity for the application. All the
 * pages should be implemented in terms of fragments inside this activity
 * 
 * @author Vince
 * 
 */
public class MainActivity extends Activity {
	// constants for argument/intents
	public static final String REQUESTED_MEETUP_KEY = "REQUESTED_MEETUP";
	public static final String REQUESTED_POI_KEY = "REQUESTED_POI";

	static final String TAG = "MainActivity";
	private PlaceDataProvider pdp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		pdp = new SQLPlaceProvider(this);

		ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.setDisplayShowTitleEnabled(false);

		// Map Page
		Tab tab = actionBar.newTab().setText(R.string.title_section_map)
				.setTabListener(new TabListener<Map_Fragment>(this, "map", Map_Fragment.class));
		actionBar.addTab(tab);
		// POI Page
		tab = actionBar
				.newTab()
				.setText(R.string.title_section_poi)
				.setTabListener(
						new TabListener<POI_List_Fragment>(this, "poi", POI_List_Fragment.class));
		actionBar.addTab(tab);
		// MeetUp Page
		tab = actionBar
				.newTab()
				.setText(R.string.title_section_meetup)
				.setTabListener(
						new TabListener<Meetup_List_Fragment>(this, "meetup",
								Meetup_List_Fragment.class));
		actionBar.addTab(tab);
		// Profile Page
		tab = actionBar
				.newTab()
				.setText(R.string.title_section_profile)
				.setTabListener(
						new TabListener<Profile_Fragment>(this, "profile", Profile_Fragment.class));
		actionBar.addTab(tab);
	}
	
	@Override
	public void onStop(){
		super.onStop();
		pdp.close();
	}
	
	@Override
	public void onStart(){
		super.onStart();
		pdp.open();
	}

	public PlaceDataProvider getPlaceDataProvider() {
		return pdp;

	}

	

	private Pair<Float, Float> addressToLocation(String streetAddress) {
		Geocoder coder = new Geocoder(this);
		List<Address> address;
		Pair<Float, Float> latLong = new Pair<Float, Float>((float) 0, (float) 0);
		try {
			address = coder.getFromLocationName(streetAddress, 5);
			if (address != null && address.size() != 0) {
				Address location = address.get(0);
				latLong = new Pair<Float, Float>((float) location.getLatitude(),
						(float) location.getLongitude());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return latLong;
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {
		case R.id.action_addpoi:
			
			Intent i = new Intent(this, New_POI.class);
			i.putExtra(MainActivity.REQUESTED_POI_KEY, -1);
			startActivity(i);
			return true;
		case R.id.action_addmeetup:

			Intent j = new Intent(this, New_Meetup.class);
			j.putExtra(MainActivity.REQUESTED_MEETUP_KEY, -1);
			startActivity(j);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	public void addPOI(View v) {
		String name = ((EditText) findViewById(R.id.name_field)).getText().toString();
		String address = ((EditText) findViewById(R.id.address_field)).getText().toString();
		String description = ((EditText) findViewById(R.id.description_field)).getText().toString();

		POI p = new POI(name, address, description, addressToLocation(address), pdp.getNextPoiId());
		pdp.addPOI(p);
		Fragment poi = getFragmentManager().findFragmentByTag("newpoi");
		FragmentTransaction ft1 = getFragmentManager().beginTransaction();
		ft1.remove(poi);
		ft1.commit();
		getActionBar().setSelectedNavigationItem(1);
	}

	public void addMeetup(View v) {
		String name = ((EditText) findViewById(R.id.poi_field)).getText().toString();
		String address = ((EditText) findViewById(R.id.address_layout)).getText().toString();
		String description = ((EditText) findViewById(R.id.descr_input)).getText().toString();
		String invited = ((EditText) findViewById(R.id.invite_input_field)).getText().toString();
		String date = ((EditText) findViewById(R.id.date_field2)).getText().toString();
		String time = ((EditText) findViewById(R.id.time_input)).getText().toString();

		Meetup m = new Meetup(name, address, description, addressToLocation(address),
				pdp.getnextMeetupId(), invited);
		m.setDate(date + " " + time);
		pdp.addMeetup(m);
		Fragment poi = getFragmentManager().findFragmentByTag("newmeetup");
		FragmentTransaction ft1 = getFragmentManager().beginTransaction();
		;
		ft1.remove(poi);
		ft1.commit();
		getActionBar().setSelectedNavigationItem(2);
	}

	public void showDetails(View v) {
		String title = ((TextView) findViewById(R.id.markerId)).getText().toString();
		if (title.charAt(0) == 'm') { // its a meetup
			Intent i = new Intent(this, Meetup_Detail.class);
			i.putExtra(MainActivity.REQUESTED_MEETUP_KEY, Integer.parseInt(title.substring(1)));
			startActivity(i);
		} else { // its a poi
			Intent i = new Intent(this, POI_Detail.class);
			i.putExtra(MainActivity.REQUESTED_POI_KEY, Integer.parseInt(title.substring(1)));
			startActivity(i);
		}
	}

	public void createMeetup(View v) {
		String title = ((TextView) findViewById(R.id.markerId)).getText().toString();		
		Intent j = new Intent(this, New_Meetup.class);
		j.putExtra(MainActivity.REQUESTED_POI_KEY, Integer.parseInt(title.substring(1)));
		startActivity(j);
	}

	public static class TabListener<T extends Fragment> implements ActionBar.TabListener {
		// c/p'd from
		// http://developer.android.com/guide/topics/ui/actionbar.html#Tabs
		private Fragment mFragment;
		private final Activity mActivity;
		private final String mTag;
		private final Class<T> mClass;

		/**
		 * Constructor used each time a new tab is created.
		 * 
		 * @param activity
		 *            The host Activity, used to instantiate the fragment
		 * @param tag
		 *            The identifier tag for the fragment
		 * @param clz
		 *            The fragment's Class, used to instantiate the fragment
		 */
		public TabListener(Activity activity, String tag, Class<T> clz) {
			mActivity = activity;
			mTag = tag;
			mClass = clz;
		}

		/* The following are each of the ActionBar.TabListener callbacks */

		public void onTabSelected(Tab tab, FragmentTransaction ft) {
			// Check if the fragment is already initialized
			if (mFragment == null) {
				// If not, instantiate and add it to the activity
				mFragment = Fragment.instantiate(mActivity, mClass.getName());
				ft.add(android.R.id.content, mFragment, mTag);
			} else {
				// If it exists, simply attach it in order to show it
				ft.attach(mFragment);
			}
		}

		public void onTabUnselected(Tab tab, FragmentTransaction ft) {
			if (mFragment != null) {
				// Detach the fragment, because another one is being attached
				ft.detach(mFragment);
			}
		}

		public void onTabReselected(Tab tab, FragmentTransaction ft) {
			// User selected the already selected tab. Usually do nothing.
			// Check if the fragment is already initialized
			if (mFragment == null) {
				// If not, instantiate and add it to the activity
				mFragment = Fragment.instantiate(mActivity, mClass.getName());
				ft.add(android.R.id.content, mFragment, mTag);
			} else {
				// If it exists, simply attach it in order to show it
				ft.attach(mFragment);
			}
		}
	}

}
