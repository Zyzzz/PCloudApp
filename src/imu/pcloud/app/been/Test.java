package imu.pcloud.app.been;

/**
 * Test entity. @author MyEclipse Persistence Tools
 */

public class Test implements java.io.Serializable {

	// Fields

	private String a;

	// Constructors

	/** default constructor */
	public Test() {
	}

	/** full constructor */
	public Test(String a) {
		this.a = a;
	}

	// Property accessors

	public String getA() {
		return this.a;
	}

	public void setA(String a) {
		this.a = a;
	}

}