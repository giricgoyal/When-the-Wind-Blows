/**
 * 
 */
package main;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.Comparator;
import java.util.List;

import javax.swing.text.html.HTMLDocument.HTMLReader.IsindexAction;

import Util.Colors;
import Util.Pos;
import Util.Positions;
import Util.StopWords;
import Util.U;
import Util.Utilities;

import omicronAPI.OmicronAPI;
import processing.core.*;
import db.*;
import types.*;
import markers.AbstractMarker;
import markers.DefaultMarker;
import markers.MarkerType;

/**
 * @author giric
 *
 */

@SuppressWarnings("serial")
public class CS424_Project4_Group4 extends PApplet{
	static public void main(String args[]) {
		System.out.println("premain");
		PApplet.main(new String[] { "main.CS424_Project4_Group4" });
		System.out.println("postmain");
	}
	
	/**
	 * Variable and object declarations
	 */	
	OmicronAPI omicronManager;
	TouchListener touchListener;
	
	// map
	Map map;
	PVector moveDis;
	boolean moved = false; 
	
	// weather panel
	WeatherPanel weatherPanel;
	
	// timeSlider
	TimeSlider timeSlider;
	
	float currentMX, currentMY; // current Mouse X & Y
	
	ArrayList<BasicControl> controls;
	ArrayList<DayButton> dayButtons;
	
	Button zoomInBtn;
	Button zoomOutBtn;
	
	Button add2List;
	Button addEvent2List;
	Button addPerson2List;
	Button trackPerson;
	Button trackButton;
	Button add2Graph;
	Button locationButton;
	Button KeywordList;
	Button PersonList;
	Button EventList;
	
	PopUp popUp;
	
	ListArea listArea;
	
	Keyboard keyboard;
	SuggestionBox sb;
	
	//TweetWindow tw;
	PopUpTweet tw;
	
	Graph graph;
	WordCloud beforeWordCloud;
	
	PlayButton playButton;
	
	// data
	ArrayList<DataPos> dataDay; //all data for current day
	ArrayList<DataPos> dataPos; //all data for current time and current keyword of current day
	ArrayList<DataLocation> dataLocation; // locations
	DataCountPair[] dataCount; //all counts of a keyword for every halfhour
	ArrayList<AbstractMarker> markers; // markers, contain all information
	String[] dataWords; // all keywords (multiple times) in dataPos (i.e. keywords for current time)
	DataCountPair[] dataKeywordCount;
	DataCountPair currentKeywordCount; // words count pair for all days for current keyword
	
	private boolean isTouchingMap = false; // mouse pressing
	
	private int whichLock = U.NEITHER;
	
	QueryManager qManager;
	
	public void initApp() {
		Utilities.CS424_Project4_Group4 = this;
		U.currentWord = "flu";
		
		dataPos = new ArrayList<DataPos>();
		dataDay = new ArrayList<DataPos>();
		dataLocation = new ArrayList<DataLocation>();
		markers = new ArrayList<AbstractMarker>();
		qManager = new QueryManager(this);
		dataDay = qManager.getDataPos_By_Date(U.currentDay);
		setCurrentData(dataPos, dataDay, U.bHalf, U.eHalf, U.currentWord);
		dataCount = qManager.getAllCount_By_Keyword("cs424");
		dataLocation = qManager.getDataLocationAll();
		dataKeywordCount = new DataCountPair[U.graphNumber];
		for (int i=0;i<U.graphNumber;i++) {
			dataKeywordCount[i] = new DataCountPair("",new int[21]);
		}
		currentKeywordCount = qManager.getKeywordCount(U.currentWord);
		/*
		U.currentMaxTweets=0;
		for (int i=0;i<21;i++) {
			if (currentKeywordCount.getCnt()[i]>U.currentMaxTweets) 
				U.currentMaxTweets = currentKeywordCount.getCnt()[i];
		}*/
			
		// begin of components initialization
		map = new Map(this, "map.png", Pos.mapX, Pos.mapY, Pos.mapWidth, Pos.mapHeight);
		map.setup(0, U.mapMaxH/2 - U.mapMaxW/Pos.mapWidth*Pos.mapHeight/2, U.mapMaxW, U.mapMaxH/2 + U.mapMaxW/Pos.mapWidth*Pos.mapHeight/2); // initial range of the map by pixel in original image
		
		weatherPanel = new WeatherPanel(this,
				Pos.weatherPanelX, Pos.weatherPanelY,
				Pos.weatherPanelWidth, Pos.weatherPanelHeight, 
				qManager.getAllWeather());
		
		timeSlider = new TimeSlider(this,
				Pos.timeSliderX, Pos.timeSliderY, Pos.timeSliderWidth, Pos.timeSliderHeight,
				map(U.bHalf, 0, 48, Pos.timeSliderX, Pos.timeSliderX+Pos.timeSliderWidth),
				map(U.eHalf, 0, 48, Pos.timeSliderX, Pos.timeSliderX+Pos.timeSliderWidth),
				Pos.lockWidth, Pos.lockHeight,
				dataCount, U.bHalf, U.eHalf);
		
		// end of components initialization
		
		moveDis = new PVector(0,0);

		setMarkerPos(dataPos,markers,MarkerType.DEFAULT_MARKER); // this must be after map is initialized
		
		Utilities.font = this.loadFont("Helvetica-Bold-100.vlw");
		
		beforeWordCloud = new WordCloud(this, Positions.wordCloudBeforeX, Positions.wordCloudBeforeY, Positions.wordCloudBeforeWidth, Positions.wordCloudBeforeHeight, "KeywordsBefore.txt");
		//afterWordCloud = new WordCloud(this, Positions.wordCloudAfterX, Positions.wordCloudAfterY, Positions.wordCloudAfterWidth, Positions.wordCloudAfterHeight, "KeywordsAfter.txt");
	}

