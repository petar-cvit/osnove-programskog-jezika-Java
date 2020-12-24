package hr.fer.zemris.java.p11.servleti;

/**
 * Class that holds data about bands. Holds band's id, name, link to the song and number of votes.
 * 
 * @author Petar Cvitanović
 *
 */
public class Quadraple {

	/**
	 * band id
	 */
	private String id;
	
	/**
	 * band name
	 */
	private String name;
	
	/**
	 * link to band's song
	 */
	private String song;
	
	/**
	 * number of votes
	 */
	private Integer votes;
	
	/**
	 * Constructor.
	 * 
	 * @param id
	 * @param name
	 * @param song
	 * @param votes
	 */
	public Quadraple(String id, String name, String song, Integer votes) {
		super();
		this.id = id;
		this.name = name;
		this.song = song;
		this.votes = votes;
	}
	
	/**
	 * Id getter
	 * 
	 * @return band's id
	 */
	public String getId() {
		return id;
	}

	/**
	 * Name getter.
	 * 
	 * @return band's name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Band's song getter.
	 * 
	 * @return link to band's song as string.
	 */
	public String getSong() {
		return song;
	}

	/**
	 * Number of votes getter.
	 * 
	 * @return number of votes.
	 */
	public Integer getVotes() {
		return votes;
	}

	/**
	 * Id setter.
	 * 
	 * @param id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Name setter.
	 * 
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Song setter
	 * 
	 * @param song
	 */
	public void setSong(String song) {
		this.song = song;
	}

	/**
	 * Number of vote setter.
	 * 
	 * @param votes
	 */
	public void setVotes(Integer votes) {
		this.votes = votes;
	}
}
