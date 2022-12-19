package com.example.secondlab;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class AuthActivity extends Activity {
    SharedPreferences sharedPref;
    SharedPreferences.Editor editor;

    String loginRus = "Логин";
    String loginEng = "Login";
    String passwordRus = "Пароль";
    String passwordEng = "Password";
    String authEng = "Login";
    String authRus = "Войти";
    String wrongPassRus = "Неверный пароль";
    String wrongPassEng = "Wrong password";
    boolean isRus;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        sharedPref =  this.getSharedPreferences("SharedPref", MODE_PRIVATE); //получаем данные
        editor = sharedPref.edit();
        isRus = sharedPref.getBoolean("isRus", true); //узнаем язык

        EditText username = (EditText)findViewById(R.id.editText1);
        EditText password = (EditText)findViewById(R.id.editText2);

        Button buttonLogin = findViewById(R.id.buttonLogin);

        username.setHint(isRus ? loginRus : loginEng);
        password.setHint(isRus ? passwordRus : passwordEng);
        buttonLogin.setText(isRus ? authRus : authEng);

        String previousUsername = sharedPref.getString("username", null); //узнаем юзернейм
        if (previousUsername != null) {
            username.setText(previousUsername);
        }

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
            errorText.setText(isRus ? wrongPassRus : wrongPassEng);
        } else {
            errorText.setText("");
        }
    }

    public void login (String username) {
        editor.putString("username", username);
       // editor.apply(); //сохранение изменений
        Intent intent = new Intent(this, ListActivity.class);
        intent.putExtra("username", username);
        startActivity(intent);
    }
}
