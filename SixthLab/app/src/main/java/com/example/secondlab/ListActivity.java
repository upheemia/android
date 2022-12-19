package com.example.secondlab;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class ListActivity extends Activity {
    int clickCounter = 0;
    String addEng = "Add";
    String addRus = "Добавить";
    String removeEng = "Remove";
    String removeRus = "Удалить";
    String settingsEng = "Settings";
    String settingsRus = "Настройки";
    boolean isRus = true;

    String username, password;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        ArrayList<String> myStringArray = new ArrayList<String>();
        ArrayAdapter<String> TextAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, myStringArray);
        ListView textList = findViewById(R.id.textList);
        textList.setAdapter(TextAdapter);

        Button buttonLang = findViewById(R.id.buttonLang);
        Button buttonAdd = findViewById(R.id.buttonAdd);
        Button buttonRemove = findViewById(R.id.buttonRemove);
        Button buttonSettings = findViewById(R.id.buttonSettings);
        TextView usernameTitle = findViewById(R.id.username);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            username = extras.getString("username");
            password = extras.getString("password");
            usernameTitle.setText(username);
        }

        buttonAdd.setText(addRus);
        buttonRemove.setText(removeRus);
        buttonSettings.setText(settingsRus);

        buttonLang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isRus) {
                    buttonAdd.setText(addEng);
                    buttonRemove.setText(removeEng);
                    buttonSettings.setText(settingsEng);
                    isRus = false;
                } else {
                    buttonAdd.setText(addRus);
                    buttonRemove.setText(removeRus);
                    buttonSettings.setText(settingsRus);
                    isRus = true;
                }
            }
        });
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myStringArray.add("Clicked : "+clickCounter++);
                TextAdapter.notifyDataSetChanged();
            }
        });
        buttonRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int lastItemPosition = myStringArray.size() - 1;
                if (lastItemPosition >= 0) {
                    myStringArray.remove(myStringArray.size() - 1);
                    TextAdapter.notifyDataSetChanged();
                }
            }
        });
        buttonSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToSettings();
            }
        });
    }

    public void goToSettings () {
        Intent intent = new Intent(this, SettingsActivity.class);
        intent.putExtra("username", username);
        intent.putExtra("password", password);
        startActivity(intent);
    }
}
