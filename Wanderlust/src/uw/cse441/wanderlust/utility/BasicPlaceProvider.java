package uw.cse441.wanderlust.utility;

import java.util.ArrayList;
import java.util.List;

public class BasicPlaceProvider implements PlaceDataProvider {
	private List<POI> pois;
	private List<Meetup> meetups;
	
	public BasicPlaceProvider() {
		pois = new ArrayList<POI>();
		meetups = new ArrayList<Meetup>();		
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
}
