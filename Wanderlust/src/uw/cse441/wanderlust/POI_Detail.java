package uw.cse441.wanderlust;

import uw.cse441.wanderlust.utility.POI;
import uw.cse441.wanderlust.utility.PlaceDataProvider;
import uw.cse441.wanderlust.utility.SQLPlaceProvider;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.support.v4.app.NavUtils;

public class POI_Detail extends Activity {
	
	private int mID;
	public static final String TAG = "POI_Detail_Activity";
	
	private PlaceDataProvider pdp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.poi_detail);
		pdp = new SQLPlaceProvider(this);
		// Show the Up button in the action bar.
		setupActionBar();
		
		Intent intent = getIntent();
		mID = intent.getIntExtra(MainActivity.REQUESTED_POI_KEY, -1);
		
		if(mID < 0){
			Log.w(TAG, "Tried to view POI without valid ID!");
			NavUtils.navigateUpFromSameTask(this); //go home
		}
	}

	/**
	 * Set up the {@link android.app.ActionBar}.
	 */
	private void setupActionBar() {

		getActionBar().setDisplayHomeAsUpEnabled(true);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		case R.id.action_addpoi:
			
			Intent i = new Intent(this, New_POI.class);
			startActivity(i);
			return true;
		case R.id.action_addmeetup:

			Intent j = new Intent(this, New_Meetup.class);
			j.putExtra(MainActivity.REQUESTED_POI_KEY, mID);
			startActivity(j);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
	@Override
	public void onResume(){
		super.onResume();
		POI p = pdp.getPOI(mID);
		
		if (p != null) {
		    TextView title = (TextView) findViewById(R.id.poi_title);
		    title.setText(p.getTitle());
		    
		    TextView location = (TextView) findViewById(R.id.text_address);
		    location.setText(p.getAddress());
		    
		    TextView desc = (TextView) findViewById(R.id.text_desc);
		    desc.setText(p.getDescription());
		}
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

}
