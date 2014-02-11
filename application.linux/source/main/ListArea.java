/**
 * 
 */
package main;

import java.util.ArrayList;

import markers.MarkerType;

import Util.Colors;
import Util.Pos;
import Util.Positions;
import Util.U;
import Util.Utilities;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PImage;
import processing.core.PShape;
import types.DataLocation;

/**
 * @author giric
 *
 */
public class ListArea extends BasicControl {
	
	PApplet parent;
	int cid;
	String parentId;
	ArrayList<DataLocation> array;
	String buttonName;
	
	PShape backButton;
	float backButtonHeight;
	float backButtonWidth;
	float backButtonX;
	float backButtonY;
	
	boolean selected;
	
	CS424_Project4_Group4 program;
	
	PImage deleteImage;
	
	//int level;
	
	float v1x, v2x, v3x, v4x, v5x, v6x, v7x;
	float v1y, v2y, v3y, v4y, v5y, v6y, v7y;

	public ListArea(PApplet parent, float x, float y, float width,
			float height, CS424_Project4_Group4 program) {
		super(parent, x, y, width, height);
		// TODO Auto-generated constructor stub
		
		this.parent = parent;
		this.program = program;
		//this.level = 0;
		this.cid = 0;
		this.parentId = "null";
		
		v1x = myX;
		v1y = myY;
		
		v2x = v1x;
		v2y = v1y + myHeight;
		
		v3x = v1x + myWidth;
		v3y = v2y;
		
		v7x = v3x;
		v7y = v1y;
		
		backButton = parent.loadShape("backButton.svg");
		backButtonHeight = backButton.height / Utilities.Converter(3);
		backButtonWidth = backButton.width / Utilities.Converter(3);
		backButtonX = Positions.listWindowX + Positions.listWindowWidth - backButtonWidth - Utilities.Converter(2);
		backButtonY = Positions.listWindowY + Positions.listWindowHeight - Utilities.Converter(1) - backButtonHeight;
		
		deleteImage = parent.loadImage("delete.png");
		
		selected = false;
		
	}
	
	private void drawLocationItems() {
		if (this.selected) {
			parent.stroke(Colors.buttonBorder);
			parent.strokeWeight(Utilities.Converter(1));
			parent.fill(Colors.buttonSelectedColor);
			parent.beginShape();
				parent.vertex(v1x, v1y);
				parent.vertex(v2x, v2y);
				parent.vertex(v3x, v3y);
				parent.vertex(v4x, v4y);
				parent.vertex(v5x, v5y);
				parent.vertex(v6x, v6y);
				parent.vertex(v7x, v7y);
			parent.endShape(PConstants.CLOSE);
			
			parent.shape(backButton, backButtonX, backButtonY, backButtonWidth, backButtonHeight);
			
		
			int count = 0;
			parent.textAlign(PConstants.LEFT, PConstants.CENTER);
			parent.textSize(Utilities.Converter(5));
			parent.fill(Colors.black);
			if (this.parentId.compareToIgnoreCase("null") == 0) {
				if (this.cid == 0){
					
					parent.text("All Locations", myX + Utilities.Converter(5), myY + Utilities.Converter(5));
					parent.text("Area", myX + Utilities.Converter(5), myY + Utilities.Converter(15));
					parent.text("Interstates", myX + Utilities.Converter(5), myY + Utilities.Converter(25));
					parent.text("Vast River", myX + Utilities.Converter(5), myY + Utilities.Converter(35));
				}
				else {
					int matchCount = 1;
					if (this.cid == 1)
						parent.text("All areas", myX + Utilities.Converter(5), myY + Utilities.Converter(4));
					else if (this.cid == 6)
						parent.text("All Interstates", myX + Utilities.Converter(5), myY + Utilities.Converter(4));
					while(count< this.array.size()){
						if (this.array.get(count).getCid() == this.cid){
							parent.text(this.array.get(count).getName(), myX + Utilities.Converter(5), myY + Utilities.Converter(4 * (2*matchCount + 1)));
							matchCount++;
						}
						count++;
					}
					
				}
			}
			else {
				int matchCount = 1;
				
				while(count< this.array.size()){
					if (this.array.get(count).getId() == Integer.valueOf(this.parentId)){
						parent.text(this.array.get(count).getName(), Positions.listWindowX + Utilities.Converter(5), Positions.listWindowY + Utilities.Converter(4));
					}
					if (this.array.get(count).getParentId().compareToIgnoreCase(this.parentId) == 0){
						parent.text(this.array.get(count).getName(), Positions.listWindowX + Utilities.Converter(5), Positions.listWindowY + Utilities.Converter(4 * (2*matchCount + 1)));
						matchCount++;
					}
					count++;
				}
			}
		}
	}
	
