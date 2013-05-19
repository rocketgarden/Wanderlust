package uw.cse441.wanderlust;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import uw.cse441.wanderlust.utility.Meetup;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class Meetup_List_Fragment extends ListFragment {
	public static final String TAG = "Meetup_Fragment";
	private SimpleAdapter mAdapter;
	private SearchView.OnQueryTextListener mSearchListener;
	private SearchView mSearchView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true); // add search provider

		// Fill in list view
		List<Map<String, String>> data = new ArrayList<Map<String, String>>();
		for (Meetup m : ((MainActivity) getActivity()).getPlaceDataProvider().getMeetupList()) {
			Map<String, String> datum = new HashMap<String, String>(2);
			datum.put("title", m.getTitle());
			datum.put("date", m.getDate());
			data.add(datum);
		}
		mAdapter = new SimpleAdapter(getActivity(), data, android.R.layout.simple_list_item_2,
				new String[] { "title", "date" }, new int[] { android.R.id.text1,
						android.R.id.text2 });
		setListAdapter(mAdapter);
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.search, menu);

		mSearchView = (SearchView) menu.findItem(R.id.menu_search).getActionView();
		mSearchListener = new SearchView.OnQueryTextListener() {

			@Override
			public boolean onQueryTextSubmit(String query) {
				// if (mAdapter == null)// for some reason this is a problem
				// return true;
				mAdapter.getFilter().filter(query);
				return true;
			}

			@Override
			public boolean onQueryTextChange(String newText) {
				// if (mAdapter == null)// for some reason this is a problem
				// return true;
				mAdapter.getFilter().filter(newText);
				return true;
			}
		};
	}

	@Override
	public void onStart() {
		super.onStart();
		mSearchView.setOnQueryTextListener(mSearchListener);

		getListView().setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View v, int pos, long id) {
				Toast.makeText(getActivity(), pos + " Clicked", Toast.LENGTH_SHORT).show();
			}

		});

	}

	public void onStop() {
		super.onStop();
		mSearchView.setOnQueryTextListener(null);
		getActivity().invalidateOptionsMenu();
	}

}
