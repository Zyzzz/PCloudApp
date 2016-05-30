package imu.pcloud.app.model;

/**
 * Created by guyu on 2016/5/29.
 */
public class LocalPlan extends Plan {
    String name;

    public LocalPlan(String name) {
        this.name = name;
    }

    public LocalPlan(String startTimeString, String endTimeString, String content, String title, String name) {
        super(startTimeString, endTimeString, content, title);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
