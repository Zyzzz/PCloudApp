package imu.pcloud.app.model;

import imu.pcloud.app.been.MultiPlan;
import imu.pcloud.app.been.MultiPlanMember;

import java.util.ArrayList;
import java.util.List;

public class MultiPlanList extends BaseModel {
	private List<MultiPlan> multiPlans = new ArrayList<MultiPlan>();
	private List<MultiPlanMember> multiPlanMembers = new ArrayList<MultiPlanMember>();
	MultiPlan multiPlan = new MultiPlan();
	public MultiPlan getMultiPlan() {
		return multiPlan;
	}
	public void setMultiPlan(MultiPlan multiPlan) {
		this.multiPlan = multiPlan;
	}
	public List<MultiPlan> getMultiPlans() {
		return multiPlans;
	}
	public void setMultiPlans(List<MultiPlan> multiPlans) {
		this.multiPlans = multiPlans;
	}
	public List<MultiPlanMember> getMultiPlanMembers() {
		return multiPlanMembers;
	}
	public void setMultiPlanMembers(List<MultiPlanMember> multiPlanMembers) {
		this.multiPlanMembers = multiPlanMembers;
	}
	
}
