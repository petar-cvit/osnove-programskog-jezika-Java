package hr.fer.zemris.java.hw05.db;

/**
 * This class offers static variables of type IFieldGetter.
 * Those variables represent attribute getters.
 * 
 * @author Petar Cvitanović
 *
 */
public class FieldValueGetters {
	
	/**
	 * gets first name
	 */
	public static final IFieldValueGetter FIRST_NAME = (r) -> r.getFirstName();
	
	/**
	 * gets last name
	 */
	public static final IFieldValueGetter LAST_NAME = (r) -> r.getLastName();
	
	/**
	 * gets JMBAG
	 */
	public static final IFieldValueGetter JMBAG = (r) -> r.getJMBAG();

}
