package imu.pcloud.app.been;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * User entity. @author MyEclipse Persistence Tools
 */

public class User implements java.io.Serializable {

    // Fields

    private Integer id;
    private Image image;
    private String username;
    private String password;
    private String email;
    private String sex;
    private Date birthday;
    private String education;
    private String working;
    private String signature;
    private Integer verifyFlag;
    private String secretKey;
    private String cookies;
    private Set comments = new HashSet(0);

    // Constructors

    /**
     * default constructor
     */
    public User() {
    }

    /**
     * minimal constructor
     */
    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    /**
     * full constructor
     */
    public User(Image image, String username, String password, String email,
                String sex, Date birthday, String education, String working,
                String signature, Integer verifyFlag, String secretKey,
                String cookies, Set comments) {
        this.image = image;
        this.username = username;
        this.password = password;
        this.email = email;
        this.sex = sex;
        this.birthday = birthday;
        this.education = education;
        this.working = working;
        this.signature = signature;
        this.verifyFlag = verifyFlag;
        this.secretKey = secretKey;
        this.cookies = cookies;
        this.comments = comments;
    }

    // Property accessors

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Image getImage() {
        return this.image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public Integer getVerifyFlag() {
        return this.verifyFlag;
    }

    public void setVerifyFlag(Integer verifyFlag) {
        this.verifyFlag = verifyFlag;
    }

    public String getSecretKey() {
        return this.secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getCookies() {
        return this.cookies;
    }

    public void setCookies(String cookies) {
        this.cookies = cookies;
    }

    public Set getComments() {
        return this.comments;
    }

    public void setComments(Set comments) {
        this.comments = comments;
    }

}