/**
 * class to implement popUp 
 * @author giric
 */
package main;

import markers.MarkerType;
import Util.Colors;
import Util.Positions;
import Util.U;
import Util.Utilities;
import processing.core.PApplet;
import processing.core.PConstants;

public class PopUp extends BasicControl{

	public PApplet parent;
	float coordX;
	float coordY;
	int color;
	float upperLeftX;
	float upperLeftY;
	float upperRightX;
	float upperRightY;
	float lowerRightX;
	float lowerRightY;
	float lowerLeftX;
	float lowerLeftY;
	float height;
	float width;
	float triangleLeftX;
	float triangleLeftY;
	float triangleRightX;
	float triangleRightY;
	
	float selectX;
	float selectY;
	float deleteX;
	float deleteY;
	float cancelX;
	float cancelY;
	float buttonHeight;
	float buttonWidth;
	
	
	float totalHeight;
	float totalWidth;
	
	boolean check;
	boolean upper;
	boolean lower;
	boolean left;
	boolean right;
	
	String keyword;
	
	CS424_Project4_Group4 program;
	
	public PopUp(PApplet parent, float x, float y, float w, float h, int color, CS424_Project4_Group4 program) {
		super(parent, x, y, w, h);
		this.parent = parent;
		this.program = program;
		// TODO Auto-generated constructor stub	
	}
	public void setPopUp(float x, float y, int color, String keyword) {
		
		this.color  = color;
		this.coordX = x;
		this.coordY = y;
		
		
		this.width =  Utilities.Converter(50);
		this.height =  Utilities.Converter(30);		
		
		this.upperLeftX = coordX -  Utilities.Converter(20);
		this.upperLeftY = coordY -  Utilities.Converter(35);
		
		this.upperRightX = upperLeftX + width;
		this.upperRightY = upperLeftY;
		
		this.lowerRightX = upperRightX;
		this.lowerRightY = upperRightY + height;
		
		this.lowerLeftX = upperLeftX;
		this.lowerLeftY = upperLeftY + height;
		
		this.triangleLeftX = coordX +  Utilities.Converter(10);
		this.triangleLeftY = upperLeftY + height;
		
		this.triangleRightX = coordX +  Utilities.Converter(20);
		this.triangleRightY = upperLeftY + height;
		
		this.check = true;
		this.upper = this.lower = this.left = this.right = false;
		
		this.keyword = keyword;
		

		this.buttonHeight = height/3 - Utilities.Converter(1);
		this.buttonWidth = width - Utilities.Converter(2);
		this.selectX = upperLeftX + Utilities.Converter(1);
		this.selectY = upperLeftY + Utilities.Converter(1);
		this.deleteX = this.selectX;
		this.deleteY = this.selectY + Utilities.Converter(0.5);
		this.cancelX = this.selectX;
		this.cancelY = this.deleteY + Utilities.Converter(0.5);
		
		Utilities.popUpWidth = this.width;
		Utilities.popUpHeight = this.height;
		Utilities.popUpX = this.upperLeftX;
		Utilities.popUpY = this.upperLeftY;
		
		/**
		 * check if popUp is intersected by the top side of the window
		 * if yes, translate the popUp
		 */
		
		if (upperLeftY < Positions.graphWindowY) {
			
			this.upper = true;
			this.upperLeftY = coordY + Utilities.Converter(35);
			this.upperRightY = upperLeftY;
			this.lowerRightY = upperRightY - height;
			this.lowerLeftY = lowerRightY;
			this.triangleLeftY = upperLeftY - height;
			this.triangleRightY = upperLeftY - height;
			
			this.selectY = lowerLeftY + Utilities.Converter(1);
			this.deleteY = this.selectY + buttonHeight + Utilities.Converter(0.5);
			this.cancelY = this.deleteY + buttonHeight + Utilities.Converter(0.5);
			
			
			Utilities.popUpY = this.lowerLeftY;
			
		}
		
		/**
		 * check if popUp is intersected by the right side of the window
		 * if yes, translate the popUp
		 */
		
		if (upperRightX > Positions.graphWindowX + Positions.graphWindowWidth) {
			this.right = true;
			this.triangleLeftX = coordX - Utilities.Converter(10);
			this.triangleRightX = coordX - Utilities.Converter(20);
			this.upperLeftX = coordX - width;
			this.upperRightX = upperLeftX + width;
			this.lowerLeftX = upperLeftX;
			this.lowerRightX = upperRightX;
			
			this.selectX = upperLeftX + Utilities.Converter(1);
			this.deleteX = this.selectX;
			this.cancelX = this.selectX;
			
			
			Utilities.popUpX = this.upperLeftX;
			
		}
		

		/**
		 * check if popUp is intersected by the left side of the window
		 * if yes, translate the popUp;
		 */
		
		if(upperLeftX < Positions.graphWindowX) {
			this.left = true;
			this.triangleLeftX = coordX + Utilities.Converter(10);
			this.triangleRightX = coordX + Utilities.Converter(20);
			this.upperLeftX = coordX + Utilities.Converter(5);
			this.upperRightX = upperLeftX + width;
			this.lowerLeftX = upperLeftX;
			this.lowerRightX = upperRightX;
			
			this.selectX = upperLeftX + Utilities.Converter(1);
			this.deleteX = this.selectX;
			this.cancelX = this.selectX;
			
			Utilities.popUpX = this.upperLeftX;
			
		}
		
		

		
	}
	
