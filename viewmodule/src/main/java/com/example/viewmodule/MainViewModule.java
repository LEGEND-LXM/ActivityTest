package com.example.viewmodule;

import android.content.Intent;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


public class MainViewModule extends ViewModel {

    private MutableLiveData<Integer> count = new MutableLiveData<>();

    public MainViewModule(int countReserved) {
        getCount().setValue(countReserved);
    }

    public int getCounter() {
        Integer value = getCount().getValue();
        return (value != null ? value:0);
    }

    public void setCounter(int counter) {

        getCount().postValue(counter);
    }

    public void pushOneCounter() {
        //
        Integer value = getCount().getValue() ;
        value = ( value != null ? value:0);
        getCount().postValue(value+1);
    }

    public void clearCountValue() {

        getCount().postValue(0);
    }

    public MutableLiveData<Integer> getCount() {
        return count;
    }


}
