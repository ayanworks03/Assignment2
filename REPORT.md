## Assignment 2 – Multi-Fragment SharedPreferences App

### Implementation Overview
- **Fragments:** `SettingsFragment` collects user inputs, validates them, and writes them to `SharedPreferences`. `ProfileFragment` reads the same store, listens for changes via `SharedPreferences.OnSharedPreferenceChangeListener`, and refreshes the UI automatically.
- **SharedPreferences:** Centralized in `PreferenceStore` (`user_preferences`). Keys cover username, email, password, theme, and notification flag. Writes use `apply()` for async persistence; reads occur on fragment resume and when preferences change.
- **Navigation:** `MainActivity` hosts a `FragmentContainerView` and a `MaterialButtonToggleGroup` that swaps between fragments via `FragmentManager`. Initial load defaults to the settings screen.
- **Validation & UX:** TextInputLayouts enforce non-empty username, valid email, and 6+ char passwords. Snackbar confirms saves/resets. `Reset Preferences` clears the store via `SharedPreferences.Editor.clear()` (bonus requirement).
- **Persistence:** Because SharedPreferences live in app storage, values remain available across app/process restarts. Profile fragment's empty state informs users when no data exists.

### Challenges & Resolutions
1. **Keeping both fragments in sync:** Solved by registering `ProfileFragment` as a preferences change listener, ensuring it updates immediately whenever settings change.
2. **Balancing validation feedback and persistence:** Used TextInputLayout errors for inline validation and prevented writes until inputs pass, guaranteeing only valid data gets persisted.
3. **Navigation clarity in a small app:** Added a toggle navigation bar plus toolbar title so users always know which fragment they're on while keeping the UI lightweight.

### How to Run / Demo
1. Build & run the project in Android Studio (Gradle sync picks up dependencies automatically).
2. In the running app:
   - Open **Settings**, fill all fields, toggle notifications, then tap **Save Preferences** (displayed confirmation).
   - Switch to **Profile** to see the saved values (password masked). Close and reopen the app to confirm persistence.
   - Go back to **Settings** and tap **Reset Preferences** to clear storage; Profile immediately shows the empty state.
3. Record the above flow (2–3 minutes) for the demonstration video.

### Next Steps
If you need optional polish, consider adding data binding, using the Jetpack Navigation component, or persisting additional preference types. Otherwise, the current implementation meets all assignment requirements plus the bonus task.

