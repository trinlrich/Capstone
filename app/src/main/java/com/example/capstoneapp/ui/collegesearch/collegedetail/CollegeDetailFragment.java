package com.example.capstoneapp.ui.collegesearch.collegedetail;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.example.capstoneapp.ParseFirebaseUser;
import com.example.capstoneapp.R;
import com.example.capstoneapp.Utilities;
import com.example.capstoneapp.ui.profile.ProfileViewModel;
import com.google.android.material.tabs.TabLayout;
import com.parse.ParseFile;

public class CollegeDetailFragment extends Fragment {

    public static final String TAG = "CollegeDetailFragment";

    private CollegeDetailViewModel viewModel;

    private ImageView ivThumbnail;
    private TextView tvName;
    private TextView tvLocation;

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private FragmentPagerAdapter fragmentPagerAdapter;

    public static CollegeDetailFragment newInstance() {
        return new CollegeDetailFragment();
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

        ivThumbnail = view.findViewById(R.id.ivDetailThumbnail);
        tvName = view.findViewById(R.id.tvDetailName);
        tvLocation = view.findViewById(R.id.tvDetailLocation);

        // Tabs ViewPager
        viewPager = view.findViewById(R.id.viewpager);
        tabLayout = view.findViewById(R.id.tabs);

        fragmentPagerAdapter = new FragmentPagerAdapter(((FragmentActivity) getContext()).getSupportFragmentManager());
        viewPager.setAdapter(fragmentPagerAdapter);

        tabLayout.setupWithViewPager(viewPager);
    }
}