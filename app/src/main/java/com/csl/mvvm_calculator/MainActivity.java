package com.csl.mvvm_calculator;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

public class MainActivity extends AppCompatActivity {

    private EditText etNumber;
    private TextView tvCounter;
    private TextView tvCountDown;
    private GuessViewModel viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewAndSetListener();
        bindViewData();
        initial();
    }

    private void findViewAndSetListener() {
        tvCounter = findViewById(R.id.tv_counter);
        tvCountDown = findViewById(R.id.tv_countDown);
        etNumber = findViewById(R.id.et_number);
        Button buttonGuess = findViewById(R.id.button_guess);

        buttonGuess.setOnClickListener((view)->{
            viewModel.setNumber(Integer.parseInt(etNumber.getText().toString()));
            etNumber.setText("");
        });
    }

    private void bindViewData() {
        //Be careful the ViewModelProviders.of(XXX)
        //XXX could be (Fragment)Activity or Fragment
        //BUT that means the lifeCycle of you ViewModel is same as XXX
        //if XXX is fragment, your ViewModel will be recycle while your fragment be recycled
        //that means you cant use it
        viewModel = ViewModelProviders.of(this).get(GuessViewModel.class);
        viewModel.getCounter().observe(this, (counter)-> {
            tvCounter.setText("counter: " +counter);
        });

        viewModel.getMessage().observe(this, (msg) -> {
            new AlertDialog.Builder(this)
                    .setTitle("Result")
                    .setMessage(msg)
                    .setPositiveButton("Restart", (dialog, which) -> {
                        viewModel.reset();
                    })
                    .setNeutralButton("OK", null)
                    .show();
        });

        viewModel.getCountDown().observe(this, (timer) -> {
            tvCountDown.setText("time left: "+ timer );
            Log.d("timer", "time left: "+ timer);
        });
    }

    private void initial() {
        viewModel.reset();
        new CountDownTask().execute();
    }


    private class CountDownTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {

            int number = 20;
            while (number-->0) {
                //if you change LiveData in background thread, you need you postValue
                viewModel.getCountDown().postValue(number);

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }
    }

}
