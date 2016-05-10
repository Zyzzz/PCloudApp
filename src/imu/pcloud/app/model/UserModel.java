package imu.pcloud.app.model;

import imu.pcloud.server.been.Image;
import imu.pcloud.server.been.User;
import imu.pcloud.server.utils.DateTool;

import java.util.Date;

public class UserModel extends BaseModel{

	private Integer id;
	private String username;
	private String password;
	private String email;
	private String sex;
	private String birthday;
	private String education;
	private String working;
	private String signature;
	private Integer verifyFlag;
	private String secretKey;
	private String cookies;
	
	public UserModel() {
		
	}
	
	public UserModel(User user) {
		this.setByUser(user);
	}
	
	public UserModel(Integer id, String username, String password,
			String email, String sex, String birthday, String education,
			String working, String signature, Integer verifyFlag,
			String secretKey, String cookies) {
		super();
		this.id = id;
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
	}
	
	public void setByUser(User user){
		this.id = user.getId();
		this.username = user.getUsername();
		this.password = user.getPassword();
		this.email = user.getEmail();
		this.sex = user.getSex();
		this.birthday = DateTool.dateToString(user.getBirthday());
		this.education = user.getEducation();
		this.working = user.getWorking();
		this.signature = user.getSecretKey();
		verifyFlag = user.getVerifyFlag();
		this.secretKey = user.getSecretKey();
		this.cookies = user.getCookies();
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public String getEducation() {
		return education;
	}
	public void setEducation(String education) {
		this.education = education;
	}
	public String getWorking() {
		return working;
	}
	public void setWorking(String working) {
		this.working = working;
	}
	public String getSignature() {
		return signature;
	}
	public void setSignature(String signature) {
		this.signature = signature;
	}
	public Integer getVerifyFlag() {
		return verifyFlag;
	}
	public void setVerifyFlag(Integer verifyFlag) {
		this.verifyFlag = verifyFlag;
	}
	public String getSecretKey() {
		return secretKey;
	}
	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}
	public String getCookies() {
		return cookies;
	}
	public void setCookies(String cookies) {
		this.cookies = cookies;
	}
	
	
}
