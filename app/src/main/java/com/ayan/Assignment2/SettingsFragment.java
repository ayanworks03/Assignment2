package com.ayan.Assignment2;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class SettingsFragment extends Fragment {

    private TextInputLayout usernameLayout;
    private TextInputLayout emailLayout;
    private TextInputLayout passwordLayout;
    private TextInputEditText usernameInput;
    private TextInputEditText emailInput;
    private TextInputEditText passwordInput;
    private Spinner themeSpinner;
    private SwitchMaterial notificationsSwitch;
    private MaterialButton saveButton;
    private MaterialButton resetButton;

    private SharedPreferences preferences;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bindViews(view);
        setupThemeSpinner();
        preferences = PreferenceStore.getPreferences(requireContext());
        populateFields();
        setupListeners();
    }

    private void bindViews(View view) {
        usernameLayout = view.findViewById(R.id.input_layout_username);
        emailLayout = view.findViewById(R.id.input_layout_email);
        passwordLayout = view.findViewById(R.id.input_layout_password);
        usernameInput = view.findViewById(R.id.input_username);
        emailInput = view.findViewById(R.id.input_email);
        passwordInput = view.findViewById(R.id.input_password);
        themeSpinner = view.findViewById(R.id.spinner_theme);
        notificationsSwitch = view.findViewById(R.id.switch_notifications);
        saveButton = view.findViewById(R.id.button_save);
        resetButton = view.findViewById(R.id.button_reset);
    }

    private void setupThemeSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                requireContext(),
                R.array.theme_options,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        themeSpinner.setAdapter(adapter);
    }

    private void setupListeners() {
        saveButton.setOnClickListener(v -> {
            if (validateInputs()) {
                savePreferences();
                Snackbar.make(v, R.string.preferences_saved, Snackbar.LENGTH_SHORT).show();
            }
        });

        resetButton.setOnClickListener(v -> {
            preferences.edit().clear().apply();
            populateFields();
            Snackbar.make(v, R.string.preferences_reset, Snackbar.LENGTH_SHORT).show();
        });
    }

    private boolean validateInputs() {
        boolean isValid = true;

        String username = getText(usernameInput);
        String email = getText(emailInput);
        String password = getText(passwordInput);

        if (TextUtils.isEmpty(username)) {
            usernameLayout.setError(getString(R.string.error_username_required));
            isValid = false;
        } else {
            usernameLayout.setError(null);
        }

        if (TextUtils.isEmpty(email) || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailLayout.setError(getString(R.string.error_email_invalid));
            isValid = false;
        } else {
            emailLayout.setError(null);
        }

        if (TextUtils.isEmpty(password) || password.length() < 6) {
            passwordLayout.setError(getString(R.string.error_password_length));
            isValid = false;
        } else {
            passwordLayout.setError(null);
        }

        return isValid;
    }

    private void savePreferences() {
        preferences.edit()
                .putString(PreferenceStore.KEY_USERNAME, getText(usernameInput))
                .putString(PreferenceStore.KEY_EMAIL, getText(emailInput))
                .putString(PreferenceStore.KEY_PASSWORD, getText(passwordInput))
                .putString(PreferenceStore.KEY_THEME, getSelectedTheme())
                .putBoolean(PreferenceStore.KEY_NOTIFICATIONS, notificationsSwitch.isChecked())
                .apply();
    }

    private void populateFields() {
        if (preferences == null) {
            return;
        }

        usernameInput.setText(preferences.getString(PreferenceStore.KEY_USERNAME, ""));
        emailInput.setText(preferences.getString(PreferenceStore.KEY_EMAIL, ""));
        passwordInput.setText(preferences.getString(PreferenceStore.KEY_PASSWORD, ""));
        setSelectedTheme(preferences.getString(PreferenceStore.KEY_THEME, getDefaultTheme()));
        notificationsSwitch.setChecked(preferences.getBoolean(PreferenceStore.KEY_NOTIFICATIONS, false));
    }

    private String getSelectedTheme() {
        return themeSpinner.getSelectedItem() != null
                ? themeSpinner.getSelectedItem().toString()
                : getDefaultTheme();
    }

    @SuppressWarnings("unchecked")
    private void setSelectedTheme(String theme) {
        ArrayAdapter<CharSequence> adapter = (ArrayAdapter<CharSequence>) themeSpinner.getAdapter();
        if (adapter == null) {
            return;
        }
        int position = adapter.getPosition(theme);
        if (position >= 0) {
            themeSpinner.setSelection(position);
        } else {
            themeSpinner.setSelection(0);
        }
    }

    private String getDefaultTheme() {
        return getResources().getStringArray(R.array.theme_options)[0];
    }

    private String getText(TextInputEditText inputEditText) {
        return inputEditText.getText() != null ? inputEditText.getText().toString().trim() : "";
    }
}

