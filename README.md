AYAN ALI
L1S22BSCS0024
The app uses two fragments that share the same stored user data. The SettingsFragment lets the user enter things like their name, email, password, theme, and notification preferences. It checks the inputs and only saves them when everything looks good. The ProfileFragment reads those saved values and listens for any changes, so its UI updates automatically whenever the settings are updated.

Switching between the two screens is handled in the MainActivity with a simple toggle, making the flow easy to understand. The main challenges were keeping both fragments in sync and ensuring the input validation felt clean and reliable, and both were solved with shared preference listeners and clear error messages.
