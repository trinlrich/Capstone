package com.example.capstoneapp.model;

import com.parse.ParseClassName;
import com.parse.ParseObject;

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
}
