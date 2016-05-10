package imu.pcloud.app.been;

/**
 * CommentId entity. @author MyEclipse Persistence Tools
 */

public class CommentId implements java.io.Serializable {

	// Fields

	private Integer userId;
	private Integer personalPlanId;

	// Constructors

	/** default constructor */
	public CommentId() {
	}

	/** full constructor */
	public CommentId(Integer userId, Integer personalPlanId) {
		this.userId = userId;
		this.personalPlanId = personalPlanId;
	}

	// Property accessors

	public Integer getUserId() {
		return this.userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getPersonalPlanId() {
		return this.personalPlanId;
	}

	public void setPersonalPlanId(Integer personalPlanId) {
		this.personalPlanId = personalPlanId;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof CommentId))
			return false;
		CommentId castOther = (CommentId) other;

		return ((this.getUserId() == castOther.getUserId()) || (this
				.getUserId() != null && castOther.getUserId() != null && this
				.getUserId().equals(castOther.getUserId())))
				&& ((this.getPersonalPlanId() == castOther.getPersonalPlanId()) || (this
						.getPersonalPlanId() != null
						&& castOther.getPersonalPlanId() != null && this
						.getPersonalPlanId().equals(
								castOther.getPersonalPlanId())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getUserId() == null ? 0 : this.getUserId().hashCode());
		result = 37
				* result
				+ (getPersonalPlanId() == null ? 0 : this.getPersonalPlanId()
						.hashCode());
		return result;
	}

}