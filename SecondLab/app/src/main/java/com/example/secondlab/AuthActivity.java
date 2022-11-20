package com.example.secondlab;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class AuthActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        EditText username = (EditText)findViewById(R.id.editText1);
        EditText password = (EditText)findViewById(R.id.editText2);

        Button buttonLogin = findViewById(R.id.buttonLogin);
        TextView errorText = findViewById(R.id.errorText);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (password.getText().toString().equals("admin")){
                    login(username.getText().toString());
                } else {
                    handleError(false);
                }
            }
        });
        username.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                handleError(true);
            }
        });
    }
    public void handleError (boolean shouldClear) {
        TextView errorText = findViewById(R.id.errorText);
        if (!shouldClear) {
            errorText.setText("Wrong password");
        } else {
            errorText.setText("");
        }
    }

    public void login (String username) {
        Intent intent = new Intent(this, ListActivity.class);
        intent.putExtra("username", username);
        startActivity(intent);
    }
}