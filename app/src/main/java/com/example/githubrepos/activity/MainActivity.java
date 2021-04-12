package com.example.githubrepos.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.example.githubrepos.R;
import com.example.githubrepos.adapter.ResponseAdapter;
import com.example.githubrepos.fragment.DetailFragment;
import com.example.githubrepos.fragment.HomeFragment;
import com.example.githubrepos.fragment.NotificationFragment;
import com.example.githubrepos.fragment.SettingsFragment;
import com.example.githubrepos.model.ResponseModel;
import com.example.githubrepos.viewmodel.ResponseViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialization();
        setListeners();
    }

    private void initialization() {
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
    }

    private void setListeners() {
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        setNavigationPage();
    }

    private void setNavigationPage() {
        displaySelectedScreen(R.id.home);
    }


    public void displaySelectedScreen(int id) {
        Fragment fragment = null;
        String backstackname = "", tag= "";
        if (id == R.id.home) {
            fragment = new HomeFragment();
            tag = "home";
            FragmentManager manager = getSupportFragmentManager();

            for(int entry = 0; entry<manager.getBackStackEntryCount(); entry++){
                String name = manager.getBackStackEntryAt(entry).getName();
                if(name.equals("com.example.githubrepos.fragment.DetailFragment")){
                    fragment = new DetailFragment();
                    tag = "detailpage";
                    break;
                }
            }

        } else if (id == R.id.notification) {
            fragment = new NotificationFragment();
            tag = " notification";

        } else if (id == R.id.settings) {
            fragment = new SettingsFragment();
            tag = "settings";
        }
        if (fragment != null) {
            backstackname = fragment.getClass().getName();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, fragment, tag);
            ft.addToBackStack(backstackname);
            ft.commit();
        }
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        displaySelectedScreen(id);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            getSupportFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }

}