package uw.cse441.wanderlust;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import uw.cse441.wanderlust.utility.Meetup;
import uw.cse441.wanderlust.utility.POI;
import uw.cse441.wanderlust.utility.PlaceDataProvider;
import uw.cse441.wanderlust.utility.SQLPlaceProvider;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentTransaction;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.text.format.DateFormat;
import android.util.Pair;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

public class New_Meetup extends Activity {
	
	private PlaceDataProvider pdp;
	private int mID;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.newmeetup);
		pdp = new SQLPlaceProvider(this);
		// Show the Up button in the action bar.
		setupActionBar();
		
		Intent intent = getIntent();
		mID = intent.getIntExtra(MainActivity.REQUESTED_POI_KEY, -1);	
	}

	/**
	 * Set up the {@link android.app.ActionBar}.
	 */
	private void setupActionBar() {

		getActionBar().setDisplayHomeAsUpEnabled(true);

	}
	
	@Override
	public void onResume(){
		super.onResume();
		if (mID == -1) {
			return;
		}
		POI p = pdp.getPOI(mID);
		
		if (p != null) {   
			((EditText) findViewById(R.id.poi_field)).setText(p.getTitle());
			((EditText) findViewById(R.id.address_text)).setText(p.getAddress());
		}
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

	// Button callbacks
	public void cancel(View v) {
		finish(); // Just die
	}

	public void submit(View v) {
		// Verify fields

		// add to database

		// verify success?
		String name = ((EditText) findViewById(R.id.poi_field)).getText().toString();
		String address = ((EditText) findViewById(R.id.address_text)).getText().toString();
		String description = ((EditText) findViewById(R.id.descr_input)).getText().toString();
		String invited = ((EditText) findViewById(R.id.invite_input_field)).getText().toString();
		//String date = ((EditText) findViewById(R.id.date_field2)).getText().toString();
		//String time = ((EditText) findViewById(R.id.time_input)).getText().toString();

		Pair<Float, Float> longLat = addressToLocation(address);
		
		if (mID != -1) {
			longLat = new Pair<Float, Float>(longLat.first, (float) (longLat.second + 0.000725));
		}
		Meetup m = new Meetup(name, address, description, longLat,
				pdp.getnextMeetupId(), invited);
		pdp.addMeetup(m);
		
		finish();
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
	public void onStop(){
		super.onStop();
		pdp.close();
	}
	
	@Override
	public void onStart(){
		super.onStart();
		pdp.open();
	}
	
	public void showDatePickerDialog(View v) {
	    DialogFragment newFragment = new DatePickerFragment();
	    newFragment.show(getFragmentManager(), "datePicker");
	}
		
	public void showTimePickerDialog(View v) {
	    DialogFragment newFragment = new TimePickerFragment();
	    newFragment.show(getFragmentManager(), "timePicker");
	}
	
	public static class DatePickerFragment extends DialogFragment
    					implements DatePickerDialog.OnDateSetListener {
		
		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			// Use the current date as the default date in the picker
			final Calendar c = Calendar.getInstance();
			int year = c.get(Calendar.YEAR);
			int month = c.get(Calendar.MONTH);
			int day = c.get(Calendar.DAY_OF_MONTH);
			
			// Create a new instance of DatePickerDialog and return it
			return new DatePickerDialog(getActivity(), this, year, month, day);
		}
		@Override
		public void onDateSet(DatePicker view, int year, int month, int day) {
			// Do something with the date chosen by the user
			New_Meetup a = (New_Meetup) getActivity();
			TextView datefield = ((TextView) a.findViewById(R.id.date_field2));
			datefield.setText(month + "/" + day + "/" + year);
			
			FragmentTransaction ft1 = getFragmentManager().beginTransaction();
			ft1.remove(getFragmentManager().findFragmentByTag("datePicker"));
			ft1.commit();
		}

	}
	
	public static class TimePickerFragment extends DialogFragment
			implements TimePickerDialog.OnTimeSetListener {
	
		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			// Use the current time as the default values for the picker
			final Calendar c = Calendar.getInstance();
			int hour = c.get(Calendar.HOUR_OF_DAY);
			int minute = c.get(Calendar.MINUTE);
			
			// Create a new instance of TimePickerDialog and return it
			return new TimePickerDialog(getActivity(), this, hour, minute,
			DateFormat.is24HourFormat(getActivity()));
		}
		
		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
			// Do something with the time chosen by the user
			String amPm;
			if(hourOfDay > 11) {
				amPm = "PM";
			} else {
				amPm = "AM";
			}
			
			if (hourOfDay == 0) {
				hourOfDay = 12;
			} else if (hourOfDay > 12){
				hourOfDay -= 12;
			}
			
			String minuteLeadingZero = "";
			if (minute < 10) {
				minuteLeadingZero = "0";
			}
			
			New_Meetup a = (New_Meetup) getActivity();
			TextView timefield = ((TextView) a.findViewById(R.id.time_input));
			timefield.setText(hourOfDay + ":" + minuteLeadingZero + minute + " " + amPm);
			
			FragmentTransaction ft1 = getFragmentManager().beginTransaction();
			ft1.remove(getFragmentManager().findFragmentByTag("timePicker"));
			ft1.commit();
		}
	}

}
