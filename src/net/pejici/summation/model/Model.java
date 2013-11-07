package net.pejici.summation.model;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class Model {
	SQLiteDatabase db;

	public Model(SQLiteDatabase db) {
		this.db = db;
	}

	public Cursor getSheets() {
		String[] columns = {"pk as _id", "pk", "name"};
		return db.query("sheet", columns, null, null, null, null, null);
	}
}