	public void initControls() {
		
		controls = new ArrayList<BasicControl>();
		
		dayButtons = new ArrayList<DayButton>();
		
		for (int i=0;i<21;i++) {
			DayButton dayButton = new DayButton(this, Positions.dayButtonX+i*Pos.dayButtonW, Positions.dayButtonY, Positions.dayButtonW, Positions.dayButtonH, U.totalTweets[i], i);
			//DayButton dayButton = new DayButton(this, Positions.dayButtonX+i*Pos.dayButtonW, Positions.dayButtonY, Positions.dayButtonW, Positions.dayButtonH, currentKeywordCount.getCnt()[i], i);
			dayButton.setName("D"+i);
			if (i == U.currentDay) {
				dayButton.setSelected(true);
			}
			dayButtons.add(dayButton);
		}
		
		zoomInBtn = new Button(this, Positions.zoomInButtonX, Positions.zoomInButtonY, Positions.zoomInButtonW, Positions.zoomInButtonH);
		zoomInBtn.setName("+");
		zoomInBtn.setStroke();
		controls.add(zoomInBtn);
		zoomOutBtn = new Button(this, Positions.zoomOutButtonX, Positions.zoomOutButtonY, Positions.zoomOutButtonW, Positions.zoomOutButtonH);
		zoomOutBtn.setName("-");
		zoomOutBtn.setStroke();
		controls.add(zoomOutBtn);
		
		//tw = new TweetWindow(this, Positions.tweetWindowX, Positions.tweetWindowY, Positions.tweetWindowWidth, Positions.tweetWindowHeight);
		tw = new PopUpTweet(this, 0, 0, 0, 0);
		controls.add(tw);
		//tw.setText("1234 6789 234567891 3456 891234 678912345678912345678912 456 89");
		
		
		locationButton = new Button(this, Positions.locationButtonX, Positions.locationButtonY, Positions.locationButtonWidth, Positions.locationButtonHeight);
		locationButton.setName("Location");
		locationButton.setShowClick();
		locationButton.setStroke();
		controls.add(locationButton);
		
		
		add2Graph = new Button(this, Positions.add2GraphX, Positions.add2GraphY, Positions.add2GraphWidth, Positions.add2GraphHeight);
		add2Graph.setName("Add to Graph");
		add2Graph.setShowClick();
		add2Graph.setStroke();
		controls.add(add2Graph);
		
		add2List = new Button(this, Positions.add2ListX, Positions.add2ListY, Positions.add2ListWidth, Positions.add2ListHeight);
		add2List.setName("Add to List");
		add2List.setShowClick();
		add2List.setStroke();
		controls.add(add2List);
		
		trackPerson = new Button(this, Positions.trackPersonX, Positions.trackPersonY, Positions.trackPersonWidth, Positions.trackPersonHeight);
		trackPerson.setName("Track Person");
		trackPerson.setShowClick();
		trackPerson.setStroke();
		controls.add(trackPerson);
		
		KeywordList = new Button(this, Pos.keywordListX, Pos.keywordListY, Pos.keywordListWidth, Pos.keywordListHeight);
		KeywordList.setName("Keyword List");
		KeywordList.setShowClick();
		KeywordList.setStroke();
		controls.add(KeywordList);
		
		EventList = new Button(this, Positions.eventListX, Positions.eventListY, Positions.eventListWidth, Positions.eventListHeight);
		EventList.setName("Event List");
		EventList.setShowClick();
		EventList.setStroke();
		controls.add(EventList);
		
		PersonList = new Button(this, Positions.personListX, Positions.personListY, Positions.personListWidth, Positions.personListHeight);
		PersonList.setName("Person List");
		PersonList.setShowClick();
		PersonList.setStroke();
		controls.add(PersonList);
		
		
		graph = new Graph(this, Positions.graphX, Positions.graphY, Positions.graphWidth, Positions.graphHeight, this);
		controls.add(graph);
		
		popUp = new PopUp(this, 0, 0, 0, 0, 0, this);
		controls.add(popUp);
		
		
		
		listArea = new ListArea(this, Positions.listWindowX, Positions.listWindowY, Positions.listWindowWidth, Positions.listWindowHeight, this);
		
		
		
		keyboard = new Keyboard(this, Positions.keyboardX, Positions.keyboardY,
				Positions.keyboardWidth, Positions.keyboardHeight);
		controls.add(keyboard);
		
		sb = new SuggestionBox(this, Positions.textBoxX, Positions.textBoxY,
				Positions.textBoxWidth, Positions.textBoxHeight,this);
		controls.add(sb);

		playButton = new PlayButton(this, Pos.playX, Pos.playY, Pos.playW, Pos.playH);
		controls.add(playButton);
	}
	
