package uw.cse441.wanderlust.utility;

import java.util.List;

public interface PlaceDataProvider {

	public abstract POI getPOI(int id);

	public abstract List<POI> getPOIList();

	public abstract Meetup getMeetup(int id);

	public abstract List<Meetup> getMeetupList();

	public abstract void addPOI(POI poi);

	public abstract void addMeetup(Meetup meetup);

	public abstract int getNextPoiId();

	public abstract int getnextMeetupId();
	
	public abstract void open();
	
	public abstract void close();

}