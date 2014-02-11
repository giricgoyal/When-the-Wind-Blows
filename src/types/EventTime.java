/**
 * 
 */
package types;

/**
 * @author joysword
 *
 */
public class EventTime {
	int day;
	int bHalf;
	int eHalf;
	String name;
	String text;
	
	public EventTime(int day, int bHalf, int eHalf, String name) {
		this.day = day;
		this.bHalf = bHalf;
		this.eHalf = eHalf;
		this.name = name;
		
		if (this.day == 0) {
			text = "4/30  ";
		}
		else if (this.day < 10) {
			text = "5/0" + day; 
		}
		else {
			text = "5/" + day;
		}
		
		text += " ";
		
		if (bHalf/2 < 10) {
			text += "0" + bHalf/2 + ":";
		}
		else {
			text += bHalf/2 + ":";
		}
		if (bHalf%2 == 1) {
			text+="30";
		}
		else {
			text+="00";
		}
		
		text += " - ";
		
		if (eHalf/2 < 10) {
			text += "0" + eHalf/2 + ":";
		}
		else {
			text += eHalf/2 + ":";
		}
		if (eHalf%2 == 1) {
			text+="30";
		}
		else {
			text+="00";
		}
		
		
		text += "\n "+name;
	}
	
	public void updateName(String _text) {
		name = _text;
		
		if (this.day == 0) {
			text = "4/30  ";
		}
		else if (this.day < 10) {
			text = "5/0" + day; 
		}
		else {
			text = "5/" + day;
		}
		
		text += " ";
		
		if (bHalf/2 < 10) {
			text += "0" + bHalf/2 + ":";
		}
		else {
			text += bHalf/2 + ":";
		}
		if (bHalf%2 == 1) {
			text+="30";
		}
		else {
			text+="00";
		}
		
		text += " - ";
		
		if (eHalf/2 < 10) {
			text += "0" + eHalf/2 + ":";
		}
		else {
			text += eHalf/2 + ":";
		}
		if (eHalf%2 == 1) {
			text+="30";
		}
		else {
			text+="00";
		}	
		
		text += "\n "+name;
	}
	
	public String getText() {
		return text;
	}
	
	public int getDay() {
		return day;
	}
	
	public int getbHalf() {
		return bHalf;
	}
	
	public int geteHalf() {
		return eHalf;
	}
}