	public void setup() {
		size((int) Utilities.width, (int) Utilities.height, JAVA2D);
		background(Colors.DARK_GRAY);
		if (Utilities.isWall) {
			initOmicron();
		}
		
		// CREATE SUPPORT VARS
		initApp();
		System.out.println("App setup DONE");
		initControls();
		System.out.println("Control setup DONE");
				
	}
	
	
	public void draw() {
		noStroke();
		
		reDrawbackground();
		map.draw();
		
		// draw markers
		if (U.selectedLocationId == -1 || U.selectedLocationId == 99) {
			for (AbstractMarker m : markers) {
				if (map.checkIn(m.getX(),m.getY())) {
					m.draw();
				}
			}
		}
		// all locations except interstates and river
		else if (U.selectedLocationId == 98) {
			for (AbstractMarker m : markers) {
				if (m.loc < 36 && m.loc >0) {
					if (map.checkIn(m.getX(),m.getY())) {
						m.draw();
					}
				}
			}
		}
		// all interstates
		else if (U.selectedLocationId == 97) {
			for (AbstractMarker m : markers) {
				if (m.loc >= 37 && m.loc <=47) {
					if (map.checkIn(m.getX(),m.getY())) {
						m.draw();
					}
				}
			}
		}
		else {
			for (AbstractMarker m : markers) {
				if (m.loc == U.selectedLocationId) {
					if (map.checkIn(m.getX(),m.getY())) {
						m.draw();
					}
				}
			}
		}
		
		// TODO: draw button - will change
		for (DayButton d : dayButtons) {
			d.draw();
		}
		
		for (BasicControl bc : controls) {
			bc.draw();
		}
		

		if (locationButton.isSelected()){
			listArea.draw();
		}
		
		if (KeywordList.isSelected())
			listArea.draw();
		
		if (EventList.isSelected())
			listArea.draw();
		
		if (PersonList.isSelected())
			listArea.draw();

		// draw weather panel
		weatherPanel.draw(U.currentDay);
		
		// draw time slider
		timeSlider.draw();
		
		// draw word cloud
		beforeWordCloud.draw();
		//afterWordCloud.draw();

		if (U.drawGridLine) {
			pushStyle();
			strokeWeight(U.Converter(0.5));
			stroke(Colors.RED);
			line(0, U.height/3*1, U.width,  U.height/3*1);
			line(0, U.height/3*2, U.width,  U.height/3*2);
			line(U.width/6*1,0,U.width/6*1,U.height);
			line(U.width/6*2,0,U.width/6*2,U.height);
			line(U.width/6*3,0,U.width/6*3,U.height);
			line(U.width/6*4,0,U.width/6*4,U.height);
			line(U.width/6*5,0,U.width/6*5,U.height);
		}
		
		pushStyle();
		textAlign(PConstants.LEFT,PConstants.TOP);
		textSize(Utilities.Converter(7));
		fill(Colors.TEXT_GRAY);
		String location = "";
		if (Utilities.selectedLocationId == -1 || Utilities.selectedLocationId == 99)
			location = "All locations";
		else if (Utilities.selectedLocationId == 97)
			location = "All Interstates";
		else if (Utilities.selectedLocationId == 98)
			location = "All areas";
		else {
			for (int count = 0; count < dataLocation.size(); count++) {
				if (dataLocation.get(count).getId() == Utilities.selectedLocationId) {
					location = dataLocation.get(count).getName();
					break;
				}
			}
		}
		text("Selected\nKeyword: "+U.currentWord + "\nLocation: " + location,Utilities.width*5/6 + Utilities.Converter(5),Utilities.Converter(5));
		popStyle();
		
		
		// PROCESS OMICRON
		if (Utilities.isWall) {
			omicronManager.process();
		}
	}
	
	void reDrawbackground() {
		rectMode(PConstants.CORNER);
		fill(Colors.BACKGROUND_COLOR);
		rect(0,0, Utilities.width * 3 / 6, Utilities.height);
		rect(Utilities.width * 3 / 6, Utilities.height / 3, Utilities.width, Utilities.height);
		rect(Utilities.width * 5/6, 0, Utilities.width, Utilities.height);
	}
	
	// second version - get all data from database when launching
	public void setCurrentData(ArrayList<DataPos> current, ArrayList<DataPos> day, int bHalf, int eHalf, String keyword) {
		current.clear();
		System.out.println("start updating current data");
		for (DataPos data : day) {
			int half = data.getHour()*2 + data.getMin()/30;
			if (bHalf <= half && half < eHalf) {
				/*
				String[] words = split(data.getKeywords(),(' '));
				for (int ii = 0; ii<words.length; ii++) {
					if (keyword.equals(words[ii])) {
						current.add(data);
						break;
					}
				}*/
				current.add(data);
			}
		}
		setTodayWordsToFile(current);
		current.clear();
		for (DataPos data : day) {
			int half = data.getHour()*2 + data.getMin()/30;
			if (bHalf <= half && half < eHalf) {
				String[] words = split(data.getKeywords(),(' '));
				for (int ii = 0; ii<words.length; ii++) {
					if (keyword.equals(words[ii])) {
						current.add(data);
						break;
					}
				}
			}
		}
		System.out.println("done!");
	}
	
	public void setCurrentData_forKeywords(ArrayList<DataPos> current, ArrayList<DataPos> day, int bHalf, int eHalf, String keyword) {
		current.clear();
		System.out.println("start updating current data");
		for (DataPos data : day) {
			int half = data.getHour()*2 + data.getMin()/30;
			if (bHalf <= half && half < eHalf) {
				String[] words = split(data.getKeywords(),(' '));
				for (int ii = 0; ii<words.length; ii++) {
					if (keyword.equals(words[ii])) {
						current.add(data);
						break;
					}
				}
			}
		}
		System.out.println("done!");
	}
	
