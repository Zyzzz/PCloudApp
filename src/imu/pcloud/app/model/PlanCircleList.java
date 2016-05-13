package imu.pcloud.app.model;



import imu.pcloud.app.been.PlanCircle;

import java.util.ArrayList;
import java.util.List;

public class PlanCircleList extends BaseModel {

	List<PlanCircle> planCircles = new ArrayList();

	public List<PlanCircle> getPlanCircles() {
		return planCircles;
	}

	public void setPlanCircles(List<PlanCircle> planCircles) {
		this.planCircles = planCircles;
	}
	
	
}
