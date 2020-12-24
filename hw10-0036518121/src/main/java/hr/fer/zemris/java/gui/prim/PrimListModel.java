package hr.fer.zemris.java.gui.prim;

import java.util.ArrayList;
import java.util.List;

import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

/**
 * Class that is used in PrimDemo frame. Every list in that frame uses this class.
 * Implements ListModel class and holds numbers in a list of integers. When instance is
 * constructed this list has only number one. Every time "next" method is called, new
 * prime number is added to this list.
 * 
 * @author Petar Cvitanović
 *
 */
class PrimListModel implements ListModel<Integer> {

	/**
	 * list of prime numbers
	 */
	private List<Integer> prim;
	
	/**
	 * list of listeners
	 */
	private List<ListDataListener> listeners;
	
	/**
	 * Default constructor.
	 */
	public PrimListModel() {
		prim = new ArrayList<Integer>();
		listeners = new ArrayList<ListDataListener>();
		
		prim.add(1);
	}
	
	@Override
	public int getSize() {
		return prim.size();
	}

	@Override
	public Integer getElementAt(int index) {
		return prim.get(index);
	}

	@Override
	public void addListDataListener(ListDataListener l) {
		listeners.add(l);
	}

	@Override
	public void removeListDataListener(ListDataListener l) {
		listeners.remove(l);
	}
	
	/**
	 * Adds new prime number to list.
	 */
	public void next() {
		int pos = prim.size();
		
		int current = prim.get(prim.size() - 1) + 1;
		
		while(true) {
			if(isPrime(current)) {
				prim.add(current);
				break;
			}
			
			current++;
		}
		
		ListDataEvent event = new ListDataEvent(this, ListDataEvent.INTERVAL_ADDED, pos, pos);
		for(ListDataListener l : listeners) {
			l.intervalAdded(event);
		}
	}

	/**
	 * Returns true if given number is prime, false otherwise.
	 * 
	 * @param n
	 * @return
	 */
	private boolean isPrime(int n) {
		for(int i = 2;i <= Math.sqrt(n);i++) {
			if(n % i == 0) {
				return false;
			}
		}
		
		return true;
	}
	
}
