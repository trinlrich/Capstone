package com.example.capstoneapp.ui.profile;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.RecoverySystem;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toolbar;

import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.example.capstoneapp.model.ParseFirebaseUser;
import com.example.capstoneapp.R;
import com.example.capstoneapp.ui.UiUtils;
import com.parse.ParseFile;

public class ProfileFragment extends Fragment {

    public static final String TAG = "ProfileFragment";

    private ProfileViewModel viewModel;

    private TextView tvUserName;
    private TextView tvFirstName;
    private TextView tvLastName;
    private TextView tvDegreeSeeking;

    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(ProfileViewModel.class);

        FragmentActivity activity = ((AppCompatActivity) getActivity());
        if (activity != null) {
            activity.setTitle(R.string.profile_title);
            ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
            if (actionBar != null) {
                actionBar.setBackgroundDrawable(getContext().getDrawable(R.color.accent_blue));
                actionBar.setElevation(30);
            }
        }

        tvUserName = view.findViewById(R.id.tvFragUserName);
        tvFirstName = view.findViewById(R.id.tvFirstName);
        tvLastName = view.findViewById(R.id.tvLastName);
        tvDegreeSeeking = view.findViewById(R.id.tvDegreeSeeking);

        viewModel.getUserProfileInfo();

        // User observer
        Observer<ParseFirebaseUser> userObserver = user -> {
            if (user == null) {
                Log.e(TAG, "No user found");
            } else {
                UiUtils.setViewText(getContext(), tvUserName, user.getFirstName() + " " + user.getLastName());
                UiUtils.setViewText(getContext(), tvFirstName, user.getFirstName());
                UiUtils.setViewText(getContext(), tvLastName, user.getLastName());
                UiUtils.setViewText(getContext(), tvDegreeSeeking, user.getDegreeSeekingAsText());
            }
        };
        viewModel.user.observe(getViewLifecycleOwner(), userObserver);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        FragmentActivity activity = ((AppCompatActivity) getActivity());
        if (activity != null) {
            ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
            if (actionBar != null) {
                actionBar.setBackgroundDrawable(getContext().getDrawable(R.color.white));
                actionBar.setElevation(0);
            }
        }
    }
}
