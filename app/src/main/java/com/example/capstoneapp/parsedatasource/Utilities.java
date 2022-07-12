package com.example.capstoneapp.parsedatasource;

import android.content.Context;
import android.util.Log;
import com.example.capstoneapp.model.College;
import com.example.capstoneapp.model.FavoriteCollege;
import com.example.capstoneapp.model.ParseFirebaseUser;
import com.example.capstoneapp.parsedatasource.GetCollegeListListenerCallback;
import com.example.capstoneapp.parsedatasource.GetUserProfileListenerCallback;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

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

    public static void getCollegesListFromParse(Long offset,List<FavoriteCollege> favColleges,  GetCollegeListListenerCallback callback) {
        Log.i(TAG, "Querying colleges...");
        ParseQuery<College> query = ParseQuery.getQuery(College.class);
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

    public static void getFavCollegesForUser(String userId, GetFavCollegesCallback callback){
        Log.i(TAG, "Querying favorite college list...");
        ParseQuery<FavoriteCollege> query = ParseQuery.getQuery(FavoriteCollege.class);
        query.whereContains(FavoriteCollege.KEY_USER_UID, userId);
        query.findInBackground((favColleges, e) -> {
            // check for any error
            if (e != null){
                Log.e(TAG, "Issue with getting favourite colleges", e);
                callback.onCompleted(null);
                return ;
            }
            if ((favColleges == null) || (favColleges.size() == 0)){
                Log.w(TAG, "No favourite colleges for user");
                callback.onCompleted(favColleges);
                return ;
            }
            // At this point there will be some fav colleges
            callback.onCompleted(favColleges);
            return ;
        });
    }

}
