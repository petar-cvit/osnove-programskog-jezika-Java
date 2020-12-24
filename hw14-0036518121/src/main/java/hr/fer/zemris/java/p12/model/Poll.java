package hr.fer.zemris.java.p12.model;

/**
 * Class that describes ine poll from database.
 * 
 * @author Petar Cvitanović
 *
 */
public class Poll {
	
	/**
	 * poll id
	 */
	private Long id;
	
	/**
	 * poll title
	 */
	private String title;
	
	/**
	 * poll message
	 */
	private String message;

	/**
	 * Constructor with all needed parameters.
	 * 
	 * @param id
	 * @param title
	 * @param message
	 */
	public Poll(Long id, String title, String message) {
		super();
		this.id = id;
		this.title = title;
		this.message = message;
	}

	/**
	 * Poll id getter.
	 * 
	 * @return poll id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Poll title getter.
	 * 
	 * @return poll title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Poll message getter.
	 * 
	 * @return poll message
	 */
	public String getMessage() {
		return message;
	}

}
