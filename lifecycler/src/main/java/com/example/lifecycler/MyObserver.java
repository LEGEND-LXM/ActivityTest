package com.example.lifecycler;

import android.app.Activity;
import android.util.Log;
import android.view.View;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.OnLifecycleEvent;

import static androidx.lifecycle.Lifecycle.State;

public class MyObserver implements LifecycleObserver {

    private Activity mActivity;

    public MyObserver(Activity activity) {
        // 获取监测 Activity 实例
        mActivity = activity;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    private void activityStart(LifecycleOwner owner) {
        if (owner.getLifecycle().getCurrentState() == State.STARTED) {
            Log.d("MyObserverTest", "activityStart");
        }
}

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    private void activityStop(LifecycleOwner owner) {

        Log.d("MyObserverTest", "activityStop");
    }
}
