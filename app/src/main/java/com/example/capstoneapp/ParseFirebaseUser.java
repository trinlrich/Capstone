package com.example.capstoneapp;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;

@ParseClassName("ParseFirebaseUser")
public class ParseFirebaseUser extends ParseObject{

    public static final String KEY_FIREBASE_UID = "firebaseUid";
    public static final String KEY_FIRST_NAME = "firstName";
    public static final String KEY_LAST_NAME = "lastName";
    public static final String KEY_DEGREE_SEEKING = "degreeSeeking";
    public static final String KEY_PROFILE_IMAGE = "profileImage";

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

    public String getDegreeSeeking() {
        return getString(KEY_DEGREE_SEEKING);
    }

    public void setDegreeSeeking(String degreeSeeking) {
        put(KEY_DEGREE_SEEKING, degreeSeeking);
    }

    public ParseFile getProfileImage() {
        return getParseFile(KEY_PROFILE_IMAGE);
    }

    public void setProfileImage(ParseFile profileImage) {
        put(KEY_PROFILE_IMAGE, profileImage);
    }
}