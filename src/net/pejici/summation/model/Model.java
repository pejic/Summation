package net.pejici.summation.model;

import net.pejici.summation.model.Query.Entry;
import net.pejici.summation.model.Query.SheetEntry;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import static net.pejici.summation.model.Query.*;

public class Model {
	SQLiteDatabase db;

	public Model(SQLiteDatabase db) {
		this.db = db;
	}

	public Cursor getSheets(String [] columns) {
		return db.query(SheetEntry.TABLE_NAME, columns,
				null, null, null, null, null);
	}

	public ContentValues getSheet(long taskId, String [] columns) {
		return firstFromQuery(SheetEntry.TABLE_NAME, columns,
				SheetEntry.COL_PKEY+"="+taskId, null, null, null, null);
	}

	public Long addSheet(String name) {
		ContentValues values = new ContentValues();
		values.put(SheetEntry.COL_NAME, name);
		Long pk = db.insert(SheetEntry.TABLE_NAME, null, values);
		db.execSQL(DB_CREATE_SHEET(pk));
		return pk;
	}

	public void deleteSheet(long taskId) {
		db.delete(SheetEntry.TABLE_NAME,
				SheetEntry.COL_PKEY + "=" + taskId,
				null);
	}

	public void updateSheet(ContentValues values) {
		Long pkey = values.getAsLong(SheetEntry.COL_PKEY);
		if (null != pkey) {
			db.update(SheetEntry.TABLE_NAME, values,
					SheetEntry.COL_PKEY + "=" + pkey,
					null);
		}
	}

	private ContentValues firstFromQuery(String tableName, String [] columns,
			String selection, String [] selectionArgs,
			String groupBy, String having, String orderBy) {
		Cursor c = db.query(tableName, columns,
				selection, selectionArgs,
				groupBy, having, orderBy);
		return firstFromCursor(c);
	}

	private ContentValues firstFromCursor(Cursor c) {
		while (c.moveToNext()) {
			ContentValues cv = new ContentValues();
			DatabaseUtils.cursorRowToContentValues(c, cv);
			return cv;
		}
		return null;
	}
}
