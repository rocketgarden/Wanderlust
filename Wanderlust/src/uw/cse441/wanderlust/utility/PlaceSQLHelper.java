package uw.cse441.wanderlust.utility;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class PlaceSQLHelper extends SQLiteOpenHelper {

	public static final String TABLE_POI = "poi";
	public static final String TABLE_MEETUP = "meetup";

	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_P_TITLE = "title";
	public static final String COLUMN_P_ADDRESS = "addr";
	public static final String COLUMN_P_LAT = "lat";
	public static final String COLUMN_P_LONG = "long";

	private static final String DATABASE_NAME = "commments.db";
	private static final int DATABASE_VERSION = 1;
	private String TAG = "PlaceSQLHelper";
	
	

	public PlaceSQLHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		//db.execSQL(DATABASE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(TAG , "Upgrading database from version " + oldVersion
				+ " to " + newVersion + ", which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_POI);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_MEETUP);

		onCreate(db);
	}

}
