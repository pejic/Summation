package net.pejici.summation;

import net.pejici.summation.adapter.ItemAdapter;
import net.pejici.summation.adapter.SheetSpinnerAdapter;
import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

public class SummationList extends FragmentActivity implements
		ActionBar.OnNavigationListener {

	/**
	 * The serialization (saved instance state) Bundle key representing the
	 * current dropdown position.
	 */
	private static final String STATE_SELECTED_NAVIGATION_ITEM = "selected_navigation_item";

	Long sheetId = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_summation_list);

		// Set up the action bar to show a dropdown list.
		final ActionBar actionBar = getActionBar();
		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);

		// Set up the dropdown list navigation in the action bar.
		SummationApplication sa = (SummationApplication) getApplication();

		String [] columns = SheetSpinnerAdapter.DB_SHEET_COLUMNS;
		Cursor sheetsCursor = sa.getModel().getSheets(columns);
		SheetSpinnerAdapter sheets = new SheetSpinnerAdapter(
				getApplication(), sheetsCursor);
		actionBar.setListNavigationCallbacks(sheets, this);
	}

	@Override
	public void onRestoreInstanceState(Bundle savedInstanceState) {
		// Restore the previously serialized current dropdown position.
		if (savedInstanceState.containsKey(STATE_SELECTED_NAVIGATION_ITEM)) {
			getActionBar().setSelectedNavigationItem(
					savedInstanceState.getInt(STATE_SELECTED_NAVIGATION_ITEM));
		}
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		// Serialize the current dropdown position.
		outState.putInt(STATE_SELECTED_NAVIGATION_ITEM, getActionBar()
				.getSelectedNavigationIndex());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.summation_list, menu);
		return true;
	}

	@Override
	public boolean onNavigationItemSelected(int position, long id) {
		// When the given dropdown item is selected, show its contents in the
		// container view.
		Fragment fragment = new DummySectionFragment();
		Bundle args = new Bundle();
		sheetId = id;
		args.putLong(DummySectionFragment.ARG_SHEET_ID, id);
		fragment.setArguments(args);
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.container, fragment).commit();
		return true;
	}

	/**
	 * A dummy fragment representing a section of the app, but that simply
	 * displays dummy text.
	 */
	public static class DummySectionFragment extends Fragment {
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		public static final String ARG_SHEET_ID = "sheetId";
		private Long sheetId = null;

		public DummySectionFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			Context context = getActivity().getApplicationContext();
			View rootView = inflater.inflate(
					R.layout.fragment_summation_list_dummy, container, false);
			ListView listView = (ListView) rootView
					.findViewById(R.id.itemListView);
			SummationApplication sa = (SummationApplication)getActivity()
					.getApplication();
			sheetId = getArguments().getLong(ARG_SHEET_ID);
			Cursor items = sa.getModel()
					.getItems(sheetId, ItemAdapter.DB_ITEM_COLUMNS);
			ItemAdapter itemAdapter = new ItemAdapter(context, items, 0);
			listView.setAdapter(itemAdapter);
			TextView sumView = (TextView) rootView
					.findViewById(R.id.summationListSum);
			OnItemClickListener listener = new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					Intent itemIntent = new Intent(getActivity(),
							ItemActivity.class);
					itemIntent.putExtra("sheetId", sheetId);
					itemIntent.putExtra("itemId", id);
					startActivity(itemIntent);
				}
			};
			listView.setOnItemClickListener(listener);
			Double sum = sa.getModel().getSheetSum(sheetId);
			if (null != sum) {
				String sumText = String.valueOf(sum);
				sumView.setText(sumText);
			}
			return rootView;
		}
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		// TODO Auto-generated method stub
		if (item.getItemId() == R.id.action_add) {
			Intent itemIntent = new Intent(this, ItemActivity.class);
			itemIntent.putExtra("sheetId", sheetId);
			startActivity(itemIntent);
		}
		else if (item.getItemId() == R.id.action_add_sheet) {
			//
			Intent intent = new Intent(this, SheetActivity.class);
			startActivity(intent);
		}
		else if (item.getItemId() == R.id.action_edit){
			Intent intent = new Intent(this, SheetActivity.class);
			intent.putExtra("sheetId", sheetId);
			startActivity(intent);
		}
		else if (item.getItemId() == R.id.action_settings) {
			// TODO
		}
		return super.onMenuItemSelected(featureId, item);
	}

}
