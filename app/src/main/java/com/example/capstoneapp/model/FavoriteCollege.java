package com.example.capstoneapp.model;

import com.parse.ParseClassName;
import com.parse.ParseObject;

@ParseClassName("FavoriteCollege")
public class FavoriteCollege  extends ParseObject {

    // College Unique Identifier
    // User Unique Identifier
    public static final String KEY_USER_UID = "firebaseUid";
    public static final String KEY_COLLEGE_ID = "collegeId";

    public int getCollegeId() { return getInt(KEY_COLLEGE_ID); }
    public void setCollegeId(int id) { put(KEY_COLLEGE_ID, id); }

    public String getFirebaseUid() {
        return getString(KEY_USER_UID);
    }
    public void setFirebaseUid(String firebaseUid) {
        put(KEY_USER_UID, firebaseUid);
    }
}
