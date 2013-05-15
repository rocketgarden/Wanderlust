package uw.cse441.wanderlust;

import java.io.IOException;
import java.util.List;

import uw.cse441.wanderlust.utility.*;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

/**
 * This is the main, and probably only activity for the application. All the
 * pages should be implemented in terms of fragments inside this activity
 * 
 * @author Vince
 * 
 */
public class MainActivity extends Activity {

	static final String TAG = "MainActivity";
	private static PlaceDataProvider pdp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		pdp = new PlaceDataProvider();
		loadDatabase();
		
		ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.setDisplayShowTitleEnabled(false);

		// Map Page
		Tab tab = actionBar
				.newTab()
				.setText(R.string.title_section_map)
				.setTabListener(
						new TabListener<Map_Fragment>(this, "map",
								Map_Fragment.class));
		actionBar.addTab(tab);
		// POI Page
		tab = actionBar
				.newTab()
				.setText(R.string.title_section_poi)
				.setTabListener(
						new TabListener<POI_Fragment>(this, "poi",
								POI_Fragment.class));
		actionBar.addTab(tab);
		// MeetUp Page
		tab = actionBar
				.newTab()
				.setText(R.string.title_section_meetup)
				.setTabListener(
						new TabListener<Meetup_Fragment>(this, "meetup",
								Meetup_Fragment.class));
		actionBar.addTab(tab);
		// Profile Page
		tab = actionBar
				.newTab()
				.setText(R.string.title_section_profile)
				.setTabListener(
						new TabListener<Profile_Fragment>(this, "profile",
								Profile_Fragment.class));
		actionBar.addTab(tab);
	}
	
	public static PlaceDataProvider getPlaceDataProvider(){
		return pdp;
		
	}

	public void loadDatabase(){
        // create pois
		//N 51st St & Meridian Ave N Seattle
		POI p1 = new POI("5020 Meridian Avenue North, Seattle, WA", 
        		addressToLocation("5020 Meridian Avenue North, Seattle, WA"), 
        		"Orange Statue", null, pdp.getNextPoiId(), null);
        pdp.addPOI(p1);
        
        POI p2 = new POI("N 57th Street, Seattle, WA", 
        		addressToLocation("N 57th Street, Seattle, WA"), 
        		"Historic Landmark", null, pdp.getNextPoiId(), null);
        pdp.addPOI(p2);
        
        POI p3 = new POI("5413 Meridian Avenue North, Seattle, WA", 
        		addressToLocation("5413 Meridian Avenue North, Seattle, WA"), 
        		"Diner", null, pdp.getNextPoiId(), null);
        pdp.addPOI(p3);
        
        // create meetups
        Meetup m1 = new Meetup("3875 N 51st St Seattle, Wa", 
        		addressToLocation("3875 N 51st St Seattle"), 
        		"Cool Park", null, pdp.getnextMeetupId(), "5/20/13, 7:00pm-9:00pm", "You, Johnny007, T63");
        pdp.addMeetup(m1);
        
        Meetup m2 = new Meetup("5050 8th Avenue Northeast, Seattle, WA", 
        		addressToLocation("5050 8th Avenue Northeast, Seattle, WA"), 
        		"Boat Rentals", null, pdp.getnextMeetupId(), "5/25/13, 1:00pm-3:00pm", "You, Dude2341, Patrick123");
        pdp.addMeetup(m2);
	}
	
	private Pair<Float, Float> addressToLocation(String streetAddress){
		Geocoder coder = new Geocoder(this);
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
	
	// closes popup on map page
	public void closeDetails(View v) {
		LinearLayout topBar = (LinearLayout) findViewById(R.id.topBar);
		topBar.setVisibility(View.GONE);
		Button b = (Button) findViewById(R.id.meetUpButton);
		b.setVisibility(View.VISIBLE);
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
				Fragment mFragment = Fragment.instantiate(this, NewPoi_Fragment.class.getName());
				FragmentTransaction ft = getFragmentManager().beginTransaction();;
				ft.add(android.R.id.content, mFragment, "new poi");
				ft.commit();
	            return true;
	        case R.id.action_addmeetup:
				Fragment mFragment1 = Fragment.instantiate(this, NewMeetup_Fragment.class.getName());
				FragmentTransaction ft1 = getFragmentManager().beginTransaction();;
				ft1.add(android.R.id.content, mFragment1, "new poi");
				ft1.commit();
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}

	public static class TabListener<T extends Fragment> implements
			ActionBar.TabListener {
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
		}
	}

}
