package imu.pcloud.app.model;

import imu.pcloud.app.utils.Information;

public class BaseModel {

	protected String result;
	protected int status;
	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
		setResult(Information.getInstance().getErrorInfo(status));
	}
}
