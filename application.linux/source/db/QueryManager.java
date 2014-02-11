package db;

import java.util.ArrayList;

import processing.core.PApplet;

import types.*;

/**
 * Call database queries from here
 * @author Shi
 *
 */
public class QueryManager {
	DatabaseManager db;
	
	public QueryManager(PApplet context){
		db = new DatabaseManager(context);
	}
	
	public ArrayList<DataLocation> getDataLocationAll() {
		return db.getAllLocation();
	}
	
	public ArrayList<DataPos> getDataPos_By_Data_Pid(int date, int pid) {
		String filters = getFilterDate(date) + getFilterPid(pid);
		return db.getDataPos(filters);
	}
	
	public ArrayList<DataPos> getDataPos_Everything() {
		String filters = "";
		return db.getDataPos(filters);
	}
	
	public ArrayList<DataPos> getDataPos_By_Date(int date) {
		String filters = getFilterDate(date);
		return db.getDataPos(filters);
	}
	
	public String[] getAllText_By_Date_TimeRange(int date, int half1, int half2) {
		String filters = getFilterDate(date) + " and "+getFilterTimeRange(half1,half2);
		return db.getAllText(filters);
	}
	
	public DataWeather[] getAllWeather() {
		return db.getAllWeather();
	}
	
	public DataCountPair[] getAllCount_By_Keyword(String keyword) {
		String filters = "keyword = '"+keyword+"'";
		return db.getTotalCount(keyword, filters);
	}
	
	public DataCountPair getKeywordCount(String keyword) {
		String filters = "keyword = '"+keyword+"'";
		return db.getKeywordCount(keyword,filters);
	}

	//get Filters
	private String getFilterDate(int date) {
		return "day = "+date;
	}
	
	private String getFilterPid(int pid) {
		return "and pid = "+pid;
	}
	
	private String getFilterTimeRange(int half1, int half2) {
		return "halfhour >= "+half1+" and halfhour < "+half2;
	}
}
