package imu.pcloud.app.been;

import java.sql.Timestamp;
import java.util.Date;

/**
 * DiscoverId entity. @author MyEclipse Persistence Tools
 */

public class DiscoverId implements java.io.Serializable {

	// Fields

	private String content;
	private String planName;
	private Integer userId;
	private String username;
	private String email;
	private String sex;
	private Date birthday;
	private String education;
	private String working;
	private String signature;
	private Integer headImageId;
	private Timestamp sharingTime;
	private Integer personalPlanId;
	private Integer planCircleId;
	private String planCircleName;
	private String discribe;
	private Integer loadingTime;
	private Long commentCount;

	// Constructors

	/** default constructor */
	public DiscoverId() {
	}

	/** minimal constructor */
	public DiscoverId(String username, String email, Integer personalPlanId,
			Integer planCircleId, String planCircleName, Integer loadingTime,
			Long commentCount) {
		this.username = username;
		this.email = email;
		this.personalPlanId = personalPlanId;
		this.planCircleId = planCircleId;
		this.planCircleName = planCircleName;
		this.loadingTime = loadingTime;
		this.commentCount = commentCount;
	}

	/** full constructor */
	public DiscoverId(String content, String planName, Integer userId,
			String username, String email, String sex, Date birthday,
			String education, String working, String signature,
			Integer headImageId, Timestamp sharingTime, Integer personalPlanId,
			Integer planCircleId, String planCircleName, String discribe,
			Integer loadingTime, Long commentCount) {
		this.content = content;
		this.planName = planName;
		this.userId = userId;
		this.username = username;
		this.email = email;
		this.sex = sex;
		this.birthday = birthday;
		this.education = education;
		this.working = working;
		this.signature = signature;
		this.headImageId = headImageId;
		this.sharingTime = sharingTime;
		this.personalPlanId = personalPlanId;
		this.planCircleId = planCircleId;
		this.planCircleName = planCircleName;
		this.discribe = discribe;
		this.loadingTime = loadingTime;
		this.commentCount = commentCount;
	}

	// Property accessors

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getPlanName() {
		return this.planName;
	}

	public void setPlanName(String planName) {
		this.planName = planName;
	}