	public void click(float mx, float my) {
		if (check) {
			if (mx > selectX && mx < selectX + buttonWidth) {
				if (my > selectY && my < selectY + buttonHeight) {
					System.out.println("click on popup select");
					Utilities.currentWord = keyword;
					program.setCurrentData_forKeywords(program.dataPos,program.dataDay,U.bHalf,U.eHalf,U.currentWord);
					program.setMarkerPos(program.dataPos,program.markers,MarkerType.DEFAULT_MARKER);
					program.currentKeywordCount = program.qManager.getKeywordCount(Utilities.currentWord);
					program.updateDayButton();
					program.dataCount = program.qManager.getAllCount_By_Keyword(Utilities.currentWord);
					program.timeSlider.update(program.dataCount);
					check = false;
					return;
				}
				
				if (my > deleteY && my < deleteY + buttonHeight) {
					System.out.println("click on popup delete");
					Utilities.keywordGraph.remove(keyword);
					program.graph.setData();
					check = false;
				}
				
				if (my > cancelY && my < cancelY + buttonHeight) {
					System.out.println("Click on popup cancel");
					check = false;
				}
			}
		}
		
	}
	
	public void drawButtons() {
		parent.rectMode(PConstants.CORNER);
		parent.fill(Colors.DARK_WHITE);
		parent.rect(selectX, selectY, buttonWidth, buttonHeight);
		parent.rect(deleteX, deleteY, buttonWidth, buttonHeight);
		parent.fill(Colors.LIGHT_GRAY);
		parent.rect(cancelX, cancelY, buttonWidth, buttonHeight);
		
		parent.textSize(Utilities.Converter(5));
		parent.textAlign(PConstants.CENTER, PConstants.CENTER);
		parent.fill(Colors.black);
		parent.text("Select", selectX + buttonWidth/2, selectY + buttonHeight/2);
		parent.text("Delete", deleteX + buttonWidth/2, deleteY + buttonHeight/2);
		parent.fill(color);
		parent.text("Cancel", cancelX + buttonWidth/2, cancelY + buttonHeight/2);
	}
	
	public void setCheck(boolean check) {
		this.check = check;
	}
	
	//@Override
	public void draw() {
		if (check) {
			parent.noStroke();
			parent.fill(color);
			parent.beginShape();
			parent.vertex(coordX, coordY);
			parent.vertex(triangleLeftX, triangleLeftY);
			parent.vertex(lowerLeftX, lowerLeftY);
			parent.vertex(upperLeftX,upperLeftY);
			parent.vertex(upperRightX, upperRightY);
			parent.vertex(lowerRightX, lowerRightY);
			parent.vertex(triangleRightX, triangleRightY);
			parent.endShape();
			drawButtons();
		}
	}
	
	public float getX() {
		return upperLeftX;
	}
	
	public float getY() {
		return upperLeftY;
	}
	
	public boolean getCheck() {
		return check;
	}

}
