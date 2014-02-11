/**
 * 
 */
package main;

import Util.Colors;
import Util.Positions;
import Util.Utilities;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PImage;
import processing.core.PShape;

/**
 * @author giric
 *
 */
public class PopUpTweet  extends BasicControl {

	PApplet parent;
	PShape tweet;
	PImage delete;
	
	String tweetText = "";
	String tweetTime = "";
	int tweetPid = -1;
	
	
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
	
	float totalHeight;
	float totalWidth;
	
	boolean check;
	boolean upper;
	boolean lower;
	boolean left;
	boolean right;
	
	public PopUpTweet(PApplet parent, float x, float y, float width,
			float height) {
		super(parent, x, y, width, height);
		// TODO Auto-generated constructor stub
		this.parent = parent;
		this.upper = this.lower = this.left = this.right = false;
		
	}
	
	public void setTweetPopUp(float x, float y, float width,
			float height) {
		tweet = parent.loadShape("tweet.svg");
		
		this.coordX = x;
		this.coordY = y;
		
		this.width =  Positions.tweetWindowWidth;
		this.height =  Positions.tweetWindowHeight;		
		
		this.upperLeftX = coordX -  Utilities.Converter(30) ; 
		this.upperLeftY = coordY -  Utilities.Converter(10) - height;
		
		this.upperRightX = upperLeftX + width;
		this.upperRightY = upperLeftY;
		
		this.lowerRightX = upperRightX;
		this.lowerRightY = upperRightY + height;
		
		this.lowerLeftX = upperLeftX;
		this.lowerLeftY = upperLeftY + height;
		
		this.triangleLeftX = coordX +  Utilities.Converter(10);
		this.triangleLeftY = upperLeftY + height;
		
		this.triangleRightX = coordX +  Utilities.Converter(25);
		this.triangleRightY = upperLeftY + height;
		
		this.check = true;
		this.upper = this.lower = this.left = this.right = false;
		/*
		Utilities.popUpWidth = this.width;
		Utilities.popUpHeight = this.height;
		Utilities.popUpX = this.upperLeftX;
		Utilities.popUpY = this.upperLeftY;
		*/
		/**
		 * check if popUp is intersected by the top side of the window
		 * if yes, translate the popUp
		 */
		if (upperLeftY < Positions.mapY) {
			
			this.upper = true;
			this.upperLeftY = coordY + Utilities.Converter(10) + height;
			this.upperRightY = upperLeftY;
			this.lowerRightY = upperRightY - height;
			this.lowerLeftY = lowerRightY;
			this.triangleLeftY = upperLeftY - height;
			this.triangleRightY = upperLeftY - height;
			Utilities.popUpY = this.lowerLeftY;
			
		}
		
		/**
		 * check if popUp is intersected by the right side of the window
		 * if yes, translate the popUp
		 */
		
		if (upperRightX > Positions.mapX + Positions.mapWidth) {
			this.right = true;
			this.triangleLeftX = coordX - Utilities.Converter(10);
			this.triangleRightX = coordX - Utilities.Converter(25);
			this.upperLeftX = coordX - width;
			this.upperRightX = upperLeftX + width;
			this.lowerLeftX = upperLeftX;
			this.lowerRightX = upperRightX;
			Utilities.popUpX = this.upperLeftX;
			
		}
		

		/**
		 * check if popUp is intersected by the left side of the window
		 * if yes, translate the popUp;
		 */
		
		if(upperLeftX < Positions.mapX) {
			this.left = true;
			this.triangleLeftX = coordX + Utilities.Converter(10);
			this.triangleRightX = coordX + Utilities.Converter(25);
			this.upperLeftX = coordX + Utilities.Converter(5);
			this.upperRightX = upperLeftX + width;
			this.lowerLeftX = upperLeftX;
			this.lowerRightX = upperRightX;
			Utilities.popUpX = this.upperLeftX;
			
		}
		System.out.println("check");
		
	}
	
