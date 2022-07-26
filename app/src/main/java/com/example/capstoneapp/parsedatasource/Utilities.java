package com.example.capstoneapp.parsedatasource;

import android.util.Log;

import com.example.capstoneapp.model.College;
import com.example.capstoneapp.model.CollegeApplicationTask;
import com.example.capstoneapp.model.FavoriteCollege;
import com.example.capstoneapp.model.ParseFirebaseUser;
import com.example.capstoneapp.ui.mycolleges.mycollegedetail.taskmgmt.TaskDetailViewModel;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Utilities {

    private static final String TAG = "Utilities";
    private static final String[] stepTitles = {"Complete Essay", "Apply Scholarships", "Get Recommendation Letters", "Complete FAFSA", "Understand Tution Costs"};

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
                // addMandatorySteps(userId, college, callback);
                addMandatoryTasks(userId,college,callback);
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

    private static void addMandatoryTasks(String userId, College college, GetFavCollegesCallback callback) {
        List<CollegeApplicationTask> applicationTasks = new ArrayList<>();
        for (int indx = 0; indx < 5; indx++) {
            CollegeApplicationTask task = new CollegeApplicationTask();
            task.setFirebaseUid(userId);
            task.setCollegeId(college.getCollegeId());
            task.setTaskTitle(stepTitles[indx]);
            task.setTaskDescription(stepTitles[indx]);
            task.setTaskPriority(0);
            task.setTaskState(0);
            task.setTaskStartDate(new Date().getTime());
            task.setTaskEndDate(getCalculatedDate(30));
            applicationTasks.add(task);
        }
        CollegeApplicationTask.saveAllInBackground(applicationTasks, e -> {
            if (e != null) {
                Log.e(TAG, "Error while creating mandatory tasks for Fav College", e);
                callback.onCompleted(null, true);
            } else {
                Log.i(TAG, "Created Mandatory taks for Fav College");
                // Get again the list of fav colleges
                getFavCollegesForUser(userId, callback);
            }

        });
    }

    public static void getAllApplicationTasks(String userId, Integer collegeId, ApplicationTaskCallback callback){
        Log.i(TAG, "Querying Application tasks for fav college ...");
        ParseQuery<CollegeApplicationTask> query = ParseQuery.getQuery(CollegeApplicationTask.class);
        query.whereEqualTo(CollegeApplicationTask.TASK_KEY_USER_UID, userId);
        query.whereEqualTo(CollegeApplicationTask.TASK_KEY_FAVCOLLEGE_ID, collegeId);

        query.findInBackground((collegeTasks, e) -> {
            // check for any error
            if (e != null) {
                Log.e(TAG, "Issue with getting application steps", e);
                callback.onCompleted(new ArrayList<>(), true);
                return;
            }
            if ((collegeTasks == null) || (collegeTasks.size() == 0)) {
                Log.w(TAG, "No application steps available ");
                callback.onCompleted(new ArrayList<>(), false);
                return;
            }
            // At this point there will application steps for that user - college combination
            callback.onCompleted(collegeTasks, false);
        });
    }

    public static void deleteAllApplicationTasks(String userId, Integer collegeId){
        getAllApplicationTasks(userId, collegeId, (applicationSteps, error) -> {
            if (error){
                Log.e(TAG, "Issue with getting application steps for deletion");
            }
            else {
                CollegeApplicationTask.deleteAllInBackground(applicationSteps, ex -> {
                    // check for any error
                    if (ex != null) {
                        Log.e(TAG, "Issue with deleting application steps", ex);
                    }else {
                        Log.d(TAG,"Successfully deleted the Application Steps");
                    }
                });
            }
        });
    }

    public static void updateApplicationTask(CollegeApplicationTask applicationTask, UpdateApplicationTaskCallback callback){
        Log.i(TAG, "Updating Application task for fav college ...");
        ParseQuery<ParseObject> query = ParseQuery.getQuery("CollegeApplicationTasks");
        query.getInBackground(applicationTask.getObjectId(), (task, e) -> {
            if (e != null){
                Log.e(TAG, "Issue with getting application step for updating", e);
            }else {
                // Update the fields we want to
                task.put(CollegeApplicationTask.TASK_KEY_TITLE, applicationTask.getTaskTitle());
                task.put(CollegeApplicationTask.TASK_KEY_STATE, applicationTask.getTaskState());
                task.put(CollegeApplicationTask.TASK_KEY_END_DATE, applicationTask.getTaskEndDate());
                task.put(CollegeApplicationTask.TASK_KEY_DESCRIPTION, applicationTask.getTaskDescription());
                //All other fields will remain the same
                task.saveInBackground(ex -> {
                    if (ex != null){
                        Log.e(TAG, "App Step Save Failed...", ex);
                        callback.onCompleted(true);
                    }else {
                        Log.d(TAG, "App Step Save Success...");
                        callback.onCompleted(false);
                    }

                });
            }
        });
    }

    private static Long getCalculatedDate(int days) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_YEAR, days);
        return cal.getTimeInMillis();
    }

    public static void getAllFavCollegeTasks(String userId, List<College> favcolleges, FavCollegesTaskMapCallback callback){

        Map<Integer, List<CollegeApplicationTask>> collegeApplicationTasksMap = new HashMap<>();

        Log.i(TAG, "Querying All the tasks for FAV Colleges for a user ...");
        ParseQuery<CollegeApplicationTask> query = ParseQuery.getQuery(CollegeApplicationTask.class);
        query.whereEqualTo(CollegeApplicationTask.TASK_KEY_USER_UID, userId);

        for (int indx =0 ; indx < favcolleges.size() ; indx++) {
            int collegeId = favcolleges.get(indx).getCollegeId();
            getAllApplicationTasks(userId,collegeId , new ApplicationTaskCallback() {
                @Override
                public void onCompleted(List<CollegeApplicationTask> applicationTasks, Boolean error) {
                    collegeApplicationTasksMap.put(collegeId,applicationTasks);
                    if (collegeApplicationTasksMap.size() == favcolleges.size())
                        callback.onCompleted(collegeApplicationTasksMap,false);
                }
            });
        }

        return;
    }
}
