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

public class CostsFragment extends Fragment {

    private CostsViewModel viewModel;
    private College college;
    private TextView tvAvgCost;
    private TextView tvTuitionIn;
    private TextView tvTuitionOut;
    private TextView tvFedLoanPercent;
    private TextView tvPellPercent;
    private TextView tvMedStudentDebt;
    private TextView tvMedParentDebt;

    public CostsFragment(College college) {
        this.college = college;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_costs, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvAvgCost = view.findViewById(R.id.tvAvgCost);
        tvTuitionIn = view.findViewById(R.id.tvTuitionIn);
        tvTuitionOut = view.findViewById(R.id.tvTuitionOut);
        tvFedLoanPercent = view.findViewById(R.id.tvFedLoanPercent);
        tvPellPercent = view.findViewById(R.id.tvPellPercent);
        tvMedStudentDebt = view.findViewById(R.id.tvMedStudentDebt);
        tvMedParentDebt = view.findViewById(R.id.tvMedParentDebt);

        UiUtils.setViewText(getContext(), tvAvgCost,college.getAvgCostAsText());
        UiUtils.setViewText(getContext(), tvTuitionIn, college.getTuitionInAsText());
        UiUtils.setViewText(getContext(), tvTuitionOut, college.getTuitionOutAsText());
        UiUtils.setViewText(getContext(), tvFedLoanPercent, college.getFedLoanPercentAsText());
        UiUtils.setViewText(getContext(), tvPellPercent, college.getPellPercentAsText());
        UiUtils.setViewText(getContext(), tvMedStudentDebt, college.getMedStudentDebtAsText());
        UiUtils.setViewText(getContext(), tvMedParentDebt, college.getMedParentDebtAsText());
    }
}