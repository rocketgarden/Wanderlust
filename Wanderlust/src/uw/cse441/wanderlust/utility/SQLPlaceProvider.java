package uw.cse441.wanderlust.utility;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.location.Address;
import android.location.Geocoder;
import android.util.Log;
import android.util.Pair;

public class SQLPlaceProvider implements PlaceDataProvider {

	private SQLiteDatabase database;
	private PlaceSQLHelper dbHelper;
	private Context mContext;

	private long lastMid;
	private long lastPid;

	public static final String TAG = "SQL Place Provider";

	// private String[] POIColumns = { PlaceSQLHelper.COLUMN_ID,
	// PlaceSQLHelper.COLUMN_P_TITLE,
	// PlaceSQLHelper.COLUMN_P_ADDRESS, PlaceSQLHelper.COLUMN_P_DESC };

	/**
	 * Construct a new SQLPlaceProvider.<br />
	 * MUST CALL open() BEFORE USING.<br />
	 * And be sure to close in the matching lifecycle method
	 * 
	 * @param c
	 *            Application context (probably "this" or getActivity())
	 */
	public SQLPlaceProvider(Context c) {
		mContext = c;
		dbHelper = new PlaceSQLHelper(mContext);
	}

	@Override
	public void open() {
		database = dbHelper.getWritableDatabase();
		if (DatabaseUtils.queryNumEntries(database, PlaceSQLHelper.TABLE_POI) < 1)
			populate();
	}

	@Override
	public void close() {
		dbHelper.close();
	}

	@Override
	public POI getPOI(int id) {
		Cursor cursor = database.query(PlaceSQLHelper.TABLE_POI, null, PlaceSQLHelper.COLUMN_ID
				+ " = " + id, null, null, null, null);
		cursor.moveToFirst();
		return cursorToPOI(cursor);
	}

	@Override
	public List<POI> getPOIList() {
		List<POI> pois = new LinkedList<POI>(); // linkedlist for efficiency
		Cursor cursor = database
				.query(PlaceSQLHelper.TABLE_POI, null, null, null, null, null, null);
		cursor.moveToFirst();

		while (!cursor.isAfterLast()) {
			POI p = cursorToPOI(cursor);
			pois.add(p);
			cursor.moveToNext();
		}

		return pois;
	}

	@Override
	public Meetup getMeetup(int id) {
		Cursor cursor = database.query(PlaceSQLHelper.TABLE_MEETUP, null, PlaceSQLHelper.COLUMN_ID
				+ " = " + id, null, null, null, null);
		cursor.moveToFirst();
		return cursorToMeetup(cursor);
	}

	@Override
	public List<Meetup> getMeetupList() {
		List<Meetup> meetups = new LinkedList<Meetup>(); // linkedlist for
															// efficiency
		Cursor cursor = database.query(PlaceSQLHelper.TABLE_MEETUP, null, null, null, null, null,
				null);
		cursor.moveToFirst();

		while (!cursor.isAfterLast()) {
			Meetup m = cursorToMeetup(cursor);
			meetups.add(m);
			cursor.moveToNext();
		}

		return meetups;
	}

	@Override
	public void addPOI(POI poi) {
		ContentValues values = new ContentValues();
		values.put(PlaceSQLHelper.COLUMN_P_TITLE, poi.getTitle());
		values.put(PlaceSQLHelper.COLUMN_P_ADDRESS, poi.getAddress());
		values.put(PlaceSQLHelper.COLUMN_P_DESC, poi.getDescription());
		values.put(PlaceSQLHelper.COLUMN_P_LAT, poi.getLocation().first);
		values.put(PlaceSQLHelper.COLUMN_P_LONG, poi.getLocation().second);
		values.put(PlaceSQLHelper.COLUMN_ID, poi.getId());
		// XXX Should really have a better way to deal with IDs

		lastPid = database.insert(PlaceSQLHelper.TABLE_POI, null, values);
		assert (lastPid >= 0);

	}

