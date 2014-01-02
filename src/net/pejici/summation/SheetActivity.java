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
package net.pejici.summation;

import net.pejici.summation.model.Query.SheetEntry;
import android.os.Bundle;
import android.content.ContentValues;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.support.v4.app.NavUtils;
import android.text.Editable;
import android.text.TextWatcher;

public class SheetActivity extends BaseActivity {

	private EditText sheetNameEditText = null;
	private static final String [] columns =
		{SheetEntry.COL_PKEY, SheetEntry.COL_NAME};

	ContentValues values = null;

	public String getName() {
		return values.getAsString(SheetEntry.COL_NAME);
	}

	public void setName(String sheetName) {
		values.put(SheetEntry.COL_NAME, sheetName);
	}

	public Long getSheetId() {
		return values.getAsLong(SheetEntry.COL_PKEY);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sheet);
		// Show the Up button in the action bar.
		setupActionBar();
		sheetNameEditText = (EditText) this.findViewById(R.id.sheet_sheet_name);
		sheetNameEditText.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {}
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
			@Override
			public void afterTextChanged(Editable s) {
				setName(s.toString());
			}
		});
		if (getIntent() != null
				&& getIntent().getExtras() != null
				&& getIntent().getExtras().containsKey("sheetId"))
		{
			Long id = getIntent().getExtras().getLong("sheetId");
			values = new ContentValues(getModel().getSheet(id, columns));
			sheetNameEditText.setText(values.getAsString(SheetEntry.COL_NAME));
		}
		else {
			values = new ContentValues();
		}
	}

	/**
	 * Set up the {@link android.app.ActionBar}.
	 */
	private void setupActionBar() {
		getActionBar().setDisplayHomeAsUpEnabled(true);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.sheet, menu);
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
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		if (item.getItemId() == R.id.action_cancel) {
			NavUtils.navigateUpFromSameTask(this);
		}
		return super.onMenuItemSelected(featureId, item);
	}

	@Override
	protected void onPause() {
		if (getSheetId() == null) {
			values.put(SheetEntry.COL_PKEY, getModel().addSheet(getName()));
		}
		else {
			getModel().updateSheet(values);
		}
		super.onPause();
	}
}