	public void setMarkerPos(ArrayList<DataPos> dataPos, ArrayList<AbstractMarker> markers, MarkerType type) {
		markers.clear();
		for (DataPos pos : dataPos) {
			float _x = pos.getLongitude();
			float _y = pos.getLatitude();
			float x1Lon = map(map.x1, 0, Utilities.mapMaxW, (float)93.5673, (float)93.1923);
			float x2Lon = map(map.x2, 0, Utilities.mapMaxW, (float)93.5673, (float)93.1923);
			float y1Lat = map(map.y1, 0, Utilities.mapMaxH, (float)42.3017, (float)42.1609);
			float y2Lat = map(map.y2, 0, Utilities.mapMaxH, (float)42.3017, (float)42.1609);
			float x = map(_x, x1Lon, x2Lon, map.x0, map.w);
			float y = map(_y, y1Lat, y2Lat, map.y0, map.h);	
			switch (type) {
			case DEFAULT_MARKER:
				markers.add(new DefaultMarker(this,x,y,pos.getPid(),pos.getHour(),pos.getMin(),pos.getTweet(),pos.getKeywords(),pos.getLocation()));
				break;
			default:
				markers.add(new DefaultMarker(this,x,y,pos.getPid(),pos.getHour(),pos.getMin(),pos.getTweet(),pos.getKeywords(),pos.getLocation()));
			}
		}
	}
	
	/*/ old version -- to get keywords directly from the Tweets
	private String[] setCurrentWords(int bHalf, int eHalf) {
		String str = "";
		System.out.println("setting current words");
		for (DataPos data : dataPos) {
			str = str + data.getTweet()+ " ";
		}
		String reg = "[,\\.\\s;!?]+";
		String[] temp = str.split(reg); // temp contains all words
		for (int i=0;i<temp.length;i++) {
				temp[i] = temp[i].toLowerCase();
		}
		System.out.println("done!");
		return temp;
	}/*/
	
	private void setTodayWordsToFile(ArrayList<DataPos> dataPos) {
		ArrayList<String> str = new ArrayList<String>();
		System.out.println("setting current words");
					// all location
					if (U.selectedLocationId == -1 || U.selectedLocationId == 99) {
						for (DataPos data : dataPos) {
							str.add(data.keywords);
						}
					}
					// all locations except interstates and river
					else if (U.selectedLocationId == 98) {
						for (DataPos data : dataPos) {
							if (data.lid < 36 && data.lid > 0) {
								str.add(data.keywords);
							}
						}
					}
					// all interstates
					else if (U.selectedLocationId == 97) {
						for (DataPos data : dataPos) {
							if (data.lid >= 37 && data.lid <=47) {
								str.add(data.keywords);
							}
						}
					}
					else {
						for (DataPos data : dataPos) {
							if (data.lid == U.selectedLocationId) {
								str.add(data.keywords);
							}
						}
					}
		String[] result = new String[str.size()];
		for (int ii=0;ii<result.length;ii++) {
			result[ii] = str.get(ii);
		}
		System.out.println("start to write!");	
		saveStrings(dataPath(sketchPath + "/data/KeywordsBefore.txt"), result);
		System.out.println("done!");
	}
	
	private ArrayList<WordCountPair> getWordCountPair(String[] words) {
		ArrayList<WordCountPair> entry = new ArrayList<WordCountPair>();
		for (int i=0;i<words.length;i++) {
			boolean exist = false;
			for (WordCountPair e : entry) {
				//boolean flag = false;
				//for (int j=0;j<StopWords.list.length;j++) {
				//	if (StopWords.list[j].equals(words[i])) {
				//		flag = true;
				//		break;
				//	}
				//}
				//if (flag) continue;
				//if (isStopWord(words[i])==true) continue;
				if (words[i].equals(e.getWord())) {
					e.countInc();
					exist = true;
					break;
				}
			}
			if (!exist) {
				entry.add(new WordCountPair(words[i]));
			}
			if (i%1000 == 0)
				System.out.println(i+" ("+(float)i*100/words.length+"%)");
		}
		/*
		Collections.sort(entry, new Comparator<WordCountPair>() {
		    public int compare(WordCountPair a, WordCountPair b) {
		    	if (a.getCount()>b.getCount()) return -1;
		    	else if (a.getCount()<b.getCount()) return 1;
		    	return 0;
		    }
		});*/
		ArrayList<WordCountPair> result = new ArrayList<WordCountPair>();
		for (WordCountPair e : entry) {
			if (!isStopWord(e.getWord()) && e.getCount()>entry.size()*0.01) {
				//System.out.println(e.getWord()+" "+e.getCount());
				result.add(new WordCountPair(e.getWord(),e.getCount()));
			}
		}
		return result;
	}
		
	private boolean isStopWord(String str) {
		return StopWords.check(str);
	}
	
	private void moveMarkers(ArrayList<AbstractMarker> markers, float x, float y) {
		for (AbstractMarker m : markers) {
			m.movePos(x,y);
		}
	}
	
	public void updateDayButton() {
		int max = 0;
		for (int i=0;i<dayButtons.size();i++) {
			if (currentKeywordCount.getCnt()[i] > max) {
				max = currentKeywordCount.getCnt()[i];
			}
		}
		U.currentMaxTweets = max;
		for (int i=0;i<dayButtons.size();i++) {
			dayButtons.get(i).updateCount(currentKeywordCount.getCnt()[i]);
		}
	}
	
	
	// ***********************************
		// --> HERE BEGINS THE MOUSE STUFF <--
		// ***********************************

