package imu.pcloud.app.model;

import imu.pcloud.app.been.PersonalPlan;

import java.util.ArrayList;
import java.util.List;

public class PlanList extends BaseModel {


    List<PersonalPlan> personalPlans = new ArrayList();


    public PlanList() {
        super();
    }

    public PlanList(List<PersonalPlan> personalPlans) {

        this.personalPlans = personalPlans;
    }

    public List<PersonalPlan> getPersonalPlans() {
        return personalPlans;
    }

    public void setPersonalPlans(List<PersonalPlan> personalPlans) {
        this.personalPlans = personalPlans;
    }


}
