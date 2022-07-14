package com.example.capstoneapp.ui.collegesearch.filter;


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
import android.widget.Button;
import com.example.capstoneapp.R;
import com.example.capstoneapp.ui.collegesearch.CollegeSearchViewModel;
import com.google.android.material.tabs.TabLayout;


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

        viewModel = new ViewModelProvider(requireActivity()).get(CollegeSearchViewModel.class);

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
        getActivity().getSupportFragmentManager().popBackStack();
    }
}