		// See TouchListener on how to use this function call
		// In this example TouchListener draws a solid ellipse
		// Ths functions here draws a ring around the solid ellipse

		// NOTE: Mouse pressed, dragged, and released events will also trigger these
		// using an ID of -1 and an xWidth and yWidth value of 10.

		// Touch position at last frame

	public void initOmicron() {
		// Creates the OmicronAPI object. This is placed in init() since we want
		// to use fullscreen
		omicronManager = new OmicronAPI(this);

		// Removes the title bar for full screen mode (present mode will not
		// work on Cyber-commons wall)
		omicronManager.setFullscreen(true);

		// Make the connection to the tracker machine
		omicronManager.ConnectToTracker(7001, 7340, "131.193.77.159");
		// Create a listener to get events
		touchListener = new TouchListener();
		touchListener.setThings(this);
		// Register listener with OmicronAPI
		omicronManager.setTouchListener(touchListener);
		System.out.println("Omicron Initiated");
	}

	public boolean isIn(float mx, float my, float bx, float by, float bw,
			float bh) {
		return (bx <= mx && mx <= bx + bw && by <= my && my <= by + bh);
	}
	
	public boolean isInCenter(float _x, float _y, float x, float y, float w, float h) {
		return (_x > x-w/2 && _x < x+w/2 && _y > y-h/2 && _y < y+h/2);
	}
	
	public boolean isIn(float mx, float my, float bx, float by, float bw,
			float bh, float tolerance) {
		return (bx * (1 - tolerance) <= mx && mx <= bx + bw * (1 + tolerance)
				&& by * (1 - tolerance) <= my && my <= by + bh
				* (1 + tolerance));
	}
	
	
	
	int touchID1;
	int touchID2;
	PVector initTouchPos = new PVector();
	PVector initTouchPos2 = new PVector();
	PVector lastTouchPos = new PVector();
	PVector lastTouchPos2 = new PVector();
	int mapDragHack=1;
	
	@SuppressWarnings("rawtypes")
	Hashtable touchList;

	public void myPressed(int id, float mx, float my) {
		if (isTouchingMap == false && whichLock == U.NEITHER) {
			if (isIn(mx,my,Pos.mapX,Pos.mapY,map.w,map.h)) {
				isTouchingMap = true;
				currentMX = mx;
				currentMY = my;
			}
			else if (isInCenter(mx,my,timeSlider.getLeftLockX(),timeSlider.getLockY(),Pos.lockWidth,Pos.lockHeight)) {
				whichLock = U.LEFT;
			}
			else if (isInCenter(mx,my,timeSlider.getRightLockX(),timeSlider.getLockY(),Pos.lockWidth,Pos.lockHeight)) {
				whichLock = U.RIGHT;
			}
		}
	}
	
	public void myDragged(int id, float mx, float my) {
		if (isTouchingMap) {
			moved = map.move(mx,my,currentMX,currentMY);
			if (moved) {
				moveMarkers(markers, mx-currentMX, my-currentMY);
				tw.setCheck(false);
			}
			currentMX = mx;
			currentMY = my;
		}
		else if (whichLock == U.LEFT) {
			int half = round(map(mx, Pos.timeSliderX, Pos.timeSliderX+Pos.timeSliderWidth, 0, 48));
			if (half<0) half = 0;
			if (half>=U.eHalf) half = U.eHalf - 1;
			U.bHalf_temp = half;
			timeSlider.updateLeft(map(half,0,48,Pos.timeSliderX,Pos.timeSliderX+Pos.timeSliderWidth), half);
			tw.setCheck(false);
		}
		else if (whichLock == U.RIGHT) {
			int half = round(map(mx, timeSlider.getX(), timeSlider.getX()+timeSlider.getW(), 0, 48));
			if (half<=U.bHalf) half = U.bHalf + 1;
			if (half>48) half = 48;
			U.eHalf_temp = half;
			timeSlider.updateRight(map(half,0,48,Pos.timeSliderX,Pos.timeSliderX+Pos.timeSliderWidth), half);
			tw.setCheck(false);
		}
	}
	
