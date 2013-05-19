package uw.cse441.wanderlust;

import uw.cse441.wanderlust.utility.Meetup;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MeetupDetail_Fragment extends Fragment {
	static final String TAG = "MeetupDetail_Fragment";
	private int id;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//get id from bundle
		id = getArguments().getInt("id", -1);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.meetup_detail, container, false);
		return view;
	}
	
	@Override
	public void onResume() {
		super.onResume();
		Meetup m = ((MainActivity) getActivity()).getPlaceDataProvider().getMeetup(id);
		
		if (m == null) {
		    TextView title = (TextView) getView().findViewById(R.id.meet_up_title);
		    title.setText("Title");
		    
		    TextView invited = (TextView) getView().findViewById(R.id.who_is_going_info);
		    invited.setText("Invited guests...");
		    
		    TextView location = (TextView) getView().findViewById(R.id.location_info);
		    location.setText("Address...");
		    
		    TextView story = (TextView) getView().findViewById(R.id.date_and_time_info);
		    story.setText("Date and Time...");
		} else {
		    TextView title = (TextView) getView().findViewById(R.id.meet_up_title);
		    title.setText(m.getTitle());
		    
		    TextView invited = (TextView) getView().findViewById(R.id.who_is_going_info);
		    invited.setText(m.getInvited());
		    
		    TextView location = (TextView) getView().findViewById(R.id.location_info);
		    location.setText(m.getAddress());
		    
		    TextView story = (TextView) getView().findViewById(R.id.date_and_time_info);
		    story.setText(m.getDate());
		}
	}

}