package com.example.capstoneapp;

import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.Key;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseQuery;

import java.util.List;

public class Utilities {

    private static final String TAG = "Utilities";

    public static void getProfileFromParse(String userId, GetUserProfileListenerCallback callback) {
        ParseQuery<ParseFirebaseUser> query = ParseQuery.getQuery(ParseFirebaseUser.class);
        query.whereContains(ParseFirebaseUser.KEY_FIREBASE_UID, userId);
        query.findInBackground(new FindCallback<ParseFirebaseUser>() {
            @Override
            public void done(List<ParseFirebaseUser> objects, ParseException e) {
                // check for errors. pass null values to indicate errors to callers
                if (e != null) {
                    Log.e(TAG, "Issue with getting user", e);
                    callback.onCompleted(null);
                    return;
                }
                if (objects.size() == 0) {
                    Log.i(TAG, "FirebaseUid not found");
                    callback.onCompleted(null);
                    return;
                }
                if (objects.size() > 1) {
                    // TODO:: Debug multiple records with firebaseUid error
                    Log.i(TAG, "Multiple records are fetched");
                    callback.onCompleted(null);
                    return;
                }
                // success cale
                Log.i(TAG, "FirebaseUid found");
                callback.onCompleted(objects);

            }
        });
    }

    public static void getCollegesListFromParse(Long offset, GetCollegeListListenerCallback callback) {
        Log.i(TAG, "Querying colleges...");
        ParseQuery<College> query = ParseQuery.getQuery(College.class);
        query.setSkip(Math.toIntExact(offset));
        query.setLimit(20);
        query.addAscendingOrder(College.KEY_NAME);
        query.findInBackground(new FindCallback<College>() {
            @Override
            public void done(List<College> colleges, ParseException e) {
                // check for errors. pass null values to indicate errors to callers
                Log.i(TAG, "Query done");
                if (e != null) {
                    Log.e(TAG, "Issue with getting colleges", e);
                    callback.onCompleted(null);
                } else if (colleges.size() == 0) {
                    Log.i(TAG, "No colleges found");
                    callback.onCompleted(null);
                    return;
                } else {
                    // Log all colleges
                    Log.i(TAG, "Colleges found:");
                    for (College college : colleges) {
                        Log.i(TAG, "- " + college.getCollegeName());
                    }
                    callback.onCompleted(colleges);
                }
            }
        });
    }
}