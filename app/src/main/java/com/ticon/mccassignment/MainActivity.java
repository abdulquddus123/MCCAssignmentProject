package com.ticon.mccassignment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;

import com.ticon.mccassignment.Interface.OnFullScreenChangeListener;
import com.ticon.mccassignment.Utils.Utils;

public class MainActivity extends AppCompatActivity implements OnFullScreenChangeListener {

    NavController navController;
    public OnFullScreenChangeListener onFullScreenChangeListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        navController = Navigation.findNavController(this, R.id.my_nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController);
        onFullScreenChangeListener = MainActivity.this;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (Utils.youtubePlayerFullScreen) {
            Log.d("MainActivity ", "full screen in back pressed");
            onFullScreenChangeListener.onChangeListener();
            this.getSupportActionBar().show();
            this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        } else {
            System.out.println("full screen not");
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        navController.navigateUp();
        return super.onSupportNavigateUp();
    }

    @Override
    public void onChangeListener() {

    }
}