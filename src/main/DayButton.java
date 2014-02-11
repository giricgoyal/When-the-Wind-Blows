/**
 * 
 */
package main;

import Util.Colors;
import Util.U;
import processing.core.PApplet;
import processing.core.PConstants;

/**
 * @author joysword
 *
 */
public class DayButton extends Button {
	
	PApplet p;
	int count;
	String text;
	boolean selected;
	
	public DayButton(PApplet p, float x, float y, float width, float height, int cnt, int day) {
		super(p, x, y, width, height);
		this.p = p;
		count = cnt;
		if (day == 0) {
			text = "4/30";
		}
		else {
			text = "5/"+day;
		}
		selected = false;
	}
	
	@Override
	public void draw() {
		p.pushStyle();
		p.noStroke();
		if (selected) {
			p.fill(Colors.GRAPH_COLOR_4);
		}
		else {
			p.fill(Colors.GRAPH_COLOR_3);
		}
		p.rectMode(PConstants.CORNERS);
		float Y = PApplet.map(count, U.dayButtonLowerBound, U.currentMaxTweets * (float)1.05, myY+myHeight, myY);
		p.rect(myX, Y, myX+myWidth, myY+myHeight);
		p.strokeWeight(U.Converter(0.5));
		p.stroke(Colors.WHITE);
		p.noFill();
		p.rectMode(PConstants.CORNER);
		p.rect(myX,myY,myWidth,myHeight);
		if (selected) {
			p.fill(Colors.RED);
		}
		else {
			p.fill(Colors.WHITE);
		}
		p.textAlign(PConstants.CENTER,PConstants.BOTTOM);
		p.textSize(U.Converter(5.5));
		p.text(text, myX+this.myWidth/2, myY+myHeight*9/10);
		p.popStyle();
	}
	
	public void setSelected(boolean st) {
		selected = st;
	}
	
	public void updateCount(int cnt) {
		count = cnt;
	}
}
