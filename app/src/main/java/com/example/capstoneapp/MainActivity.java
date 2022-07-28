package com.example.capstoneapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.example.capstoneapp.model.ParseFirebaseUser;
import com.example.capstoneapp.ui.SharedPreferenceUtils;
import com.example.capstoneapp.ui.UiUtils;
import com.example.capstoneapp.model.CollegeFilter;
import com.example.capstoneapp.ui.mycolleges.MyCollegesFragment;
import com.example.capstoneapp.ui.profile.ProfileFragment;
import com.example.capstoneapp.ui.collegesearch.CollegeSearchFragment;
import com.example.capstoneapp.auth.AuthActivity;
import com.firebase.ui.auth.AuthUI;
import com.google.android.material.navigation.NavigationView;
import com.parse.ParseFile;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    public static final String TAG = "MainActivity";

    private androidx.appcompat.widget.Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    private ActionBar actionBar;
    private NavigationView sideNav;

    // ViewModel
    private MainViewModel viewModel;

    // Navigation fragments
    final FragmentManager fragmentManager = getSupportFragmentManager();
    final Fragment profileFragment = new ProfileFragment();
    final Fragment collegeSearchFragment = new CollegeSearchFragment();
    final Fragment collegesFragment = new MyCollegesFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);

        // Side Navigation Bar
        toolbar = findViewById(R.id.nav_action);
        drawerLayout = findViewById(R.id.drawerLayout);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        sideNav = findViewById(R.id.sideNav);

        viewModel.getUserProfileInfo();

        // Set toolbar
        setSupportActionBar(toolbar);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        sideNav.setNavigationItemSelectedListener(this);

        actionBar = getSupportActionBar();
        if (actionBar != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (toggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment;
        switch (item.getItemId()) {
            case R.id.nav_profile:
                fragment = profileFragment;
                break;
            case R.id.nav_college_search:
                setDefaultFilters();
                fragment = collegeSearchFragment;
                break;
            case R.id.nav_logout:
                logoutUser();
            case R.id.nav_colleges:
            default:
                fragment = collegesFragment;
                break;
        }
        fragmentManager.beginTransaction().replace(R.id.flContainer, fragment).commit();
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    public void logoutUser() {
        AuthUI.getInstance()
                .signOut(this)
                .addOnCompleteListener(task ->
                        startActivity(new Intent(this, AuthActivity.class)));
        finish();
    }

    private void setDefaultFilters() {
        // Create filters with default "All" value
        List<CollegeFilter> stateFilters = new ArrayList<>();
        stateFilters.add(new CollegeFilter(getString(R.string.state_key)));
        SharedPreferenceUtils.putFilter(this, stateFilters);
        SharedPreferenceUtils.putDefaultFilter(this, new CollegeFilter(getString(R.string.type_key)));
        SharedPreferenceUtils.putDefaultFilter(this, new CollegeFilter(getString(R.string.mission_key)));
    }
}
