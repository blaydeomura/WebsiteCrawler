package for_WebCrawler;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import java.util.Properties;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;

public class Driver{

	
	/** initializes wordsToAvoid array
	 */
	String[] wordsToAvoid;
	
	/** constructor for Driver Class.
	 * immediately assigns wordsToAvoid
	 */
	public Driver() {
		wordsToAvoid = getWordsToAvoid();
		
	}
	
	/** getWordsToAvoid function reads our property file and creates an arr of words to avoid
	 * @return an arr of words to avoid
	 */
	public String[] getWordsToAvoid() { 
		Properties p = new Properties();
		try {
			p.load(new FileInputStream("textanalyzer.properties"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String propertyFile = p.getProperty("avoid");
		String[] avoidArr = propertyFile.split(",");
		return avoidArr;
	}
	
	/** getWebpageContent reads a URL and creates a LinkedList of all the webpage contents
	 * @param url to read from
	 * @return linked list of all the words from the URL
	 * @throws IOException
	 */
	public LinkedList<String> getWebpageContent(String url) throws IOException {
		Connection conn = Jsoup.connect(url);
		Document doc = conn.get();
		String result = doc.body().text().toLowerCase(); //toLower  
		String[] sepWords = result.split("[ ,?.!\"()]+");
		LinkedList<String> allWords = new LinkedList<String>();
		Collections.addAll(allWords, sepWords);
		return allWords;
	}
	
	/** finalList function takes out all of the words to avoid (property file) from LinkedList of all words
	 * @param allWords linkedList that contain the webpage contents
	 * @return linked list of all the words excluding the words to avoid
	 */
	public LinkedList<String> finalList(LinkedList<String> allWords) {
		for (Iterator<String> iter = allWords.iterator(); iter.hasNext();) {
			String word = iter.next();
			for (int k = 0; k < wordsToAvoid.length; k++) {    
				if (word.equals(wordsToAvoid[k])) {
					iter.remove();
				}
			}
		}
		return allWords;
	}
	
	/** avoidList function creates an ArrayList of all the words to avoid
	 * @param allWords is a linked list of all the words from webpage 
	 * @return an array list of all the avoided words 
	 */
	public ArrayList<String> avoidList(LinkedList<String> allWords) {
		ArrayList<String> countAvoidWordsList = new ArrayList<String>();
		for (Iterator<String> iter = allWords.iterator(); iter.hasNext();) {
			String word = iter.next();
			for (int k = 0; k < wordsToAvoid.length; k++) {
				if (word.equals(wordsToAvoid[k])) {
					countAvoidWordsList.add(word);
				}
			}
		}
		return countAvoidWordsList;
	}
	
	/** sortLinkedList sorts a linked list alphabetically 
	 * @param finalList takes in a linked list that excludes the words to avoid
	 * @return a sorted linked list alphabetically
	 */
	public LinkedList<String> sortLinkedList(LinkedList<String> finalList) {
		Collections.sort(finalList);
		return finalList;
	}
	
	/** getMostCommonWords sorts a linked list and creates an array list of WordObjects where a count is also given
	 * @param allWords a list of all the words excluding the words to avoid
	 * @return an array list of word objects and their counts
	 */
	public ArrayList<WordObject> getMostCommonWords(LinkedList<String> allWords) {
		sortLinkedList(allWords);														
		ArrayList<WordObject> countWordsList = new ArrayList<WordObject>();			
		Set<String> lookup = new HashSet<>();											
		
		for (int i = 0; i < allWords.size(); i++) {										
			String name = allWords.get(i);												
			if (lookup.add(name)) {													
				countWordsList.add(new WordObject(name, 1));								
			} else {																    
				for(int j = 0; j < countWordsList.size(); j++) {						
					if (countWordsList.get(j).word.equals(name)) {							
						countWordsList.get(j).addCount();									
					}
				}
			}

		}
		return countWordsList;
	}
	
	/** tenMostCommon function finds the ten most common words in the webpage
	 * @param wordList is an array list WordObjects that excludes words to avoid
	 * prints out the top 10 most common words and their count
	 */
	public void tenMostCommon(ArrayList<WordObject> wordList) {
		Collections.sort(wordList);
		System.out.print("Top 10 words: ");
		for (int i = 0; i < 10; i++) {
			System.out.print(wordList.get(i));
		}
		System.out.println();
	}
	
	/** hundredMostCommon function finds the hundred most common words in the webpage
	 * @param wordList is an array list WordObjects that excludes words to avoid
	 * prints out the top 100 most common words and their count
	 */
	public void hundredMostCommon(ArrayList<WordObject> wordList) {
		Collections.sort(wordList);
		System.out.print("Top 100 words: ");
		for (int i = 0; i < 100; i++) {
			System.out.print(wordList.get(i));
		}
		System.out.println();
	}

	/** getShortest function finds the shortest word in the webpage
	 * @param wordsList takes in an ArrayList of WordObjects that contains words from the web page
	 * prints out the shortest word and how many times it appears
	 */
	public void getShortest(ArrayList<WordObject> wordsList) {
		Collections.sort(wordsList, new Comparator<WordObject>() {
			public int compare(WordObject w1, WordObject w2) {
				return w1.getWord().length() - w2.getWord().length();
			}
		});
		System.out.println("Shortest: " + wordsList.get(0).getWord() + ": " + wordsList.get(0).getCount());
	}
	
	/** getLongest function finds the longet word in the webpage
	 * @param wordsList takes in an ArrayList of WordObjects that contains words from the web page
	 * prints out the longest word and how many times it appears
	 */
	public void getLongest(ArrayList<WordObject> wordsList) {
		Collections.sort(wordsList, new Comparator<WordObject>() {
			public int compare(WordObject w1, WordObject w2) {
				return w2.getWord().length() - w1.getWord().length();
			}
		});
		System.out.println("Longest: " + wordsList.get(0).getWord() + ": " + wordsList.get(0).getCount());
	}

	/** getSummary function prints the summary of word count and characters in web page
	 * @param wordsList takes in an ArrayList of WordObjects that contains words from the web page
	 * prints out the number of words and characters in web page
	 */
	public void getSummary(ArrayList<WordObject> wordList) {
		int sum = 0;
		for (int i = 0; i < wordList.size(); i++) {
			sum += wordList.get(i).getCount();
		}
		System.out.println("Total Words: " + wordList.size() + " | Total Letters: " + sum);
	}
	
	public static void main(String[] args) {
		Driver d = new Driver();
		try {
			d.getWebpageContent(args[0]);
			
			//LinkedList of ALL words (essential for program to work)
			LinkedList<String> allWordsList = d.getWebpageContent(args[0]);
			
			//Array List of all avoided words
			ArrayList<String> avoidedWordsList = d.avoidList(allWordsList);
			
			//LinkedList without avoided words
			LinkedList<String> finalWordsList = d.finalList(allWordsList);
			
			//ArrayList of words and their count
			ArrayList<WordObject> wordsAndCountList = d.getMostCommonWords(finalWordsList);
				
			System.out.println("Welcome to Text Analyzer program!");
			
			System.out.println("Please order your commands from these options: ");
			System.out.println(" '1': Top 10 words \n '2': Top 100 words \n '3': Longest word \n '4': Shortest word \n '5': Summary");
			System.out.println("Your input must be a numerical value from 1-5 that is seperated by a space.");
			System.out.println("Any input that fails these requirements will not produce an output...");
			Scanner scan = new Scanner(System.in);
			String userInput = scan.nextLine();
			String input[] = userInput.split(" ");
			
			Queue<String> q = new LinkedList<>();
			for (int i = 0; i < input.length; i++) {
				if (Integer.parseInt(input[i]) <= 5 && Integer.parseInt(input[i]) >= 0)
					q.add(input[i]);
			}
			
			System.out.println();
			System.out.println("Top10 Longest Shortest Summary:");
			
			for (String s : q) {
				if (s.equals("1")) 
					d.tenMostCommon(wordsAndCountList);
				if (s.equals("2"))
					d.hundredMostCommon(wordsAndCountList);
				if (s.equals("3"))
					d.getLongest(wordsAndCountList);
				if (s.equals("4"))
					d.getShortest(wordsAndCountList);
				if (s.equals("5"))
					d.getSummary(wordsAndCountList);
			}
			scan.close();
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
