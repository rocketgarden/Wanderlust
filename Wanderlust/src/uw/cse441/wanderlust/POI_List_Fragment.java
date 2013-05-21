package uw.cse441.wanderlust;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import uw.cse441.wanderlust.utility.POI;
import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.SearchView;
import android.widget.SimpleAdapter;

public class POI_List_Fragment extends ListFragment {
	public static final String TAG = "POI_Fragment";
	private SimpleAdapter mAdapter;
	private SearchView.OnQueryTextListener mSearchListener;
	private SearchView mSearchView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true); // add search provider

		// Fill in list view
		List<Map<String, String>> data = new ArrayList<Map<String, String>>();
		for (POI p : ((MainActivity) getActivity()).getPlaceDataProvider().getPOIList()) {
			Map<String, String> datum = new HashMap<String, String>(2);
			datum.put("title", p.getTitle());
			datum.put("address", p.getAddress());
			data.add(datum);
		}
		mAdapter = new SimpleAdapter(getActivity(), data, android.R.layout.simple_list_item_2,
				new String[] { "title", "address" }, new int[] { android.R.id.text1,
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

		mSearchView.setOnQueryTextListener(mSearchListener);
	}

	@Override
	public void onStart() {
		super.onStart();
		mSearchView.setOnQueryTextListener(mSearchListener);

		getListView().setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View v, int pos, long id) {
				Intent i = new Intent(getActivity(), POI_Detail.class);
				i.putExtra(MainActivity.REQUESTED_POI_KEY, pos);
				startActivity(i);
			}
		});

	}

	public void onStop() {
		super.onStop();
		mSearchView.setOnQueryTextListener(null);
		getActivity().invalidateOptionsMenu();
	}

}
