package com.example.capstoneapp;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;

@ParseClassName("Colleges")
public class College extends ParseObject {

    public static final String KEY_NAME = "collegeId";
    public static final String KEY_THUMBNAIL = "collegeThumbnail";
    public static final String KEY_CITY = "collegeCity";

    public String getCollegeName() { return getString(KEY_NAME); }

    public void setCollegeName(String name) { put(KEY_NAME, name); }

    public ParseFile getThumbnail() { return getParseFile(KEY_THUMBNAIL); }

    public void setThumbnail(ParseFile thumbnail) { put(KEY_THUMBNAIL, thumbnail); }

    public String getCity() { return getString(KEY_CITY); }

    public void setCity(String city) { put(KEY_CITY, city); }

}
