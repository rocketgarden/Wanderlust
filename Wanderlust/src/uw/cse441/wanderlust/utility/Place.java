package uw.cse441.wanderlust.utility;

import android.util.Pair;

/**
 * Abstract class for Meetups and POIs
 * 
 * @author Vince
 * 
 */
public abstract class Place {
	// TODO Actually fill in code and stuff
	// Title, location, description, creator?, etc?

	public abstract String getTitle();

	public abstract void setTitle(String title);
	
	public abstract String getAddress();

	public abstract void setAddress(String address);
	
	public abstract int getId();

	public abstract void setId(int id);

	/**
	 * @return Pair of Latitude, Longitude values
	 */
	public abstract Pair<Float, Float> getLocation();

	public abstract void setLocation(Pair<Float, Float> location);

	public abstract String getDescription();

	public abstract void setDescription(String desc);
}
