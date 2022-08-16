package com.burakgulsoy.freelancerproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.burakgulsoy.freelancerproject.models.Task;
import com.burakgulsoy.freelancerproject.retrofit.RetrofitService;
import com.burakgulsoy.freelancerproject.retrofit.modelAPIs.TaskAPI;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FreelancerPageActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;

    BottomNavigationView bottomNavigationView;

    ToDoListFragment toDoListFragment = new ToDoListFragment();
    InProgressFragment inProgressFragment = new InProgressFragment();
    DoneFragment doneFragment = new DoneFragment();

    private int freelancer_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_freelancer_page);
        setTitle("");

        Intent intent = getIntent();
        freelancer_id = intent.getIntExtra("loginIntent",-1);


//        Log.d("freelancerid A->B", String.valueOf(freelancer_id));
        Log.d("freelancer page activity on create, freelancer_id:", String.valueOf(freelancer_id));


        // send correct freelancer_id to fragments so that they can send it back here
        Bundle bundle = new Bundle();
        bundle.putInt("freelancerid",freelancer_id);
        toDoListFragment.setArguments(bundle);
        inProgressFragment.setArguments(bundle);
        doneFragment.setArguments(bundle);

        drawerInitializer();
        bottomBarInitializer();
        setNavigationListener();
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void bottomBarInitializer() {
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        getSupportFragmentManager().beginTransaction().replace(R.id.bottom_nav_framelayout, toDoListFragment).commit();
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {


            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {

                    case R.id.toDoListBar:
                        getSupportFragmentManager().beginTransaction().replace(R.id.bottom_nav_framelayout,  toDoListFragment).commit();
                        return true;
                    case R.id.inProgressBar:
                        getSupportFragmentManager().beginTransaction().replace(R.id.bottom_nav_framelayout, inProgressFragment).commit();
                        return true;
                    case R.id.doneBar:
                        getSupportFragmentManager().beginTransaction().replace(R.id.bottom_nav_framelayout, doneFragment).commit();
                        return true;
                }

                return false;
            }
        });
    }

    public void drawerInitializer() {
        // drawer layout instance to toggle the menu icon to open
        // drawer and back button to close drawer
        drawerLayout = findViewById(R.id.my_drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(FreelancerPageActivity.this, drawerLayout, R.string.nav_open, R.string.nav_close);

        // pass the Open and Close toggle for the drawer layout listener
        // to toggle the button
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        // to make the Navigation drawer icon always appear on the action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void setNavigationListener() {
        NavigationView navigationView = findViewById(R.id.drawer_nav_view);
        navigationView.setNavigationItemSelectedListener(FreelancerPageActivity.this);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {

            case R.id.nav_account:
                Intent intent = new Intent(FreelancerPageActivity.this, MyAccountPage.class);
                intent.putExtra("loginIntent", freelancer_id);
                startActivity(intent);
                break;
            case R.id.nav_logout:
                Intent intent2 = new Intent(FreelancerPageActivity.this,MainActivity.class);
                intent2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent2);
                finish();
                break;

        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}