package net.pejici.summation;

import net.pejici.summation.model.DBHelper;
import net.pejici.summation.model.Model;
import android.app.Application;

public class SummationApplication extends Application {

	private Model model = null;

	public Model getModel() {
		if (null == model) {
			DBHelper dbhelper = new DBHelper(this.getApplicationContext());
			model = new Model(dbhelper.getWritableDatabase());
		}
		return model;
	}

}
