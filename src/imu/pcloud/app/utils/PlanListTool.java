package imu.pcloud.app.utils;

import imu.pcloud.app.model.Plan;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by guyu on 2016/5/29.
 */
public class PlanListTool {

    public static <T extends Plan> void  sort(ArrayList<T> planArrayList) {
        Collections.sort(planArrayList, new Comparator<T>() {
            @Override
            public int compare(T lhs, T rhs) {
                return lhs.compareTo(rhs);
            }
        });
    }
}
