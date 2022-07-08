package com.example.capstoneapp.ui.profile;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.example.capstoneapp.ParseFirebaseUser;
import com.example.capstoneapp.R;
import com.example.capstoneapp.Utilities;
import com.parse.ParseFile;

public class ProfileFragment extends Fragment {

    public static final String TAG = "ProfileFragment";

    private ProfileViewModel viewModel;

    private ImageView ivFragProfileImage;
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

        getActivity().setTitle(R.string.profile_title);

        ivFragProfileImage = view.findViewById(R.id.ivFragProfileImage);
        tvUserName = view.findViewById(R.id.tvFragUserName);
        tvFirstName = view.findViewById(R.id.tvFirstName);
        tvLastName = view.findViewById(R.id.tvLastName);
        tvDegreeSeeking = view.findViewById(R.id.tvDegreeSeeking);

        viewModel.getUserProfileInfo();

        // User observer
        Observer<ParseFirebaseUser> userObserver = new Observer<ParseFirebaseUser>() {
            @Override
            public void onChanged(ParseFirebaseUser user) {
                if (user == null) {
                    Log.e(TAG, "No user found");
                } else {
                    tvUserName.setText(user.getFirstName() + " " + user.getLastName());
                    tvFirstName.setText(user.getString("firstName"));
                    tvLastName.setText(user.getLastName());
                    tvDegreeSeeking.setText(user.getDegreeSeeking());
                    ParseFile profileImage = user.getProfileImage();
                    if (profileImage != null) {
                        Utilities.setImage(getContext(), ivFragProfileImage, profileImage.getUrl(), new CircleCrop(), R.drawable.profile_black_48);
                    }
                }
            }
        };
        viewModel.user.observe(getViewLifecycleOwner(), userObserver);
    }
}