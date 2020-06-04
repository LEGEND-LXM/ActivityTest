package com.example.broadcastbest;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

public class ActivityCollector {
    public static List<Activity> activities = new ArrayList<>();

    public static void addActivity(Activity activity) {  //将活动添加到集合中
        activities.add(activity);
    }

    public static void removeActivity(Activity activity) {  //将获得从集合中移除
        activities.remove(activity);
    }

    public static void finishAll() {  //结束所以集合中的活动
        for(Activity activity : activities) {
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
    }
}
