package for_WebCrawler;

public class WordObject implements Comparable<WordObject>{
	
	String word;
	int count;
	
	/** Constructor for WordObject 
	 * @param theWord is a String word
	 * @param theCount is the count
	 */
	public WordObject(String theWord, int theCount) {
		word = theWord;
		count = theCount;
	}
	
	/** getCount function gets the count
	 * @param word 
	 * @return the count associated woth the word
	 */
	public int getCount(WordObject word) {
		return count;
	}
	
	/** getCount function gets the count
	 * @return the count associated woth the word
	 */
	public int getCount() {
		return count;
	}
	
	/** setCount function sets the count of a word object
	 * @param value the value to set
	 */
	public void setCount(int value) {
		count = value;
	}
	
	/** getWord function gets the word 
	 * @return the word
	 */
	public String getWord() {
		return word;
	}
	
	/** setWord function sets the word attribute
	 * @param theWord the desired word
	 */
	public void setWord(String theWord) {
		word = theWord;
	}
		
	/** addCount function updates the count attribute
	 */
	public void addCount() {
		this.count += 1;
	}
	
	/**
	 * overrides the toString method
	 */
	@Override 
	public String toString() {
		return word + ": " + count + " | ";
	}
	
	/** 
	 * compareTo method compares the count of two word objects
	 */
	public int compareTo(WordObject wordsCompare) {
		int compareCount = ((WordObject)wordsCompare).getCount();
		return compareCount - this.count;
	}


	

	
	
}
