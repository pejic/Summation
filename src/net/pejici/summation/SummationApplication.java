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
