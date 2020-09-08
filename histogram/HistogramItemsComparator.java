package il.ac.tau.cs.sw1.ex8.histogram;

import java.util.Comparator;
import java.util.Map.Entry;

public class HistogramItemsComparator<T extends Comparable<T>> implements Comparator<Entry<T,Integer>> {
	public int compare(Entry<T,Integer> item1,Entry<T,Integer> item2) {
		int c = item1.getValue().compareTo(item2.getValue());
		if(c!=0)
			return c;
		else
			return -item1.getKey().compareTo(item2.getKey());
	}
}
