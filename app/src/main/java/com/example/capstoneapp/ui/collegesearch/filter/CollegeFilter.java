package com.example.capstoneapp.ui.collegesearch.filter;

import android.content.res.Resources;

import com.example.capstoneapp.R;

public class CollegeFilter {

    private String key;
    private String value;
    private int position;

    public CollegeFilter() {
    }

    public CollegeFilter(String key) {
        this.key = key;
        this.value = "All";
        this.position = 0;
    }

    public String getKey() {
        return key;
    }
    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }
    public void setValue(String value) {
        this.value = value;
    }

    public int getPosition() {
        return position;
    }
    public void setPosition(int position) {
        this.position = position;
    }
}
