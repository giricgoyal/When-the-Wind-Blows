/**
 * class to implement histograph
 */
package main;

import Util.Colors;
import Util.Positions;
import Util.Utilities;


import processing.core.PApplet;
import processing.core.PConstants;
/**
 * @author giric
 *
 */
public class Graph extends BasicControl {
	
	float lowerBound;
	float upperBound;
	String xLabel;
	String yLabel;
	String mainLabel;
	String focusAttribute;
	String defaultFocusAttribute;

	
	String[] dates = {"4/30" , "5/01", "5/02", "5/03" ,"5/04", "5/05", "5/06", "5/07", "5/08", 
						"5/09", "5/10", "5/11", "5/12", "5/13", "5/14", "5/15", "5/16", "5/17", "5/18", "5/19", "5/20"};
	
	CS424_Project4_Group4 program;
	
	
	public Graph(PApplet parent, float x, float y, float width,float height, CS424_Project4_Group4 program) {
		super(parent, x, y, width, height);
		this.program = program;
		// TODO Auto-generated constructor stub
	}
	
	public void setData() {
		int count = 0;
		while(count < Utilities.keywordGraph.size()) {
			program.dataKeywordCount[count] = program.qManager.getKeywordCount(Utilities.keywordGraph.get(count));
			count++;
		}
		setBounds();
	}
	
	public void addData() {
		program.dataKeywordCount[Utilities.keywordGraph.size()-1] = program.currentKeywordCount;
		setBounds();
	}
	
	private float getMax() {
		float max = PConstants.MIN_FLOAT;
		int count = 0;
		int[] tempArray;
		while (count < Utilities.keywordGraph.size()) {
			tempArray = program.dataKeywordCount[count].getCnt();
			for (int i = 0; i < tempArray.length; i++) {
				if (tempArray[i] > max) {
					max = tempArray[i];
				}
			}
			count++;
		}
		System.out.println("max : " + max);
		return max;
	}
	
	private float getMin() {
		float min = PConstants.MAX_FLOAT;
		int count = 0;
		int[] tempArray;
		while (count < Utilities.keywordGraph.size()) {
			tempArray = program.dataKeywordCount[count].getCnt();
			for (int i = 0; i < tempArray.length; i++) {
				if (tempArray[i] < min) {
					min = tempArray[i];
				}
			}
			count++;
		}
		System.out.println("min : " + min);
		return min;
	}
	
	@SuppressWarnings("static-access")
	private void drawDataLines() {
		parent.rectMode(PConstants.CORNER);
		parent.noFill();
		parent.stroke(Colors.light);
		parent.strokeWeight(Utilities.Converter(1));
		parent.rect(Positions.graphWindowX, Positions.graphWindowY, Positions.graphWindowWidth, Positions.graphWindowHeight);
		parent.fill(Colors.dark);
		parent.stroke(Colors.light);
		parent.strokeWeight(Utilities.Converter(1));
		parent.rect(myX, myY, myWidth, myHeight);
		
		
		// Y-axis units
		parent.fill(Colors.WHITE);
		parent.textSize(Utilities.Converter(3.5));
		parent.textAlign(PConstants.RIGHT, PConstants.CENTER);
		if (Utilities.keywordGraph.size() > 0)
			for (int value = (int)this.lowerBound; value <= this.upperBound; value += (this.upperBound)/10) {
				parent.fill(Colors.WHITE);
				float y = parent.map(value, this.lowerBound, this.upperBound,myY + myHeight - Utilities.Converter(1), myY + Utilities.Converter(5));
				parent.textAlign(PConstants.RIGHT, PConstants.CENTER);
				parent.text(value, myX - Utilities.Converter(2), y);
				parent.strokeWeight(Utilities.Converter(0.5));
				parent.stroke(Colors.DARK_GRAY);
				parent.line(myX + Utilities.Converter(1), y, myX + myWidth - Utilities.Converter(1), y);
			}
		parent.noStroke();
		parent.fill(Colors.WHITE);
		parent.text((int)this.upperBound, myX - Utilities.Converter(2), parent.map(this.upperBound, this.lowerBound, this.upperBound,myY + myHeight - Utilities.Converter(1), myY + Utilities.Converter(5)));
		parent.stroke(Colors.DARK_GRAY);
		parent.strokeWeight(Utilities.Converter(0.5));
		parent.line(myX + Utilities.Converter(1), parent.map(this.upperBound, this.lowerBound, this.upperBound,myY + myHeight - Utilities.Converter(1), myY + Utilities.Converter(5)), myX + myWidth - Utilities.Converter(1), parent.map(this.upperBound, this.lowerBound, this.upperBound,myY + myHeight - Utilities.Converter(1), myY + Utilities.Converter(5)));

		
		// Draw Data lines
		int count = 0;
		String tempKeyword;
		int[] tempCountArray;
		while( count < Utilities.keywordGraph.size()) {
			tempCountArray = program.dataKeywordCount[count].getCnt();
			tempKeyword = program.dataKeywordCount[count].getKeyword();
			parent.beginShape();
			//parent.curveVertex(myX  + Utilities.Converter(2), myY + myHeight - Utilities.Converter(1));
			for (int i=0; i<tempCountArray.length; i++) {
				float value = tempCountArray[i];
				//System.out.println(i + " : " + value);
				float x = parent.map(i, 0, tempCountArray.length - 1, myX + Utilities.Converter(1), myX + myWidth - Utilities.Converter(1));
				float y = parent.map(value, this.lowerBound, this.upperBound, myY + myHeight - Utilities.Converter(1), myY + Utilities.Converter(5));
				
				// draw lines
				parent.stroke(Colors.GRAPH_COLORS[count]);
				parent.noFill();
				parent.strokeWeight(Utilities.Converter(1));
				parent.vertex(x, y);
				
				// draw X-axis lines
				parent.stroke(Colors.DARK_GRAY);
				parent.strokeWeight(Utilities.Converter(0.5));
				parent.noFill();
				parent.line(x, myY + Utilities.Converter(1), x, myY + myHeight - Utilities.Converter(1));
				parent.stroke(Colors.GRAPH_COLORS[count]);
				parent.strokeWeight(Utilities.Converter(1));
	
			}
			//parent.curveVertex(myX + myWidth, myY + myHeight - Utilities.Converter(1));
			parent.endShape();
			count++;
		}
		// X-axis units
		for (int i=0; i<21; i++) {
			float x = parent.map(i, 0, 20, myX + Utilities.Converter(1), myX + myWidth - Utilities.Converter(1));
			parent.fill(Colors.WHITE);
			parent.textSize(Utilities.Converter(3.5));
			parent.textAlign(PConstants.CENTER, PConstants.TOP);
			parent.text(dates[i], x, myY + myHeight + Utilities.Converter(2));
		}
		
		// X-axis and Y-axis label
		parent.fill(Colors.WHITE);
		parent.textSize(Utilities.Converter(5));
		parent.textAlign(PConstants.CENTER, PConstants.TOP);
		parent.text("Day" , myX + myWidth/2, myY + myHeight + Utilities.Converter(6));
		parent.textAlign(PConstants.CENTER, PConstants.CENTER);
		parent.text("Tweet\nCount", Positions.graphWindowX + Utilities.Converter(10), myY + myHeight/2);
		
		// if empty print something
		if (Utilities.keywordGraph.isEmpty()) {
			parent.textSize(Utilities.Converter(15));
			parent.textAlign(PConstants.CENTER, PConstants.CENTER);
			parent.fill(Colors.DARK_GRAY);
			parent.text("Add a keyword to begin", Positions.graphX + Positions.graphWidth/2, Positions.graphY + Positions.graphHeight/2);
		}
			
	}
	
