package hr.fer.zemris.java.tecaj_13.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

/**
 * Class that represents a blog user.
 * 
 * @author Petar Cvitanović
 *
 */
@Entity
@Table(name="blog_users")
public class BlogUser {

	/**
	 * user id
	 */
	private Long id;
	
	/**
	 * user's first name
	 */
	private String firstName;
	
	/**
	 * user's last name
	 */
	private String lastName;
	
	/**
	 * user's nickname
	 */
	private String nick;
	
	/**
	 * user's email
	 */
	private String email;
	
	/**
	 * hash calculated from user's password
	 */
	private String passwordHash;
	
	/**
	 * list of blog entries that this user wrote
	 */
	private List<BlogEntry> blogEntries = new ArrayList<BlogEntry>();

	/**
	 * Id getter.
	 * 
	 * @return users id
	 */
	@Id @GeneratedValue
	public Long getId() {
		return id;
	}

	/**
	 * User's first name getter.
	 * 
	 * @return users first name
	 */
	@Column(length=50,nullable=true)
	public String getFirstName() {
		return firstName;
	}
	
	/**
	 * User's last name getter.
	 * 
	 * @return users first name
	 */
	@Column(length=50,nullable=true)
	public String getLastName() {
		return lastName;
	}
	
	/**
	 * User's nickname getter.
	 * 
	 * @return users nickname
	 */
	@Column(length=50,nullable=false,unique = true)
	public String getNick() {
		return nick;
	}

	/**
	 * User's email getter.
	 * 
	 * @return users email
	 */
	@Column(length=50,nullable=true)
	public String getEmail() {
		return email;
	}
	
	/**
	 * Password hash getter.
	 * 
	 * @return password hash
	 */
	@Column(length=50,nullable=false)
	public String getPasswordHash() {
		return passwordHash;
	}
	
	/**
	 * Blog entries getter.
	 * 
	 * @return blog entries
	 */
	@OneToMany(mappedBy="creator", fetch=FetchType.LAZY, cascade=CascadeType.PERSIST, orphanRemoval=true)
	@OrderBy("createdAt")
	public List<BlogEntry> getBlogEntries() {
		return blogEntries;
	}
	
	/**
	 * Id setter.
	 * 
	 * @param id
	 */
	public void setId(Long id) {
		this.id = id;
	}
	
	/**
	 * First name setter.
	 * 
	 * @param firstName
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	/**
	 * Last name setter.
	 * 
	 * @param lastName
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	/**
	 * Nickname setter.
	 * 
	 * @param nick
	 */
	public void setNick(String nick) {
		this.nick = nick;
	}
	
	/**
	 * Email setter.
	 * 
	 * @param email
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	
	/**
	 * Password hash setter.
	 * 
	 * @param passwordHash
	 */
	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}
	
	/**
	 * Blog entries setter.
	 * 
	 * @param blogEntries
	 */
	public void setBlogEntries(List<BlogEntry> blogEntries) {
		this.blogEntries = blogEntries;
	}

	@Override
	public String toString() {
		return "BlogUser [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", nick=" + nick
				+ ", email=" + email + ", passwordHash=" + passwordHash + ", blogEntries=" + blogEntries + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		BlogUser other = (BlogUser) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	
}