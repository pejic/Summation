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

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import static net.pejici.summation.model.Query.*;

public class DBHelper extends SQLiteOpenHelper {

	private static String DB_NAME = "summation.db";
	private static int DB_VERSION = 1;

	public DBHelper(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
		// TODO Error handler
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(DB_CREATE_ALL);
		Model model = new Model(db);
		Long pk = model.addSheet("Groceries");
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