	private void drawKeywordItems() {
		if (this.selected) {
			parent.stroke(Colors.buttonBorder);
			parent.strokeWeight(Utilities.Converter(1));
			parent.fill(Colors.buttonSelectedColor);
			parent.beginShape();
				parent.vertex(v1x, v1y);
				parent.vertex(v2x, v2y);
				parent.vertex(v3x, v3y);
				parent.vertex(v4x, v4y);
				parent.vertex(v5x, v5y);
				parent.vertex(v6x, v6y);
				parent.vertex(v7x, v7y);
			parent.endShape(PConstants.CLOSE);
			
			parent.shape(backButton, backButtonX, backButtonY, backButtonWidth, backButtonHeight);
			
			parent.textAlign(PConstants.LEFT, PConstants.CENTER);
			parent.textSize(Utilities.Converter(5));
			parent.fill(Colors.black);
			
			int count = 0;
			while (count < Utilities.keywordList.size()) {
				parent.text(Utilities.keywordList.get(count), Positions.listWindowX + Utilities.Converter(5), Positions.listWindowY + Utilities.Converter(4 * (2*count + 1)));
				parent.imageMode(PConstants.CENTER);
				parent.image(deleteImage, Positions.listWindowX + Positions.listWindowWidth - Utilities.Converter(5), Positions.listWindowY + Utilities.Converter(4 * (2*count + 1)), Utilities.Converter(6), Utilities.Converter(6));
				//parent.text("x", Positions.listWindowX + Positions.listWindowWidth - Utilities.Converter(5), Positions.listWindowY + Utilities.Converter(4 * (2*count + 1)));
				count++;
			}
			
		}
	}
	
	private void drawEventItems() {
		if (this.selected) {
			parent.stroke(Colors.buttonBorder);
			parent.strokeWeight(Utilities.Converter(1));
			parent.fill(Colors.buttonSelectedColor);
			parent.beginShape();
				parent.vertex(v1x, v1y);
				parent.vertex(v2x, v2y);
				parent.vertex(v3x, v3y);
				parent.vertex(v4x, v4y);
				parent.vertex(v5x, v5y);
				parent.vertex(v6x, v6y);
				parent.vertex(v7x, v7y);
			parent.endShape(PConstants.CLOSE);
			
			parent.shape(backButton, backButtonX, backButtonY, backButtonWidth, backButtonHeight);
			
			parent.textAlign(PConstants.LEFT, PConstants.CENTER);
			parent.textSize(Utilities.Converter(5));
			parent.fill(Colors.black);
			
			int count = 0;
			while (count < Utilities.eventList.size()) {
				parent.text(Utilities.eventList.get(count).text, Positions.listWindowX + Utilities.Converter(5), Positions.listWindowY + Utilities.Converter(4 * (2*count + 1)));
				parent.imageMode(PConstants.CENTER);
				parent.image(deleteImage, Positions.listWindowX + Positions.listWindowWidth - Utilities.Converter(5), Positions.listWindowY + Utilities.Converter(4 * (2*count + 1)), Utilities.Converter(6), Utilities.Converter(6));
				//parent.text("x", Positions.listWindowX + Positions.listWindowWidth - Utilities.Converter(5), Positions.listWindowY + Utilities.Converter(4 * (2*count + 1)));
				count++;
			}
			
		}
	}
	
