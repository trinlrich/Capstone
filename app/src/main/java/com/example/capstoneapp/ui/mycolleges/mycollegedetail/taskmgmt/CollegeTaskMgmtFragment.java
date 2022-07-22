package com.example.capstoneapp.ui.mycolleges.mycollegedetail.taskmgmt;


import android.content.ClipData;
import android.content.ClipDescription;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.transition.AutoTransition;
import androidx.transition.Transition;
import androidx.transition.TransitionManager;

import com.example.capstoneapp.R;
import com.example.capstoneapp.model.CollegeApplicationTask;
import com.example.capstoneapp.ui.collegesearch.collegedetail.CollegeDetailFragment;
import com.example.capstoneapp.ui.mycolleges.mycollegedetail.CustomCardDragShadowBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CollegeTaskMgmtFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CollegeTaskMgmtFragment extends Fragment {

    private static final String TAG = "AppStepManagementFragment";
    private static final String USERID = "userid";
    private static final String COLLEGEID = "collegeid";

    Map<CardView, LinearLayout> tasksToTaskLayoutMap = new HashMap<>();
    Map<Button, LinearLayout> dropAreaToLayoutMap = new HashMap<>();
    private Button createButton;
    private LinearLayout masterToDoLayout;

    private LinearLayout masterInProgressLayout;
    private Button dropButtonIP;

    private LinearLayout masterCompletedLayout;
    private Button dropButtonCompleted;

    private LinearLayout sourceLayout;
    private LinearLayout targetLayout;

    private List<CollegeApplicationTask> applicationTasks = new ArrayList<>();

    private TaskMgmtViewModel collegeTaskViewModel;
    private String userId ;

    public CollegeTaskMgmtFragment(String userId, Integer collegeId) {
        this.userId = userId;
        this.collegeId = collegeId;
    }

    private Integer collegeId;

    public CollegeTaskMgmtFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment AppStepManagementFragment.
     */
    public static CollegeTaskMgmtFragment newInstance(String userId, Integer collegeId) {
        CollegeTaskMgmtFragment f = new CollegeTaskMgmtFragment();
        Bundle args = new Bundle();
        args.putString(USERID, userId);
        args.putInt(COLLEGEID, collegeId);
        f.setArguments(args);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_app_step_management, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments().containsKey(USERID)){
            userId = getArguments().getString(USERID);
        }
        if (getArguments().containsKey(COLLEGEID)){
            collegeId = getArguments().getInt(COLLEGEID);
        }

        // To Do layout Setup And Initialization
        masterToDoLayout = view.findViewById(R.id.masterToDoLayout);
        createButton = view.findViewById(R.id.createTaskBtn);
        createButton.setOnClickListener(this::onCreateTaskClick);

        // In-progress layout Setup And Initialization
        masterInProgressLayout = view.findViewById(R.id.masterIPLayout);
        dropButtonIP = view.findViewById(R.id.dropIPAreaButton);
        attachDragListener(dropButtonIP);

        // Completed layout Setup And Initialization
        masterCompletedLayout = view.findViewById(R.id.masterCompletedLayout);
        dropButtonCompleted = view.findViewById(R.id.dropCompletedAreaButton);
        initStates();
        attachDragListener(dropButtonCompleted);
        // viewmodel creation
        TaskMgmtViewModelFactory factory = new TaskMgmtViewModelFactory(userId,collegeId);
        collegeTaskViewModel = new ViewModelProvider( this, factory).get(TaskMgmtViewModel.class);
        collegeTaskViewModel.getCollegeTasksLiveData().observe(getActivity(), tasks -> {
            applicationTasks = tasks;
            loadApplicationsTasks();
        });
    }

    private void initStates() {
        // Initialize the states drop area to LayoutMapping
        dropAreaToLayoutMap.put(dropButtonIP, masterInProgressLayout);
        dropAreaToLayoutMap.put(dropButtonCompleted, masterCompletedLayout);

        // Initalize the Task->Layout Mapping
    }

    private void attachDragListener(Button dropButton) {
        dropButton.setOnDragListener((v, e) -> {
            View draggableItem = (View) e.getLocalState();
            // Handles each of the expected events.
            switch (e.getAction()) {
                case DragEvent.ACTION_DRAG_STARTED:
                    Log.d(TAG, "DragEvent.ACTION_DRAG_STARTED");
                    dropButton.setAlpha(0.7f);
                    // Invalidate the view to force a redraw
                    v.invalidate();
                    return true;
                case DragEvent.ACTION_DRAG_ENTERED:
                    Log.d(TAG, "DragEvent.ACTION_DRAG_ENTERED");
                    dropButton.setAlpha(0.3f);
                    // When every drop area is entered we need to update the target layout
                    targetLayout = dropAreaToLayoutMap.get(dropButton);
                    v.invalidate();
                    return true;
                case DragEvent.ACTION_DRAG_LOCATION:
                    // Ignore the event.
                    Log.d(TAG, "DragEvent.ACTION_DRAG_LOCATION ->IGNORE");
                    return true;
                case DragEvent.ACTION_DRAG_EXITED:
                    Log.d(TAG, "DragEvent.ACTION_DRAG_EXITED");
                    dropButton.setAlpha(1.0f);
                    draggableItem.setVisibility(View.VISIBLE);
                    v.invalidate();
                    return true;
                case DragEvent.ACTION_DROP:
                    Log.d(TAG, "DragEvent.ACTION_DROP");
                    dropButton.setAlpha(1.0f);
                    updateAppStep(sourceLayout, targetLayout, draggableItem);
                    v.invalidate();
                    return true;
                case DragEvent.ACTION_DRAG_ENDED:
                    Log.d(TAG, "DragEvent.ACTION_DRAG_ENDED");
                    dropButton.setAlpha(1.0f);
                    // Does a getResult(), and displays what happened.
                    if (e.getResult()) {
                        Toast.makeText(getContext(), "The drop was handled.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), "The drop didn't work.", Toast.LENGTH_SHORT).show();
                    }
                    v.invalidate();
                    return true;
                default:
                    // An unknown action type was received.
                    Log.e(TAG, "Unknown action type received by View.OnDragListener.");
                    break;
            }

            return false;
        });
    }

    private void updateAppStep(LinearLayout srcLayout, LinearLayout tgtLayout, View draggableItem) {
        // update parse database via view model
        int state = Integer.parseInt((String) tgtLayout.getTag());
        int stepIndex = Integer.parseInt((String) draggableItem.getTag());
        collegeTaskViewModel.updateApplicationStepState(applicationTasks.get(stepIndex), state);
        // Animation and adjust the view
        Transition move = new AutoTransition()
                .addTarget(draggableItem)
                .setDuration(1000);
        TransitionManager.beginDelayedTransition(srcLayout, move);
        srcLayout.removeView(draggableItem);
        tgtLayout.addView(draggableItem);
        // Change the state of the tasks
        tasksToTaskLayoutMap.put((CardView) draggableItem, tgtLayout);
    }

    private void loadApplicationsTasks() {

        for (int indx = 0; indx < applicationTasks.size(); indx++) {
            CollegeApplicationTask task = applicationTasks.get(indx);
            CardView newCard = createCardView(task.getTaskTitle(), indx);
            // For this example the newly created card is always in TODO Layout
            // For other apps this table should be initialized after loading data from repository
            // And drawing to UI
            LinearLayout initalLayout = getInitialLayoutForTasks(task);
            tasksToTaskLayoutMap.put(newCard, initalLayout);

            newCard.setOnLongClickListener(v -> {
                // Create a new ClipData.Item from the CardView object's tag.
                ClipData.Item item = new ClipData.Item((CharSequence) v.getTag());

                // Create a new ClipData using the tag as a label, the plain text MIME type, and
                // the already-created item. This creates a new ClipDescription object within the
                // ClipData and sets its MIME type to "text/plain".
                ClipData dragData = new ClipData(
                        (CharSequence) v.getTag(),
                        new String[]{ClipDescription.MIMETYPE_TEXT_PLAIN},
                        item);

                // Instantiate the drag shadow builder.
                View.DragShadowBuilder cardShadow = new CustomCardDragShadowBuilder(newCard);

                // Start the drag.
                v.startDragAndDrop(dragData, cardShadow, v, 0);

                // Hide the card when drag is being started or you can do you custom implementation here
                // v.setVisibility(View.INVISIBLE);

                // When every drag is started we need to update the source layout
                sourceLayout = tasksToTaskLayoutMap.get(newCard);

                // Indicate that the long-click was handled.
                return true;

            });
            initalLayout.addView(newCard);
        }
    }

    private LinearLayout getInitialLayoutForTasks(CollegeApplicationTask task) {

        Integer state = task.getTaskState();
        switch (state) {
            case 0:
                return masterToDoLayout;
            case 1:
                return masterInProgressLayout;
            case 2:
                return masterCompletedLayout;
            default: {
                Log.e(TAG, "INVALID STATE FOR App Step");
                return null;
            }
        }
    }

    private CardView createCardView(String taskName, Integer appStepIndex) {
        ContextThemeWrapper newCardContext = new ContextThemeWrapper(getContext(), R.style.taskCardStyle);
        CardView cardview = new CardView(newCardContext);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(newCardContext.getResources().getDimensionPixelSize(R.dimen.task_card_width), newCardContext.getResources().getDimensionPixelSize(R.dimen.task_card_height));
        layoutParams.setMargins(5, 5, 5, 5);
        cardview.setLayoutParams(layoutParams);
        cardview.setTag(appStepIndex.toString());

        ContextThemeWrapper newCardTextContext = new ContextThemeWrapper(getContext(), R.style.taskCardTextStyle);
        TextView textview = new TextView(newCardTextContext);
        textview.setText(taskName);
        cardview.addView(textview);
        return cardview;
    }

    private void onCreateTaskClick(View view) {
        Fragment fragment = TaskDetailFragment.newInstance(createDefaultTask());
        ((FragmentActivity) getContext()).getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.flContainer, fragment)
                .addToBackStack(null)
                .commit();
    }

    private CollegeApplicationTask createDefaultTask() {
        CollegeApplicationTask task = new CollegeApplicationTask();
        task.setTaskTitle("New Task");
        task.setTaskState(0);
        task.setTaskEndDate(System.currentTimeMillis());
        task.setTaskDescription("");
        return task;
    }
}
