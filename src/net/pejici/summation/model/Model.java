package net.pejici.summation.model;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import static net.pejici.summation.model.Query.*;

public class Model {
	SQLiteDatabase db;

	public Model(SQLiteDatabase db) {
		this.db = db;
	}

	public Cursor getSheets() {
		String[] columns = {"pk as _id", "pk", "name"};
		return db.query("sheet", columns, null, null, null, null, null);
	}

	public Long addSheet(String name) {
		ContentValues values = new ContentValues();
		values.put("name", name);
		Long pk = db.insert("sheet", null, values);
		db.execSQL(DB_CREATE_SHEET(pk));
		return pk;
	}
}
