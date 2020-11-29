package com.example.viewmodule;

import android.content.Intent;
import android.util.Log;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;


public class MainViewModule extends ViewModel {

    private final MutableLiveData<Integer> count = new MutableLiveData<>();

    private final   MutableLiveData<String> counter = (MutableLiveData<String>) Transformations.map(count, new Function<Integer, String>() {
        @Override
        public String apply(Integer input) {
            // 返回String
            return input.toString();
        }
    });

    private final MutableLiveData<String> countSwitchMap = (MutableLiveData<String>) Transformations
            .switchMap(count, new Function<Integer, LiveData<String>>() {
                @Override
                public LiveData<String> apply(Integer input) {
                    MutableLiveData<String> stringMutableLiveData = new Repository().getString(input.toString());
                    return stringMutableLiveData;
                }
            });

    public MainViewModule(int countReserved) {
        getCount().setValue(countReserved);
    }

    public int getCounterValue() {
        Integer value = getCount().getValue();
        return (value != null ? value:0);
    }
    // set 方法尽量不要公开，以保护数据
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

        count.postValue(0);
    }

    public MutableLiveData<Integer> getCount() {
        return count;
    }

    public MutableLiveData<String> getCounter() {
        return counter;
    }

    public MutableLiveData<String> getCountSwitchMap() {
        return countSwitchMap;
    }


    // 内部类，用于模拟使用另外的方法产生 LiveData 对象
    class Repository {
        public MutableLiveData<String> getString(String string) {
            MutableLiveData<String> liveData = new MutableLiveData<>();
            liveData.postValue(string);
            return liveData;
        }
    }
}
