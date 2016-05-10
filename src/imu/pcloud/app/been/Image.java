package imu.pcloud.app.been;

import java.util.HashSet;
import java.util.Set;

/**
 * Image entity. @author MyEclipse Persistence Tools
 */

public class Image implements java.io.Serializable {

	// Fields

	private Integer id;
	private String imageFile;
	private Set users = new HashSet(0);

	// Constructors

	/** default constructor */
	public Image() {
	}

	/** minimal constructor */
	public Image(Integer id) {
		this.id = id;
	}

	/** full constructor */
	public Image(Integer id, String imageFile, Set users) {
		this.id = id;
		this.imageFile = imageFile;
		this.users = users;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getImageFile() {
		return this.imageFile;
	}

	public void setImageFile(String imageFile) {
		this.imageFile = imageFile;
	}

	public Set getUsers() {
		return this.users;
	}

	public void setUsers(Set users) {
		this.users = users;
	}

}