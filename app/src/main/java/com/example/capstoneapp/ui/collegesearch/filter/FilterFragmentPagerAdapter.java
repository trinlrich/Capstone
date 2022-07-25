package com.example.capstoneapp.ui.collegesearch.filter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.capstoneapp.R;
import com.example.capstoneapp.ui.collegesearch.filter.tabs.MissionFragment;
import com.example.capstoneapp.ui.collegesearch.filter.tabs.StateFragment;
import com.example.capstoneapp.ui.collegesearch.filter.tabs.TypeFragment;

public class FilterFragmentPagerAdapter extends androidx.fragment.app.FragmentPagerAdapter {

    Context context;

    public FilterFragmentPagerAdapter(@NonNull FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new StateFragment();
                break;
            case 1:
                fragment = new TypeFragment();
                break;
            case 2:
                fragment = new MissionFragment();
                break;
        }
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
            title = context.getString(R.string.state_tab);
        } else if (position == 1) {
            title = context.getString(R.string.type_tab);
        } else if (position == 2) {
            title = context.getString(R.string.mission_tab);
        }
        return title;
    }
}
