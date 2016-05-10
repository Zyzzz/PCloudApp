package imu.pcloud.app.been;

import java.util.HashSet;
import java.util.Set;

/**
 * PersonalPlan entity. @author MyEclipse Persistence Tools
 */

public class PersonalPlan implements java.io.Serializable {

	// Fields

	private Integer id;
	private String content;
	private String name;
	private Integer userId;
	private Integer coverImageId;
	private Set comments = new HashSet(0);

	// Constructors

	/** default constructor */
	public PersonalPlan() {
	}

	/** full constructor */
	public PersonalPlan(String content, String name, Integer userId,
			Integer coverImageId, Set comments) {
		this.content = content;
		this.name = name;
		this.userId = userId;
		this.coverImageId = coverImageId;
		this.comments = comments;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getUserId() {
		return this.userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getCoverImageId() {
		return this.coverImageId;
	}

	public void setCoverImageId(Integer coverImageId) {
		this.coverImageId = coverImageId;
	}

	public Set getComments() {
		return this.comments;
	}

	public void setComments(Set comments) {
		this.comments = comments;
	}

}