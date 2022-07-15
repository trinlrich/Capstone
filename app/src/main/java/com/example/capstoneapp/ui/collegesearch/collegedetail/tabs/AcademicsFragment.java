package com.example.capstoneapp.ui.collegesearch.collegedetail.tabs;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.capstoneapp.R;
import com.example.capstoneapp.model.College;
import com.example.capstoneapp.ui.UiUtils;

public class AcademicsFragment extends Fragment {

    private AcademicsViewModel viewModel;
    private College college;
    private TextView tvGradRate;
    private TextView tvAvgSat;
    private TextView tvAvgAct;

    public AcademicsFragment(College college) {
        this.college = college;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_academics, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvGradRate = view.findViewById(R.id.tvGradRate);
        tvAvgSat = view.findViewById(R.id.tvAvgSat);
        tvAvgAct = view.findViewById(R.id.tvAvgAct);

        UiUtils.setViewText(getContext(), tvGradRate, college.getGradRateAsText());
        UiUtils.setViewText(getContext(), tvAvgSat, college.getAvgSat());
        UiUtils.setViewText(getContext(), tvAvgAct, college.getAct75()); //TODO :: get avg
    }
}