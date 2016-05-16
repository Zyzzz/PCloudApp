package imu.pcloud.app.model;


import imu.pcloud.app.been.SharingRecord;

import java.util.ArrayList;
import java.util.List;

public class SharingList extends BaseModel{
	
	List<SharingRecord> sharingRecords = new ArrayList();
	

	public SharingList() {
		super();
	}

	public SharingList(List<SharingRecord> sharingRecords) {
		super();
		this.sharingRecords = sharingRecords;
	}

	public List<SharingRecord> getSharingRecords() {
		return sharingRecords;
	}

	public void setSharingRecords(List<SharingRecord> sharingRecords) {
		this.sharingRecords = sharingRecords;
	}
	
	
}
