package com.example.capstoneapp.model;

import com.parse.ParseClassName;
import com.parse.ParseObject;

import java.util.Date;

@ParseClassName("Task")
public class Task extends ParseObject {

    public static final String KEY_TASK_ID = "objectId";
    public static final String KEY_NAME = "taskName";
    public static final String KEY_STATUS = "taskStatus";
    public static final String KEY_IS_REQUIRED = "taskIsRequired";
    public static final String KEY_DEADLINE = "taskDeadline";
    public static final String KEY_NOTES = "taskNotes";

    public String getTaskId() { return getString(KEY_TASK_ID); }
    public void setTaskId(String id) { put(KEY_TASK_ID, id); }

    public String getTaskName() { return getString(KEY_NAME); }
    public void setTaskName(String name) { put(KEY_NAME, name); }

    public String getStatus() { return getString(KEY_STATUS); }
    public void setStatus(String status) { put(KEY_STATUS, status); }

    public Boolean isRequired() { return getBoolean(KEY_IS_REQUIRED); }
    public void setIsRequired(Boolean isRequired) { put(KEY_IS_REQUIRED, isRequired); }

    public Date getDeadline() { return getDate(KEY_DEADLINE); }
    public void setDeadline(Date deadline) { put(KEY_DEADLINE, deadline); }

    public String getNotes() { return getString(KEY_NOTES); }
    public void setNotes(String notes) { put(KEY_NOTES, notes); }
}
