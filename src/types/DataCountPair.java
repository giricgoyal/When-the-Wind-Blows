/**
 * 
 */
package types;

/**
 * @author joysword
 *
 */
public class DataCountPair {
	private String keyword;
	private int[] count;
	
	public DataCountPair(String k, int[] cnt) {
		keyword = k;
		count = cnt;
	}
	
	public String getKeyword() {
		return keyword;
	}

	public int[] getCnt() {
		return count;
	}
}
