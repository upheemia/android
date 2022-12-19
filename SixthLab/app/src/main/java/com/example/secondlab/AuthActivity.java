package com.example.secondlab;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.example.secondlab.db.User;
import androidx.annotation.Nullable;
import com.example.secondlab.db.DatabaseHandler;

public class AuthActivity extends Activity {

    DatabaseHandler db;
    EditText username, password;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        db = new DatabaseHandler(this);

        username = (EditText)findViewById(R.id.editText1);
        password = (EditText)findViewById(R.id.editText2);

        Button buttonLogin = findViewById(R.id.buttonLogin);
        Button buttonSignUp = findViewById(R.id.buttonSingUp);

        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Log.i("ThreadStart", Thread.currentThread().getName());
                        String messageAdd = db.addUser(new User(username.getText().toString(), password.getText().toString())); //добавление нового юзера
                        if(messageAdd.equals("Login available")){ //еще не зареган
                            handleError(String.format("User %s is registered", username.getText().toString()));
                        }
                        else{
                            handleError(messageAdd); //уже занят
                        }
                    }
                }).start();
            }
        });
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (username.getText().toString().length() == 0 || password.getText().toString().length() == 0){
                    handleError("Fill all fields"); //заполните
                } else {
                    handleError("");
                    signInApp();
                }
            }
        });
        username.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                handleError("");
            }
        });
    }

    public void handleError (String message) {
        TextView errorText = findViewById(R.id.errorText);
        errorText.post(new Runnable() {
            @Override
            public void run() {
                errorText.setText(message);
            }
        });
    }

    public void signInApp () {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.i("ThreadStart", Thread.currentThread().getName());
                int condition = 0;
                String thisLoginUser = username.getText().toString();
                String passCmp= password.getText().toString();

                String dataUser = db.getUser(thisLoginUser);

                if(!dataUser.equals("null")){
                    String[] cmpData = dataUser.split(" ");

                    if(cmpData[0].equals(thisLoginUser)){
                        Log.e("cmplog", cmpData[0]);
                        condition += 1;
                    }else{
                        handleError("Wrong username");
                        condition = 0;
                    }

                    if (cmpData[1].equals(passCmp)){
                        Log.e("cmppass", cmpData[1]);
                        condition += 1;
                    }else{
                        handleError("Wrong password");
                        condition = 0;
                    }

                    if(condition == 2){
                        handleError("");
                        Intent intent = new Intent(AuthActivity.this, ListActivity.class);
                        intent.putExtra("username", cmpData[0]);
                        intent.putExtra("password", cmpData[1]);
                        username.post(new Runnable() {
                            @Override
                            public void run() {
                                username.setText("");
                            }
                        });
                        password.post(new Runnable() {
                            @Override
                            public void run() {
                                password.setText("");
                            }
                        });
                        startActivity(intent);
                    }
                }
                else{
                    handleError("User not found");
                }
            }
        }).start();

    }
}
