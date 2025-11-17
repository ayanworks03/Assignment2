package com.ayan.Assignment2;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.button.MaterialButtonToggleGroup;

public class MainActivity extends AppCompatActivity {

    private MaterialButtonToggleGroup toggleGroup;
    private MaterialButton settingsButton;
    private MaterialButton profileButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        toggleGroup = findViewById(R.id.toggle_group);
        settingsButton = findViewById(R.id.button_settings);
        profileButton = findViewById(R.id.button_profile);

        settingsButton.setOnClickListener(v -> showFragment(new SettingsFragment(), R.id.button_settings));
        profileButton.setOnClickListener(v -> showFragment(new ProfileFragment(), R.id.button_profile));

        if (savedInstanceState == null) {
            toggleGroup.check(R.id.button_settings);
            showFragment(new SettingsFragment(), R.id.button_settings);
        }
    }

    private void showFragment(Fragment fragment, int buttonId) {
        getSupportFragmentManager()
                .beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.fragment_container, fragment)
                .commit();
        toggleGroup.check(buttonId);
    }
}