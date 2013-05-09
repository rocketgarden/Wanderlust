package uw.cse441.wanderlust;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.Menu;

/**
 * This is the main, and probably only activity for the application.
 * All the pages should be implemented in terms of fragments inside this activity
 * 
 * @author Vince
 *
 */
public class MainActivity extends Activity{
	
	static final String TAG = "";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		ActionBar actionBar = getActionBar();
	    actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
	    actionBar.setDisplayShowTitleEnabled(false);
	    
	    //Map Page
	    Tab tab = actionBar.newTab()
	            .setText(R.string.title_section_map)
	            .setTabListener(new TabListener<Map_Fragment>(
	                    this, "map", Map_Fragment.class));
	    actionBar.addTab(tab);
	    //POI Page
	    tab = actionBar.newTab()
	        .setText(R.string.title_section_poi)
	        .setTabListener(new TabListener<POI_Fragment>(
	                this, "poi", POI_Fragment.class));
	    actionBar.addTab(tab);
	    //MeetUp Page
	    tab = actionBar.newTab()
		        .setText(R.string.title_section_meetup)
		        .setTabListener(new TabListener<Meetup_Fragment>(
		                this, "poi", Meetup_Fragment.class));
		actionBar.addTab(tab);
		//Profile Page
		tab = actionBar.newTab()
		        .setText(R.string.title_section_profile)
		        .setTabListener(new TabListener<Profile_Fragment>(
		                this, "poi", Profile_Fragment.class));
		actionBar.addTab(tab);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	
	
	
	public static class TabListener<T extends Fragment> implements ActionBar.TabListener {
		//c/p'd from http://developer.android.com/guide/topics/ui/actionbar.html#Tabs
	    private Fragment mFragment;
	    private final Activity mActivity;
	    private final String mTag;
	    private final Class<T> mClass;

	    /** Constructor used each time a new tab is created.
	      * @param activity  The host Activity, used to instantiate the fragment
	      * @param tag  The identifier tag for the fragment
	      * @param clz  The fragment's Class, used to instantiate the fragment
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
