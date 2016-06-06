package imu.pcloud.app.been;

/**
 * MultiPlan entity. @author MyEclipse Persistence Tools
 */

public class MultiPlan implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer userId;
	private String name;
	private String content;
	private Integer maxmumber;
	private Integer userCondition;
	private Integer coverImageId;

	// Constructors

	/** default constructor */
	public MultiPlan() {
	}

	/** full constructor */
	public MultiPlan(Integer userId, String name, String content,
			Integer maxmumber, Integer userCondition, Integer coverImageId) {
		this.userId = userId;
		this.name = name;
		this.content = content;
		this.maxmumber = maxmumber;
		this.userCondition = userCondition;
		this.coverImageId = coverImageId;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserId() {
		return this.userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getMaxmumber() {
		return this.maxmumber;
	}

	public void setMaxmumber(Integer maxmumber) {
		this.maxmumber = maxmumber;
	}

	public Integer getUserCondition() {
		return this.userCondition;
	}

	public void setUserCondition(Integer userCondition) {
		this.userCondition = userCondition;
	}

	public Integer getCoverImageId() {
		return this.coverImageId;
	}

	public void setCoverImageId(Integer coverImageId) {
		this.coverImageId = coverImageId;
	}

}