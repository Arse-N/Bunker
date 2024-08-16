package com.example.bunker.fragments;

import android.os.Bundle;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreferenceCompat;
import com.example.bunker.R;
import com.example.bunker.model.GameInfo;
import com.example.bunker.service.EmailSender;
import com.example.bunker.util.JsonUtil;

public class SettingsFragment extends PreferenceFragmentCompat {

    private EmailSender emailSender;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey);
        emailSender = new EmailSender(requireContext());
        setupPreferences();
    }

    private void setupPreferences() {
        setupReportBugPreference();
        setupIncludeGenderPreference();
    }

    private void setupReportBugPreference() {
        Preference reportBugPreference = findPreference("report_bug");
        if (reportBugPreference != null) {
            reportBugPreference.setOnPreferenceClickListener(preference -> {
                emailSender.reportBug();
                return true;
            });
        }
    }

    private void setupIncludeGenderPreference() {
        SwitchPreferenceCompat includeGenderPreference = findPreference("gender");
        if (includeGenderPreference != null) {
            includeGenderPreference.setOnPreferenceChangeListener((preference, newValue) -> {
                boolean isEnabled = (boolean) newValue;
                GameInfo gameInfo = JsonUtil.readFromGameInfoJson(requireContext());
                if (gameInfo != null) {
                    gameInfo.setGenderIncluded(isEnabled);
                }
                JsonUtil.writeToGameInfoJson(requireContext(), gameInfo);

                return true;
            });
        }
    }
}
