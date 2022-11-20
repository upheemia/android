package com.example.secondlab;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Set;

public class ListActivity extends Activity {
    SharedPreferences sharedPref;
    SharedPreferences.Editor editor;

    int clickCounter = 0;
    String addEng = "Add";
    String addRus = "Добавить";
    String removeEng = "Remove";
    String removeRus = "Удалить";
    boolean isRus;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        sharedPref = this.getSharedPreferences("SharedPref", MODE_PRIVATE);
        editor = sharedPref.edit();
        isRus = sharedPref.getBoolean("isRus", true);

        ArrayList<String> myStringArray = new ArrayList<String>();
        ArrayAdapter<String> TextAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, myStringArray);
        ListView textList = findViewById(R.id.textList);
        textList.setAdapter(TextAdapter);

        Button buttonLang = findViewById(R.id.buttonLang);
        Button buttonAdd = findViewById(R.id.buttonAdd);
        Button buttonRemove = findViewById(R.id.buttonRemove);
        TextView usernameTitle = findViewById(R.id.username);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            usernameTitle.setText(extras.getString("username"));
        }

        buttonAdd.setText(isRus ? addRus : addEng);
        buttonRemove.setText(isRus ? removeRus : removeEng);

        buttonLang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isRus) {
                    editor.putBoolean("isRus", false);
                    isRus = false;
                    buttonAdd.setText(addEng);
                    buttonRemove.setText(removeEng);
                } else {
                    editor.putBoolean("isRus", true);
                    isRus = true;
                    buttonAdd.setText(addRus);
                    buttonRemove.setText(removeRus);
                }
                editor.apply();
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

    }
}