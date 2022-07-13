package com.example.capstoneapp.ui.collegesearch.filter;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.capstoneapp.College;
import com.example.capstoneapp.R;
import com.example.capstoneapp.ui.collegesearch.CollegeSearchViewModel;
import com.example.capstoneapp.ui.collegesearch.collegedetail.CollegeDetailViewModel;
import com.example.capstoneapp.ui.collegesearch.filter.tabs.MissionFragment;
import com.example.capstoneapp.ui.collegesearch.filter.tabs.StateFragment;
import com.example.capstoneapp.ui.collegesearch.filter.tabs.TypeFragment;
import com.facebook.share.Share;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;
import com.parse.ParseQuery;

public class FilterFragment extends Fragment {

    public static final String TAG = "FilterFragment";

    private CollegeSearchViewModel viewModel;

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private FilterFragmentPagerAdapter fragmentPagerAdapter;
    private Button btnApply;

    private FilterFragment() {
    }

    public static FilterFragment newInstance() {
        return new FilterFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_filter, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(this).get(CollegeSearchViewModel.class);

        FragmentActivity activity = getActivity();
        if (activity != null) {
            activity.setTitle(getString(R.string.filter_title));
        }

        // Tabs ViewPager
        viewPager = view.findViewById(R.id.viewpager);
        tabLayout = view.findViewById(R.id.tabs);
        btnApply = view.findViewById(R.id.btnApply);

        fragmentPagerAdapter = new FilterFragmentPagerAdapter(getChildFragmentManager(), getContext());
        viewPager.setAdapter(fragmentPagerAdapter);

        tabLayout.setupWithViewPager(viewPager);

        btnApply.setOnClickListener(this::onApplyClick);
    }

    public void onApplyClick(View view) {
//        viewModel.getCollegesList();
        getActivity().getSupportFragmentManager().popBackStack();
    }
}