	public void myReleased(int id, float mx, float my) {
		//touchList.remove(id);
		
		// touch tweet
		if (tw.check) {
			if (tw.checkIn(mx, my)){
				tw.setCheck(false);
			}
		}
		
		// first reset every variable that indicates 'pressing' to original value
		if (isTouchingMap) {
			isTouchingMap = false;
			popUp.setCheck(false);
		}
		
		if (whichLock == U.LEFT) {
			popUp.setCheck(false);
			whichLock = U.NEITHER;
			U.bHalf = U.bHalf_temp;
			setCurrentData(dataPos,dataDay,U.bHalf,U.eHalf,U.currentWord);
			setMarkerPos(dataPos,markers,MarkerType.DEFAULT_MARKER);
			beforeWordCloud.clearArea();
			beforeWordCloud = new WordCloud(this, Positions.wordCloudBeforeX, Positions.wordCloudBeforeY, Positions.wordCloudBeforeWidth, Positions.wordCloudBeforeHeight, "KeywordsBefore.txt");
			tw.setCheck(false);
			return;
		}
		else if (whichLock == U.RIGHT) {
			popUp.setCheck(false);
			whichLock = U.NEITHER;
			U.eHalf = U.eHalf_temp;
			setCurrentData(dataPos,dataDay,U.bHalf,U.eHalf,U.currentWord);
			setMarkerPos(dataPos,markers,MarkerType.DEFAULT_MARKER);
			beforeWordCloud.clearArea();
			beforeWordCloud = new WordCloud(this, Positions.wordCloudBeforeX, Positions.wordCloudBeforeY, Positions.wordCloudBeforeWidth, Positions.wordCloudBeforeHeight, "KeywordsBefore.txt");
			tw.setCheck(false);
			return;
		}
		else if (timeSlider.checkIn(mx,my)) {
			popUp.setCheck(false);
			int half = round(map(mx, Pos.timeSliderX, Pos.timeSliderX+Pos.timeSliderWidth, 0, 48));	
			float disLeft = abs(mx-timeSlider.getLeftLockX());
			float disRight = abs(mx-timeSlider.getRightLockX());
			if (disLeft<disRight) {
				timeSlider.updateLeft(map(half,0,48,Pos.timeSliderX,Pos.timeSliderX+Pos.timeSliderWidth), half);
				U.bHalf = half;
				setCurrentData(dataPos,dataDay,U.bHalf,U.eHalf,U.currentWord);
				setMarkerPos(dataPos,markers,MarkerType.DEFAULT_MARKER);
				beforeWordCloud.clearArea();
				beforeWordCloud = new WordCloud(this, Positions.wordCloudBeforeX, Positions.wordCloudBeforeY, Positions.wordCloudBeforeWidth, Positions.wordCloudBeforeHeight, "KeywordsBefore.txt");
				tw.setCheck(false);
			}
			else if (disRight<disLeft) {
				timeSlider.updateRight(map(half,0,48,Pos.timeSliderX,Pos.timeSliderX+Pos.timeSliderWidth), half);
				U.eHalf = half;
				setCurrentData(dataPos,dataDay,U.bHalf,U.eHalf,U.currentWord);
				setMarkerPos(dataPos,markers,MarkerType.DEFAULT_MARKER);
				beforeWordCloud.clearArea();
				beforeWordCloud = new WordCloud(this, Positions.wordCloudBeforeX, Positions.wordCloudBeforeY, Positions.wordCloudBeforeWidth, Positions.wordCloudBeforeHeight, "KeywordsBefore.txt");
				tw.setCheck(false);
			}
			return;
		}
		
		// then check interfaces
		for (int i=0;i<=20;i++) {
			if ((dayButtons.get(i)).checkIn(mx,my)) {
				popUp.setCheck(false);
				System.out.println("Day "+i+" Clicked");
				dayButtons.get(U.currentDay).setSelected(false);
				U.currentDay = i;
				dayButtons.get(U.currentDay).setSelected(true);
				dataDay = qManager.getDataPos_By_Date(U.currentDay);
				setCurrentData(dataPos,dataDay,U.bHalf,U.eHalf,U.currentWord);
				setMarkerPos(dataPos,markers,MarkerType.DEFAULT_MARKER);
				//Utilities.currentTweet = "";
				//tw.setTweet();
				beforeWordCloud.clearArea();
				beforeWordCloud = new WordCloud(this, Positions.wordCloudBeforeX, Positions.wordCloudBeforeY, Positions.wordCloudBeforeWidth, Positions.wordCloudBeforeHeight, "KeywordsBefore.txt");
				tw.setCheck(false);
				return;
			}
		}
		
		// if clicking keyboard
		if (isIn(mx, my, Positions.keyboardX, Positions.keyboardY,
				Positions.keyboardWidth, Positions.keyboardHeight)) {
			sb.updateTextBox(keyboard.Click(mx, my));
			popUp.setCheck(false);
			KeywordList.setSelected(false);
			EventList.setSelected(false);
			PersonList.setSelected(false);
			locationButton.setSelected(false);
			trackPerson.setSelected(false);
			return;
		}

		// if sb is on
		if (Utilities.suggestionBox){
			if (isIn(mx, my, Positions.suggestionBoxX,
					Positions.suggestionBoxY, Positions.suggestionBoxWidth,
					Positions.suggestionBoxHeight)) {
				popUp.setCheck(false);
				sb.Click(mx, my);
				return;
			}
		}
		// if sb is off
		else if (!Utilities.suggestionBox) {
			
			// Location Button
			if (locationButton.isInRectangle(mx, my)) {
				// toggle
				locationButton.setSelected(!locationButton.isSelected());
				listArea.setSelected(locationButton.isSelected());
				popUp.setCheck(false);
				// Location List On
				if (locationButton.isSelected()){
					System.out.println("Location List On");
					listArea.setButtonSelected("location", Positions.locationButtonX, Positions.locationButtonY, Positions.locationButtonHeight);
					listArea.setLocationData(dataLocation, 0, "null");
					KeywordList.setSelected(false);
					PersonList.setSelected(false);
					EventList.setSelected(false);
					trackPerson.setSelected(false);
					return;
				}
				else {
					System.out.println("Location List Off");
					return;
				}
			}
			
			// Keyword List
			if (KeywordList.isInRectangle(mx, my)) {
				popUp.setCheck(false);
				// toggle
				KeywordList.setSelected(!KeywordList.isSelected());
				listArea.setSelected(KeywordList.isSelected());
				
				// Keyword List On
				if (KeywordList.isSelected()) {
					System.out.println("Keyword List On");
					listArea.setButtonSelected("keyword", Positions.keywordListX, Positions.keywordListY, Positions.keywordListHeight);
					locationButton.setSelected(false);
					PersonList.setSelected(false);
					EventList.setSelected(false);
					trackPerson.setSelected(false);
					return;
				}
				// Keyword List Off
				else {
					System.out.println("Keyword List Off");
				}
			}
			
			// Person List
			if (PersonList.isInRectangle(mx, my)) {
				popUp.setCheck(false);
				// toggle
				PersonList.setSelected(!PersonList.isSelected());
				listArea.setSelected(PersonList.isSelected());
				
				// Person List On
				if (PersonList.isSelected()) {
					System.out.println("Person List On");
					listArea.setButtonSelected("person", Positions.personListX, Positions.personListY, Positions.personListHeight);
					locationButton.setSelected(false);
					KeywordList.setSelected(false);
					EventList.setSelected(false);
					trackPerson.setSelected(false);
					return;
				}
				// Person List Off
				else {
					System.out.println("Person List Off");
				}
					
					
					/*
					if (Utilities.tweetPid != -1) {
						System.out.println("click on add Person 2 List");
						for (int count = 0; count < Utilities.personList.size(); count++) {
							if (Utilities.personList.get(count) == Utilities.tweetPid) {
								add2List.setSelected(!add2List.isSelected());
								return;
							}
						}
						Utilities.personList.add(Utilities.tweetPid);
						System.out.println("Size : "  + Utilities.personList.size());
						add2List.setSelected(!add2List.isSelected());
					}*/
			}
			
			// Event List
			if (EventList.isInRectangle(mx, my)) {
				popUp.setCheck(false);
				// toggle
				EventList.setSelected(!EventList.isSelected());
				listArea.setSelected(EventList.isSelected());
				
				
				// Event List On
				if (EventList.isSelected()) {
					System.out.println("Event List On");
					listArea.setButtonSelected("event", Positions.eventListX, Positions.eventListY, Positions.eventListHeight);
					locationButton.setSelected(false);
					PersonList.setSelected(false);
					KeywordList.setSelected(false);
					trackPerson.setSelected(false);
					return;
				}
				// Event List Off
				else {
					System.out.println("Event List Off");
				}
			}
			
			
			if (listArea.isSelected()){
				if (isIn(mx, my, Positions.listWindowX, Positions.listWindowY, Positions.listWindowWidth, Positions.listWindowHeight)){
					popUp.setCheck(false);
					listArea.click(mx, my);
					if (!listArea.isSelected()) {
						locationButton.setSelected(false);
						KeywordList.setSelected(false);
						EventList.setSelected(false);
						PersonList.setSelected(false);
					}
					return;
				}
			}
			
			if (!listArea.isSelected()) {
				//popUp.setCheck(false);
				graph.click(mx, my);
			}
		}
		
		if (!locationButton.isSelected() && !KeywordList.isSelected() && !EventList.isSelected() && !PersonList.isSelected()) {
			// add to graph
			if (add2Graph.isInRectangle(mx, my)){
				popUp.setCheck(false);
				System.out.println("Click on add to graph");
				for (int count = 0; count < Utilities.keywordGraph.size(); count++) {
					if (Utilities.keywordGraph.get(count).equals(Utilities.currentWord)) {
						return;
					}
				}
				if (Utilities.keywordGraph.size() < Utilities.graphNumber) {
					Utilities.keywordGraph.add(Utilities.currentWord);
					graph.addData();
					System.out.println("Graph Size : "  + Utilities.keywordGraph.size());
					return;
				}
				return;
			}
			
			// track person
			else if (trackPerson.isInRectangle(mx, my)) {
				popUp.setCheck(false);
				trackPerson.setSelected(!trackPerson.isSelected());
				if (trackPerson.isSelected()) {
					System.out.println("Track Person On");
					U.isTrackingPerson = true;
				}
				else {
					System.out.println("Track Person Off");
					U.isTrackingPerson = false;
				}
				return;
			}
						
			
			if (popUp.getCheck()) {
				if (isIn(mx, my, Utilities.popUpX, Utilities.popUpY, Utilities.popUpWidth, Utilities.popUpHeight)) {
					System.out.println("click on popup");
					popUp.click(mx, my);
					
				}
			}
		}
		else {
			// add to list
			if (add2List.isInRectangle(mx, my)) {
				popUp.setCheck(false);
				if (PersonList.isSelected()) {
					System.out.println("Click on add Person to list");
					for (int count = 0; count < Utilities.personList.size(); count++) {
						if (Utilities.personList.get(count).equals(Utilities.tweetPid)) {
							return;
						}
					}
					if (Utilities.personList.size() <= Utilities.listSize) {
						if (U.tweetPid != -1) {
							Utilities.personList.add(Utilities.tweetPid);
							System.out.println("Person List Size : " + Utilities.personList.size());
							return;
						}
						else {
							System.out.println("No Person selected");
						}
					}
				}
				else if (EventList.isSelected()) {
					System.out.println("Click on add Event to list");
					for (int count = 0; count < Utilities.eventList.size(); count++) {
						if (Utilities.eventList.get(count).equals(Utilities.bHalf)) {
							return;
						}
					}
					if (Utilities.eventList.size() <= Utilities.listSize) {
						Utilities.eventList.add(new EventTime(U.currentDay, U.bHalf, U.eHalf));
						System.out.println("Event List Size : " + Utilities.eventList.size());
						return;
					}
				}
				else if (KeywordList.isSelected()) {
					System.out.println("Click on add Keyword to list");
					for (int count = 0; count < Utilities.keywordList.size(); count++) {
						if (Utilities.keywordList.get(count).equals(Utilities.currentWord)) {
							return;
						}
					}
					if (Utilities.keywordList.size() <= Utilities.listSize) {
						Utilities.keywordList.add(Utilities.currentWord);
						System.out.println("Keyword List Size : " + Utilities.keywordList.size());
						return;
					}
				}
				else {
					System.out.println("No List selected");
				}
				return;
			}
		}
		if (zoomInBtn.checkIn(mx,my)) {
			System.out.println("Zoom in Clicked");
			tw.setCheck(false);
			float mW =  map.x2 - map.x1 + 1;
			float mH = map.y2 - map.y1 + 1;
			if (mW > Pos.mapWidth && mW > Pos.mapHeight) {
				mW = mW/4;
				mH = mH/4;
				map.x1 += mW;
				map.x2 -= mW;
				map.y1 += mH;
				map.y2 -= mH;
				mW = mW*2;
				mH = mH*2;
				setMarkerPos(dataPos,markers,MarkerType.DEFAULT_MARKER);
				//updateMarkerPos(markers);
			}
			return;
		}
		
		if (zoomOutBtn.checkIn(mx,my)) {
			System.out.println("Zoom out Clicked");
			tw.setCheck(false);
			float mW = map.x2 - map.x1 + 1;
			float mH = map.y2 - map.y1 + 1;
			if (mW < U.mapMaxW && mW < U.mapMaxH) { 
				mW = mW/2;
				mH = mH/2;
				map.x1 -= mW;
				map.x2 += mW;
				map.y1 -= mH;
				map.y2 += mH;
				
				// we don't want blank
				float offsetX = 0;
				float offsetY = 0;
				if (map.x1<0) {
					offsetX = 0-map.x1;
				}
				else if (map.x2 > U.mapMaxW) {
					offsetX = U.mapMaxW - map.x2;
				}
				if (map.y1<0) {
					offsetY = 0-map.y1;
				}
				else if (map.y2 > U.mapMaxH){
					offsetY = U.mapMaxH-map.y2;
				}
				map.x1 += offsetX;
				map.x2 += offsetX;
				map.y1 += offsetY;
				map.y2 += offsetY;
				// end
				
				mW = mW*4;
				mH = mH*4;
				setMarkerPos(dataPos,markers,MarkerType.DEFAULT_MARKER);
				//updateMarkerPos(markers);
			}
			return;
		}
	
		//FIXME: because we return in every function, maybe 'moved' won't be reset here
		if (!moved) {
			for (AbstractMarker m : markers) {
				float disMin = Float.MAX_VALUE; 
				if (map.checkIn(m.getX(),m.getY())) {
					if (m.checkIn(mx, my) && m.dis(mx,my)<disMin) {
						U.currentTweet = m.getTweet();
						U.tweetTime = (m.getHour() > 9)? 
								((m.getMin()>9)? (m.getHour()+":"+m.getMin()) : (m.getHour()+":0"+m.getMin()) )
								: 
								( (m.getMin()>9)? ("0"+m.getHour()+":"+m.getMin()) : ("0"+m.getHour()+":0"+m.getMin()) );
						U.tweetPid = m.getPid();
						tw.setTweetPopUp(mx, my, Positions.tweetWindowWidth, Positions.tweetWindowHeight);
						tw.setTweet();
					}
				}
			}
			System.out.println("pid: "+U.tweetPid+", Time: "+U.tweetTime+", Text: "+U.currentTweet);
			return;
		}
		else {
			moved = false;
		}
	}
	
	
	public void myClicked(int id, float mx, float my) {
	}

