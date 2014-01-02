/*
 * Summation - A simple Android app with list of items with a total at the end.
 * Copyright (C) 2013  Slobodan Pejic (slobo@pejici.net)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
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
