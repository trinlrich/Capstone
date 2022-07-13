package com.example.capstoneapp;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.Transformation;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import org.w3c.dom.Text;

import java.lang.reflect.Type;
import java.util.List;

public class Utilities {

    private static final String TAG = "Utilities";

    public static void getProfileFromParse(String userId, GetUserProfileListenerCallback callback) {
        ParseQuery<ParseFirebaseUser> query = ParseQuery.getQuery(ParseFirebaseUser.class);
        query.whereContains(ParseFirebaseUser.KEY_FIREBASE_UID, userId);
        query.findInBackground((users, e) -> {
            // check for errors. pass null values to indicate errors to callers
            if (e != null) {
                Log.e(TAG, "Issue with getting user", e);
                callback.onCompleted(null);
                return;
            }
            if (users.size() == 0) {
                Log.i(TAG, "FirebaseUid not found");
                callback.onCompleted(null);
                return;
            }
            if (users.size() > 1) {
                // TODO:: Debug multiple records with firebaseUid error
                Log.i(TAG, "Multiple records are fetched");
                callback.onCompleted(null);
                return;
            }
            // success cale
            Log.i(TAG, "FirebaseUid found");
            callback.onCompleted(users);

        });
    }

    public static void getCollegesListFromParse(ParseQuery<College> query, Long offset, GetCollegeListListenerCallback callback) {
        Log.i(TAG, "Querying colleges...");
        query.setSkip(Math.toIntExact(offset));
        query.setLimit(20);
        query.addAscendingOrder(College.KEY_NAME);
        query.findInBackground((colleges, e) -> {
            // check for errors. pass null values to indicate errors to callers
            Log.i(TAG, "Query done");
            if (e != null) {
                Log.e(TAG, "Issue with getting colleges", e);
                callback.onCompleted(null);
            } else if (colleges.size() == 0) {
                Log.i(TAG, "No colleges found");
                callback.onCompleted(null);
            } else {
                // Log all colleges
                Log.i(TAG, "Colleges found:");
                for (College college : colleges) {
                    Log.i(TAG, "- " + college.getName());
                }
                callback.onCompleted(colleges);
            }
        });
    }

    public static void getCollegeFromParse(GetCollegeListListenerCallback callback) {
        Log.i(TAG, "Querying college...");
        ParseQuery<College> query = ParseQuery.getQuery(College.class);
        query.findInBackground((colleges, e) -> {
            // check for errors. pass null values to indicate errors to callers
            Log.i(TAG, "Query done");
            if (e != null) {
                Log.e(TAG, "Issue with getting colleges", e);
                callback.onCompleted(null);
            } else if (colleges.size() == 0) {
                Log.i(TAG, "No colleges found");
                callback.onCompleted(null);
            } else {
                // Log all colleges
                Log.i(TAG, "Colleges found:");
                for (College college : colleges) {
                    Log.i(TAG, "- " + college.getName());
                }
                callback.onCompleted(colleges);
            }
        });
    }

    public static void setViewText(Context context, TextView textView, String text) {
        if (text.isEmpty()) {
            textView.setText(context.getString(R.string.na_text));
        } else {
            textView.setText(text);
        }
    }

    public static void setViewImage(Context context, ImageView imageView, String imageUrl, Transformation transformation, int defaultImage) {
        if (!imageUrl.isEmpty()) {
            if (transformation != null) {
                Glide.with(context)
                        .load(imageUrl)
                        .transform(transformation)
                        .into(imageView);
            } else {
                Glide.with(context)
                        .load(imageUrl)
                        .into(imageView);
            }
        } else {
            imageView.setImageResource(defaultImage);
            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        }
    }
}