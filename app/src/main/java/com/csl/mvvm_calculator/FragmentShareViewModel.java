package com.csl.mvvm_calculator;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class FragmentShareViewModel extends ViewModel {

    private MutableLiveData<Boolean> switchLiveData = new MutableLiveData<>();


    public MutableLiveData<Boolean> getSwitchLiveData() {
        return switchLiveData;
    }
}
