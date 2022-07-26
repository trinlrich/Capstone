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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.capstoneapp.model.College;
import com.example.capstoneapp.R;
import com.example.capstoneapp.ui.UiUtils;
import com.example.capstoneapp.ui.collegesearch.CollegeSearchViewModel;
import com.google.android.material.tabs.TabLayout;

import org.parceler.Parcels;

public class CollegeDetailFragment extends Fragment {

    public static final String TAG = "CollegeDetailFragment";
    private static final String COLLEGE = "college";

    private CollegeSearchViewModel viewModel;

    private College college;

    private ImageView ivThumbnail;
    private TextView tvName;
    private TextView tvLocation;
    private Button btnFavorite;

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private CollegeDetailFragmentPagerAdapter fragmentPagerAdapter;

    public static CollegeDetailFragment newInstance(College college) {
        CollegeDetailFragment f = new CollegeDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable(COLLEGE, Parcels.wrap(college));
        f.setArguments(args);
        return f;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_college_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments().containsKey(COLLEGE)){
            college = Parcels.unwrap(getArguments().getParcelable(COLLEGE));
        }

        viewModel = new ViewModelProvider(requireActivity()).get(CollegeSearchViewModel.class);

        FragmentActivity activity = getActivity();
        if (activity != null) {
            activity.setTitle("");
        }

        ivThumbnail = view.findViewById(R.id.ivDetailThumbnail);
        tvName = view.findViewById(R.id.tvDetailName);
        tvLocation = view.findViewById(R.id.tvDetailLocation);
        btnFavorite = view.findViewById(R.id.btnFavorite);

        // Tabs ViewPager
        viewPager = view.findViewById(R.id.viewpager);
        tabLayout = view.findViewById(R.id.tabs);

        fragmentPagerAdapter = new CollegeDetailFragmentPagerAdapter(getChildFragmentManager(), getContext(), college);
        viewPager.setAdapter(fragmentPagerAdapter);

        tabLayout.setupWithViewPager(viewPager);

        UiUtils.setViewImage(getContext(), ivThumbnail, college.getThumbnail(), null, R.drawable.college_black_48);
        UiUtils.setViewText(getContext(), tvName, college.getName());
        UiUtils.setViewText(getContext(), tvLocation, college.getLocation());
        btnFavorite.setOnClickListener(this::onFavoriteClick);
    }

    private void onFavoriteClick(View view) {
        viewModel.updateFavCollege(college);
    }
}
