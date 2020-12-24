package hr.fer.zemris.java.hw05.db;

/**
 * Represents one student record with student's JMBAG, first name, last name, and
 * his final grade.
 * 
 * @author Petar Cvitanoivć
 *
 */
public class StudentRecord {

	/**
	 * student's JMBAG
	 */
	String JMBAG;
	
	/**
	 * student's last name
	 */
	String lastName;
	
	/**
	 * student's first name
	 */
	String firstName;
	
	/**
	 * student's final grade
	 */
	int finalGrade;
	
	/**
	 * Constructor with JMBAG, last name, first name and final grade.
	 * 
	 * @throws IllegalArgumentException
	 * 				if grade is less than 1 or bigger than 5
	 * 
	 * @throws NullPointerException
	 * 				if attributes are null
	 */
	public StudentRecord(String JMBAG, String lastName, String firstName, int finalGrade) {
		if(finalGrade < 1 || finalGrade > 5) {
			throw new IllegalArgumentException("Invalid grade!");
		}
		
		if(JMBAG == null || lastName == null || firstName == null) {
			throw new NullPointerException("Null attributes!");
		}
		
		this.JMBAG = JMBAG;
		this.lastName = lastName;
		this.firstName = firstName;
		this.finalGrade = finalGrade;
	}
	
	/**
	 * JMBAG getter.
	 * 
	 * @return JMBAG
	 */
	public String getJMBAG() {
		return JMBAG;
	}
	
	/**
	 * Last name getter.
	 * 
	 * @return last name
	 */
	public String getLastName() {
		return lastName;
	}
	
	/**
	 * First name getter.
	 * 
	 * @return first name
	 */
	public String getFirstName() {
		return firstName;
	}
	
	/**
	 * Final grade getter.
	 * 
	 * @return final grade
	 */
	public int getFinalGrade() {
		return finalGrade;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((JMBAG == null) ? 0 : JMBAG.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StudentRecord other = (StudentRecord) obj;
		if (JMBAG == null) {
			if (other.JMBAG != null)
				return false;
		} else if (!JMBAG.equals(other.JMBAG))
			return false;
		return true;
	}

}