	private void drawPersonItems() {
		if (this.selected) {
			parent.stroke(Colors.buttonBorder);
			parent.strokeWeight(Utilities.Converter(1));
			parent.fill(Colors.buttonSelectedColor);
			parent.beginShape();
				parent.vertex(v1x, v1y);
				parent.vertex(v2x, v2y);
				parent.vertex(v3x, v3y);
				parent.vertex(v4x, v4y);
				parent.vertex(v5x, v5y);
				parent.vertex(v6x, v6y);
				parent.vertex(v7x, v7y);
			parent.endShape(PConstants.CLOSE);
			
			parent.shape(backButton, backButtonX, backButtonY, backButtonWidth, backButtonHeight);
			
			parent.textAlign(PConstants.LEFT, PConstants.CENTER);
			parent.textSize(Utilities.Converter(5));
			parent.fill(Colors.black);
			
			int count = 0;
			while (count < Utilities.personList.size()) {
				parent.text("pid: "+Utilities.personList.get(count), Positions.listWindowX + Utilities.Converter(5), Positions.listWindowY + Utilities.Converter(4 * (2*count + 1)));
				parent.imageMode(PConstants.CENTER);
				parent.image(deleteImage, Positions.listWindowX + Positions.listWindowWidth - Utilities.Converter(5), Positions.listWindowY + Utilities.Converter(4 * (2*count + 1)), Utilities.Converter(6), Utilities.Converter(6));
				//parent.text("x", Positions.listWindowX + Positions.listWindowWidth - Utilities.Converter(5), Positions.listWindowY + Utilities.Converter(4 * (2*count + 1)));
				count++;
			}
			
		}
	}

	@Override
	public void draw() {
		if (this.buttonName.compareTo("location") == 0)
			this.drawLocationItems();
		else if (this.buttonName.compareTo("keyword") == 0) 
			this.drawKeywordItems();
		else if (this.buttonName.compareTo("event") == 0)
			this.drawEventItems();
		else if (this.buttonName.compareTo("person") == 0)
			this.drawPersonItems();
	}
	
