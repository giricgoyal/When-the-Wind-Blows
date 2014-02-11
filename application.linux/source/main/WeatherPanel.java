package main;

import Util.Colors;
import Util.Positions;
import Util.U;
import Util.Utilities;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PImage;
import processing.core.PShape;
import types.DataWeather;

/**
 * contains all information, 
 * doesn't need to update, 
 * will show different information according to different days, 
 * which is updated in main class
 * 
 * @author joysword
 * 
 * @since Nov 14
 */

@SuppressWarnings("unused")
public class WeatherPanel {
	
	PApplet parent;
	float x,y,w,h;
	DataWeather[] info;
	
	PImage weather;
	PImage direction;
	
	public WeatherPanel(PApplet _p, float _x, float _y, float _w, float _h, DataWeather[] info) {
		parent = _p;
		x = _x;
		y = _y;
		w = _w;
		h = _h;
		this.info = info;
		//Utilities.rain = parent.loadShape("rain.svg");
		//Utilities.cloudy = parent.loadShape("cloudy.svg");
		//Utilities.showers = parent.loadShape("rain.svg");
		//Utilities.clear = parent.loadShape("clear.svg");
		Utilities.clear = parent.loadImage("clear.png");
		Utilities.cloudy = parent.loadImage("cloudy.png");
		Utilities.rain = parent.loadImage("rain.png");
		Utilities.showers = parent.loadImage("showers.png");
		Utilities.N = parent.loadImage("N.png");
		Utilities.S = parent.loadImage("S.png");
		Utilities.E = parent.loadImage("E.png");
		Utilities.W = parent.loadImage("W.png");
		Utilities.NE = parent.loadImage("NE.png");
		Utilities.NW = parent.loadImage("NW.png");
		Utilities.SE = parent.loadImage("SE.png");
		Utilities.SW = parent.loadImage("SW.png");
		Utilities.NNW = parent.loadImage("NNW.png");
		Utilities.WNW = parent.loadImage("WNW.png");
	}
	
	public void draw(int day) {
		
		parent.pushStyle();
		
		parent.rectMode(PConstants.CORNER);
		parent.fill(Colors.weatherPanel);
		parent.noStroke();
		parent.rect(x,y,w,h);
		
		parent.fill(Colors.white);
		parent.textAlign(PConstants.LEFT, PConstants.TOP);
		parent.textSize(U.Converter(5));
		
		
		if (info[day].getWeather().compareToIgnoreCase("clear") == 0){
			this.weather = Utilities.clear;	
		}
		else if (info[day].getWeather().compareToIgnoreCase("rain")==0){
			this.weather = Utilities.rain;
		}
		else if (info[day].getWeather().compareToIgnoreCase("showers")==0){
			this.weather = Utilities.showers;
		}
		else if (info[day].getWeather().compareToIgnoreCase("cloudy") == 0) {
			this.weather = Utilities.cloudy;
		}
		
		if (info[day].getDirection().equals("E")) {
			this.direction = Utilities.E;
		}
		else if (info[day].getDirection().equals("N")) {
			this.direction = Utilities.N;
		}
		else if (info[day].getDirection().equals("W")) {
			this.direction = Utilities.W;
		}
		else if (info[day].getDirection().equals("NW")) {
			this.direction = Utilities.NW;
		}
		else if (info[day].getDirection().equals("SE")) {
			this.direction = Utilities.SE;
		}
		else if (info[day].getDirection().equals("NNW")) {
			this.direction = Utilities.NNW;
		}
		else { // WNW
			this.direction = Utilities.WNW;
		}

		
		if (weather!=null){
			parent.imageMode(PConstants.CORNER);
			parent.image(weather, x, y, Utilities.Converter(30), Utilities.Converter(30));
		}
		if (direction!=null) {
			parent.imageMode(PConstants.CORNER);
			parent.image(direction, x + Utilities.Converter(35), y + Utilities.Converter(7.5), Utilities.Converter(20), Utilities.Converter(20));
		}
		//parent.text("Weather: "+ info[day].getWeather()+"\nWind Speed: "+ info[day].getSpeed()+" mph\nWind Direction: " + info[day].getDirection(), x+w/20, y+h/15);
		parent.textAlign(PConstants.CENTER, PConstants.CENTER);
		parent.text(info[day].getSpeed() + "\nmph", x + Utilities.Converter(35) + Utilities.Converter(25),y + Positions.weatherPanelHeight/2);
				
		parent.popStyle();
	}
}
