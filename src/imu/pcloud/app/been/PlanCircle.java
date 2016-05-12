package imu.pcloud.app.been;

/**
 * PlanCircle entity. @author MyEclipse Persistence Tools
 */

public class PlanCircle implements java.io.Serializable {

    // Fields

    private Integer id;
    private String name;
    private Integer coverImageId;

    // Constructors

    /**
     * default constructor
     */
    public PlanCircle() {
    }

    /**
     * minimal constructor
     */
    public PlanCircle(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * full constructor
     */
    public PlanCircle(Integer id, String name, Integer coverImageId) {
        this.id = id;
        this.name = name;
        this.coverImageId = coverImageId;
    }

    // Property accessors

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCoverImageId() {
        return this.coverImageId;
    }

    public void setCoverImageId(Integer coverImageId) {
        this.coverImageId = coverImageId;
    }

}