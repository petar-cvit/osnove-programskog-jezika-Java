package hr.fer.zemris.java.custom.collections;

/**
 * Class is doubly-linked list implementation. It extends Collection
 * class. Class has its private static class ListNode. Every element
 * of the list has pointers to the next and previous list element.
 * 
 * @author Petar Cvitanović
 *
 */
public class LinkedListIndexedCollection extends Collection{
	
	/**
	 * current size of the list
	 */
	int size;
	
	/**
	 * reference to the first node of the linked list
	 */
	ListNode first;
	
	/**
	 * reference to the last node of the linked list
	 */
	ListNode last;
	
	/**
	 * Private static class ListNode with pointers
	 * to previous and next list node and additional reference
	 * for value storage.
	 * 
	 * @author Petar Cvitanović
	 *
	 */
	private static class ListNode {
		
		/**
		 * reference to the previous node
		 */
		ListNode previous;
		
		/**
		 * reference to the previous node
		 */
		ListNode next;
		
		/**
		 * elements value
		 */
		Object value;
		
		/**
		 * ListNode constructor.
		 * 
		 * @param previous
		 * @param next
		 * @param value
		 */
		ListNode(ListNode previous, ListNode next, Object value) {
			this.previous = previous;
			this.next = next;
			this.value = value;
		}
	}
	
	/**
	 * Default constructor that creates instance of a doubly-linked list.
	 */
	public LinkedListIndexedCollection() {
		first = null;
		last = null;
		size = 0;
	}
	
	/**
	 * Constructor that creates constructor and adds all elements of
	 * collection provided as parameter.
	 * 
	 * @throws NullPointerException
	 * 				if other collection is null
	 * 
	 * @param other
	 */
	public LinkedListIndexedCollection(Collection other) {
		if(other.equals(null)) {
			throw new NullPointerException();
		}
		
		addAll(other);
		size = other.size();
	}
	
	@Override
	public void add(Object value) {
		if(value.equals(null)) {
			throw new NullPointerException();
		}
		
		ListNode listNode = new ListNode(last, null, value);
		
		if(size == 0) {
			first = listNode;
			last = listNode;
		} else {
			last.next = listNode;
			last = listNode;
		}
		
		size++;
	}
	
	/**
	 * Return object on index that was provided.
	 * 
	 * @throws IndexOutOfBoundsException
	 * 				If index is smaller than 1 or bigger than size-1
	 * 
	 * @param index
	 * @return object on index in list
	 */
	public Object get(int index) {
		if(index < 0 || index > size-1) {
			throw new IndexOutOfBoundsException("Index out of bounds.(0, "+ (size-1) +")");
		}
		
		int out;
		ListNode node;
		if(index > size/2) {
			out = size - 1;
			node = last;
			while(true) {
				if(index == out) {
					return node.value;
				}
				out--;
				node = node.previous;
			}
		} else {
			out = 0;
			node = first;
			while(true) {
				if(index == out) {
					return node.value;
				}
				out++;
				node = node.next;
			}
		}
	}
	
	@Override
	public void clear() {
		first = null;
		last = null;
		size = 0;
	}
	
	/**
	 * Inserts given value on the position provided. All elements after
	 * position shifted one place to the right.
	 * 
	 * @throws IndexOutOfBoundsException
	 * 				If position is smaller than zero or bigger than
	 * 				collections size
	 * 
	 * @param value
	 * @param position
	 */
	public void insert(Object value, int position) {
		
		if(position < 0 || position > size) {
			throw new IndexOutOfBoundsException("Index out of bounds.(0, "+ size +")");
		}

		if(value.equals(null)) {
			throw new NullPointerException();
		}
		
		if(size == 0) {
			ListNode node = new ListNode(null, null, value);
			first = node;
			last = node;
			
		} else if(position == size) {
			add(value);	
			size--;
		} else {
			int index = 0;
			ListNode node = first;
			
			while(index < position) {
				node = node.next;
				index++;
			}
			
			ListNode newNode = new ListNode(node.previous, node, value);
			if(position != 0) {	
				node.previous.next = newNode;
			} else {
				first = newNode;
			}
			node.previous = newNode;
		}
		size++;
	}
	
	/**
	 * Return index of the given value in list.
	 * If collection doesn't contain given value, method returns -1.
	 * 
	 * @param value
	 * @return index of value
	 */
	public int indexOf(Object value) {
		ListNode node = first;
		
		for(int i = 0;i < size;i++) {
			if(node.value.equals(value)) {
				return i;
			}
			node = node.next;
		}
		
		return -1;
	}
	
	/**
	 * Removes value on index in list.
	 * 
	 * @throws IndexOutOfBoundsException
	 * 				if index is smaller than 1 or bigger than size - 1
	 * 
	 * @param index
	 */	
	public void remove(int index) {
		if(index < 0 || index > size - 1) {
			throw new IndexOutOfBoundsException("Index out of bounds.(0, "+ (size-1) +")");
		}
		
		if(size == 1) {
			last = null;
			first = null;
		} else if(index == 0) {
			first = first.next;
			first.previous = null;
		} else if(index == size-1) {
			last = last.previous;
			last.next = null;
		} else {
			int i = 0;
			ListNode node = first;
			
			while(i < index) {
				node = node.next;
				i++;
			
			}
			
			node.previous.next = node.next;
			node.next.previous = node.previous;
		}
		
		size--;
	}
	
	@Override
	public int size() {
		return size;
	}

	@Override
	public boolean contains(Object value) {
		if(value == null) {
			return false;
		}
		
		ListNode node = first;
		while(node != null) {
			if(node.value.equals(value)) {
				return true;
			}
			node = node.next;
		}
		return false;
	}
	
	@Override
	public boolean remove(Object value) {
		int index = indexOf(value);
		if(index != -1) {
			remove(index);
			return true;
		}
		
		return false;
	}

	@Override
	public Object[] toArray() {
		Object[] array = new Object[size];
		ListNode node = first;
		for(int i = 0;i < size;i++) {
			array[i] = node.value;
			node = node.next;
		}
		
		return array;
	}

	@Override
	public void forEach(Processor processor) {
		ListNode node = first;
		for(int i = 0;i < size;i++) {
			processor.process(node.value);
			node = node.next;
		}
	}
	
}













