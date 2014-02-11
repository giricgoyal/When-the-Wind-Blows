package main;

import Util.Colors;
import Util.Utilities;
import processing.core.PApplet;
import processing.core.PConstants;

/**
 * Button class. Create a button and assign a name to it.
 * If you want the button change color while selected call setShowClicked()
 * @author Giric
 *
 */
public class Button extends BasicControl {
	private boolean selected;
	private boolean showClicked;
	private boolean addStroke;
	private String name;
	private boolean elps;

	public Button(PApplet parent, float x, float y, float width, float height) {
		super(parent, x, y, width, height);
		elps=false;
		selected = false;
		addStroke = false;
		showClicked = false;
	}
	
	@Override
	public void draw() {
		parent.pushStyle();
		if(showClicked) parent.fill(selected?Colors.buttonSelectedColor:Colors.buttonColor);
		else parent.fill(Colors.buttonColor);
		if(addStroke) {
			parent.stroke(Colors.buttonBorder);
			parent.strokeWeight(Utilities.Converter(1));
		}
		else {
			parent.noStroke();
		}
		parent.rectMode(PConstants.CORNER);
		parent.ellipseMode(PConstants.CORNER);
		if(elps) parent.ellipse(myX, myY, myWidth, myHeight);
		//else 
		parent.rect(myX, myY, myWidth, myHeight);
		parent.textAlign(PConstants.CENTER,PConstants.CENTER);
		parent.fill(Colors.black);
		//if(elps) parent.textSize(Utilities.Converter(20));
		//else 
		parent.textSize(Utilities.Converter(5));
		parent.text(name, (myWidth)/2+myX, (myHeight)/2+myY);
		parent.popStyle();
	}
	
	public boolean isSelected(){
		return selected;
	}
	
	public void setElpsFalse(){
		elps = false;
	}
	
	public boolean isInRectangle(float posX,float posY){
		return (myX<posX && posX<myX+myWidth && myY<posY && posY<myY+myHeight) ? true: false;
	}
	
	public boolean checkIn(float posX,float posY){
		return (myX<posX && posX<myX+myWidth && myY<posY && posY<myY+myHeight) ? true: false;
	}
	
	public void setSelected(boolean selected){
		this.selected = selected;
	}
	
	public void setName(String name){
		this.name=name;
	}
	
	public void setShowClick(){
		this.showClicked=true;
	}
	
	public void setStroke(){
		this.addStroke=true;
	}
}
