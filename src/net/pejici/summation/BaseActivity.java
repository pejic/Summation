package net.pejici.summation;

import net.pejici.summation.model.Model;
import android.app.Activity;

public class BaseActivity extends Activity {

	SummationApplication getSummationApplication() {
		return (SummationApplication) getApplication();
	}

	Model getModel() {
		return getSummationApplication().getModel();
	}

}
