/**
 * 
 */
package types;

/**
 * @author joysword
 *
 */
public class DataWeather {
	private String w;
	private String s;
	private String d;
	
	public DataWeather(String _w, String _s, String _d) {
		w = _w;
		s = _s;
		d = _d;
	}
	
	public void update(String _w, String _s, String _d) {
		w = _w;
		s = _s;
		d = _d;
	}
	
	public String getWeather() {
		return w;
	}
	public String getSpeed() {
		return s;
	}
	public String getDirection() {
		return d;
	}
}
