package imu.pcloud.app.been;

/**
 * MultiPlanMember entity. @author MyEclipse Persistence Tools
 */

public class MultiPlanMember implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer multiPlanId;
	private Integer userId;
	private User user;

	// Constructors

	/** default constructor */
	public MultiPlanMember() {
	}

	/** full constructor */
	public MultiPlanMember(Integer multiPlanId, Integer userId) {
		this.multiPlanId = multiPlanId;
		this.userId = userId;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getMultiPlanId() {
		return this.multiPlanId;
	}

	public void setMultiPlanId(Integer multiPlanId) {
		this.multiPlanId = multiPlanId;
	}

	public Integer getUserId() {
		return this.userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	

}