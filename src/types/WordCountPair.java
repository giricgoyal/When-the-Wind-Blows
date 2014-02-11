package types;

public class WordCountPair {
	private String word;
	private int count;
	
	public WordCountPair(String word) {
		this.word = word;
		count = 1;
	}
	
	public WordCountPair(String word, int count) {
		this.word = word;
		this.count = count;
	}
	
	public String getWord() {
		return word;
	}
	
	public int getCount() {
		return count;
	}
	
	public void countInc() {
		count++;
	}
}
