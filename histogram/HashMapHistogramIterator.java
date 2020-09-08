package il.ac.tau.cs.sw1.ex8.histogram;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;


/**************************************
 *  Add your code to this class !!!   *
 **************************************/
public class HashMapHistogramIterator<T extends Comparable<T>> implements Iterator<T>{
	
	private List<Entry<T,Integer>> items;
	private Iterator<Entry<T,Integer>> myIter;
	
	public HashMapHistogramIterator(HashMap<T,Integer> his) {
		Set<Entry<T,Integer>> tmp = his.entrySet();
		items = new ArrayList<Entry<T,Integer>>();
		for (Entry<T, Integer> item : tmp) {
			items.add(item);
		}
		items.sort(new HistogramItemsComparator<T>().reversed());
		myIter = items.iterator();
	}
	
	@Override
	public boolean hasNext() {
		return myIter.hasNext();
	}

	@Override
	public T next() {
		return myIter.next().getKey();
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException(); //no need to change this
	}
}
