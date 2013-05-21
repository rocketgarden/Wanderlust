package uw.cse441.wanderlust;

import uw.cse441.wanderlust.utility.Meetup;
import uw.cse441.wanderlust.utility.PlaceDataProvider;
import uw.cse441.wanderlust.utility.SQLPlaceProvider;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class Meetup_Detail extends Activity {

	private int mID;
	public static final String TAG = "MeetUp_Detail_Activity";

	private PlaceDataProvider pdp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.meetup_detail);
		pdp = new SQLPlaceProvider(this);

		// Show the Up button in the action bar.
		setupActionBar();
		
		Intent intent = getIntent();
		mID = intent.getIntExtra(MainActivity.REQUESTED_MEETUP_KEY, -1);
		
		if(mID < 0){
			Log.w(TAG, "Tried to view MeetUp without valid ID!");
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
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public void onResume(){
		super.onResume();
		Meetup m = pdp.getMeetup(mID);
		
		if (m != null) {
		    TextView title = (TextView) findViewById(R.id.title_meetup);
		    title.setText(m.getTitle());
		    
		    TextView invited = (TextView) findViewById(R.id.text_attending);
		    invited.setText(m.getInvited());
		    
		    TextView location = (TextView) findViewById(R.id.text_address);
		    location.setText(m.getAddress());
		    
		    TextView date = (TextView) findViewById(R.id.text_datetime);
		    date.setText(m.getDate());
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
