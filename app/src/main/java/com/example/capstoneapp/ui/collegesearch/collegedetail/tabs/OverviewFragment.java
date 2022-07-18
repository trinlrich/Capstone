package com.example.capstoneapp.ui.collegesearch.collegedetail.tabs;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.Resources;
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
        tvMission.setOnClickListener(this::onMissionClicked);
        tvAcceptRate = view.findViewById(R.id.tvAvgAcceptRate);
        tvUndergradEnroll = view.findViewById(R.id.tvUndergradEnroll);
        tvWebPage = view.findViewById(R.id.tvWebpage);

        UiUtils.setViewText(getContext(), tvCollegeType, college.getCollegeTypeAsText());
        UiUtils.setViewText(getContext(), tvDegreeType, college.getDegreeType());
        UiUtils.setViewText(getContext(), tvMission, college.getRawMissionData());
        UiUtils.setViewText(getContext(), tvAcceptRate, String.valueOf(college.getAcceptRateAsText()));
        UiUtils.setViewText(getContext(), tvUndergradEnroll, String.valueOf(college.getUndergradEnrollAsText()));
        UiUtils.setViewText(getContext(), tvWebPage, college.getWebpage());
    }

    private void onMissionClicked(View view) {
        if (!tvMission.getText().equals(getString(R.string.data_not_available))) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle(getString(R.string.special_mission));
            builder.setMessage(college.getFullMission());
            builder.setCancelable(true);
            builder.setNegativeButton("Close", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }
    }
}