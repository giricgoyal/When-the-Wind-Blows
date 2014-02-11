/**
 * 
 */
package main;

import Util.U;
import Util.Utilities;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PImage;
import processing.core.PVector;

/**
 * @author joysword
 *
 */
@SuppressWarnings("unused")
public class Map {
	
	private PApplet parent;
	private PImage pic;
	public PVector cen;
	private PVector dis;
	
	public float x0, y0, w, h; // coordinates in screen
	public float x1, x2, y1, y2; // the corner coordinate of the original image; (0,0)(5215,2652) means whole image
	
	public Map(PApplet parent, String file, float x, float y, float w, float h) {
			this.parent = parent;
			pic = this.parent.loadImage(file);
			x0 = x;
			y0 = y;
			this.w = w;
			this.h = h;
			cen = new PVector((x1+x2)/2,(y1+y2)/2);
	}
	
	public void setup(float x1, float y1, float x2, float y2) {
		this.x1 = x1;
		this.x2 = x2;
		this.y1 = y1;
		this.y2 = y2;
		dis=new PVector(0,0);
	}
	
	public void draw() {
		parent.pushStyle();
		parent.imageMode(PConstants.CORNER);
		parent.image(pic, x0, y0, w, h, PApplet.round(x1), PApplet.round(y1), PApplet.round(x2), PApplet.round(y2));
		parent.popStyle();
	}

	public boolean move(float mx, float my, float currentMX, float currentMY) {
		dis.x = (mx-currentMX) / w * (x2 - x1); 
		dis.y = (my-currentMY) / h * (y2 - y1);
		cen.add(dis);
		x1-=dis.x;
		x2-=dis.x;
		y1-=dis.y;
		y2-=dis.y;
		if (x1 < 0 || x2 > U.mapMaxW || y1 < 0 || y2 > U.mapMaxH) {
			x1+=dis.x;
			x2+=dis.x;
			y1+=dis.y;
			y2+=dis.y;
			return false;
		}
		return true;
	}

	public boolean checkIn(float x, float y) {
		if (x>x0 && x<x0+w && y>y0 && y<y0+h) return true;
		return false;
	}
}