	private void clickLocation(float mx, float my) {
		if (mx > backButtonX && mx < backButtonX + backButtonWidth && my > backButtonY && my < backButtonY + backButtonHeight){
			
			if (this.parentId.compareTo("null") == 0) {
				if (this.cid == 0) {
					this.selected = false;
				}
				else if (this.cid == 1 || this.cid == 6) {
					this.cid = 0;
				}
			}
			else {
				this.cid = 1;
				this.parentId = "null";
			}
		}
		else {
			if (this.parentId.compareToIgnoreCase("null") == 0){
				if (this.cid == 0) {
					if (mx > myX && my > myY && mx < myX + myWidth && my < myY + Utilities.Converter(10)){
						Utilities.selectedLocationId = 99;
						//program.setTodayWordsToFile(program.dataPos);
						program.setCurrentData(program.dataPos, program.dataDay, U.bHalf, U.eHalf, U.currentWord);
						System.out.println("All locations Selected" + " : " + Utilities.selectedLocationId);
						this.selected = false;
						program.beforeWordCloud.clearArea();
						program.beforeWordCloud = new WordCloud(program, Positions.wordCloudBeforeX, Positions.wordCloudBeforeY, Positions.wordCloudBeforeWidth, Positions.wordCloudBeforeHeight, "KeywordsBefore.txt");
					}
					if (mx > myX && my > myY + Utilities.Converter(10) && mx < myX + myWidth && my < myY + Utilities.Converter(20))
						this.cid = 1;
					if (mx > myX && my > myY + Utilities.Converter(20) && mx < myX + myWidth && my < myY + Utilities.Converter(30))
						this.cid = 6;
					if (mx > myX && my > myY + Utilities.Converter(30) && mx < myX + myWidth && my < myY + Utilities.Converter(40)) {
						Utilities.selectedLocationId = 36;
						//program.setTodayWordsToFile(program.dataPos);
						program.setCurrentData(program.dataPos, program.dataDay, U.bHalf, U.eHalf, U.currentWord);
						System.out.println("VAst LAke selected" + " : " + Utilities.selectedLocationId);
						this.selected = false;
						program.beforeWordCloud.clearArea();
						program.beforeWordCloud = new WordCloud(program, Positions.wordCloudBeforeX, Positions.wordCloudBeforeY, Positions.wordCloudBeforeWidth, Positions.wordCloudBeforeHeight, "KeywordsBefore.txt");
					}
				}
				else if (this.cid == 6) {
					int count = 0;
					int matchCount = 1;
					if (mx >  myX && mx < myX + myWidth) {
						if (my > myY && my < myY + Utilities.Converter(8)){
							Utilities.selectedLocationId = 97;
							//program.setTodayWordsToFile(program.dataPos);
							program.setCurrentData(program.dataPos, program.dataDay, U.bHalf, U.eHalf, U.currentWord);
							System.out.println("All interstates selected" + " : " + Utilities.selectedLocationId);
							this.selected = false;
							program.beforeWordCloud.clearArea();
							program.beforeWordCloud = new WordCloud(program, Positions.wordCloudBeforeX, Positions.wordCloudBeforeY, Positions.wordCloudBeforeWidth, Positions.wordCloudBeforeHeight, "KeywordsBefore.txt");
						}
					}
					while(count< this.array.size()){
						if (this.array.get(count).getCid() == this.cid){
							if (mx > myX && mx < myX + myWidth) {
								if (my > myY + Utilities.Converter(8 * matchCount) && my < myY + Utilities.Converter(8 * (matchCount+1))) {
									Utilities.selectedLocationId = this.array.get(count).getId();
									//program.setTodayWordsToFile(program.dataPos);
									program.setCurrentData(program.dataPos, program.dataDay, U.bHalf, U.eHalf, U.currentWord);
									System.out.println(this.array.get(count).getName() + " : " + Utilities.selectedLocationId);
									this.selected = false;
									program.beforeWordCloud.clearArea();
									program.beforeWordCloud = new WordCloud(program, Positions.wordCloudBeforeX, Positions.wordCloudBeforeY, Positions.wordCloudBeforeWidth, Positions.wordCloudBeforeHeight, "KeywordsBefore.txt");
								}
							}
							matchCount++;
						}
						count++;
					}
				}
				else if (this.cid == 1) {
					int count = 0;
					int matchCount = 1;
					if (mx >  myX && mx < myX + myWidth) {
						if (my > myY && my < myY + Utilities.Converter(8)){
							Utilities.selectedLocationId = 98;
							//program.setTodayWordsToFile(program.dataPos);
							program.setCurrentData(program.dataPos, program.dataDay, U.bHalf, U.eHalf, U.currentWord);
							System.out.println("All areas except interstates and the vast river selected" + " : " + Utilities.selectedLocationId);
							this.selected = false;
							program.beforeWordCloud.clearArea();
							program.beforeWordCloud = new WordCloud(program, Positions.wordCloudBeforeX, Positions.wordCloudBeforeY, Positions.wordCloudBeforeWidth, Positions.wordCloudBeforeHeight, "KeywordsBefore.txt");
						}
					}
					while(count< this.array.size()){
						if (this.array.get(count).getCid() == this.cid){
							if (mx > myX && mx < myX + myWidth) {
								if (my > myY + Utilities.Converter(8 * matchCount) && my < myY + Utilities.Converter(8 * (matchCount+1))) {
									this.parentId = String.valueOf(this.array.get(count).getId());
								}
							}
							matchCount++;
						}
						count++;
					}
				}
			}
			else if (this.parentId.compareToIgnoreCase("null")!=0) {
				int count = 0;
				int matchCount = 1;
				while(count < this.array.size()){
					if (this.array.get(count).getId() == Integer.valueOf(this.parentId)) {
						if (mx > myX && mx < myX + myWidth) {
							if (my > myY && my < myY + Utilities.Converter(8)){
								Utilities.selectedLocationId = this.array.get(count).getId();
								//program.setTodayWordsToFile(program.dataPos);
								program.setCurrentData(program.dataPos, program.dataDay, U.bHalf, U.eHalf, U.currentWord);
								System.out.println(this.array.get(count).getName() + " : " + Utilities.selectedLocationId);
								this.selected = false;
								program.beforeWordCloud.clearArea();
								program.beforeWordCloud = new WordCloud(program, Positions.wordCloudBeforeX, Positions.wordCloudBeforeY, Positions.wordCloudBeforeWidth, Positions.wordCloudBeforeHeight, "KeywordsBefore.txt");
							}
						}
					}
					if (this.array.get(count).getParentId().compareToIgnoreCase(this.parentId) == 0){
						if (mx > myX && mx < myX + myWidth) {
							if (my > myY + Utilities.Converter(8 * matchCount) && my < myY + Utilities.Converter(8 * (matchCount+1))) {
								Utilities.selectedLocationId = this.array.get(count).getId();
								//program.setTodayWordsToFile(program.dataPos);
								program.setCurrentData(program.dataPos, program.dataDay, U.bHalf, U.eHalf, U.currentWord);
								System.out.println(this.array.get(count).getName() + " : " + Utilities.selectedLocationId);
								this.selected = false;
								program.beforeWordCloud.clearArea();
								program.beforeWordCloud = new WordCloud(program, Positions.wordCloudBeforeX, Positions.wordCloudBeforeY, Positions.wordCloudBeforeWidth, Positions.wordCloudBeforeHeight, "KeywordsBefore.txt");
							}
						}
						matchCount++;
					}
					count++;
				}
			}
		}

	}
	
