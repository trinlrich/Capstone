package com.example.capstoneapp.model;

import android.graphics.Color;
import android.util.Log;

import com.parse.ParseClassName;
import com.parse.ParseObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@ParseClassName("CollegeApplicationTasks")
public class CollegeTask extends ParseObject {

    public static final String KEY_TASK_ID = "objectId";
    public static final String KEY_NAME = "taskTitle";
    public static final String KEY_STATUS = "taskState";
    public static final String KEY_IS_REQUIRED = "taskPriority";
    public static final String KEY_DEADLINE = "taskEndDate";
    public static final String KEY_NOTES = "taskDescription";

    public static final HashMap<Integer, String> STATUSES = new HashMap<>();
    private static final HashMap<Integer, String> STATUS_COLORS = new HashMap<>();

    public CollegeTask() {
        createStatusMap();
        createStatusColorsMap();
    }

    public String getTaskId() { return getString(KEY_TASK_ID); }
    public void setTaskId(String id) { put(KEY_TASK_ID, id); }

    public String getTaskName() { return getString(KEY_NAME); }
    public void setTaskName(String name) { put(KEY_NAME, name); }

    public int getStatusCode() { return getInt(KEY_STATUS); }
    public String getStatusAsText() { return processTaskStatus(getStatusCode()); }
    public void setStatus(String status) { put(KEY_STATUS, status); }

    public int isRequired() { return getInt(KEY_IS_REQUIRED); }
    public void setIsRequired(int isRequired) { put(KEY_IS_REQUIRED, isRequired); }

    public Date getDeadline() { return getDate(KEY_DEADLINE); }
    public void setDeadline(Date deadline) { put(KEY_DEADLINE, deadline); }

    public String getNotes() { return getString(KEY_NOTES); }
    public void setNotes(String notes) { put(KEY_NOTES, notes); }

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
        Date date = getDeadline();

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
}
