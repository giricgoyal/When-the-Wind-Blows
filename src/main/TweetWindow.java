package main;

import Util.Colors;
import Util.Positions;
import Util.Utilities;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PShape;

public class TweetWindow extends BasicControl{

	PApplet parent;
	
	PShape tweet;
	String tweetText = "";
	String tweetTime = "";
	int tweetPid = -1;
	
	
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
	
	
	
	
	
	float v1x, v1y, v2x, v2y, v3x, v3y, v4x, v4y, v5x, v5y, v6x, v6y, v7x, v7y, v8x, v8y;
	

	public TweetWindow(PApplet parent, float x, float y, float width,
			float height) {
		super(parent, x, y, width, height);
		// TODO Auto-generated constructor stub
		
		this.parent = parent;
		
		tweet = parent.loadShape("tweet.svg");
		Positions.tweetWidth = tweet.width/Utilities.Converter(8);
		Positions.tweetHeight = tweet.height/Utilities.Converter(8);
		
		
		v1x = myX;
		v1y = myY;
		
		v2x = v1x;
		v2y = myY + myHeight;
		
		v6x = myX + myWidth;
		v6y = v2y;
		
		v7x = v6x;
		v7y = v1y;
		
		v3x = myX + Utilities.Converter(20);
		v3y = v2y;
		
		v5x = v3x + Utilities.Converter(5);
		v5y = v2y;
		
		v4x = myX + Positions.tweetWidth + Utilities.Converter(2);
		v4y = v5y + Utilities.Converter(6);
		
		/*
		v2x = myX + Utilities.Converter(20);
		v2y = myY + Positions.tweetHeight + Utilities.Converter(3);
		
		v3x = v2x - Utilities.Converter(18);
		v3y = v2y;
		
		v4x = v3x;
		v4y = myY + myHeight - Utilities.Converter(2);
		
		v5x = myX + myWidth - Utilities.Converter(2);
		v5y = v4y;
		
		v6x = v5x;
		v6y = v2y;
		
		v7x = v2x + Utilities.Converter(5);
		v7y = v2y;
		
		v1x = myX + Positions.tweetWidth + Utilities.Converter(2);
		v1y = v2y - Utilities.Converter(8);
		*/
	}

	@Override
	public void draw() {
		// TODO Auto-generated method stub
		parent.shapeMode(PConstants.CORNER);
		parent.shape(tweet, myX, myY + myHeight, Positions.tweetWidth, Positions.tweetHeight);
		parent.fill(Colors.tweetColor);
		parent.strokeWeight(Utilities.Converter(0.5));
		parent.stroke(Colors.DARK_GRAY);
		parent.beginShape();
			parent.vertex(v1x, v1y);
			parent.vertex(v2x, v2y);
			parent.vertex(v3x, v3y);
			parent.vertex(v4x, v4y);
			parent.vertex(v5x, v5y);
			parent.vertex(v6x, v6y);
			parent.vertex(v7x, v7y);
		parent.endShape(PConstants.CLOSE);
		parent.fill(Colors.tweetColor2);
		parent.beginShape();
			parent.vertex(v2x, v2y);
			parent.vertex(v3x, v3y);
			parent.vertex(v4x, v4y);
			parent.vertex(v5x, v5y);
			parent.vertex(v6x, v6y);
			parent.vertex(v6x, v6y - Utilities.Converter(10));
			parent.vertex(v2x, v6y - Utilities.Converter(10));
		parent.endShape(PConstants.CLOSE);
			
		drawText();
	}
	
	public void setTweet() {
		this.tweetTime = Utilities.tweetTime;
		this.tweetPid = Utilities.tweetPid;
		char[] array = Utilities.currentTweet.toCharArray();
		float length = Utilities.currentTweet.length();
		float maxLenghtPerLine = (v7x - v1x) / Utilities.Converter(3);
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
			parent.text(this.tweetText, v1x + Utilities.Converter(3), v1y + Utilities.Converter(4));
			parent.textAlign(PConstants.RIGHT , PConstants.BOTTOM);
			
			if (Utilities.currentDay == 30) {
				parent.text(this.tweetTime + ", April " + Utilities.currentDay, v6x - Utilities.Converter(2), v6y - Utilities.Converter(2));
				parent.textAlign(PConstants.LEFT, PConstants.BOTTOM);
				parent.text("PID : " + this.tweetPid, v2x + Utilities.Converter(2), v2y - Utilities.Converter(2));
			}
			else {
				parent.text(this.tweetTime + ", May " + Utilities.currentDay, v6x - Utilities.Converter(2), v6y - Utilities.Converter(2));
				parent.textAlign(PConstants.LEFT, PConstants.BOTTOM);
				parent.text("PID : " + this.tweetPid, v2x + Utilities.Converter(2), v2y - Utilities.Converter(2));
			}
		}
	}

}
