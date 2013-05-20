package uw.cse441.wanderlust.utility;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class PlaceSQLHelper extends SQLiteOpenHelper {

	// Brace for constants!
	public static final String TABLE_POI = "poi";
	public static final String TABLE_MEETUP = "meetup";
	// Probably don't edit ^those two^

	public static final String COLUMN_ID = "_id";

	public static final String COLUMN_P_TITLE = "title";
	public static final String COLUMN_P_DESC = "description";
	public static final String COLUMN_P_ADDRESS = "addr";
	public static final String COLUMN_P_LAT = "lat";
	public static final String COLUMN_P_LONG = "long";

	public static final String COLUMN_M_TITLE = "title";
	public static final String COLUMN_M_DESC = "description";
	public static final String COLUMN_M_ADDRESS = "addr";
	public static final String COLUMN_M_LAT = "lat";
	public static final String COLUMN_M_LONG = "long";
	public static final String COLUMN_M_INVITES = "invited";
	public static final String COLUMN_M_DATE = "date";

	private static final String DATABASE_NAME = "commments.db";

	private static final int DATABASE_VERSION = 7;
	// ^^need to increment this whenever we modify the database definition

	private String TAG = "PlaceSQLHelper";

	private static final String POI_TABLE_CREATE = "create table " + TABLE_POI + "(" + COLUMN_ID
			+ " integer primary key, " + COLUMN_P_TITLE + " text not null, " + COLUMN_P_ADDRESS
			+ " text not null, " + COLUMN_P_DESC + " text, " + COLUMN_P_LAT + " integer, "
			+ COLUMN_P_LONG + " integer" + ");";

	private static final String MEETUP_TABLE_CREATE = "create table " + TABLE_MEETUP + "("
			+ COLUMN_ID + " integer primary key, " + COLUMN_M_TITLE + " text not null, "
			+ COLUMN_M_ADDRESS + " text not null, " + COLUMN_M_DESC + " text, " + COLUMN_M_LAT
			+ " integer, " + COLUMN_M_LONG + " integer, " + COLUMN_M_INVITES + " text, "
			+ COLUMN_M_DATE + " text" + ");";

	public PlaceSQLHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(POI_TABLE_CREATE);
		Log.v(TAG, POI_TABLE_CREATE);
		db.execSQL(MEETUP_TABLE_CREATE);
		Log.v(TAG, MEETUP_TABLE_CREATE);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(TAG, "Upgrading database from version " + oldVersion + " to " + newVersion
				+ ", which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_POI);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_MEETUP);

		onCreate(db);
	}

}
