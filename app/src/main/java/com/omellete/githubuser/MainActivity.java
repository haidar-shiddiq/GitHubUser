package com.omellete.githubuser;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

import com.omellete.githubuser.adapter.UserListAdapter;
import com.omellete.githubuser.model.SearchModel;
import com.omellete.githubuser.viewmodel.MyViewModel;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    MyViewModel searchViewModel;
    ProgressDialog loadingMessage;
    RecyclerView rv_main;
    UserListAdapter userListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rv_main = findViewById(R.id.rv_main);
        loadingMessage = new ProgressDialog(this);
        loadingMessage.setCancelable(false);
        loadingMessage.setMessage("Wait for a moment");
        userListAdapter = new UserListAdapter(this);
        rv_main.setLayoutManager(new LinearLayoutManager(this));
        rv_main.setAdapter(userListAdapter);
        rv_main.setHasFixedSize(true);


        searchViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(MyViewModel.class);
        searchViewModel.getResultList().observe(this, new Observer<ArrayList<SearchModel>>() {
            @Override
            public void onChanged(ArrayList<SearchModel> modelSearchData) {
                loadingMessage.dismiss();
                if (modelSearchData.size() != 0) {
                    userListAdapter.setSearchUserList(modelSearchData);
                } else {
                    Toast.makeText(MainActivity.this, "Not Found", Toast.LENGTH_SHORT).show();
                }
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

}