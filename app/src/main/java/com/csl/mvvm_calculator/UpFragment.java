package com.csl.mvvm_calculator;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;


public class UpFragment extends Fragment {

    private static UpFragment instance;
    public static UpFragment newInstance() {

        if (instance == null)
            instance = new UpFragment();
        return instance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_up, container, false);
        TextView textViewFragmentShare = view.findViewById(R.id.tv_fragment_activity_share);
        Switch switchFragmentShared = view.findViewById(R.id.switch_shared);


        //you need set ViewModelProviders.of( Activity ) for "letting the lifecycle of ViewModel as same as Activity (not fragment)"
        // and "letting activity share data with fragment"
        GuessViewModel viewModel = ViewModelProviders.of(getActivity()).get(GuessViewModel.class);
        // //you "must" set  observe(Fragment) for letting the lifecycle of observer as same as Fragment (not activity)
        viewModel.getTimer().observe(this, (timer)-> {
            textViewFragmentShare.setText("Timer: "+ timer );
            Log.d("MVVM", "UpFragment timer observe: "+ timer);
        });


        FragmentShareViewModel shareViewModel = ViewModelProviders.of(getActivity()).get(FragmentShareViewModel.class);
        switchFragmentShared.setOnCheckedChangeListener((buttonView, isChecked) ->{
            Log.d("MVVM", "UpFragment switch changed: "+ isChecked);
            shareViewModel.getSwitchLiveData().setValue(isChecked);
        });

        return view;
    }

}
