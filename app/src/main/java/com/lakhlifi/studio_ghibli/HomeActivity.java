package com.lakhlifi.studio_ghibli;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import com.lakhlifi.studio_ghibli.Fragements.HomeFragment;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        FragmentManager fragmentManager;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.frameHomeContainer,new HomeFragment(), HomeFragment.class.getSimpleName()).commit();



    }
}