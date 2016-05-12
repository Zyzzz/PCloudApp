package imu.pcloud.app.been;

/**
 * SharingRecordId entity. @author MyEclipse Persistence Tools
 */

public class SharingRecordId implements java.io.Serializable {

    // Fields

    private Integer personalPlanId;
    private Integer planCircleId;

    // Constructors

    /**
     * default constructor
     */
    public SharingRecordId() {
    }

    /**
     * full constructor
     */
    public SharingRecordId(Integer personalPlanId, Integer planCircleId) {
        this.personalPlanId = personalPlanId;
        this.planCircleId = planCircleId;
    }

    // Property accessors

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

    public boolean equals(Object other) {
        if ((this == other))
            return true;
        if ((other == null))
            return false;
        if (!(other instanceof SharingRecordId))
            return false;
        SharingRecordId castOther = (SharingRecordId) other;

        return ((this.getPersonalPlanId() == castOther.getPersonalPlanId()) || (this
                .getPersonalPlanId() != null
                && castOther.getPersonalPlanId() != null && this
                .getPersonalPlanId().equals(castOther.getPersonalPlanId())))
                && ((this.getPlanCircleId() == castOther.getPlanCircleId()) || (this
                .getPlanCircleId() != null
                && castOther.getPlanCircleId() != null && this
                .getPlanCircleId().equals(castOther.getPlanCircleId())));
    }

    public int hashCode() {
        int result = 17;

        result = 37
                * result
                + (getPersonalPlanId() == null ? 0 : this.getPersonalPlanId()
                .hashCode());
        result = 37
                * result
                + (getPlanCircleId() == null ? 0 : this.getPlanCircleId()
                .hashCode());
        return result;
    }

}