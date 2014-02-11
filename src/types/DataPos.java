package types;

public class DataPos {
	private float lat;
	private float lon;
	private int pid;
	private int day;
	private int hour;
	private int min;
	private String text;
	public String keywords;
	public int lid;
	
	public DataPos(int pid, int day, int hour, int min, float _lat, float _lon, String _txt, String _key, int _lid) {
		this.pid = pid;
		this.day = day;
		this.hour = hour;
		this.min = min;
		lat = _lat;
		lon = _lon;
		text = _txt;
		keywords = _key;
		lid = _lid;
	}
	
	public int getPid() {
		return pid;
	}
	
	public int getDay() {
		return day;
	}
	
	public int getHour() {
		return hour;
	}
	
	public int getMin() {
		return min;
	}
	
	public float getLatitude() {
		return lat;
	}

	public float getLongitude() {
		return lon;
	}
	
	public String getTweet() {
		return text;
	}
	
	public String getKeywords() {
		return keywords;
	}
	
	public int getLocation() {
		return lid;
	}
}
