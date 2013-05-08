package uw.cse441.wanderlust;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

/**
 * This is the main, and probably only activity for the application.
 * All the pages should be implemented in terms of fragments inside this activity
 * 
 * @author Vince
 *
 */
public class MainActivity extends Activity{

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.maptest);

		

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
