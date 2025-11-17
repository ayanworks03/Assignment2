package com.ayan.Assignment2;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ProfileFragment extends Fragment implements SharedPreferences.OnSharedPreferenceChangeListener {

    private SharedPreferences preferences;
    private TextView usernameView;
    private TextView emailView;
    private TextView passwordView;
    private TextView themeView;
    private TextView notificationsView;
    private View emptyStateView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        usernameView = view.findViewById(R.id.text_username_value);
        emailView = view.findViewById(R.id.text_email_value);
        passwordView = view.findViewById(R.id.text_password_value);
        themeView = view.findViewById(R.id.text_theme_value);
        notificationsView = view.findViewById(R.id.text_notifications_value);
        emptyStateView = view.findViewById(R.id.layout_empty_state);
        preferences = PreferenceStore.getPreferences(requireContext());
    }

    @Override
    public void onResume() {
        super.onResume();
        preferences.registerOnSharedPreferenceChangeListener(this);
        populateProfile();
    }

    @Override
    public void onPause() {
        super.onPause();
        preferences.unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        populateProfile();
    }

    private void populateProfile() {
        boolean hasData = preferences.contains(PreferenceStore.KEY_USERNAME)
                || preferences.contains(PreferenceStore.KEY_EMAIL)
                || preferences.contains(PreferenceStore.KEY_PASSWORD);

        emptyStateView.setVisibility(hasData ? View.GONE : View.VISIBLE);

        usernameView.setText(getValueOrPlaceholder(preferences.getString(PreferenceStore.KEY_USERNAME, null)));
        emailView.setText(getValueOrPlaceholder(preferences.getString(PreferenceStore.KEY_EMAIL, null)));
        passwordView.setText(maskPassword(preferences.getString(PreferenceStore.KEY_PASSWORD, null)));
        themeView.setText(getValueOrPlaceholder(preferences.getString(PreferenceStore.KEY_THEME, null)));
        notificationsView.setText(preferences.getBoolean(PreferenceStore.KEY_NOTIFICATIONS, false)
                ? getString(R.string.enabled)
                : getString(R.string.disabled));
    }

    private String getValueOrPlaceholder(String value) {
        return value == null || value.isEmpty()
                ? getString(R.string.not_set)
                : value;
    }

    private String maskPassword(String password) {
        if (password == null || password.isEmpty()) {
            return getString(R.string.not_set);
        }
        return new String(new char[password.length()]).replace("\0", "•");
    }
}

