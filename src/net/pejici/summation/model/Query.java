package net.pejici.summation.model;

/**
 * This class contains static strings and methods with SQL Queries.
 *
 * @author slobo
 *
 */
public class Query {
	static String DB_CREATE_ALL =
			"CREATE TABLE sheet ("
			+ "pk INTEGER PRIMARY KEY, "
			+ "name VARCHAR(255) NOT NULL"
			+ ");";

	static String DB_CREATE_SHEET(Long pk) {
		return "CREATE TABLE sheet_"+pk+" ("
				+ "pk INTEGER PRIMARY KEY, "
				+ "date DATETIME DEFAULT NOW, "
				+ "label TEXT DEFAULT NULL, "
				+ "value DOUBLE DEFAULT NULL"
				+ ");";
	}
}
