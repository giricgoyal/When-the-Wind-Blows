/**
 * 
 */
package main;

import Util.Colors;
import Util.Utilities;
import Util.U;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PVector;

/**
 * @author joysword
 *
 */
@SuppressWarnings("unused")
public class Lock {
	
	private PApplet p;
	private PVector cen;
	private String text;
	private float lockW, lockH;

	public Lock(PApplet p, float _x, float _y, float lockW, float lockH, int half) {
		this.p = p;
		cen = new PVector(_x, _y);
		this.lockW= lockW;
		this.lockH = lockH;
		updateTime(half);
	}
	
	public void draw() {
		p.pushStyle();
	    p.fill(Colors.GRAPH_COLOR_2);
	    p.noStroke();
	    p.ellipse(cen.x, cen.y, lockW, lockH);
	    //rect(x-w*scale,y-h*scale,x+w*scale,y+h*scale);
	    p.fill(Colors.BLACK);
	    p.textSize(U.Converter(5.5));
	    p.textAlign(PConstants.CENTER, PConstants.BOTTOM);
	    p.text(text, cen.x, cen.y + lockH*4/10);
	    p.popStyle();
	}
	
	protected float getX() {
		return cen.x;
	}
	
	protected float getY() {
		return cen.y;
	}
	
	protected void update(float _x, int half) {
		cen.x = _x;
		updateTime(half);
	}
	
	private void updateTime(int half) {
		if (half/2 < 10) {
			text = "0" + half/2 + ":";
		}
		else {
			text = half/2 + ":";
		}
		if (half%2 == 1) {
			text+="30";
		}
		else {
			text+="00";
		}
	}
}
