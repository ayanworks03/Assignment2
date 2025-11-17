package com.ayan.Assignment2;

import android.content.Context;
import android.content.SharedPreferences;

public final class PreferenceStore {

    private static final String PREFS_NAME = "user_preferences";

    public static final String KEY_USERNAME = "key_username";
    public static final String KEY_EMAIL = "key_email";
    public static final String KEY_PASSWORD = "key_password";
    public static final String KEY_THEME = "key_theme";
    public static final String KEY_NOTIFICATIONS = "key_notifications";

    private PreferenceStore() {
    }

    public static SharedPreferences getPreferences(Context context) {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }
}

