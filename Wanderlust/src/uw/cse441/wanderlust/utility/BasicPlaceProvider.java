package uw.cse441.wanderlust.utility;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.util.Log;
import android.util.Pair;

public class BasicPlaceProvider implements PlaceDataProvider {
	private List<POI> pois;
	private List<Meetup> meetups;
	private Context mContext;
	
	public static final String TAG = "Basic Place Provider";
	
	
	public BasicPlaceProvider(Context c) {
		pois = new ArrayList<POI>();
		meetups = new ArrayList<Meetup>();	
		mContext = c;
	}
	
	public void loadDatabase() {
		// create pois
		// N 51st St & Meridian Ave N Seattle
		POI p1 = new POI("Orange Statue",
				"5020 Meridian Avenue North, Seattle, WA", null,
				addressToLocation("5020 Meridian Avenue North, Seattle, WA"), getNextPoiId());
		addPOI(p1);

		POI p2 = new POI("Historic Landmark",
				"N 57th Street, Seattle, WA", null, addressToLocation("N 57th Street, Seattle, WA"),
				getNextPoiId());
		addPOI(p2);

		POI p3 = new POI("Diner",
				"5413 Meridian Avenue North, Seattle, WA", null, addressToLocation("5413 Meridian Avenue North, Seattle, WA"),
				getNextPoiId());
		addPOI(p3);

		// create meetups
		Meetup m1 = new Meetup("Cool Park",
				"3875 N 51st St Seattle, Wa", null, addressToLocation("3875 N 51st St Seattle"),
				getnextMeetupId(), "You, Johnny007, T63");
		addMeetup(m1);

		Meetup m2 = new Meetup("Boat Rentals",
				"5050 8th Avenue Northeast, Seattle, WA", null, addressToLocation("5050 8th Avenue Northeast, Seattle, WA"),
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
	
	/* (non-Javadoc)
	 * @see uw.cse441.wanderlust.utility.PlaceDataProvider#getPOI(int)
	 */
	@Override
	public POI getPOI(int id){
		return pois.get(id);
	}
	
	/* (non-Javadoc)
	 * @see uw.cse441.wanderlust.utility.PlaceDataProvider#getPOIList()
	 */
	@Override
	public List<POI> getPOIList(){
		return pois;
	}
	
	/* (non-Javadoc)
	 * @see uw.cse441.wanderlust.utility.PlaceDataProvider#getMeetup(int)
	 */
	@Override
	public Meetup getMeetup(int id){
		return meetups.get(id);
	}
	
	/* (non-Javadoc)
	 * @see uw.cse441.wanderlust.utility.PlaceDataProvider#getMeetupList()
	 */
	@Override
	public List<Meetup> getMeetupList(){
		return meetups;
	}
	
	/* (non-Javadoc)
	 * @see uw.cse441.wanderlust.utility.PlaceDataProvider#addPOI(uw.cse441.wanderlust.utility.POI)
	 */
	@Override
	public void addPOI(POI poi){
		pois.add(poi.getId(), poi);
	}
	
	/* (non-Javadoc)
	 * @see uw.cse441.wanderlust.utility.PlaceDataProvider#addMeetup(uw.cse441.wanderlust.utility.Meetup)
	 */
	@Override
	public void addMeetup(Meetup meetup){
		meetups.add(meetup.getId(), meetup);
	}
	
	/* (non-Javadoc)
	 * @see uw.cse441.wanderlust.utility.PlaceDataProvider#getNextPoiId()
	 */
	@Override
	public int getNextPoiId() {
		return pois.size();
	}

	/* (non-Javadoc)
	 * @see uw.cse441.wanderlust.utility.PlaceDataProvider#getnextMeetupId()
	 */
	@Override
	public int getnextMeetupId() {
		return meetups.size();
	}

	@Override
	public void open() {
		//do nothing
		
	}

	@Override
	public void close() {
		//do nothing
		
	}
}
