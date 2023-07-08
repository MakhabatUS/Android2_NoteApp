package com.makhabatusen.noteapp;

import android.os.Bundle;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.makhabatusen.noteapp.databinding.ActivityMainBinding;
import com.makhabatusen.noteapp.utils.Preferences;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    private AppBarConfiguration appBarConfiguration;
    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initNavController();

        boolean isBoardShown = new Preferences(this).isShown();
        if (!isBoardShown) navController.navigate(R.id.boardFragment);

    }

    private void initNavController() {
        BottomNavigationView navView = findViewById(R.id.nav_view);
        appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

        navController.addOnDestinationChangedListener((navController, navDestination, bundle) -> {

            if (navDestination.getId() == R.id.navigation_home ||
                    navDestination.getId() == R.id.navigation_dashboard ||
                    navDestination.getId() == R.id.navigation_notifications
            ) {
                navView.setVisibility(View.VISIBLE);
            } else {
                navView.setVisibility(View.GONE);
            }


            if (getSupportActionBar() != null) {
                if (navDestination.getId() == R.id.boardFragment)
                    getSupportActionBar().hide();
                else
                    getSupportActionBar().show();
            }
        });


    }

    @Override
    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(navController, appBarConfiguration) || super.onSupportNavigateUp();
    }


    public void closeFragment() {
        navController.navigateUp();
    }
}