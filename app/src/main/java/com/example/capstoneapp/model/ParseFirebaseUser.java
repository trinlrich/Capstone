package com.example.capstoneapp.model;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;

import java.util.HashMap;
import java.util.Map;

@ParseClassName("ParseFirebaseUser")
public class ParseFirebaseUser extends ParseObject{

    public static final String KEY_FIREBASE_UID = "firebaseUid";
    public static final String KEY_FIRST_NAME = "firstName";
    public static final String KEY_LAST_NAME = "lastName";
    public static final String KEY_DEGREE_SEEKING = "degreeSeeking";
    public static final String KEY_PROFILE_IMAGE = "profileImage";

    public static final String DATA_NOT_AVAILABLE = "Data Not Available";

    public static final HashMap<Integer, String> DEGREES = new HashMap<>();

    public ParseFirebaseUser() {
        createDegreesMap();
    }

    public String getFirebaseUid() {
        return getString(KEY_FIREBASE_UID);
    }
    public void setFirebaseUid(String firebaseUid) {
        put(KEY_FIREBASE_UID, firebaseUid);
    }

    public String getFirstName() {
        return getString(KEY_FIRST_NAME);
    }
    public void setFirstName(String firstName) {
        put(KEY_FIRST_NAME, firstName);
    }

    public String getLastName() {
        return getString(KEY_LAST_NAME);
    }
    public void setLastName(String lastName) {
        put(KEY_LAST_NAME, lastName);
    }

    public int getDegreeSeekingCode() {
        return getInt(KEY_DEGREE_SEEKING);
    }
    public String getDegreeSeekingAsText() { return convertDegreeCodeToString(getDegreeSeekingCode()); }
    public void setDegreeSeeking(String degreeSeeking) { put(KEY_DEGREE_SEEKING, convertDegreeStringToCode(degreeSeeking)); }
    public static HashMap<Integer, String> getDegrees() { return DEGREES; }

    public ParseFile getProfileImage() {
        return getParseFile(KEY_PROFILE_IMAGE);
    }
    public void setProfileImage(ParseFile profileImage) {
        put(KEY_PROFILE_IMAGE, profileImage);
    }

    public void createDegreesMap() {
        DEGREES.put(0, "Associate");
        DEGREES.put(1, "Bachelor's");
        DEGREES.put(2, "Master's");
        DEGREES.put(3, "Doctoral");
    }

    private String convertDegreeCodeToString(int code) {
        return DEGREES.getOrDefault(code, DATA_NOT_AVAILABLE);
    }

    private int convertDegreeStringToCode(String degree) {
        for (Map.Entry<Integer, String> entry : DEGREES.entrySet()) {
            if (entry.getValue().equals(degree)) {
                return entry.getKey();
            }
        }
        return -1;
    }
}
