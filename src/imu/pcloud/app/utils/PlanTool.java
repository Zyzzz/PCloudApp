package imu.pcloud.app.utils;

import android.content.Context;
import android.content.SharedPreferences;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import imu.pcloud.app.been.PersonalPlan;
import imu.pcloud.app.model.Plan;
import imu.pcloud.app.model.Plans;

import java.util.ArrayList;

/**
 * Created by guyu on 2016/5/17.
 */
public class PlanTool {
    Gson gson = new GsonBuilder().create();
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Plans plans = new Plans();
    PlanTool(Context context, String name) {
        sharedPreferences = context.getSharedPreferences(name, 0);
        editor = sharedPreferences.edit();
    }

    void setPlans(PersonalPlan personalPlan) {
        editor.putString(personalPlan.getId() + "", personalPlan.getContent());
        editor.commit();
    }

    ArrayList<Plan> getPlans(int planId) {
        String planString = sharedPreferences.getString(planId + "", "");
        plans.setByJsonString(planString);
        return plans.getPlans();
    }
}
