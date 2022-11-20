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
                String messageAdd = db.addUser(new User(username.getText().toString(), password.getText().toString()));
                if(messageAdd.equals("Login available")){
                    handleError(String.format("User %s is registered", username.getText().toString()));
                }
                else{
                    handleError(messageAdd);
                }
            }
        });
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (username.getText().toString().length() == 0 || password.getText().toString().length() == 0){
                    handleError("Fill all fields");
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
        errorText.setText(message);
    }

    public void signInApp () {
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
                Intent intent = new Intent(this, ListActivity.class);
                intent.putExtra("username", cmpData[0]);
                intent.putExtra("password", cmpData[1]);
                username.setText("");
                password.setText("");
                startActivity(intent);
            }
            Log.e("cmp", cmpData[0] +" "+ cmpData[1]);
        }
        else{
            handleError("User not found");
        }
    }
}
