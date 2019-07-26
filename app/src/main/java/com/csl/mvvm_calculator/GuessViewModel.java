package com.csl.mvvm_calculator;

import java.util.Random;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class GuessViewModel extends ViewModel {
    private boolean isInitialed = false;
    private int secret = 0;
    private MutableLiveData<Integer> counter = new MutableLiveData<>();
    private MutableLiveData<Integer> timer = new MutableLiveData<>();
    private MutableLiveData<String> message = new MutableLiveData<>();

    public void initial() {
        //it will be trigger in onCreate(),
        // to avoid do the initial function twice while the activity is re-created for a configuration change(onSaveInstanceState() ->  onCreate())
        if (isInitialed)
            return;

        secret = new Random().nextInt(10)+1;
        counter.setValue(0);
        timer.setValue(0);
        isInitialed = true;
    }

    public void reset() {
        secret = new Random().nextInt(10)+1;
        counter.setValue(0);
        timer.setValue(0);
    }

    public void setNumber(int value) {

        String msg = "Yes, you got it";
        if (value < secret) {
            msg = "smaller";
        }
        else if (value > secret) {
            msg = "bigger";
        }

        message.setValue(msg);
        counter.setValue(counter.getValue()+1);
    }


    public MutableLiveData<String> getMessage() {
        return message;
    }


    public MutableLiveData<Integer> getCounter() {
        return counter;
    }

    public MutableLiveData<Integer> getTimer() {
        return timer;
    }
}
