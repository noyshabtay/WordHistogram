package il.ac.tau.cs.sw1.ex8.wordsRank;

import java.util.Comparator;

import il.ac.tau.cs.sw1.ex8.wordsRank.RankedWord.rankType;

class RankedWordComparator implements Comparator<RankedWord>{
	
	private rankType cType;
	
	public RankedWordComparator(rankType cType) {
		this.cType = cType;
	}
	
	@Override
	public int compare(RankedWord o1, RankedWord o2) {
		Integer o1R = o1.getRankByType(cType);
		Integer o2R = o2.getRankByType(cType);
		return o1R.compareTo(o2R);
	}
}
