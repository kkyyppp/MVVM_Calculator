package com.csl.mvvm_calculator;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;


public class BottomFragment extends Fragment {

    private static BottomFragment instance;
    public static BottomFragment newInstance() {
        if (instance==null)
            instance = new BottomFragment();
        return instance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_bottom, container, false);
        Switch switchFragmentShared = view.findViewById(R.id.switch_shared);
        FragmentShareViewModel shareViewModel = ViewModelProviders.of(getActivity()).get(FragmentShareViewModel.class);
        shareViewModel.getSwitchLiveData().observe(this, (ischecked)->{
            Log.d("MVVM", "BottomFragment switch observe: "+ ischecked);
            switchFragmentShared.setChecked(ischecked);
        });

        return view;
    }
}