	private void clickKeyword(float mx, float my) {
		if (mx > backButtonX && mx < backButtonX + backButtonWidth && my > backButtonY && my < backButtonY + backButtonHeight){
			this.selected = false;
		}
		else {
			for (int count = 0; count < Utilities.keywordList.size(); count++) {
				if (mx > myX && mx < myX + myWidth - Utilities.Converter(9)) {
					if (my > myY + Utilities.Converter(8 * count) && my < myY + Utilities.Converter(8 * (count+1))) {
						
						// same update as in Suggestion Box
						Utilities.currentWord = Utilities.keywordList.get(count);
						this.selected = false;
						program.setCurrentData_forKeywords(program.dataPos,program.dataDay,U.bHalf,U.eHalf,U.currentWord);
						program.setMarkerPos(program.dataPos,program.markers,MarkerType.DEFAULT_MARKER);
						program.currentKeywordCount = program.qManager.getKeywordCount(Utilities.currentWord);
						program.updateDayButton();
						program.dataCount = program.qManager.getAllCount_By_Keyword(Utilities.currentWord);
						program.timeSlider.update(program.dataCount);
						break;
					}
				}
				if (mx < myX + myWidth && mx > myX + myWidth - Utilities.Converter(9)) {
					if (my > myY + Utilities.Converter(8 * count) && my < myY + Utilities.Converter(8 * (count+1))) {
						Utilities.keywordList.remove(count);
						
					}
				}
			}
		}		
	}
	
