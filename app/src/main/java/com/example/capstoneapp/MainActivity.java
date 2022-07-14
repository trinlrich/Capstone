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
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.example.capstoneapp.model.ParseFirebaseUser;
import com.example.capstoneapp.ui.UiUtils;
import com.example.capstoneapp.ui.collegesearch.filter.CollegeFilter;
import com.example.capstoneapp.ui.collegesearch.filter.FilterUtils;
import com.example.capstoneapp.ui.profile.ProfileFragment;
import com.example.capstoneapp.ui.dashboard.DashboardFragment;
import com.example.capstoneapp.ui.collegesearch.CollegeSearchFragment;
import com.example.capstoneapp.ui.mycolleges.CollegesFragment;
import com.example.capstoneapp.auth.AuthActivity;
import com.example.capstoneapp.ui.settings.SettingsFragment;
import com.firebase.ui.auth.AuthUI;
import com.google.android.material.navigation.NavigationView;
import com.parse.ParseFile;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    public static final String TAG = "MainActivity";

    private androidx.appcompat.widget.Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    private ActionBar actionBar;
    private NavigationView sideNav;
    private View navHeader;
    private ImageView ivNavProfileImage;
    private TextView tvNavUserName;

    // ViewModel
    private MainViewModel viewModel;

    // Shared Preferences
    private SharedPreferences preferences;

    // Navigation fragments
    final FragmentManager fragmentManager = getSupportFragmentManager();
    final Fragment profileFragment = new ProfileFragment();
    final Fragment dashboardFragment = new DashboardFragment();
    final Fragment collegeSearchFragment = new CollegeSearchFragment();
    final Fragment collegesFragment = new CollegesFragment();
    final Fragment settingsFragment = new SettingsFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);

        Log.i(TAG, "main activity started");

        // Side Navigation Bar
        toolbar = findViewById(R.id.nav_action);
        drawerLayout = findViewById(R.id.drawerLayout);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        sideNav = findViewById(R.id.sideNav);

        // Navigation Header
        navHeader = sideNav.getHeaderView(0);
        ivNavProfileImage = navHeader.findViewById(R.id.ivNavProfileImage);
        tvNavUserName = navHeader.findViewById(R.id.tvNavUserName);

        viewModel.getUserProfileInfo();

        // User observer
        Observer<ParseFirebaseUser> userObserver = user -> {
            if (user == null) {
                Log.e(TAG, "No user found");
            } else {
                ParseFile profileImage = user.getProfileImage();
                if (profileImage != null) {
                    UiUtils.setViewImage(getApplicationContext(), ivNavProfileImage, profileImage.getUrl(), new CircleCrop(), R.drawable.profile_black_48);
                }
                UiUtils.setViewText(getApplicationContext(), tvNavUserName, user.getFirstName() + " " + user.getLastName());
            }
        };
        viewModel.user.observe(this, userObserver);

        // Set toolbar
        setSupportActionBar(toolbar);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        sideNav.setNavigationItemSelectedListener(this);

        actionBar = getSupportActionBar();
        if (actionBar != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        preferences = getSharedPreferences(getString(R.string.filter_key), Context.MODE_PRIVATE);
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
            case R.id.nav_college_search:
                Log.i(TAG, "College Search Clicked");
                setDefaultFilters();
                fragment = collegeSearchFragment;
                break;
            case R.id.nav_colleges:
                Log.i(TAG, "My Colleges Clicked");
                fragment = collegesFragment;
                break;
            case R.id.nav_settings:
                Log.i(TAG, "Settings Clicked");
                fragment = settingsFragment;
                break;
            case R.id.nav_logout:
                Log.i(TAG, "Logout Clicked");
                logoutUser();
            case R.id.nav_dashboard:
            default:
                Log.i(TAG, "Dashboard Clicked");
                fragment = dashboardFragment;
                break;
        }
        fragmentManager.beginTransaction().replace(R.id.flContainer, fragment).commit();
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    public void onProfileClick(View view) {
        Log.i(TAG, "Profile Clicked");
        fragmentManager.beginTransaction().replace(R.id.flContainer, profileFragment).commit();
        drawerLayout.closeDrawer(GravityCompat.START);
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
        FilterUtils.putDefaultFilter(this, new CollegeFilter(getString(R.string.state_key)));
        FilterUtils.putDefaultFilter(this, new CollegeFilter(getString(R.string.type_key)));
        FilterUtils.putDefaultFilter(this, new CollegeFilter(getString(R.string.mission_key)));
    }
}
