package com.example.capstoneapp.model;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;

@ParseClassName("FavoriteCollege")
public class FavoriteCollege  extends ParseObject {

    // College Unique Identifier
    public static final String KEY_COLLEGE_ID = "collegeId";
    public String getCollegeId() { return getString(KEY_COLLEGE_ID); }
    public void setCollegeId(String id) { put(KEY_COLLEGE_ID, id); }

    // User Unique Identifier
    public static final String KEY_USER_UID = "firebaseUid";
    public String getFirebaseUid() {
        return getString(KEY_USER_UID);
    }
    public void setFirebaseUid(String firebaseUid) {
        put(KEY_USER_UID, firebaseUid);
    }
}