	@Override
	public void addMeetup(Meetup meetup) {
		ContentValues values = new ContentValues();
		values.put(PlaceSQLHelper.COLUMN_M_TITLE, meetup.getTitle());
		values.put(PlaceSQLHelper.COLUMN_M_ADDRESS, meetup.getAddress());
		values.put(PlaceSQLHelper.COLUMN_M_DESC, meetup.getDescription());
		values.put(PlaceSQLHelper.COLUMN_M_LAT, meetup.getLocation().first);
		values.put(PlaceSQLHelper.COLUMN_M_LONG, meetup.getLocation().second);
		values.put(PlaceSQLHelper.COLUMN_ID, meetup.getId());
		values.put(PlaceSQLHelper.COLUMN_M_DATE, meetup.getDate());
		// XXX Should really have a better way to deal with IDs

		lastMid = database.insert(PlaceSQLHelper.TABLE_MEETUP, null, values);
		assert (lastMid >= 0);

	}

	@Override
	public int getNextPoiId() {
		if (database == null)
			throw new IllegalStateException("Must open database first");
		return (int) DatabaseUtils.queryNumEntries(database, PlaceSQLHelper.TABLE_POI);
	}

	@Override
	public int getnextMeetupId() {
		if (database == null)
			throw new IllegalStateException("Must open database first");
		return (int) DatabaseUtils.queryNumEntries(database, PlaceSQLHelper.TABLE_MEETUP);
	}

	private POI cursorToPOI(Cursor cursor) {
		int id = cursor.getInt(0);
		String title = cursor.getString(1);
		String address = cursor.getString(2);
		String desc = cursor.getString(3);
		float lat = cursor.getFloat(4);
		float lon = cursor.getFloat(5);
		Pair<Float, Float> ll = new Pair<Float, Float>(lat, lon);

		POI p = new POI(title, address, desc, ll, id);
		return p;
	}

	private Meetup cursorToMeetup(Cursor cursor) {
		int id = cursor.getInt(0);
		String title = cursor.getString(1);
		String address = cursor.getString(2);
		String desc = cursor.getString(3);
		float lat = cursor.getFloat(4);
		float lon = cursor.getFloat(5);
		String invited = cursor.getString(6);
		String date = cursor.getString(7);
		Pair<Float, Float> ll = new Pair<Float, Float>(lat, lon);

		Meetup m = new Meetup(title, address, desc, ll, id, invited);
		m.setDate(date);

		return m;

	}

	private void populate() {
		// create pois
		// N 51st St & Meridian Ave N Seattle
		POI p1 = new POI("Orange Statue", "5020 Meridian Avenue North, Seattle, WA",
				"An orange statue", addressToLocation("5020 Meridian Avenue North, Seattle, WA"),
				getNextPoiId());
		addPOI(p1);

		POI p2 = new POI("Historic Landmark", "N 57th Street, Seattle, WA",
				"A landmark of historic value", addressToLocation("N 57th Street, Seattle, WA"),
				getNextPoiId());
		addPOI(p2);

		POI p3 = new POI("Diner", "5413 Meridian Avenue North, Seattle, WA", "A delicious diner",
				addressToLocation("5413 Meridian Avenue North, Seattle, WA"), getNextPoiId());
		addPOI(p3);

		// create meetups
		Meetup m1 = new Meetup("Cool Park", "3875 N 51st St Seattle, Wa", "A pretty nifty park",
				addressToLocation("3875 N 51st St Seattle"), getnextMeetupId(),
				"You, Johnny007, T63");
		addMeetup(m1);

		Meetup m2 = new Meetup("Boat Rentals", "5050 8th Avenue Northeast, Seattle, WA",
				"Rent a boat here", addressToLocation("5050 8th Avenue Northeast, Seattle, WA"),
				getnextMeetupId(), "You, Dude2341, Patrick123");
		addMeetup(m2);
	}

	private Pair<Float, Float> addressToLocation(String streetAddress) {
		Geocoder coder = new Geocoder(mContext);
		List<Address> address;
		Pair<Float, Float> latLong = null;
		try {
			address = coder.getFromLocationName(streetAddress, 5);
			if (address != null && address.size() != 0) {
				Address location = address.get(0);
				latLong = new Pair<Float, Float>((float) location.getLatitude(),
						(float) location.getLongitude());
			}
		} catch (IOException e) {
			e.printStackTrace();
			Log.e(TAG, "Could not parse address!");
		}
		if (latLong == null) {
			latLong = new Pair<Float, Float>((float) 0, (float) 0);
		}
		return latLong;
	}

}
