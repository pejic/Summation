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

import net.pejici.summation.R;
import net.pejici.summation.model.Query.ItemEntry;
import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

public class ItemAdapter extends CursorAdapter {

	public static final String [] DB_ITEM_COLUMNS =
		{ItemEntry._ID, ItemEntry.COL_LABEL, ItemEntry.COL_VALUE};

	public ItemAdapter(Context context, Cursor cursor, int flags) {
		super(context, cursor, flags);
	}

	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		// TODO Auto-generated method stub
		int colLabel = cursor.getColumnIndex(ItemEntry.COL_LABEL);
		int colValue = cursor.getColumnIndex(ItemEntry.COL_VALUE);
		TextView title = (TextView) view.findViewById(R.id.label_title);
		TextView value = (TextView) view.findViewById(R.id.label_value);
		title.setText(cursor.getString(colLabel));
		value.setText(cursor.getString(colValue));
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		// TODO Auto-generated method stub
		LayoutInflater inflater = (LayoutInflater)context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rootView = inflater.inflate(R.layout.adapter_item, parent, false);
		return rootView;
	}

	public String getLabelAt(int position) {
		Cursor cursor = (Cursor) getItem(position);
		return cursor.getString(1);
	}
}
