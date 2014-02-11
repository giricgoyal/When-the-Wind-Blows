package markers;

import Util.U;
import processing.core.PApplet;

public class AbstractMarker {
	float x;
	float y;
	int pid;
	int hour;
	int min;
	int halfhour;
	String text;
	String keywords;
	public int loc;
	PApplet p;
	
	public AbstractMarker(PApplet p, float x, float y, int pid, int hour, int min, String text, String keywords, int loc) {
		this.p = p;
		this.x = x;
		this.y = y;
		this.pid = pid;
		this.hour = hour;
		this.min = min;
		this.text = text;
		this.keywords = keywords;
		this.loc = loc;
		
		halfhour = this.hour*2 + this.min/30;
	}
	
	public void draw() {
	}
	
	public void updatePos(float x1, float x2, float x3, float x4, float y1, float y2, float y3, float y4) {
		x = PApplet.map(x,x1,x2,x3,x4);
		y = PApplet.map(y,y1,y2,y3,y4);
	}

	public void movePos(float x, float y) {
		this.x += x;
		this.y += y;
	}
	
	public PApplet getP() {
		return p;
	}
	
	public float getX() {
		return x;
	}
	
	public float getY() {
		return y;
	}
	
	public int getPid() {
		return pid;
	}
	
	public int getHour() {
		return hour;
	}
	
	public int getMin() {
		return min;
	}
	
	public String getTweet() {
		return text;
	}
	
	public String getKeywords() {
		return keywords;
	}
	
	public int getLocation() {
		return loc;
	}
	
	public boolean checkIn(float _x, float _y) {
		return (_x > x - U.markerHalfWidth && _x < x + U.markerHalfWidth && _y > y - U.markerHalfHeight && _y < y + U.markerHalfHeight);
	}

	public float dis(float _x, float _y) {
		return (_x-x)*(_x-x)+(_y-y)*(_y-y);
	}
}
