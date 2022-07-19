package com.example.capstoneapp.ui.mycolleges.mycollegesdetail;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.capstoneapp.R;
import com.example.capstoneapp.model.College;
import com.example.capstoneapp.model.CollegeTask;
import com.example.capstoneapp.ui.UiUtils;
import com.example.capstoneapp.ui.collegesearch.CollegeSearchViewModel;
import com.example.capstoneapp.ui.collegesearch.collegedetail.CollegeDetailFragment;

import java.util.List;

public class MyCollegeDetailFragment extends Fragment {

    private static final String TAG = "MyCollegeDetailFragment";
    private MyCollegeDetailViewModel viewModel;
    College college;

    private TasksAdapter tasksAdapter;
    private RecyclerView rvTasks;
    private RecyclerView.LayoutManager layoutManager;
    private ImageView ivFavDetailThumbnail;
    private TextView tvFavDetailName;
    private TextView tvFavDetailLocation;
    private TextView tvViewDetails;

    public MyCollegeDetailFragment(College college) {
        this.college = college;
    }

    public static MyCollegeDetailFragment newInstance(College college) {
        return new MyCollegeDetailFragment(college);
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_my_college_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(requireActivity()).get(MyCollegeDetailViewModel.class);

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

        UiUtils.setViewImage(getContext(), ivFavDetailThumbnail, college.getThumbnail(), null, R.drawable.college_black_48);
        UiUtils.setViewText(getContext(), tvFavDetailName, college.getName());
        UiUtils.setViewText(getContext(), tvFavDetailLocation, college.getLocation());
        tvViewDetails.setOnClickListener(this::onViewDetailsClick);

        // Observe Tasks List
        Observer<List<CollegeTask>> tasksObserver = tasks -> {
            if ((tasks == null) || (tasks.size() == 0)){
                Log.e(TAG, "No tasks found");
//                tvNoTasks.setVisibility(View.VISIBLE);
            } else {
                Log.i(TAG, "Tasks found");
//                tvNoTasks.setVisibility(View.GONE);
                tasksAdapter.setTasks(tasks);
            }
        };
        viewModel.getAllTasksLiveData().observe(getViewLifecycleOwner(), tasksObserver);
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