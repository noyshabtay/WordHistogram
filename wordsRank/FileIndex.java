package il.ac.tau.cs.sw1.ex8.wordsRank;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import il.ac.tau.cs.sw1.ex8.histogram.HashMapHistogram;
import il.ac.tau.cs.sw1.ex8.histogram.IHistogram;
import il.ac.tau.cs.sw1.ex8.wordsRank.RankedWord.rankType;

/**************************************
 *  Add your code to this class !!!   *
 **************************************/

public class FileIndex {
	
	public static final int UNRANKED_CONST = 30;
	private Map<String, IHistogram<String>> index;
	private Map<String ,RankedWord> rankedWordIndex;
	private Map<String ,Integer> differentWordsInFile;

	/*
	 * @pre: the directory is no empty, and contains only readable text files
	 */
  	public void indexDirectory(String folderPath) {
		File folder = new File(folderPath);
		File[] listFiles = folder.listFiles();
		index = new HashMap<>(); //full index
		rankedWordIndex = new HashMap<>();
		differentWordsInFile = new HashMap<>();
		Map<String, HashMap<String, Integer>> ranksForFiles = new HashMap<String, HashMap<String, Integer>>();
		for (File file : listFiles) { //for every file in the folder
			if (file.isFile()) {
				try {
					List<String> wordsInFile = FileUtils.readAllTokens(file); //reading from the file.
					IHistogram<String> hisWords = new HashMapHistogram<>(); //making a histogram for the current file.
					hisWords.addAll(wordsInFile);
					differentWordsInFile.put(file.getName(), hisWords.getItemsSet().size());
					int i = 1;
					for(String word : hisWords) {
						if (ranksForFiles.containsKey(word))
							ranksForFiles.get(word).put(file.getName(), i);
						else {
							ranksForFiles.put(word, new HashMap<String, Integer>());
							ranksForFiles.get(word).put(file.getName(), i);
						}
						i++;
					}
					index.put(file.getName(), hisWords); // linking file and it's histogram.
					}
				catch(Exception e) {}
			}
		}
		for (Map.Entry<String, HashMap<String, Integer>> entry : ranksForFiles.entrySet()) {
			for (File file : listFiles) {
				if(!entry.getValue().containsKey(file.getName()))
					ranksForFiles.get(entry.getKey()).put(file.getName(), differentWordsInFile.get(file.getName()) + UNRANKED_CONST);
			}
		}
		for (Map.Entry<String, HashMap<String, Integer>> entry : ranksForFiles.entrySet()) {
			rankedWordIndex.put(entry.getKey(), new RankedWord(entry.getKey(), entry.getValue()));
		}
	}
	
  	/*
	 * @pre: the index is initialized
	 * @pre filename is a name of a valid file
	 * @pre word is not null
	 */
  	
	public int getCountInFile(String filename, String word) throws FileIndexException{
		if (index.containsKey(filename))
			return index.get(filename).getCountForItem(word.toLowerCase());
		else
			throw new FileIndexException(filename + "does not in current index.");
	}
	
	/*
	 * @pre: the index is initialized
	 * @pre filename is a name of a valid file
	 * @pre word is not null
	 */
	public int getRankForWordInFile(String filename, String word) throws FileIndexException{
		if (index.containsKey(filename)) {
			if (rankedWordIndex.containsKey(word)) { // the word was in one file at least.
				if (rankedWordIndex.get(word).getRanksForFile().containsKey(filename))
					return rankedWordIndex.get(word).getRanksForFile().get(filename); 
				}
			// that means the word is not in any file.
			return index.get(filename).getItemsSet().size() + UNRANKED_CONST; 
		}
		else
			throw new FileIndexException(filename + "does not in current index.");
	}
	
	/*
	 * @pre: the index is initialized
	 * @pre word is not null
	 */
	public int getAverageRankForWord(String word){
		if (rankedWordIndex.containsKey(word))
			return rankedWordIndex.get(word).getRankByType(rankType.average);
		int sum = 0;
		for (Map.Entry<String, Integer> entry : differentWordsInFile.entrySet()) {
			sum += entry.getValue() + UNRANKED_CONST;
		}
		return (int)Math.round(((double)sum)/differentWordsInFile.size());
	}
	
	public List<String> getWordsWithAverageRankSmallerThanK(int k, rankType type) {
		List<String> ans = new ArrayList<String>();
		List<RankedWord> rWList = new ArrayList<RankedWord>();
		for (Entry<String, RankedWord> entry : rankedWordIndex.entrySet()) {
			if (entry.getValue().getRankByType(type) < k)
				rWList.add(entry.getValue());
		}
		Collections.sort(rWList, new RankedWordComparator(type));
		for (RankedWord e : rWList) {
			ans.add(e.getWord());
		}
		return ans;
	}
	
	public List<String> getWordsWithAverageRankSmallerThanK(int k){
		return getWordsWithAverageRankSmallerThanK(k, rankType.average);
	}
	
	public List<String> getWordsWithMinRankSmallerThanK(int k){
		return getWordsWithAverageRankSmallerThanK(k, rankType.min);
	}
	
	public List<String> getWordsWithMaxRankSmallerThanK(int k){
		return getWordsWithAverageRankSmallerThanK(k, rankType.max);
	}

}
