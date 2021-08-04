package com.example.financetrackerapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.financetrackerapp.Fragment.DashBoardFragment;
import com.example.financetrackerapp.Fragment.ExpenseFragment;
import com.example.financetrackerapp.Fragment.IncomeFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private BottomNavigationView bottomNavigationView;
    private FrameLayout frameLayout;

    //Fragments
    private DashBoardFragment dashBoardFragment;
    private ExpenseFragment expenseFragment;
    private IncomeFragment incomeFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.my_toolbar);
        toolbar.setTitle("Finace Manger");
        setSupportActionBar(toolbar);
        getSupportFragmentManager().beginTransaction().replace(R.id.main_frame,new DashBoardFragment()).commit();
        bottomNavigationView = findViewById(R.id.bottomnavigationbar);
        frameLayout = findViewById(R.id.main_frame);
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);

        dashBoardFragment = new DashBoardFragment();
        expenseFragment = new ExpenseFragment();
        incomeFragment = new IncomeFragment();

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.navView);
        navigationView.setNavigationItemSelectedListener(this);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {

                    case R.id.dashboard:
                        startFragment(dashBoardFragment);
                        return true;

                    case R.id.income:
                        startFragment(incomeFragment);
                        return true;

                    case R.id.expense:
                        startFragment(expenseFragment);
                        return true;

                    case R.id.analytics:
                        startActivity(new Intent(MainActivity.this,AnalyticsActivity.class));
                        return true;

                    default:
                        return true;

                }
            }
        });
    }

    private void startFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_frame, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        if (drawerLayout.isDrawerOpen(GravityCompat.END)) {
            drawerLayout.closeDrawer(GravityCompat.END);
        } else {
            super.onBackPressed();
        }
        super.onBackPressed();
    }

    public void displaySelectedListner(int itemId) {
        Fragment fragment = null;
        switch (itemId) {
            case R.id.dashboard:
                fragment=new DashBoardFragment();
                break;
            case R.id.income:
                fragment=new IncomeFragment();
                break;
            case R.id.expense:
                fragment=new ExpenseFragment();
                break;
            case R.id.analytics:
                startActivity(new Intent(MainActivity.this,AnalyticsActivity.class));
                break;
        }

        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.main_frame, fragment);
            ft.commit();
        }
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        drawerLayout.closeDrawer(GravityCompat.START);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        displaySelectedListner(item.getItemId());
        return false;
    }
}