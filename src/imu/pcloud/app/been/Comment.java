package imu.pcloud.app.been;

import java.sql.Timestamp;

/**
 * Comment entity. @author MyEclipse Persistence Tools
 */

public class Comment implements java.io.Serializable {

    // Fields

    private CommentId id;
    private PersonalPlan personalPlan;
    private User user;
    private String content;
    private Timestamp commentingTime;

    // Constructors

    /**
     * default constructor
     */
    public Comment() {
    }

    /**
     * minimal constructor
     */
    public Comment(CommentId id, PersonalPlan personalPlan, User user,
                   String content) {
        this.id = id;
        this.personalPlan = personalPlan;
        this.user = user;
        this.content = content;
    }

    /**
     * full constructor
     */
    public Comment(CommentId id, PersonalPlan personalPlan, User user,
                   String content, Timestamp commentingTime) {
        this.id = id;
        this.personalPlan = personalPlan;
        this.user = user;
        this.content = content;
        this.commentingTime = commentingTime;
    }

    // Property accessors

    public CommentId getId() {
        return this.id;
    }

    public void setId(CommentId id) {
        this.id = id;
    }

    public PersonalPlan getPersonalPlan() {
        return this.personalPlan;
    }

    public void setPersonalPlan(PersonalPlan personalPlan) {
        this.personalPlan = personalPlan;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
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