	public void setCheck(boolean check) {
		this.check = check;
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

	public boolean checkIn(float mx, float my) {
		return upper?(mx > upperLeftX && mx < upperRightX && my > lowerLeftY && my < upperLeftY):(mx > upperLeftX && mx < upperRightX && my > upperLeftY && my < lowerLeftY);
	}
	
	@Override
	public void draw() {
		// TODO Auto-generated method stub
		if (this.check) {
			
			parent.noStroke();
			parent.fill(Colors.tweetColor);
			parent.beginShape();
			parent.vertex(coordX, coordY);
			parent.vertex(triangleLeftX, triangleLeftY);
			parent.vertex(lowerLeftX, lowerLeftY);
			parent.vertex(upperLeftX,upperLeftY);
			parent.vertex(upperRightX, upperRightY);
			parent.vertex(lowerRightX, lowerRightY);
			parent.vertex(triangleRightX, triangleRightY);
			parent.endShape();		
	
			parent.fill(Colors.tweetColor2);
			
			if (upper) {
				
				parent.beginShape();
				parent.vertex(upperLeftX, upperLeftY - Utilities.Converter(10));
				parent.vertex(upperLeftX,upperLeftY);
				parent.vertex(upperRightX, upperRightY);
				parent.vertex(upperRightX, upperRightY  - Utilities.Converter(10));
				parent.endShape();
				
				parent.shapeMode(PConstants.CENTER);
				parent.shape(tweet, lowerLeftX + width/2, lowerLeftY + height/2, (float)(tweet.width/3) , (float)(tweet.height/3) );
				//parent.shape(tweet, upperRightX - Utilities.Converter(2) - Positions.tweetWidth, upperRightY - Utilities.Converter(12) - Positions.tweetWidth, Positions.tweetWidth, Positions.tweetHeight);
			}
			else {
				parent.beginShape();
				parent.vertex(coordX, coordY);
				parent.vertex(triangleLeftX, triangleLeftY);
				parent.vertex(lowerLeftX, lowerLeftY);
				parent.vertex(lowerLeftX,lowerLeftY - Utilities.Converter(10));
				parent.vertex(lowerRightX, lowerRightY - Utilities.Converter(10));
				parent.vertex(lowerRightX, lowerRightY);
				parent.vertex(triangleRightX, triangleRightY);
				parent.endShape();
				parent.shapeMode(PConstants.CENTER);
				parent.shape(tweet, upperLeftX + width/2, upperLeftY + height/2, (float)(tweet.width/3) , (float)(tweet.height/3) );
				parent.shape(tweet, lowerRightX - Utilities.Converter(2) - Positions.tweetWidth, lowerRightY - Utilities.Converter(12) - Positions.tweetWidth, Positions.tweetWidth, Positions.tweetHeight);
	
			}
			drawText();
		}
		
	}
	
	public void setTweet() {
		this.tweetTime = Utilities.tweetTime;
		this.tweetPid = Utilities.tweetPid;
		char[] array = Utilities.currentTweet.toCharArray();
		float length = Utilities.currentTweet.length();
		float maxLenghtPerLine = (width) / Utilities.Converter(3);
		int spaceIndex = 0;
		int index = 0;
		/*
		while(length > maxLenghtPerLine + 1){
			spaceIndex = (int)length;
			while(spaceIndex > index + maxLenghtPerLine ) {
				spaceIndex = (int)(Utilities.currentTweet.lastIndexOf(" ", spaceIndex-1));
				//System.out.println(spaceIndex);
			}
			index = spaceIndex;
			array[spaceIndex] = '\n';
			//this.tweetText = this.tweetText.replace(this.tweetText.charAt(spaceIndex), '\n');
			//System.out.println(array);
			length = length - spaceIndex + 1;
		}
		*/
		
		for (int counter = 0; counter < length; counter++){
			if (array[counter] == ' ')
				spaceIndex = counter;
			if (counter > maxLenghtPerLine + index) {
				array[spaceIndex] = '\n';
				index = spaceIndex;
			}
			
		}
		this.tweetText = "";
		for (int counter = 0; counter < array.length; counter++)
			this.tweetText = this.tweetText + array[counter];
		
	}
	
	
	public void drawText() {
		if (this.tweetText != "") {
			parent.noStroke();
			parent.fill(Colors.BLACK);
			parent.textSize(Utilities.Converter(5));
			parent.textAlign(PConstants.LEFT, PConstants.TOP);
			if (upper) {
				parent.text(this.tweetText, lowerLeftX + Utilities.Converter(3), lowerLeftY + Utilities.Converter(4));
				parent.textAlign(PConstants.RIGHT , PConstants.BOTTOM);
				
				if (Utilities.currentDay == 30) {
					parent.text(this.tweetTime + ", April " + Utilities.currentDay, upperRightX - Utilities.Converter(2), upperRightY - Utilities.Converter(2));
					parent.textAlign(PConstants.LEFT, PConstants.BOTTOM);
					parent.text("PID : " + this.tweetPid, upperLeftX + Utilities.Converter(2), upperLeftY - Utilities.Converter(2));
				}
				else {
					parent.text(this.tweetTime + ", May " + Utilities.currentDay, upperRightX - Utilities.Converter(2), upperRightY - Utilities.Converter(2));
					parent.textAlign(PConstants.LEFT, PConstants.BOTTOM);
					parent.text("PID : " + this.tweetPid, upperLeftX + Utilities.Converter(2), upperLeftY - Utilities.Converter(2));
				}
			}
			else {
				parent.text(this.tweetText, upperLeftX + Utilities.Converter(3), upperLeftY + Utilities.Converter(4));
				parent.textAlign(PConstants.RIGHT , PConstants.BOTTOM);
				
				if (Utilities.currentDay == 0) {
					parent.text(this.tweetTime + ", April 30", lowerRightX - Utilities.Converter(2), lowerRightY - Utilities.Converter(2));
					parent.textAlign(PConstants.LEFT, PConstants.BOTTOM);
					parent.text("PID : " + this.tweetPid, lowerLeftX + Utilities.Converter(2), lowerLeftY - Utilities.Converter(2));
				}
				else {
					parent.text(this.tweetTime + ", May " + Utilities.currentDay, lowerRightX - Utilities.Converter(2), lowerRightY - Utilities.Converter(2));
					parent.textAlign(PConstants.LEFT, PConstants.BOTTOM);
					parent.text("PID : " + this.tweetPid, lowerLeftX + Utilities.Converter(2), lowerLeftY - Utilities.Converter(2));
				}
			}
			
		}
	}

}

