package net.pejici.summation;

import net.pejici.summation.model.Query.ItemEntry;
import android.os.Bundle;
import android.content.ContentValues;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.support.v4.app.NavUtils;
import android.text.Editable;
import android.text.TextWatcher;

public class ItemActivity extends BaseActivity {

	ContentValues values = null;

	String columns[] = {
			ItemEntry.COL_PKEY,
			ItemEntry.COL_CREATION_DATE,
			ItemEntry.COL_LABEL,
			ItemEntry.COL_VALUE
	};

	EditText labelEdit = null;
	EditText valueEdit = null;

	Long sheetId = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_item);
		sheetId = getIntent().getExtras().getLong("sheetId");
		Long itemId = getIntent().getExtras().getLong("itemId");
		if (null != sheetId && null != itemId) {
			values = getModel().getItem(sheetId, itemId, columns);
		}
		else if (null != sheetId) {
			values = new ContentValues();
		}
		labelEdit = (EditText) this.findViewById(R.id.item_label_edit);
		valueEdit = (EditText) this.findViewById(R.id.item_value_edit);
		String label = values.getAsString(ItemEntry.COL_LABEL);
		if (null != label) {
			labelEdit.setText(label);
			labelEdit.setHint(label);
		}
		Double value = values.getAsDouble(ItemEntry.COL_VALUE);
		if (null != value) {
			String valueString = String.valueOf(value);
			valueEdit.setText(valueString);
			valueEdit.setHint(valueString);
		}
		labelEdit.addTextChangedListener(
				new ValuesTextWatcher(values, ItemEntry.COL_LABEL));
		valueEdit.addTextChangedListener(
				new ValuesTextWatcher(values, ItemEntry.COL_VALUE));
		setupActionBar();
	}

	private class ValuesTextWatcher implements TextWatcher {
		ContentValues values = null;
		String key = null;
		private ValuesTextWatcher(ContentValues values, String key) {
			this.values = values;
			this.key = key;
		}
		
		@Override
		public void onTextChanged(CharSequence s,
				int start, int before, int count) {}
		
		@Override
		public void beforeTextChanged(CharSequence s,
				int start, int count, int after) {}
		
		@Override
		public void afterTextChanged(Editable s) {
			values.put(key, s.toString());
		}
	}

	/**
	 * Set up the {@link android.app.ActionBar}.
	 */
	private void setupActionBar() {

		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setTitle(R.string.item_activity_title);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.item, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		if (null != sheetId) {
			Long itemId = values.getAsLong(ItemEntry.COL_PKEY);
			if (null != itemId) {
				getModel().updateItem(sheetId, values);
			}
			else {
				getModel().addItem(sheetId, values);
			}
		}
		super.onPause();
	}

}
