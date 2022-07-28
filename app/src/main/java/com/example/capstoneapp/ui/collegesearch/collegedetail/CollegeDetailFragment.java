package com.example.capstoneapp.ui.collegesearch.collegedetail;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.example.capstoneapp.model.College;
import com.example.capstoneapp.R;
import com.example.capstoneapp.model.ParseFirebaseUser;
import com.example.capstoneapp.ui.UiUtils;
import com.example.capstoneapp.ui.collegesearch.CollegeSearchViewModel;
import com.example.capstoneapp.ui.collegesearch.filter.FilterFragment;
import com.example.capstoneapp.ui.mycolleges.MyCollegesFragment;
import com.example.capstoneapp.ui.mycolleges.mycollegedetail.MyCollegeDetailFragment;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.parse.ParseFile;

import org.parceler.Parcels;

import java.util.List;

public class CollegeDetailFragment extends Fragment {

    public static final String TAG = "CollegeDetailFragment";
    private static final String COLLEGE = "college";

    private CollegeSearchViewModel viewModel;

    private College college;

    private ImageView ivThumbnail;
    private TextView tvName;
    private TextView tvLocation;
    private Button btnAddFavorite;
    private ImageButton btnUnfavorite;
    private Button btnGoFavorites;

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
        btnAddFavorite = view.findViewById(R.id.btnAddFavorite);
        btnUnfavorite = view.findViewById(R.id.btnUnfavorite);
        btnGoFavorites = view.findViewById(R.id.btnGoFavorites);


        // Tabs ViewPager
        viewPager = view.findViewById(R.id.viewpager);
        tabLayout = view.findViewById(R.id.tabs);

        fragmentPagerAdapter = new CollegeDetailFragmentPagerAdapter(getChildFragmentManager(), getContext(), college);
        viewPager.setAdapter(fragmentPagerAdapter);

        tabLayout.setupWithViewPager(viewPager);

        UiUtils.setViewImage(getContext(), ivThumbnail, college.getThumbnail(), null, R.drawable.college_black_48);
        UiUtils.setViewText(getContext(), tvName, college.getName());
        UiUtils.setViewText(getContext(), tvLocation, college.getLocation());
        btnAddFavorite.setOnClickListener(this::onFavoriteClick);
        btnUnfavorite.setOnClickListener(this::onFavoriteClick);
        btnGoFavorites.setOnClickListener(this::onGoFavoriteClick);
        setFavoriteButtons();

        Observer<List<College>> userObserver = college -> {
            if (college == null) {
                Log.e(TAG, "No colleges found");
            } else {
                setFavoriteButtons();
            }
        };
        viewModel.getAllFavCollegesLiveData().observe(getActivity(), userObserver);

    }

    private void setFavoriteButtons() {
        if (college.isFavorite()) {
            btnAddFavorite.setVisibility(View.GONE);
            btnUnfavorite.setVisibility(View.VISIBLE);
            btnGoFavorites.setVisibility(View.VISIBLE);
        } else {
            btnAddFavorite.setVisibility(View.VISIBLE);
            btnUnfavorite.setVisibility(View.GONE);
            btnGoFavorites.setVisibility(View.GONE);
        }
    }

    private void onFavoriteClick(View view) {
        viewModel.updateFavCollege(college);
    }

    private void onGoFavoriteClick(View view) {
        FragmentManager fragmentManager = ((FragmentActivity) getContext()).getSupportFragmentManager();
        if (fragmentManager != null) {
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.flContainer, MyCollegesFragment.newInstance())
                    .addToBackStack(null)
                    .commit();
        }
    }
}
