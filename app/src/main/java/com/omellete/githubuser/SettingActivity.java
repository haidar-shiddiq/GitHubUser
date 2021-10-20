package com.omellete.githubuser;

import static com.omellete.githubuser.MainActivity.dataStore;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.switchmaterial.SwitchMaterial;
import com.omellete.githubuser.databinding.ActivitySettingBinding;
import com.omellete.githubuser.preference.SettingPreferences;
import com.omellete.githubuser.viewmodel.MainViewModel;
import com.omellete.githubuser.viewmodel.ViewModelFactory;

public class SettingActivity extends AppCompatActivity {

    ActivitySettingBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ActionBar back = getSupportActionBar();
        if (back != null) {
            back.setDisplayHomeAsUpEnabled(true);
        }

        SwitchMaterial switchTheme = findViewById(R.id.switch_theme);
        SettingPreferences pref = SettingPreferences.getInstance(dataStore);

        MainViewModel mainViewModel = new ViewModelProvider(this, new ViewModelFactory(pref)).get(MainViewModel.class);
        mainViewModel.getThemeSettings().observe(this, isDarkModeActive -> {
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                switchTheme.setChecked(true);
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                switchTheme.setChecked(false);
            }
        });

        switchTheme.setOnCheckedChangeListener((buttonView, isChecked) ->
                mainViewModel.saveThemeSetting(isChecked)
        );
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

}