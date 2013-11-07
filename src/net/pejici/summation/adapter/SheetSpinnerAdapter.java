package net.pejici.summation.adapter;

import android.R;
import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.SimpleCursorAdapter;

public class SheetSpinnerAdapter extends SimpleCursorAdapter {

	static final String [] from = {"name"};
	static final int[] to = {R.id.text1};

	public SheetSpinnerAdapter(Context context, Cursor c) {
		super(context, R.layout.simple_spinner_dropdown_item, c, from, to, 0);
	}

}
