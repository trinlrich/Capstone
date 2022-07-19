package com.example.capstoneapp.parsedatasource;

import android.util.Log;
import com.example.capstoneapp.model.College;
import com.example.capstoneapp.model.CollegeTask;
import com.example.capstoneapp.model.FavoriteCollege;
import com.example.capstoneapp.model.ParseFirebaseUser;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.List;
import java.util.Set;

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

    public static void getCollegesListFromParse(Set<Integer> favCollegesSet, GetCollegeListListenerCallback callback) {
        Log.i(TAG, "Querying for All Colleges...");
        ParseQuery<College> query = ParseQuery.getQuery(College.class);
        query.setLimit(10000);
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
                Log.i(TAG, "Colleges found: " + colleges.size());
                for (College college : colleges) {
                    Log.i(TAG, "- " + college.getName());
                    if (favCollegesSet.contains(college.getCollegeId()))
                        college.setFavorite(true);
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
                callback.onCompleted(null, true);
                return;
            }
            if ((favColleges == null) || (favColleges.size() == 0)){
                Log.w(TAG, "No favourite colleges for user");
                callback.onCompleted(favColleges, false);
                return;
            }
            // At this point there will be some fav colleges
            callback.onCompleted(favColleges, false);
        });
    }
    public static void addFavCollegeForUser(String userId, College college, GetFavCollegesCallback callback){
        FavoriteCollege favoriteCollege = new FavoriteCollege();
        favoriteCollege.setFirebaseUid(userId);
        int collegeId = college.getCollegeId();
        favoriteCollege.setCollegeId(collegeId);
        favoriteCollege.saveInBackground(exsave -> {
            if (exsave != null) {
                Log.e(TAG, "Error while saving Fav College", exsave);
                callback.onCompleted(null, true);
            } else {
                Log.i(TAG, "Fav College add was successful");
                // Get again the list of fav colleges
                getFavCollegesForUser(userId, callback);
            }

        });
    }

    public static void removeFavCollegeForUser(String userId, College college, GetFavCollegesCallback callback){

        ParseQuery<FavoriteCollege> query = ParseQuery.getQuery(FavoriteCollege.class);
        query.whereEqualTo(FavoriteCollege.KEY_USER_UID, userId);
        query.whereEqualTo(FavoriteCollege.KEY_COLLEGE_ID, college.getCollegeId());


        query.findInBackground((favColleges, e) -> {
            // check for errors. pass null values to indicate errors to callers
            if (e != null) {
                Log.e(TAG, "Issue with getting fav college", e);
            }
            if (favColleges.size() == 0) {
                Log.e(TAG, "No fav college found");
            }
            if (favColleges.size() > 1) {
                Log.e(TAG, "Multiple records are fetched for fav college");
            }
            // success case
            Log.i(TAG, "Fav College found Delete");
            favColleges.get(0).deleteInBackground (e2 -> {
                if (e2 != null)
                    Log.e(TAG, "Error while deleting Fav College", e2);
                // Get again the list of fav colleges
                getFavCollegesForUser(userId, callback);
            });
        });
    }

    public static void getTaskListFromParse(GetTasksListListenerCallback callback) {
        ParseQuery<CollegeTask> query = ParseQuery.getQuery(CollegeTask.class);
        query.findInBackground((tasks, e) -> {
            if (e != null) {
                Log.e(TAG, "Issue with getting tasks", e);
            }
            if (tasks.size() == 0) {
                Log.e(TAG, "No tasks found");
            }
            Log.i(TAG, "Tasks found: " + tasks.size());
            for (CollegeTask task : tasks) {
                Log.i(TAG, "- " + task.getTaskName());
            }
            callback.onCompleted(tasks);
        });

    }
}
