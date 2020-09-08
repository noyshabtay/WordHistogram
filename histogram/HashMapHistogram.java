package il.ac.tau.cs.sw1.ex8.histogram;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

/**************************************
 *  Add your code to this class !!!   *
 **************************************/
public class HashMapHistogram<T extends Comparable<T>> implements IHistogram<T>{
	
	private HashMap<T, Integer> his;
	
	public HashMapHistogram() {
		his = new HashMap<T, Integer>();
	}
	
	@Override
	public Iterator<T> iterator() {
		return new HashMapHistogramIterator<T>(his);
	}

	@Override
	public void addItem(T item) {
		if (his.containsKey(item))
			his.put(item, his.get(item)+1);
		else
			his.put(item, 1);
	}

	@Override
	public void removeItem(T item) throws IllegalItemException {
		if (his.containsKey(item)) {
			if (his.get(item) > 1)
				his.put(item, his.get(item)-1);
			else  // value == 1
				his.remove(item);
		}
		else
			throw new IllegalItemException();
	}

	@Override
	public void addItemKTimes(T item, int k) throws IllegalKValueException {
		if (k < 0)
			throw new IllegalKValueException(k);
		else if (k == 0)
			return;
		else {
			if (his.containsKey(item))
				his.put(item, his.get(item)+k);
			else
				his.put(item, k);
		}
	}

	@Override
	public void removeItemKTimes(T item, int k) throws IllegalItemException, IllegalKValueException {
		if (his.containsKey(item)) {
			if (k < 0 || k > his.get(item))
				throw new IllegalKValueException(k);
			if (his.get(item) > k)
				his.put(item, his.get(item)-k);
			else
				his.remove(item);
		}
		else
			throw new IllegalItemException();
		
	}

	@Override
	public int getCountForItem(T item) {
		if (his.containsKey(item))
			return his.get(item);
		else
			return 0;
	}

	@Override
	public void addAll(Collection<T> items) {
		for (T item : items) {
			this.addItem(item);
		}
	}

	@Override
	public void clear() {
		his.clear();
	}

	@Override
	public Set<T> getItemsSet() {
		return his.keySet();
	}

	@Override
	public void update(IHistogram<T> anotherHistogram) {
		for (T item : anotherHistogram) {
			for (int i=1; i<=anotherHistogram.getCountForItem(item); i++) {
				this.addItem(item);
			}
		}
	}

	
}
