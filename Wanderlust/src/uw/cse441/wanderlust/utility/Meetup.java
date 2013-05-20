package uw.cse441.wanderlust.utility;

import java.util.GregorianCalendar;

import android.util.Pair;

public class Meetup extends Place {
	private String Address;
	private Pair<Float, Float> Location;
	private String Title;
	private String Description;
	private int Id;
	private String Date;
	private String Invited;
	
	public Meetup(String title, String address, String description, 
					Pair<Float, Float> location, int id, String invited){
		Address = address;
		Location = location;
		Title = title;
		Description = description;
		Id = id;
		Date = new GregorianCalendar().getTime().toString();
		Invited = invited;
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
	
	public String getInvited() {
		return Invited;
	}

	public void setInvited(String invited) {
		Invited = invited;
	}
	
	public String getDate() {
		return Date;
	}

	public void setDate(String date) {
		Date = date;
	}

}
