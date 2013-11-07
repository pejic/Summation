package net.pejici.summation.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

	private static String DB_NAME = "summation.db";
	private static int DB_VERSION = 1;

	private static String DB_CREATE_ALL =
			"CREATE TABLE sheet ("
			+ "pk INTEGER PRIMARY KEY, "
			+ "name VARCHAR(255) NOT NULL"
			+ ");";

	private static String DB_CREATE_SHEET(Long pk) {
		return "CREATE TABLE sheet_"+pk+" ("
				+ "pk INTEGER PRIMARY KEY, "
				+ "date DATETIME DEFAULT NOW, "
				+ "label TEXT DEFAULT NULL, "
				+ "value DOUBLE DEFAULT NULL"
				+ ");";
	}

	public DBHelper(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
		// TODO Error handler
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(DB_CREATE_ALL);
		ContentValues values = new ContentValues();
		values.put("name", "Groceries");
		Long pk = db.insert("sheet", null, values);
		db.execSQL(DB_CREATE_SHEET(pk));
		ContentValues rowValues = new ContentValues();
		rowValues.put("label", "Gummies");
		rowValues.put("value", 3.99);
		db.insert("sheet_"+pk, null, rowValues);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Update when there are new versions
	}

}
