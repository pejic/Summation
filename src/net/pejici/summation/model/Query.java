package net.pejici.summation.model;

import android.provider.BaseColumns;

/**
 * This class contains static strings and methods with SQL Queries.
 *
 * @author slobo
 *
 */
public class Query {
	static String DB_CREATE_ALL =
			"CREATE TABLE sheet ("
			+ "pk INTEGER PRIMARY KEY, "
			+ "name VARCHAR(255) NOT NULL"
			+ ");";

	static String DB_CREATE_SHEET(Long pk) {
		return "CREATE TABLE sheet_"+pk+" ("
				+ "pk INTEGER PRIMARY KEY, "
				+ "date DATETIME DEFAULT NOW, "
				+ "label TEXT DEFAULT NULL, "
				+ "value DOUBLE DEFAULT NULL"
				+ ");";
	}

	static String DB_SHEET_SUM(Long pk) {
		return "SELECT SUM(" + ItemEntry.COL_VALUE + ") FROM sheet_"+pk+";";
	}

	public static class Entry implements BaseColumns {
		public static final String TABLE_NAME = "";
		public static final String _ID = "pk as _id";
		public static final String COL_PKEY = "pk";
	}

	public static class SheetEntry extends Entry {
		public static final String TABLE_NAME = "sheet";
		public static final String COL_NAME = "name";
	}

	public static class ItemEntry extends Entry {
		public static String TABLE_NAME(Long sheetId) {
			return "sheet_"+sheetId;
		}
		public static final String COL_CREATION_DATE = "date";
		public static final String COL_LABEL = "label";
		public static final String COL_VALUE = "value";
	}
}
