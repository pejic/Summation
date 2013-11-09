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

}
