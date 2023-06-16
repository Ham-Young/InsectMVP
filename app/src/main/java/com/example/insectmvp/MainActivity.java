package com.example.insectmvp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import com.example.insectmvp.adapter.GridAdapter;
import com.example.insectmvp.fragment.GridFragment;

public class MainActivity extends AppCompatActivity {

    public static int currentPosition;
    private static final String KEY_CURRENT_POSITION = "com.example.insectmvp.key.currentPosition";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null) {
            currentPosition = savedInstanceState.getInt(KEY_CURRENT_POSITION, 0);
            // Return here to prevent adding additional GridFragments when changing orientation.
            return;
        }

        loadFragment(new GridFragment());
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_CURRENT_POSITION, currentPosition);
    }

    private void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager
                .beginTransaction()
                .add(R.id.fragment_container, fragment, GridFragment.class.getSimpleName())
                .commit();
    }
}