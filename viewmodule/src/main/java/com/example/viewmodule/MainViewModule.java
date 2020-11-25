package com.example.viewmodule;

import android.util.Log;

import androidx.lifecycle.ViewModel;

public class MainViewModule extends ViewModel {
    private int counter = 0;

    public int getCounter() {
        Log.d("MainViewModuleTest", "getCounter"+counter);
        return counter;
    }

    public void setCounter(int counter) {
        Log.d("MainViewModuleTest", "setCounter"+counter);
        this.counter = counter;
    }

    public void pushOneCounter() {
        Log.d("MainViewModuleTest", "pushOneCounter"+counter);
        this.counter += 1 ;
    }
}
