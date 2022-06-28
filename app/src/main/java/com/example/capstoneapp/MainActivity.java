package com.example.capstoneapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toolbar;

import com.example.capstoneapp.ui.profile.ProfileFragment;
import com.example.capstoneapp.ui.dashboard.DashboardFragment;
import com.example.capstoneapp.ui.collegesearch.CollegeSearchFragment;
import com.example.capstoneapp.ui.mycolleges.CollegesFragment;
import com.example.capstoneapp.auth.AuthActivity;
import com.example.capstoneapp.ui.settings.SettingsFragment;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    public static final String TAG = "MainActivity";

    private androidx.appcompat.widget.Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    private NavigationView sideNav;
    private ImageView ivProfileImage;
    private TextView tvUserName;

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

        // Side Navigation Bar
        toolbar = findViewById(R.id.nav_action);
        drawerLayout = findViewById(R.id.drawerLayout);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        sideNav = findViewById(R.id.sideNav);
        ivProfileImage = findViewById(R.id.ivProfileImage);
        tvUserName = findViewById(R.id.tvUserName);

        setSupportActionBar(toolbar);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        sideNav.setNavigationItemSelectedListener(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (toggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onLogoutClick(View view) {
        AuthUI.getInstance()
                .signOut(this)
                .addOnCompleteListener(task ->
                        startActivity(new Intent(MainActivity.this, AuthActivity.class)));

        startActivity(new Intent(this, AuthActivity.class));
        finish();
    }

    public void onProfileClick(View view) {
        Log.i(TAG, "Profile Clicked");
        fragmentManager.beginTransaction().replace(R.id.flContainer, profileFragment).commit();
        drawerLayout.closeDrawer(GravityCompat.START);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment;
        switch (item.getItemId()) {
            case R.id.nav_college_search:
                Log.i(TAG, "College Search Clicked");
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
            case R.id.nav_dashboard:
            default:
                Log.i(TAG, "Settings Clicked");
                fragment = dashboardFragment;
                break;
        }
        fragmentManager.beginTransaction().replace(R.id.flContainer, fragment).commit();
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}