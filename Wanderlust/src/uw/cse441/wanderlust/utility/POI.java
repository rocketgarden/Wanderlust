package uw.cse441.wanderlust.utility;

import android.util.Pair;

public class POI extends Place {
	private String Address;
	private Pair<Float, Float> Location;
	private String Title;
	private String Description;
	private int Id;
	@SuppressWarnings("unused")
	private String Date;
	
	public POI(String address, Pair<Float, Float> location, String title, 
			   String description, int id, String date){
		Address = address;
		Location = location;
		Title = title;
		Description = description;
		Id = id;
		Date = date;		
	}
	
	@Override
	public String getTitle() {
		return Title;
	}

	@Override
	public void setTitle(String title) {
		Title = title;
	}

	@Override
	public Pair<Float, Float> getLocation() {
		return Location;
	}

	@Override
	public void setLocation(Pair<Float, Float> location) {
		Location = location;
	}

	@Override
	public String getDescription() {
		return Description;
	}

	@Override
	public void setDescription(String desc) {
		Description = desc;
	}

	@Override
	public String getAddress() {
		return Address;
	}

	@Override
	public void setAddress(String address) {
		Address = address;
	}

	@Override
	public int getId() {
		return Id;
	}

	@Override
	public void setId(int id) {
		Id = id;
	}

}
