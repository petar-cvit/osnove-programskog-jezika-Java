package hr.fer.zemris.java.p12.model;

/**
 * Class that describes one poll option in database.
 * 
 * @author Petar Cvitanović
 *
 */
public class Option {
	
	/**
	 * option id
	 */
	private Long id;
	
	/**
	 * option name
	 */
	private String name;
	
	/**
	 * option link
	 */
	private String link;
	
	/**
	 * number of votes this option got
	 */
	private Integer votes;
	
	/**
	 * Constructor with all needed parameters.
	 * 
	 * @param id
	 * @param name
	 * @param link
	 * @param votes
	 */
	public Option(Long id, String name, String link, Integer votes) {
		super();
		this.id = id;
		this.name = name;
		this.link = link;
		this.votes = votes;
	}

	/**
	 * Option ID getter.
	 * 
	 * @return poll option ID
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Option name getter.
	 * 
	 * @return option name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Option link getter.
	 * 
	 * @return option link
	 */
	public String getLink() {
		return link;
	}

	/**
	 * Number of votes getter.
	 * 
	 * @return number of votes
	 */
	public Integer getVotes() {
		return votes;
	}
}
