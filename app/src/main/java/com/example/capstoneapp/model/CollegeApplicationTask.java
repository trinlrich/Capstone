package com.example.capstoneapp.model;

import android.graphics.Color;
import android.util.Log;

import com.parse.ParseClassName;
import com.parse.ParseObject;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@ParseClassName("CollegeApplicationTasks")
public class CollegeApplicationTask  extends ParseObject {
    public static final String TASK_KEY_USER_UID = "firebaseUid";
    public static final String TASK_KEY_FAVCOLLEGE_ID = "favcollegeId";
    public static final String TASK_KEY_TITLE = "taskTitle";
    public static final String TASK_KEY_DESCRIPTION = "taskDescription";
    // STATE = To-Do or In-Progress or Completed
    public static final String TASK_KEY_STATE = "taskState";
    // STATE = Mandatory / Optional
    public static final String TASK_KEY_PRIORITY = "taskPriority";
    public static final String TASK_KEY_START_DATE = "taskStartDate";
    public static final String TASK_KEY_END_DATE = "taskEndDate";

    public static final HashMap<Integer, String> STATUSES = new HashMap<>();
    private static final HashMap<Integer, String> STATUS_COLORS = new HashMap<>();

    public CollegeApplicationTask() {
        createStatusMap();
        createStatusColorsMap();
    }

    public String getFirebaseUid() {
        return getString(TASK_KEY_USER_UID);
    }
    public void setFirebaseUid(String firebaseUid) {
        put(TASK_KEY_USER_UID, firebaseUid);
    }

    public int getCollegeId() { return getInt(TASK_KEY_FAVCOLLEGE_ID); }
    public void setCollegeId(int id) { put(TASK_KEY_FAVCOLLEGE_ID, id); }

    public String getTaskTitle() {
        return getString(TASK_KEY_TITLE);
    }
    public void setTaskTitle(String stepTitle) {
        put(TASK_KEY_TITLE, stepTitle);
    }

    public String getTaskDescription()  { return getString(TASK_KEY_DESCRIPTION);}
    public void setTaskDescription(String stepDescription) {
        put(TASK_KEY_DESCRIPTION, stepDescription);
    }

    public int getTaskState(){ return getInt(TASK_KEY_STATE); }
    public void setTaskState(int stepState){put(TASK_KEY_STATE,stepState);}

    public int getTaskPriority(){return getInt(TASK_KEY_PRIORITY);}
    public void setTaskPriority(int stepPriority){put(TASK_KEY_PRIORITY,stepPriority);}

    public Long getTaskStartDate(){return getLong(TASK_KEY_START_DATE);}
    public void setTaskStartDate(Long startDate){put(TASK_KEY_START_DATE,startDate);}

    public Long getTaskEndDate(){return getLong(TASK_KEY_END_DATE);}
    public void setTaskEndDate(Long endDateDate){put(TASK_KEY_END_DATE,endDateDate);}

    private void createStatusMap() {
        STATUSES.put(0, "To Do");
        STATUSES.put(1, "In Progress");
        STATUSES.put(2, "Complete");
    }

    private void createStatusColorsMap() {
        STATUS_COLORS.put(0, "#FF9590");
        STATUS_COLORS.put(1, "#FFD966");
        STATUS_COLORS.put(2, "#92CC59");
    }

    public String processTaskStatus(int statusCode) {
        return STATUSES.getOrDefault(statusCode, "NoStatus");
    }

    public static int getStatusColor(String status) {
        int statusCode = -1;
        for (Map.Entry<Integer, String> entry : STATUSES.entrySet()) {
            if (status.equals(entry.getValue())) {
                statusCode = entry.getKey();
            }
        }

        return Color.parseColor(STATUS_COLORS.get(statusCode));
    }

    public String calculateTimeUntil() {
        Date date = new Date(getTaskEndDate());

        int SECOND_MILLIS = 1000;
        int MINUTE_MILLIS = 60 * SECOND_MILLIS;
        int HOUR_MILLIS = 60 * MINUTE_MILLIS;
        int DAY_MILLIS = 24 * HOUR_MILLIS;

        try {
            date.getTime();
            long time = date.getTime();
            long now = System.currentTimeMillis();

            final long diff = time - now;
            if (diff < MINUTE_MILLIS) {
                return "now";
            } else if (diff < 2 * MINUTE_MILLIS) {
                return "in " + diff + "minute";
            } else if (diff < 50 * MINUTE_MILLIS) {
                return "in " + diff / MINUTE_MILLIS + " minutes";
            } else if (diff < 90 * MINUTE_MILLIS) {
                return "in " + diff + " hour";
            } else if (diff < 24 * HOUR_MILLIS) {
                return "in " + diff / HOUR_MILLIS + "  hours";
            } else if (diff < 48 * HOUR_MILLIS) {
                return "tomorrow";
            } else {
                return "in" + diff / DAY_MILLIS + " days";
            }
        } catch (Exception e) {
            Log.i("Error:", "getRelativeTimeAgo failed", e);
            e.printStackTrace();
        }

        return "";
    }

    public String getStatusAsText() { return processTaskStatus(getTaskState()); }
}
