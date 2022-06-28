package com.example.capstoneapp.ui.survey;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.Toast;

import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstoneapp.FirebaseUser;
import com.example.capstoneapp.MainActivity;
import com.parse.ParseException;
import com.parse.SaveCallback;

public class SurveyViewModel extends ViewModel {

    public static final String TAG = "SurveyViewModel";

    private void saveUser(String firstName, String lastName, String degreeSeeking) {
        FirebaseUser user = new FirebaseUser();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setDegreeSeeking(degreeSeeking);
        user.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e != null) {
                    Log.e(TAG, "Error while saving", e);
                    Toast.makeText(this, "Error while saving!", Toast.LENGTH_SHORT).show();
                }
                Log.i(TAG, "Post save was successful");
                Intent intent = new Intent(this, MainActivity.class);
            }
        });

    }

}