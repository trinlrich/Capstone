package com.example.capstoneapp;

import com.parse.ParseClassName;
import com.parse.ParseObject;

@ParseClassName("User")
public class FirebaseUser extends ParseObject{

    public static final String KEY_FIRST_NAME = "firstName";
    public static final String KEY_LAST_NAME = "lastName";
    public static final String KEY_DEGREE_SEEKING = "degreeSeeking";

    public String getFirstName() {
        return getString(KEY_FIRST_NAME);
    }

    public void setFirstName(String firstName) {
        put(KEY_FIRST_NAME, firstName);
    }

    public String getLastName() {
        return getString(KEY_FIRST_NAME);
    }

    public void setLastName(String lastName) {
        put(KEY_LAST_NAME, lastName);
    }

    public String getDegreeSeeking() {
        return getString(KEY_FIRST_NAME);
    }

    public void setDegreeSeeking(String degreeSeeking) {
        put(KEY_DEGREE_SEEKING, degreeSeeking);
    }
}