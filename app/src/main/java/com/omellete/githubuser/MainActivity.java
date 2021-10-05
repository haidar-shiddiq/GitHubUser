package com.omellete.githubuser;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.app.ProgressDialog;
import android.widget.TextView;
import android.view.KeyEvent;
import android.content.Context;
import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    MyViewModel searchViewModel;
    ImageView clear;
    ProgressDialog loadingMessage;
    RecyclerView rv_main;
    EditText searchfield;
    UserListAdapter userListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchfield = findViewById(R.id.fieldSearch);
        rv_main = findViewById(R.id.rv_main);
        loadingMessage = new ProgressDialog(this);
        loadingMessage.setCancelable(false);
        loadingMessage.setMessage("Wait for a moment...");
        userListAdapter = new UserListAdapter(this);
        rv_main.setLayoutManager(new LinearLayoutManager(this));
        rv_main.setAdapter(userListAdapter);
        rv_main.setHasFixedSize(true);

        clear = findViewById(R.id.clear);
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchfield.getText().clear();
//                rv_main.setVisibility(View.GONE);
            }
        });

        searchfield.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                String strUsername = searchfield.getText().toString();
                if (strUsername.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please type a userneme", Toast.LENGTH_SHORT).show();
                } else {
                    if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                        loadingMessage.show();
                        searchViewModel.searchUsername(strUsername);
                        InputMethodManager inputMethodManager = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                        inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
                        clear.setVisibility(View.VISIBLE);
                        return true;
                    }
                }
                return false;
            }
        });

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

}