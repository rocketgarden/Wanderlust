package uw.cse441.wanderlust.utility;

import android.util.Pair;

public class POI extends Place {
	private String mAddress;
	private Pair<Float, Float> mLocation;
	private String mTitle;
	private String mDescription;
	private int mID;
	
	public POI(String title, String address, String description, 
			   Pair<Float, Float> location, int id){
		mAddress = address;
		mLocation = location;
		mTitle = title;
		mDescription = description;
		mID = id;
	}
	
	@Override
	public String getTitle() {
		return mTitle;
	}

	@Override
	public void setTitle(String title) {
		mTitle = title;
	}

	@Override
	public Pair<Float, Float> getLocation() {
		return mLocation;
	}

	@Override
	public void setLocation(Pair<Float, Float> location) {
		mLocation = location;
	}

	@Override
	public String getDescription() {
		return mDescription;
	}

	@Override
	public void setDescription(String desc) {
		mDescription = desc;
	}

	@Override
	public String getAddress() {
		return mAddress;
	}

	@Override
	public void setAddress(String address) {
		mAddress = address;
	}

	@Override
	public int getId() {
		return mID;
	}

	@Override
	public void setId(int id) {
		mID = id;
	}

}
