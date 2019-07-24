package com.csl.mvvm_calculator;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

public class MainActivity extends AppCompatActivity {

    private EditText etNumber;
    private TextView tvCounter, tvLastGuess;
    private TextView tvCountDown;
    private GuessViewModel viewModel;
    private GuessModel model;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewAndSetListener();
        setFragment();
        bindViewData();
        initial();
    }

    private void findViewAndSetListener() {
        tvCounter = findViewById(R.id.tv_counter);
        tvLastGuess = findViewById(R.id.tv_last_guess);
        tvCountDown = findViewById(R.id.tv_timer);
        etNumber = findViewById(R.id.et_number);
        View fragment_up = findViewById(R.id.fragment_up);
        View fragment_bottom = findViewById(R.id.fragment_bottom);

        Button buttonGuess = findViewById(R.id.button_guess);
        Button buttonSwitchShowFragmentUp = findViewById(R.id.switch_show_fragment_up);

        buttonGuess.setOnClickListener((view)->{
            int number = Integer.parseInt(etNumber.getText().toString());
            viewModel.setNumber(number);
            etNumber.setText("");

            model.getNumber().setValue(number);
        });

        buttonSwitchShowFragmentUp.setOnClickListener((view)->{
            getSupportFragmentManager().beginTransaction().remove( UpFragment.newInstance()).commit();
            buttonSwitchShowFragmentUp.setVisibility(View.GONE);
        });
    }

    private void setFragment() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.fragment_up, UpFragment.newInstance());
        ft.commit();

        FragmentTransaction ft2 = getSupportFragmentManager().beginTransaction();
        ft2.add(R.id.fragment_bottom, BottomFragment.newInstance());
        ft2.commit();
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

        viewModel.getTimer().observe(this, (timer) -> {
            tvCountDown.setText("Timer: "+ timer );
            Log.d("MVVM", "Activity timer observe: "+ timer);
        });


        model = new GuessModel();
        model.getNumber().observe(this, (counter)-> {
            tvLastGuess.setText("last guess: " +counter);
        });
    }

    private void initial() {

        viewModel.initial();
        new CountDownTask().execute();
    }


    private class CountDownTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            int timer=0;
            while (timer++<100) {
                //if you change LiveData in background thread, you need you postValue
                viewModel.getTimer().postValue(timer);

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
