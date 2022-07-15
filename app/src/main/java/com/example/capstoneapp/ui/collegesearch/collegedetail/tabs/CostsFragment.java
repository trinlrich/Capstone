package com.example.capstoneapp.ui.collegesearch.collegedetail.tabs;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.capstoneapp.R;
import com.example.capstoneapp.model.College;

public class CostsFragment extends Fragment {

    private CostsViewModel viewModel;
    private College college;

    public CostsFragment(College college) {
        this.college = college;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_costs, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(CostsViewModel.class);
        // TODO: Use the ViewModel
    }

}