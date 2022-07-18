package com.example.capstoneapp.parsedatasource;

import static com.example.capstoneapp.model.ApplicationStep.KEY_STEP_DESCRIPTION;
import static com.example.capstoneapp.model.ApplicationStep.KEY_STEP_END_DATE;
import static com.example.capstoneapp.model.ApplicationStep.KEY_STEP_PRIORITY;
import static com.example.capstoneapp.model.ApplicationStep.KEY_STEP_START_DATE;
import static com.example.capstoneapp.model.ApplicationStep.KEY_STEP_STATE;
import static com.example.capstoneapp.model.ApplicationStep.KEY_STEP_TITLE;
import static com.example.capstoneapp.model.ApplicationStep.STEP_KEY_FAVCOLLEGE_ID;
import static com.example.capstoneapp.model.ApplicationStep.STEP_KEY_USER_UID;

import android.util.Log;

import com.example.capstoneapp.model.ApplicationStep;
import com.example.capstoneapp.model.College;
import com.example.capstoneapp.model.FavoriteCollege;
import com.example.capstoneapp.model.ParseFirebaseUser;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;

public class Utilities {

    private static final String TAG = "Utilities";
    private static String[] stepTitles = {"Complete Essay", "Apply Scholarships", "Get Recommendation Letters", "Complete FAFSA", "Understand Tution Costs"};

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

    public static void getFavCollegesForUser(String userId, GetFavCollegesCallback callback) {
        Log.i(TAG, "Querying favorite college list...");
        ParseQuery<FavoriteCollege> query = ParseQuery.getQuery(FavoriteCollege.class);
        query.whereContains(FavoriteCollege.KEY_USER_UID, userId);
        query.findInBackground((favColleges, e) -> {
            // check for any error
            if (e != null) {
                Log.e(TAG, "Issue with getting favourite colleges", e);
                callback.onCompleted(null, true);
                return;
            }
            if ((favColleges == null) || (favColleges.size() == 0)) {
                Log.w(TAG, "No favourite colleges for user");
                callback.onCompleted(favColleges, false);
                return;
            }
            // At this point there will be some fav colleges
            callback.onCompleted(favColleges, false);
        });
    }

    public static void addFavCollegeForUser(String userId, College college, GetFavCollegesCallback callback) {
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
                // Add the mandatory steps
                addMandatorySteps(userId, college, callback);
            }

        });
    }

    public static void removeFavCollegeForUser(String userId, College college, GetFavCollegesCallback callback) {

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
            favColleges.get(0).deleteInBackground(e2 -> {
                if (e2 != null)
                    Log.e(TAG, "Error while deleting Fav College", e2);
                // Get again the list of fav colleges
                getFavCollegesForUser(userId, callback);
            });
        });
    }

    private static void addMandatorySteps(String userId, College college, GetFavCollegesCallback callback) {
        List<ParseObject> applicationSteps = new ArrayList<>();
        for (int indx = 0; indx < 5; indx++) {
            ParseObject step = new ParseObject("ApplicationStep");
            step.put(STEP_KEY_USER_UID, userId);
            step.put(STEP_KEY_FAVCOLLEGE_ID, college.getCollegeId());
            step.put(KEY_STEP_TITLE, stepTitles[indx]);
            step.put(KEY_STEP_DESCRIPTION, stepTitles[indx]);
            step.put(KEY_STEP_STATE, 0);
            step.put(KEY_STEP_PRIORITY, 0);
            step.put(KEY_STEP_START_DATE, new Date().getTime());
            step.put(KEY_STEP_END_DATE, getCalculatedDate(30));

            applicationSteps.add(step);
        }
        ApplicationStep.saveAllInBackground(applicationSteps, new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e != null) {
                    Log.e(TAG, "Error while creating mandatory tasks for Fav College", e);
                    callback.onCompleted(null, true);
                } else {
                    Log.i(TAG, "Created Mandatory taks for Fav College");
                    // Get again the list of fav colleges
                    getFavCollegesForUser(userId, callback);
                }

            }
        });
    }

    public static void getAllApplicationSteps(String userId, Integer collegeId,ApplicationStepsCallback callback){
        Log.i(TAG, "Querying Application steps for fav college ...");
        ParseQuery<ApplicationStep> query = ParseQuery.getQuery(ApplicationStep.class);
        query.whereEqualTo(STEP_KEY_USER_UID, userId);
        query.whereEqualTo(STEP_KEY_FAVCOLLEGE_ID, collegeId);

        query.findInBackground(new FindCallback<ApplicationStep>() {
            @Override
            public void done(List<ApplicationStep> applicationSteps, ParseException e) {
                // check for any error
                if (e != null) {
                    Log.e(TAG, "Issue with getting application steps", e);
                    callback.onCompleted(new ArrayList<>(), true);
                    return;
                }
                if ((applicationSteps == null) || (applicationSteps.size() == 0)) {
                    Log.w(TAG, "No application steps available ");
                    callback.onCompleted(new ArrayList<>(), false);
                    return;
                }
                // At this point there will application steps for that user - college combination
                callback.onCompleted(applicationSteps, false);
            }
        });
    }

    private static Long getCalculatedDate(int days) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_YEAR, days);
        return cal.getTimeInMillis();
    }

}
