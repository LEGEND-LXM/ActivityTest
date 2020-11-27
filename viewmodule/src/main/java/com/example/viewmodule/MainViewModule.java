package com.example.viewmodule;

import android.util.Log;

import androidx.lifecycle.ViewModel;


public class MainViewModule extends ViewModel {
    private int counter;

    public MainViewModule(int countReserved) {
        counter = countReserved;
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public void pushOneCounter() {
        this.counter += 1 ;
    }


}
