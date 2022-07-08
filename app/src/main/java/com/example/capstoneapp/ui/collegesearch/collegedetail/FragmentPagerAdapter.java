package com.example.capstoneapp.ui.collegesearch.collegedetail;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.capstoneapp.ui.collegesearch.collegedetail.tabs.AcademicsFragment;
import com.example.capstoneapp.ui.collegesearch.collegedetail.tabs.CostsFragment;
import com.example.capstoneapp.ui.collegesearch.collegedetail.tabs.OverviewFragment;

public class FragmentPagerAdapter extends androidx.fragment.app.FragmentPagerAdapter {
    public FragmentPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        if (position == 0)
            fragment = new OverviewFragment();
        else if (position == 1)
            fragment = new AcademicsFragment();
        else if (position == 2)
            fragment = new CostsFragment();

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
            title = "Overview";
        } else if (position == 1) {
            title = "Academics";
        } else if (position == 2) {
            title = "Costs";
        }
        return title;
    }
}
