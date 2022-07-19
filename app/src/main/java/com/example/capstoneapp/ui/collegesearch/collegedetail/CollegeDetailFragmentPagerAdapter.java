package com.example.capstoneapp.ui.collegesearch.collegedetail;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.capstoneapp.R;
import com.example.capstoneapp.model.College;
import com.example.capstoneapp.ui.collegesearch.collegedetail.tabs.AcademicsFragment;
import com.example.capstoneapp.ui.collegesearch.collegedetail.tabs.CostsFragment;
import com.example.capstoneapp.ui.collegesearch.collegedetail.tabs.OverviewFragment;

public class CollegeDetailFragmentPagerAdapter extends androidx.fragment.app.FragmentPagerAdapter {

    Context context;
    College college;

    public CollegeDetailFragmentPagerAdapter(@NonNull FragmentManager fm, Context context, College college) {
        super(fm);
        this.context = context;
        this.college = college;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        if (position == 0)
            fragment = new OverviewFragment(college);
        else if (position == 1)
            fragment = new AcademicsFragment(college);
        else if (position == 2)
            fragment = new CostsFragment(college);

        return fragment;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title = null;
        if (position == 0) {
            title = context.getString(R.string.overview_tab);
        } else if (position == 1) {
            title = context.getString(R.string.academics_tab);
        } else if (position == 2) {
            title = context.getString(R.string.costs_tab);
        }
        return title;
    }
}
