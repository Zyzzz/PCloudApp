package imu.pcloud.app.model;

import java.util.Date;

/**
 * Created by guyu on 2016/6/9.
 */
public class TimeConfig {
    int planId;
    int planMode;
    int timeMode;
    //plan mode
    final public static int MODE_PERSONAL_PLAN = 0;
    final public static int MODE_MULTI_PLAN = 1;
    //time mode
    final public static int MODE_WEEKS = 0;
    final public static int MODE_DAYS = 1;
    final public static int MODE_ALL = 2;
    boolean []weeks = new boolean[8];
    Date startDate;
    Date endDate;
    public int getPlanId() {
        return planId;
    }

    public void setPlanId(int planId) {
        this.planId = planId;
    }

    public int getPlanMode() {
        return planMode;
    }

    public void setPlanMode(int planMode) {
        this.planMode = planMode;
    }

    public int getTimeMode() {
        return timeMode;
    }

    public void setTimeMode(int timeMode) {
        this.timeMode = timeMode;
    }

    public boolean[] getWeeks() {
        return weeks;
    }

    public void setWeeks(boolean[] weeks) {
        this.weeks = weeks;
    }
}
