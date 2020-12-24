package hr.fer.zemris.java.custom.scripting.exec;

import java.util.EmptyStackException;
import java.util.HashMap;
import java.util.Map;

/**
 * Data structure that holds multiple stack like structure. It adapts class {@link HashMap} so it
 * maps stack name to top stack element.
 * 
 * @author Petar Cvitanović
 *
 */
public class ObjectMultistack {
	
	/**
	 * Adapted map
	 */
	Map<String, MultistackEntry> map;
	
	/**
	 * Class that represents on entry in {@link ObjectMultistack} data structure.
	 * 
	 * @author Petar Cvitanović
	 *
	 */
	private static class MultistackEntry {
		
		/**
		 * value held in this entry
		 */
		private ValueWrapper value;
		
		/**
		 * pointer to entry that is pushed to same stack before this entry
		 */
		private MultistackEntry next;
		
		/**
		 * Constructor with value and next entry.
		 * 
		 * @param value
		 * @param next
		 */
		public MultistackEntry(ValueWrapper value, MultistackEntry next) {
			super();
			this.value = value;
			this.next = next;
		}
	}

	/**
	 * Default constructor.
	 */
	public ObjectMultistack() {
		map = new HashMap<String, MultistackEntry>();
	}
	
	/**
	 * Pushes value to stack that is mapped to given keyName argument.
	 * 
	 * @param keyName
	 * @param value
	 */
	public void push(String keyName, ValueWrapper value) {
		MultistackEntry entry = new MultistackEntry(value,
				map.get(keyName));
		
		map.put(keyName, entry);
	}
	
	/**
	 * Pops entry from stack mapped to given keyName.
	 * 
	 * @param keyName
	 * @return popped value
	 */
	public ValueWrapper pop(String keyName) {
		ValueWrapper out = peek(keyName);
		map.put(keyName,
				map.get(keyName).next);
		
		return out;
	}
	
	/**
	 * Returns top stack entry from stack mapped to given keyName.
	 * 
	 * @param keyName
	 * @return stack entry
	 */
	public ValueWrapper peek(String keyName) {
		MultistackEntry entry = map.get(keyName);
		
		if(entry == null) {
			throw new EmptyStackException();
		}
		
		return entry.value;
	}
	
	/**
	 * Returns whether or not stack mapped to given keyName is empty or not.
	 * 
	 * @param keyName
	 * @return is stack empty
	 */
	public boolean isEmpty(String keyName) {
		return map.get(keyName) == null || !map.containsKey(keyName);
	}
}
