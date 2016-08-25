package imu.pcloud.app.model;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class Plans {

    public ArrayList<Plan> plans = new ArrayList<Plan>();
    //public String discribe;
    Gson gson = new GsonBuilder().create();

    public ArrayList<Plan> getPlans() {
        return plans;
    }

    public void setPlans(ArrayList<Plan> plans) {
        this.plans = plans;
    }

    public void addPlan(Plan plan) {
        plans.add(plan);
    }

    public String getJsonString() {
        return gson.toJson(plans);
    }

    public void setByJsonString(String jsonString) {
        try {
            plans = gson.fromJson(jsonString, new TypeToken<List<Plan>>(){}.getType());
        } catch (Exception e) {
            // TODO Auto-generated catch block
            plans = new ArrayList<Plan>();
            e.printStackTrace();
        }
    }
}
