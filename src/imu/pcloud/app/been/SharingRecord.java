package imu.pcloud.app.been;

import java.sql.Timestamp;

/**
 * SharingRecord entity. @author MyEclipse Persistence Tools
 */

public class SharingRecord implements java.io.Serializable {

	// Fields

	private SharingRecordId id;
	private Integer loadingTime;
	private Timestamp sharingTime;
	private Timestamp lastUpdatingTime;
    private Integer userId;
	// Constructors

	/** default constructor */
	public SharingRecord() {
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	/** minimal constructor */
	public SharingRecord(SharingRecordId id, Integer loadingTime) {
		this.id = id;
		this.loadingTime = loadingTime;
	}

	/** full constructor */
	public SharingRecord(SharingRecordId id, Integer loadingTime,
			Timestamp sharingTime, Timestamp lastUpdatingTime) {
		this.id = id;
		this.loadingTime = loadingTime;
		this.sharingTime = sharingTime;
		this.lastUpdatingTime = lastUpdatingTime;
	}

	// Property accessors

	public SharingRecordId getId() {
		return this.id;
	}

	public void setId(SharingRecordId id) {
		this.id = id;
	}

	public Integer getLoadingTime() {
		return this.loadingTime;
	}

	public void setLoadingTime(Integer loadingTime) {
		this.loadingTime = loadingTime;
	}

	public Timestamp getSharingTime() {
		return this.sharingTime;
	}

	public void setSharingTime(Timestamp sharingTime) {
		this.sharingTime = sharingTime;
	}

	public Timestamp getLastUpdatingTime() {
		return this.lastUpdatingTime;
	}

	public void setLastUpdatingTime(Timestamp lastUpdatingTime) {
		this.lastUpdatingTime = lastUpdatingTime;
	}

}