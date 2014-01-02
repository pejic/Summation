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

	public ContentValues getSheet(long sheetId, String [] columns) {
		return firstFromQuery(SheetEntry.TABLE_NAME, columns,
				SheetEntry.COL_PKEY+"="+sheetId, null, null, null, null);
	}

	public Double getSheetSum(long sheetId) {
		Cursor cursor = db.rawQuery(DB_SHEET_SUM(sheetId), null);
		while (cursor.moveToNext()) {
			return cursor.getDouble(0);
		}
		return null;
	}

	public Long addSheet(String name) {
		ContentValues values = new ContentValues();
		values.put(SheetEntry.COL_NAME, name);
		Long pk = db.insert(SheetEntry.TABLE_NAME, null, values);
		db.execSQL(DB_CREATE_SHEET(pk));
		return pk;
	}

	public void deleteSheet(long sheetId) {
		db.delete(SheetEntry.TABLE_NAME,
				SheetEntry.COL_PKEY + "=" + sheetId,
				null);
	}

	public Cursor getItems(long sheetId, String [] columns) {
		return db.query(ItemEntry.TABLE_NAME(sheetId), columns,
				null, null, null, null, null);
	}

	public ContentValues getItem(long sheetId, long itemId, String [] columns) {
		return firstFromQuery(ItemEntry.TABLE_NAME(sheetId), columns,
				ItemEntry.COL_PKEY+"="+itemId, null, null, null, null);
	}

	public void updateSheet(ContentValues values) {
		Long pkey = values.getAsLong(SheetEntry.COL_PKEY);
		if (null != pkey) {
			db.update(SheetEntry.TABLE_NAME, values,
					SheetEntry.COL_PKEY + "=" + pkey,
					null);
		}
	}

	public Long addItem(long sheetId, ContentValues values) {
		return db.insert(ItemEntry.TABLE_NAME(sheetId), null, values);
	}

	public void deleteItem(long sheetId, long itemId) {
		db.delete(ItemEntry.TABLE_NAME(sheetId),
				ItemEntry.COL_PKEY + "=" + itemId,
				null);
	}

	public void updateItem(long sheetId, ContentValues values) {
		Long pkey = values.getAsLong(ItemEntry.COL_PKEY);
		if (null != pkey) {
			db.update(ItemEntry.TABLE_NAME(sheetId), values,
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
