package com.moral.airtree.utils;

import android.app.Activity;

import java.util.Stack;

/**
 * Created by bin.shen on 5/20/16.
 */
public class ScreenManager {
    private static Stack<Activity> activityStack;
    private static ScreenManager instance;

    private ScreenManager() {
    }

    public static ScreenManager getScreenManager() {
        if(instance == null) {
            instance = new ScreenManager();
        }
        return instance;
    }

    public void popActivity(Activity activity) {
        if(activity != null) {
            activity.finish();
            activityStack.remove(activity);
            activity = null;
        }
    }

    public Activity currentActivity() {
        Activity activity = (Activity)activityStack.firstElement();
        return activity;
    }

    public void pushActivity(Activity activity) {
        if(activityStack == null) {
            activityStack = new Stack();
        }
        activityStack.add(activity);
    }

    public void popAllActivityExceptOne(Class cls) {
        Activity activity = currentActivity();
        if(activity == null) {
            return;
        }
        if(!activity.getClass().equals(cls)) {
            popActivity(activity);
        }
    }
}