	private void drawLegend() {
		int count = 0;
		float x = myX;
		while(count < Utilities.keywordGraph.size()) {
			parent.textSize(Utilities.Converter(5));
			parent.fill(Colors.GRAPH_COLORS[count]);
			parent.textAlign(PConstants.LEFT, PConstants.BOTTOM);
			parent.text(Utilities.keywordGraph.get(count), x, myY - Utilities.Converter(2));
			x += Utilities.keywordGraph.get(count).length() * Utilities.Converter(2.5) + Utilities.Converter(4);
			count++;
		}
	}
	
	public void setBounds(){
		//float min = 0;
		float min = getMin();
		float max = getMax();
		
		this.lowerBound = min;
		this.upperBound = max;
		
		if (max > 0) {
			this.lowerBound = (int)(min - min % 0);
			this.upperBound = (int)(max + 0 - max % 0);
		}
		if (max>10){
			this.lowerBound = (int)(min - min % 10);
			this.upperBound = (int)(max + 10 - max % 10);
		}
		if (max > 100) {
			this.lowerBound = (int)(min - min % 100);
			this.upperBound = (int)(max + 100 - max % 100);
		}
		if (max > 1000) {
			this.lowerBound = (int)(min - min % 1000);
			this.upperBound = (int)(max + 1000 - max % 1000);
		}
		if (max > 10000) {
			this.lowerBound = (int)(min - min % 10000);
			this.upperBound = (int)(max + 10000 - max % 10000);
		}
		if (max > 100000) {
			this.lowerBound = (int)(min - min % 100000);
			this.upperBound = (int)(max + 100000 - max % 100000);
		}
		
	}
	
	@Override
	public void draw() {
		// TODO Auto-generated method stub
		drawDataLines();
		drawLegend();
	}
	
	public void click(float mx, float my) {
		int count = 0;
		float x1 = myX;
		float x2 = myX;
		while(count < Utilities.keywordGraph.size()) {
			x2 += Utilities.keywordGraph.get(count).length() * Utilities.Converter(2.5) + Utilities.Converter(4);
			if (mx > x1  && mx < x2) {
				if (my < myY && my > myY - Utilities.Converter(8)) {
					System.out.println(Utilities.keywordGraph.get(count));
					program.popUp.setPopUp(mx, my, Colors.GRAPH_COLORS[count], Utilities.keywordGraph.get(count));
				}
			}		
			x1 = x2;
			count++;
		}

	}

}
