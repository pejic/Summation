package net.pejici.summation;

import net.pejici.summation.adapter.ItemAdapter;
import net.pejici.summation.adapter.SheetSpinnerAdapter;
import net.pejici.summation.model.Model;
import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
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

	private Long sheetId = null;
	private SheetSpinnerAdapter sheetsAdapter = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_summation_list);

		// Set up the action bar to show a dropdown list.
		final ActionBar actionBar = getActionBar();
		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
		recreateSheetsList();
	}

	@Override
	protected void onResume() {
		super.onResume();
		recreateSheetsList();
	}

	private void recreateSheetsList() {
		SummationApplication sa = (SummationApplication) getApplication();
		String [] columns = SheetSpinnerAdapter.DB_SHEET_COLUMNS;
		Cursor sheetsCursor = sa.getModel().getSheets(columns);
		if (null == sheetsAdapter) {
			sheetsAdapter = new SheetSpinnerAdapter(
					getApplication(), sheetsCursor);
			getActionBar().setListNavigationCallbacks(sheetsAdapter, this);
		}
		else {
			sheetsAdapter.changeCursor(sheetsCursor);
		}
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
		if (null == sheetId || sheetId != id) {
			Fragment fragment = new ItemTableFragment();
			Bundle args = new Bundle();
			sheetId = id;
			args.putLong(ItemTableFragment.ARG_SHEET_ID, id);
			fragment.setArguments(args);
			getSupportFragmentManager().beginTransaction()
					.replace(R.id.container, fragment).commit();
		}
		return true;
	}

	/**
	 * A dummy fragment representing a section of the app, but that simply
	 * displays dummy text.
	 */
	public static class ItemTableFragment extends Fragment {
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		public static final String ARG_SHEET_ID = "sheetId";
		private Long sheetId = null;
		private ListView listView = null;
		private ItemAdapter itemAdapter = null;
		private TextView sumView = null;

		public ItemTableFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(
					R.layout.fragment_summation_list_dummy, container, false);
			listView = (ListView) rootView
					.findViewById(R.id.itemListView);
			sheetId = getArguments().getLong(ARG_SHEET_ID);
			recreateItemAdapter();
			sumView = (TextView) rootView.findViewById(R.id.summationListSum);
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
			registerForContextMenu(listView);
			updateSum();
			return rootView;
		}

		@Override
		public void onCreateContextMenu(ContextMenu menu, View v,
				ContextMenuInfo menuInfo) {
			// TODO Auto-generated method stub
			super.onCreateContextMenu(menu, v, menuInfo);
			if (v.getId() == R.id.itemListView) {
				AdapterView.AdapterContextMenuInfo info =
						(AdapterView.AdapterContextMenuInfo)menuInfo;
				menu.setHeaderTitle(itemAdapter.getLabelAt(info.position));
				String options[] = getResources()
						.getStringArray(R.array.item_context_menu);
				for (int i = 0; i < options.length; i++) {
					String option = options[i];
					menu.add(Menu.NONE, i, i, option);
				}
			}
		}

		@Override
		public boolean onContextItemSelected(MenuItem item) {
			AdapterView.AdapterContextMenuInfo info =
					(AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
			int menuItemId = item.getItemId();
			if (menuItemId == 0) { // Delete
				getSummationApplication().getModel()
					.deleteItem(sheetId, info.id);
				updateAll();
			}
			return true;
		}

		@Override
		public void onResume() {
			super.onResume();
			updateAll();
		}

		private void updateAll() {
			recreateItemAdapter();
			updateSum();
		}

		private SummationApplication getSummationApplication() {
			return (SummationApplication)getActivity().getApplication();
		}

		private void updateSum() {
			Double sum = getSummationApplication().getModel()
					.getSheetSum(sheetId);
			if (null != sum) {
				String sumText = String.valueOf(sum);
				sumView.setText(sumText);
			}
		}

		private void recreateItemAdapter() {
			Context context = getActivity().getApplicationContext();
			Cursor items = getSummationApplication().getModel()
					.getItems(sheetId, ItemAdapter.DB_ITEM_COLUMNS);
			if (itemAdapter == null) {
				itemAdapter = new ItemAdapter(context, items, 0);
				listView.setAdapter(itemAdapter);
			}
			else {
				itemAdapter.changeCursor(items);
			}
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
		else if (item.getItemId() == R.id.action_edit_sheet){
			Intent intent = new Intent(this, SheetActivity.class);
			intent.putExtra("sheetId", sheetId);
			startActivity(intent);
		}
		else if (item.getItemId() == R.id.action_delete_sheet) {
			Model model = getSummationApplication().getModel();
			if (null != sheetId) {
				model.deleteSheet(sheetId);
				sheetId = null;
				recreateSheetsList();
			}
		}
		return super.onMenuItemSelected(featureId, item);
	}

	private SummationApplication getSummationApplication() {
		return (SummationApplication)getApplication();
	}
}
