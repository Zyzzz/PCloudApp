package imu.pcloud.app.been;



/**
 * Discover entity. @author MyEclipse Persistence Tools
 */

public class Discover  implements java.io.Serializable {


    private User user;
    private PersonalPlan personalPlan;
    private SharingRecord sharingRecord;
    private PlanCircle planCircle;
    private long commentTime;
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public PersonalPlan getPersonalPlan() {
		return personalPlan;
	}
	public void setPersonalPlan(PersonalPlan personalPlan) {
		this.personalPlan = personalPlan;
	}
	public SharingRecord getSharingRecord() {
		return sharingRecord;
	}
	public void setSharingRecord(SharingRecord sharingRecord) {
		this.sharingRecord = sharingRecord;
	}
	public PlanCircle getPlanCircle() {
		return planCircle;
	}
	public void setPlanCircle(PlanCircle planCircle) {
		this.planCircle = planCircle;
	}
	public long getCommentTime() {
		return commentTime;
	}
	public void setCommentTime(long commentTime) {
		this.commentTime = commentTime;
	}


}