	private void clickEvent(float mx, float my) {
		if (mx > backButtonX && mx < backButtonX + backButtonWidth && my > backButtonY && my < backButtonY + backButtonHeight){
			this.selected = false;
		}
		else {
			for (int count = 0; count < Utilities.eventList.size(); count++) {
				if (mx > myX && mx < myX + myWidth  - Utilities.Converter(9)) {
					if (my > myY + Utilities.Converter(8 * count) && my < myY + Utilities.Converter(8 * (count+1))) {
						
						// same update as in myReleased
						this.selected = false;
						U.bHalf = Utilities.eventList.get(count).bHalf;
						U.eHalf = Utilities.eventList.get(count).eHalf;
						// different day
						if (Utilities.eventList.get(count).day !=  Utilities.currentDay) {
							program.dayButtons.get(U.currentDay).setSelected(false);
							U.currentDay = U.eventList.get(count).day;
							program.dayButtons.get(U.currentDay).setSelected(true);
							program.dataDay = program.qManager.getDataPos_By_Date(U.currentDay);
						}
						program.timeSlider.updateLeft(PApplet.map(U.bHalf,0,48,Pos.timeSliderX,Pos.timeSliderX+Pos.timeSliderWidth), U.bHalf);
						program.timeSlider.updateRight(PApplet.map(U.eHalf,0,48,Pos.timeSliderX,Pos.timeSliderX+Pos.timeSliderWidth), U.eHalf);
						program.setCurrentData(program.dataPos,program.dataDay,U.bHalf,U.eHalf,U.currentWord);
						program.setMarkerPos(program.dataPos,program.markers,MarkerType.DEFAULT_MARKER);
						program.beforeWordCloud.clearArea();
						program.beforeWordCloud = new WordCloud(program, Positions.wordCloudBeforeX, Positions.wordCloudBeforeY, Positions.wordCloudBeforeWidth, Positions.wordCloudBeforeHeight, "KeywordsBefore.txt");
						break;
					}
				}
				if (mx < myX + myWidth && mx > myX + myWidth - Utilities.Converter(9)) {
					if (my > myY + Utilities.Converter(8 * count) && my < myY + Utilities.Converter(8 * (count+1))) {
						Utilities.eventList.remove(count);
						
					}
				}
			}
		}
	}
	
	private void clickPerson(float mx, float my) {
		
		if (mx > backButtonX && mx < backButtonX + backButtonWidth && my > backButtonY && my < backButtonY + backButtonHeight){
			this.selected = false;
		}
		else {
			int count = 0;
			while(count < Utilities.personList.size()){
				if (mx > myX && mx < myX + myWidth - Utilities.Converter(9)) {
					if (my > myY + Utilities.Converter(8 * count) && my < myY + Utilities.Converter(8 * (count+1))) {
						Utilities.tweetPid = Utilities.personList.get(count);
						this.selected = false;
						
						//program.setCurrentData_forKeywords(program.dataPos,program.dataDay,U.bHalf,U.eHalf,U.currentWord);
						//program.setMarkerPos(program.dataPos,program.markers,MarkerType.DEFAULT_MARKER);
						
						System.out.println(Utilities.personList.get(count));
					}
				}
				if (mx < myX + myWidth && mx > myX + myWidth - Utilities.Converter(9)) {
					if (my > myY + Utilities.Converter(8 * count) && my < myY + Utilities.Converter(8 * (count+1))) {
						Utilities.personList.remove(count);
						
					}
				}
				
				count++;
			}
		}		
	}
	
	public void click(float mx, float my){
		if (this.buttonName.compareTo("location") == 0) 
			this.clickLocation(mx, my);
		else if (this.buttonName.compareTo("keyword") == 0)
			this.clickKeyword(mx, my);
		else if (this.buttonName.compareTo("event") == 0)
			this.clickEvent(mx, my);
		else if (this.buttonName.compareTo("person") == 0)
			this.clickPerson(mx, my);
	}

	public void setButtonSelected(String button, float buttonX, float buttonY, float buttonHeight) {

		this.buttonName = button;
		v5x = buttonX;
		v5y = buttonY;
		
		v4x = v3x;
		v4y = v5y + Utilities.Converter(13);
		
		v6x = v3x;
		v6y = v5y + Utilities.Converter(3);
	}
	
	public boolean isSelected() {
		return selected;
	}
	
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	
	
	public void setLocationData(ArrayList<DataLocation> array, int cid, String parentId){
		this.array = array;
		this.cid = cid;
		this.parentId = parentId;
	}
}
