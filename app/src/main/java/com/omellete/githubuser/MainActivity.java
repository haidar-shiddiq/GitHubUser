package com.omellete.githubuser;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SearchView;
import androidx.datastore.preferences.core.Preferences;
import androidx.datastore.preferences.rxjava3.RxPreferenceDataStoreBuilder;
import androidx.datastore.rxjava3.RxDataStore;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.omellete.githubuser.adapter.UserListAdapter;
import com.omellete.githubuser.databinding.ActivityMainBinding;
import com.omellete.githubuser.preference.SettingPreferences;
import com.omellete.githubuser.viewmodel.MainViewModel;
import com.omellete.githubuser.viewmodel.MyViewModel;
import com.omellete.githubuser.viewmodel.ViewModelFactory;

public class MainActivity extends AppCompatActivity {

    MyViewModel searchViewModel;
    ProgressDialog loadingMessage;
    UserListAdapter userListAdapter;
    private ActivityMainBinding binding;
    public static RxDataStore<Preferences> dataStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        loadingMessage = new ProgressDialog(this);
        loadingMessage.setCancelable(false);
        loadingMessage.setMessage("Wait for a moment");
        userListAdapter = new UserListAdapter(this);
        binding.rvMain.setLayoutManager(new LinearLayoutManager(this));
        binding.rvMain.setAdapter(userListAdapter);
        binding.rvMain.setHasFixedSize(true);

        dataStore = new RxPreferenceDataStoreBuilder(this, "settings").build();
        SettingPreferences pref = SettingPreferences.getInstance(dataStore);
        MainViewModel mainViewModel = new ViewModelProvider(this, new ViewModelFactory(pref)).get(MainViewModel.class);

        mainViewModel.getThemeSettings().observe(this, isDarkModeActive -> {
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }
        });

        searchViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(MyViewModel.class);
        searchViewModel.getResultList().observe(this, modelSearchData -> {
            loadingMessage.dismiss();
            if (modelSearchData.size() != 0) {
                userListAdapter.setSearchUserList(modelSearchData);
            } else {
                Toast.makeText(MainActivity.this, "Not Found", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_menu, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        if (searchManager != null) {
            SearchView searchView = (SearchView) (menu.findItem(R.id.search)).getActionView();
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
            searchView.setQueryHint(getResources().getString(R.string.search_hint));
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    if (query.isEmpty()) {
                        Toast.makeText(MainActivity.this, "Please type a userneme", Toast.LENGTH_SHORT).show();
                    } else {
                        loadingMessage.show();
                        searchViewModel.searchUsername(query);
                        return true;
                    }
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    return false;
                }
            });
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.setting:
                Intent i = new Intent(this, SettingActivity.class);
                startActivity(i);
                break;
            case R.id.favorite:
                Intent j = new Intent(this, FavoriteActivity.class);
                startActivity(j);
                break;
        }
        return super.onOptionsItemSelected(item);
    }


}