	public Integer getUserId() {
		return this.userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSex() {
		return this.sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public Date getBirthday() {
		return this.birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getEducation() {
		return this.education;
	}

	public void setEducation(String education) {
		this.education = education;
	}

	public String getWorking() {
		return this.working;
	}

	public void setWorking(String working) {
		this.working = working;
	}

	public String getSignature() {
		return this.signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public Integer getHeadImageId() {
		return this.headImageId;
	}

	public void setHeadImageId(Integer headImageId) {
		this.headImageId = headImageId;
	}

	public Timestamp getSharingTime() {
		return this.sharingTime;
	}

	public void setSharingTime(Timestamp sharingTime) {
		this.sharingTime = sharingTime;
	}

	public Integer getPersonalPlanId() {
		return this.personalPlanId;
	}

	public void setPersonalPlanId(Integer personalPlanId) {
		this.personalPlanId = personalPlanId;
	}

	public Integer getPlanCircleId() {
		return this.planCircleId;
	}

	public void setPlanCircleId(Integer planCircleId) {
		this.planCircleId = planCircleId;
	}

	public String getPlanCircleName() {
		return this.planCircleName;
	}

	public void setPlanCircleName(String planCircleName) {
		this.planCircleName = planCircleName;
	}

	public String getDiscribe() {
		return this.discribe;
	}

	public void setDiscribe(String discribe) {
		this.discribe = discribe;
	}

	public Integer getLoadingTime() {
		return this.loadingTime;
	}

	public void setLoadingTime(Integer loadingTime) {
		this.loadingTime = loadingTime;
	}

	public Long getCommentCount() {
		return this.commentCount;
	}

	public void setCommentCount(Long commentCount) {
		this.commentCount = commentCount;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof DiscoverId))
			return false;
		DiscoverId castOther = (DiscoverId) other;

		return ((this.getContent() == castOther.getContent()) || (this
				.getContent() != null && castOther.getContent() != null && this
				.getContent().equals(castOther.getContent())))
				&& ((this.getPlanName() == castOther.getPlanName()) || (this
						.getPlanName() != null
						&& castOther.getPlanName() != null && this
						.getPlanName().equals(castOther.getPlanName())))
				&& ((this.getUserId() == castOther.getUserId()) || (this
						.getUserId() != null && castOther.getUserId() != null && this
						.getUserId().equals(castOther.getUserId())))
				&& ((this.getUsername() == castOther.getUsername()) || (this
						.getUsername() != null
						&& castOther.getUsername() != null && this
						.getUsername().equals(castOther.getUsername())))
				&& ((this.getEmail() == castOther.getEmail()) || (this
						.getEmail() != null && castOther.getEmail() != null && this
						.getEmail().equals(castOther.getEmail())))
				&& ((this.getSex() == castOther.getSex()) || (this.getSex() != null
						&& castOther.getSex() != null && this.getSex().equals(
						castOther.getSex())))
				&& ((this.getBirthday() == castOther.getBirthday()) || (this
						.getBirthday() != null
						&& castOther.getBirthday() != null && this
						.getBirthday().equals(castOther.getBirthday())))
				&& ((this.getEducation() == castOther.getEducation()) || (this
						.getEducation() != null
						&& castOther.getEducation() != null && this
						.getEducation().equals(castOther.getEducation())))
				&& ((this.getWorking() == castOther.getWorking()) || (this
						.getWorking() != null && castOther.getWorking() != null && this
						.getWorking().equals(castOther.getWorking())))
				&& ((this.getSignature() == castOther.getSignature()) || (this
						.getSignature() != null
						&& castOther.getSignature() != null && this
						.getSignature().equals(castOther.getSignature())))
				&& ((this.getHeadImageId() == castOther.getHeadImageId()) || (this
						.getHeadImageId() != null
						&& castOther.getHeadImageId() != null && this
						.getHeadImageId().equals(castOther.getHeadImageId())))
				&& ((this.getSharingTime() == castOther.getSharingTime()) || (this
						.getSharingTime() != null
						&& castOther.getSharingTime() != null && this
						.getSharingTime().equals(castOther.getSharingTime())))
				&& ((this.getPersonalPlanId() == castOther.getPersonalPlanId()) || (this
						.getPersonalPlanId() != null
						&& castOther.getPersonalPlanId() != null && this
						.getPersonalPlanId().equals(
								castOther.getPersonalPlanId())))
				&& ((this.getPlanCircleId() == castOther.getPlanCircleId()) || (this
						.getPlanCircleId() != null
						&& castOther.getPlanCircleId() != null && this
						.getPlanCircleId().equals(castOther.getPlanCircleId())))
				&& ((this.getPlanCircleName() == castOther.getPlanCircleName()) || (this
						.getPlanCircleName() != null
						&& castOther.getPlanCircleName() != null && this
						.getPlanCircleName().equals(
								castOther.getPlanCircleName())))
				&& ((this.getDiscribe() == castOther.getDiscribe()) || (this
						.getDiscribe() != null
						&& castOther.getDiscribe() != null && this
						.getDiscribe().equals(castOther.getDiscribe())))
				&& ((this.getLoadingTime() == castOther.getLoadingTime()) || (this
						.getLoadingTime() != null
						&& castOther.getLoadingTime() != null && this
						.getLoadingTime().equals(castOther.getLoadingTime())))
				&& ((this.getCommentCount() == castOther.getCommentCount()) || (this
						.getCommentCount() != null
						&& castOther.getCommentCount() != null && this
						.getCommentCount().equals(castOther.getCommentCount())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getContent() == null ? 0 : this.getContent().hashCode());
		result = 37 * result
				+ (getPlanName() == null ? 0 : this.getPlanName().hashCode());
		result = 37 * result
				+ (getUserId() == null ? 0 : this.getUserId().hashCode());
		result = 37 * result
				+ (getUsername() == null ? 0 : this.getUsername().hashCode());
		result = 37 * result
				+ (getEmail() == null ? 0 : this.getEmail().hashCode());
		result = 37 * result
				+ (getSex() == null ? 0 : this.getSex().hashCode());
		result = 37 * result
				+ (getBirthday() == null ? 0 : this.getBirthday().hashCode());
		result = 37 * result
				+ (getEducation() == null ? 0 : this.getEducation().hashCode());
		result = 37 * result
				+ (getWorking() == null ? 0 : this.getWorking().hashCode());
		result = 37 * result
				+ (getSignature() == null ? 0 : this.getSignature().hashCode());
		result = 37
				* result
				+ (getHeadImageId() == null ? 0 : this.getHeadImageId()
						.hashCode());
		result = 37
				* result
				+ (getSharingTime() == null ? 0 : this.getSharingTime()
						.hashCode());
		result = 37
				* result
				+ (getPersonalPlanId() == null ? 0 : this.getPersonalPlanId()
						.hashCode());
		result = 37
				* result
				+ (getPlanCircleId() == null ? 0 : this.getPlanCircleId()
						.hashCode());
		result = 37
				* result
				+ (getPlanCircleName() == null ? 0 : this.getPlanCircleName()
						.hashCode());
		result = 37 * result
				+ (getDiscribe() == null ? 0 : this.getDiscribe().hashCode());
		result = 37
				* result
				+ (getLoadingTime() == null ? 0 : this.getLoadingTime()
						.hashCode());
		result = 37
				* result
				+ (getCommentCount() == null ? 0 : this.getCommentCount()
						.hashCode());
		return result;
	}

}