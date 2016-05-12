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
    private Integer condition;
    private Integer coverImageId;

    // Constructors

    /**
     * default constructor
     */
    public MultiPlan() {
    }

    /**
     * minimal constructor
     */
    public MultiPlan(Integer id) {
        this.id = id;
    }

    /**
     * full constructor
     */
    public MultiPlan(Integer id, Integer userId, String name, String content,
                     Integer maxmumber, Integer condition, Integer coverImageId) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.content = content;
        this.maxmumber = maxmumber;
        this.condition = condition;
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

    public Integer getCondition() {
        return this.condition;
    }

    public void setCondition(Integer condition) {
        this.condition = condition;
    }

    public Integer getCoverImageId() {
        return this.coverImageId;
    }

    public void setCoverImageId(Integer coverImageId) {
        this.coverImageId = coverImageId;
    }

}