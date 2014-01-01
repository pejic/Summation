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
