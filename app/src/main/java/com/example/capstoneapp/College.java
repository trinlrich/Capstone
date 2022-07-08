package com.example.capstoneapp;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;

@ParseClassName("Colleges")
public class College extends ParseObject {

    public static final String KEY_ID = "collegeId";
    public static final String KEY_NAME = "collegeName";
    public static final String KEY_THUMBNAIL = "collegeThumbnail";
    public static final String KEY_CITY = "collegeCity";
    public static final String KEY_AVERAGE_GPA = "averageGpa";

    public String getCollegeId() { return getString(KEY_ID); }

    public void setCollegeId(String id) { put(KEY_ID, id); }

    public String getName() { return getString(KEY_NAME); }

    public void setName(String name) { put(KEY_NAME, name); }

    public String getThumbnail() { return getString(KEY_THUMBNAIL); }

    public void setThumbnail(String thumbnail) { put(KEY_THUMBNAIL, thumbnail); }

    public String getCity() { return getString(KEY_CITY); }

    public void setCity(String city) { put(KEY_CITY, city); }

    // TODO:: Pull State and concat city and state (City, ST)

}