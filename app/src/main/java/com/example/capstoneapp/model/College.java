package com.example.capstoneapp.model;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;

@ParseClassName("Colleges")
public class College extends ParseObject {

    public static final String KEY_ID = "schoolId";
    public static final String KEY_NAME = "schoolName";
    public static final String KEY_THUMBNAIL = "schoolThumbnail";
    public static final String KEY_CITY = "schoolCity";
    public static final String KEY_STATE = "schoolStateCode";
    public static final String KEY_CONTROL = "schoolControl";
    public static final String KEY_MISSION = "schoolSpecialMission";

    private boolean isFavorite;
    public boolean isFavorite() {
        return isFavorite;
    }
    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    public int getCollegeId() { return getInt(KEY_ID); }
    public void setCollegeId(int id) { put(KEY_ID, id); }

    public String getName() { return getString(KEY_NAME); }
    public void setName(String name) { put(KEY_NAME, name); }

    public String getThumbnail() { return getString(KEY_THUMBNAIL); }
    public void setThumbnail(String thumbnail) { put(KEY_THUMBNAIL, thumbnail); }

    public String getCity() { return getString(KEY_CITY); }
    public void setCity(String city) { put(KEY_CITY, city); }

    public String getCollegeState() { return getString(KEY_STATE); }
    public void setCollegeState(String state) { put(KEY_STATE, state); }

    public int getControl() { return getInt(KEY_CONTROL); }
    public void setControl(int control) { put(KEY_CONTROL, control); }

    public String getMission() { return getString(KEY_MISSION); }
    public void setMission(String mission) { put(KEY_MISSION, mission); }

    public String getLocation() {
        // TODO:: Pull State and concat city and state (City, ST)
        return getCity() + ", " + getCollegeState();
    }



}
