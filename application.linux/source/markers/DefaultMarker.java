/**
 * 
 */
package markers;

import Util.Colors;
import Util.Pos;
import Util.U;
import Util.Utilities;
import processing.core.PApplet;
import processing.core.PConstants;
import markers.AbstractMarker;

/**
 * @author joysword
 *
 */
public class DefaultMarker extends AbstractMarker {

	public DefaultMarker(PApplet p, float x, float y, int pid, int hour, int min, String text, String keywords, int loc) {
		super(p, x, y, pid, hour, min, text, keywords, loc);
	}
	
	@Override
	public void draw() {
		p.pushStyle();
		p.noStroke();
		//float scale = PApplet.map(super.halfhour,U.bHalf,U.eHalf,2,1);
		//p.fill(p.color(scale*p.red(Colors.LIGHT_BLUE),scale*p.green(Colors.LIGHT_BLUE),scale*p.blue(Colors.LIGHT_BLUE)));
		if (U.isTrackingPerson == true && pid == U.tweetPid) {
			p.fill(Colors.LIGHT_ORANGE);
		}
		else {
			p.fill(Colors.LIGHT_BLUE);
		}
		p.ellipseMode(PConstants.CENTER);
		p.ellipse(x, y, Utilities.Converter(5), Utilities.Converter(5));
		p.popStyle();
	}
}