package com.example.capstoneapp.model;

import com.parse.ParseClassName;
import com.parse.ParseObject;

import java.util.Date;

@ParseClassName("ApplicationStep")
public class ApplicationStep extends ParseObject {
    public static final String STEP_KEY_USER_UID = "firebaseUid";
    public static final String STEP_KEY_FAVCOLLEGE_ID = "favcollegeId";
    public static final String KEY_STEP_TITLE = "stepTitle";
    public static final String KEY_STEP_DESCRIPTION = "stepDescription";
    // STATE = To-Do or In-Progress or Completed
    public static final String KEY_STEP_STATE = "stepState";
    // STATE = Mandatory / Optional
    public static final String KEY_STEP_PRIORITY = "stepPriority";
    public static final String KEY_STEP_START_DATE = "stepStartDate";
    public static final String KEY_STEP_END_DATE = "stepEndDate";

    public int getCollegeId() { return getInt(STEP_KEY_FAVCOLLEGE_ID); }
    public void setCollegeId(int id) { put(STEP_KEY_FAVCOLLEGE_ID, id); }

    public String getFirebaseUid() {
        return getString(STEP_KEY_USER_UID);
    }
    public void setFirebaseUid(String firebaseUid) {
        put(STEP_KEY_USER_UID, firebaseUid);
    }

    public String getKeyStepTitle() {
        return getString(KEY_STEP_TITLE);
    }
    public void setKeyStepTitle(String stepTitle) {
        put(KEY_STEP_TITLE, stepTitle);
    }

    public String getKeyStepDescription()  { return getString(KEY_STEP_DESCRIPTION);}
    public void setKeyStepDescription(String stepDescription) {
        put(KEY_STEP_TITLE, stepDescription);
    }

    public Integer getKeyStepState(){ return getInt(KEY_STEP_STATE); }
    public void setKeyStepState(Integer stepState){put(KEY_STEP_STATE,stepState);}

    public Integer getKeyStepPriority(){return getInt(KEY_STEP_PRIORITY);}
    public void setKeyStepPriority(Integer stepPriority){put(KEY_STEP_STATE,stepPriority);}

    public Long getKeyStepGetStartDate(){return getLong(KEY_STEP_START_DATE);}
    public void setKeyStepStartDate(Long startDate){put(KEY_STEP_START_DATE,startDate);}

    public Long getKeyStepGetEndDate(){return getLong(KEY_STEP_END_DATE);}
    public void setKeyStepEndDate(Long endDateDate){put(KEY_STEP_END_DATE,endDateDate);}
}
