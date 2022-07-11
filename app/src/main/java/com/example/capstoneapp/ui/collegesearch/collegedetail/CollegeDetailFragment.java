package com.example.capstoneapp.ui.collegesearch.collegedetail;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.capstoneapp.College;
import com.example.capstoneapp.R;
import com.example.capstoneapp.Utilities;
import com.google.android.material.tabs.TabLayout;

public class CollegeDetailFragment extends Fragment {

    public static final String TAG = "CollegeDetailFragment";

    private CollegeDetailViewModel viewModel;

    private College college;

    private ImageView ivThumbnail;
    private TextView tvName;
    private TextView tvLocation;

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private CollegeDetailFragmentPagerAdapter fragmentPagerAdapter;

    private CollegeDetailFragment(College college) {
        this.college = college;
    }

    public static CollegeDetailFragment newInstance(College college) {
        return new CollegeDetailFragment(college);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_college_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(CollegeDetailViewModel.class);

        FragmentActivity activity = getActivity();
        if (activity != null) {
            activity.setTitle("");
        }

        ivThumbnail = view.findViewById(R.id.ivDetailThumbnail);
        tvName = view.findViewById(R.id.tvDetailName);
        tvLocation = view.findViewById(R.id.tvDetailLocation);

        // Tabs ViewPager
        viewPager = view.findViewById(R.id.viewpager);
        tabLayout = view.findViewById(R.id.tabs);

        fragmentPagerAdapter = new CollegeDetailFragmentPagerAdapter(getChildFragmentManager(), getContext());
        viewPager.setAdapter(fragmentPagerAdapter);

        tabLayout.setupWithViewPager(viewPager);

        Utilities.setViewImage(getContext(), ivThumbnail, college.getThumbnail(), null, R.drawable.college_black_48);
        Utilities.setViewText(getContext(), tvName, college.getName());
        Utilities.setViewText(getContext(), tvLocation, college.getLocation());
    }
}