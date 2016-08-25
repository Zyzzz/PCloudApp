package imu.pcloud.app.been;

/**
 * Discover entity. @author MyEclipse Persistence Tools
 */

public class Discover implements java.io.Serializable {

	// Fields

	private DiscoverId id;

	// Constructors

	/** default constructor */
	public Discover() {
	}

	/** full constructor */
	public Discover(DiscoverId id) {
		this.id = id;
	}

	// Property accessors

	public DiscoverId getId() {
		return this.id;
	}

	public void setId(DiscoverId id) {
		this.id = id;
	}

}