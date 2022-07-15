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

public class OverviewFragment extends Fragment {

    private OverviewViewModel viewModel;
    private College college;
    private TextView tvCollegeType;
    private TextView tvDegreeType;
    private TextView tvMission;
    private TextView tvAcceptRate;
    private TextView tvUndergradEnroll;
    private TextView tvWebPage;

    public OverviewFragment(College college) {
        this.college = college;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_overview, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvCollegeType = view.findViewById(R.id.tvCollegeType);
        tvDegreeType = view.findViewById(R.id.tvDegreeType);
        tvMission = view.findViewById(R.id.tvMission);
        tvAcceptRate = view.findViewById(R.id.tvAvgAcceptRate);
        tvUndergradEnroll = view.findViewById(R.id.tvUndergradEnroll);
        tvWebPage = view.findViewById(R.id.tvWebpage);

        UiUtils.setViewText(getContext(), tvCollegeType, college.getCollegeTypeAsText());
        UiUtils.setViewText(getContext(), tvDegreeType, college.getDegreeType());
        UiUtils.setViewText(getContext(), tvMission, college.getMission());
        UiUtils.setViewText(getContext(), tvAcceptRate, String.valueOf(college.getAcceptRateAsText()));
        UiUtils.setViewText(getContext(), tvUndergradEnroll, String.valueOf(college.getUndergradEnroll()));
        UiUtils.setViewText(getContext(), tvWebPage, college.getWebpage());
    }
}