	public void mouseDragged() {
		myDragged(-1, mouseX, mouseY);
	}

	public void mousePressed() {
		//System.out.println("MOUSE PRESSED");
		myPressed(-1, mouseX, mouseY);
	}

	public void mouseClicked() {
		myClicked(-1, mouseX, mouseY);
	}

	public void mouseReleased() {
		// MAP CLICK:
		myReleased(-1, mouseX, mouseY);
	}

	@SuppressWarnings("unchecked")
	public void touchDown(int ID, float xPos, float yPos, float xWidth,
			float yWidth) {
		
		Touch t = new Touch(ID, xPos, yPos, xWidth, yWidth);
		touchList.put(ID,t);
		System.out.println("Added Touch "+ID);
		
		if (touchList.size() == 1) { // If one touch record initial position
			// (for dragging). Saving ID 1 for later
			touchID1 = ID;
			initTouchPos.x = mouseX;
			initTouchPos.y = mouseY;
		} else if (touchList.size() == 2) { 
			// If second touch record initial
			// position (for zooming). Saving ID
			// 2 for later
			touchID2 = ID;
			initTouchPos2.x = mouseX;
			initTouchPos2.y = mouseY;
		}
		
		if (touchList.size() >= 5) {
		
		}
		
		if (ID == touchID1) {
			lastTouchPos.x = mouseX;
			lastTouchPos.y = mouseY;
		} else if (ID == touchID2) {
			lastTouchPos2.x = mouseX;
			lastTouchPos2.y = mouseY;
		}
		
		myPressed(ID, xPos, yPos);
	}

	public void touchMove(int ID, float xPos, float yPos, float xWidth,
			float yWidth) {
		myDragged(ID, xPos, yPos);
	}

	public void touchUp(int ID, float xPos, float yPos, float xWidth,
			float yWidth) {
		touchList.remove(ID);
		System.out.println("Released Touch "+ID);
		myReleased(ID, xPos, yPos);
	}

}
