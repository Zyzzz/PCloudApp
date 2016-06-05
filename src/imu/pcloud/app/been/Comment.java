package imu.pcloud.app.been;

import java.sql.Timestamp;

/**
 * Comment entity. @author MyEclipse Persistence Tools
 */

public class Comment implements java.io.Serializable {

	// Fields

	private Integer id;
	private User user;
	private Integer personalPlanId;
	private String content;
	private Timestamp commentingTime;

	// Constructors

	/** default constructor */
	public Comment() {
	}

	/** minimal constructor */
	public Comment(User user, Integer personalPlanId, String content) {
		this.user = user;
		this.personalPlanId = personalPlanId;
		this.content = content;
	}

	/** full constructor */
	public Comment(User user, Integer personalPlanId, String content,
			Timestamp commentingTime) {
		this.user = user;
		this.personalPlanId = personalPlanId;
		this.content = content;
		this.commentingTime = commentingTime;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Integer getPersonalPlanId() {
		return this.personalPlanId;
	}

	public void setPersonalPlanId(Integer personalPlanId) {
		this.personalPlanId = personalPlanId;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Timestamp getCommentingTime() {
		return this.commentingTime;
	}

	public void setCommentingTime(Timestamp commentingTime) {
		this.commentingTime = commentingTime;
	}

}