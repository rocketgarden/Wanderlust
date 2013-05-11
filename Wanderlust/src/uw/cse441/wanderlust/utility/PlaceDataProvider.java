package uw.cse441.wanderlust.utility;

import java.util.ArrayList;
import java.util.List;

public class PlaceDataProvider {
	private List<POI> pois;
	private List<Meetup> meetups;
	
	public PlaceDataProvider() {
		pois = new ArrayList<POI>();
		meetups = new ArrayList<Meetup>();		
	}
	
	public POI getPOI(int id){
		return pois.get(id);
	}
	
	public List<POI> getPOIList(){
		return pois;
	}
	
	public Meetup getMeetup(int id){
		return meetups.get(id);
	}
	
	public List<Meetup> getMeetupList(){
		return meetups;
	}
	
	public void addPOI(POI poi){
		pois.add(poi.getId(), poi);
	}
	
	public void addMeetup(Meetup meetup){
		meetups.add(meetup.getId(), meetup);
	}
	
	public int getNextPoiId() {
		return pois.size();
	}

	public int getnextMeetupId() {
		return meetups.size();
	}
}
