package com.csl.mvvm_calculator;

import androidx.lifecycle.MutableLiveData;

public class GuessModel {
    private MutableLiveData<Integer> number = new MutableLiveData<>();


    public MutableLiveData<Integer> getNumber() {
        return number;
    }
}
