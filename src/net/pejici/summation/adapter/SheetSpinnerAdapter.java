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
package net.pejici.summation.adapter;

import net.pejici.summation.model.Query.SheetEntry;
import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.SimpleCursorAdapter;

public class SheetSpinnerAdapter extends SimpleCursorAdapter {

	static final String [] from = {SheetEntry.COL_NAME};
	static final int[] to = {android.R.id.text1};

	static final int viewId = android.R.layout.simple_spinner_dropdown_item;

	public static final String[] DB_SHEET_COLUMNS =
		{SheetEntry._ID, SheetEntry.COL_NAME};

	public SheetSpinnerAdapter(Context context, Cursor c) {
		super(context, viewId, c, from, to, 0);
	}

	public Integer positionByItemId(long itemId) {
		for (int i = 0; i < getCount(); i++) {
			Cursor cursor = (Cursor) getItem(i);
			long id = cursor.getLong(0);
			if (id == itemId) {
				return i;
			}
		}
		return null;
	}

}
