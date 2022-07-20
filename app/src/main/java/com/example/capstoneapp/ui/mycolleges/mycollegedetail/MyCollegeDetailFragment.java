package com.example.capstoneapp.ui.mycolleges.mycollegedetail;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.capstoneapp.R;
import com.example.capstoneapp.model.College;
import com.example.capstoneapp.model.CollegeApplicationTask;
import com.example.capstoneapp.ui.UiUtils;
import com.example.capstoneapp.ui.collegesearch.collegedetail.CollegeDetailFragment;
import com.example.capstoneapp.ui.mycolleges.mycollegedetail.taskmgmt.CollegeTaskMgmtFragment;

import org.parceler.Parcel;
import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

public class MyCollegeDetailFragment extends Fragment {

    private static final String TAG = "MyCollegeDetailFragment";
    private static final String USERID = "userid";
    private static final String COLLEGE_ID = "collegeid";
    private static final String COLLEGE = "college";

    private MyCollegeDetailViewModel myCollegeDetailViewModel;
    private List<CollegeApplicationTask> applicationStepsList = new ArrayList<>();

    private TasksAdapter tasksAdapter;
    private RecyclerView rvTasks;
    private RecyclerView.LayoutManager layoutManager;
    private ImageView ivFavDetailThumbnail;
    private TextView tvFavDetailName;
    private TextView tvFavDetailLocation;
    private TextView tvViewDetails;

    private String userId = "";
    private Integer collegeId = 0;
    private College college;

    private Button manageTaskButton;

    public static MyCollegeDetailFragment newInstance(String userId, Integer collegeId, College college) {
        MyCollegeDetailFragment f = new MyCollegeDetailFragment();
        Bundle args = new Bundle();
        args.putString(USERID, userId);
        args.putInt(COLLEGE_ID, collegeId);
        args.putParcelable(COLLEGE, Parcels.wrap(college));
        f.setArguments(args);
        return f;
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_my_college_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments().containsKey(USERID)){
            userId = getArguments().getString(USERID);
        }
        Log.i(TAG, String.valueOf(getArguments().containsKey(COLLEGE_ID)));
        if (getArguments().containsKey(COLLEGE_ID)){
            collegeId = getArguments().getInt(COLLEGE_ID);
        }
        if (getArguments().containsKey(COLLEGE)){
            college = Parcels.unwrap(getArguments().getParcelable(COLLEGE));
        }
        // set up view model
        MyCollegeDetailViewModelFactory factory = new MyCollegeDetailViewModelFactory(userId, collegeId);
        myCollegeDetailViewModel = new ViewModelProvider( this, factory).get(MyCollegeDetailViewModel.class);
        myCollegeDetailViewModel.getCollegeTasksLiveData().observe(getActivity(), new Observer<List<CollegeApplicationTask>>() {
            @Override
            public void onChanged(List<CollegeApplicationTask> tasks) {
                applicationStepsList = tasks;
                tasksAdapter.setTasks(applicationStepsList);
            }
        });

        // Set up recycler view
        rvTasks = view.findViewById(R.id.rvToDo);
        tasksAdapter = new TasksAdapter(getContext());
        layoutManager = new LinearLayoutManager(getContext());
        rvTasks.setLayoutManager(layoutManager);
        rvTasks.setAdapter(tasksAdapter);

        ivFavDetailThumbnail = view.findViewById(R.id.ivFavDetailThumbnail);
        tvFavDetailName = view.findViewById(R.id.tvFavDetailName);
        tvFavDetailLocation = view.findViewById(R.id.tvFavDetailLocation);
        tvViewDetails = view.findViewById(R.id.tvViewDetails);
        manageTaskButton = view.findViewById(R.id.manageTaskBtn);
        manageTaskButton.setOnClickListener(v -> {
            Fragment fragment = CollegeTaskMgmtFragment.newInstance(userId,collegeId);
            ((FragmentActivity) getActivity()).getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.flContainer, fragment)
                    .addToBackStack(null)
                    .commit();
        });

        UiUtils.setViewImage(getContext(), ivFavDetailThumbnail, college.getThumbnail(), null, R.drawable.college_black_48);
        UiUtils.setViewText(getContext(), tvFavDetailName, college.getName());
        UiUtils.setViewText(getContext(), tvFavDetailLocation, college.getLocation());
        tvViewDetails.setOnClickListener(this::onViewDetailsClick);
        myCollegeDetailViewModel.getAllApplicationSteps(userId,collegeId);
    }

    private void onViewDetailsClick(View view) {
        Fragment fragment = CollegeDetailFragment.newInstance(college);
        ((FragmentActivity) getContext()).getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.flContainer, fragment)
                .addToBackStack(null)
                .commit();
    }
}
