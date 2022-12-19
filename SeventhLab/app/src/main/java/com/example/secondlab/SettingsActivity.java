package com.example.secondlab;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.secondlab.db.DatabaseHandler;
import com.example.secondlab.db.User;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener{

    Button settingEditBut, settingDelBut;
    EditText oldPass, newPass;
    //DatabaseHandler db;
    String username, password;

    final Looper looper = Looper.getMainLooper();
    final Handler handler = new Handler(looper) {
        @Override
        public void handleMessage(Message msg) {
            if (msg.sendingUid == 3) {

                String mess = (String) msg.obj;
                handleError(mess);
            }
            if(msg.sendingUid == 4){
                String mess = (String) msg.obj;
                handleError(mess);
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        //db = new DatabaseHandler(this);

        oldPass = findViewById(R.id.oldPass);
        newPass = findViewById(R.id.newPass);
        settingDelBut = findViewById(R.id.settingDelBut);
        settingEditBut = findViewById(R.id.settingEditBut);

        settingEditBut.setOnClickListener(this);
        settingDelBut.setOnClickListener(this);

        Bundle extras = getIntent().getExtras();
        String usernameDB = extras.get("username").toString();
        String passDB = extras.get("password").toString();

        username = usernameDB;
        password = passDB;
    }

    @Override
    public void onClick (View view) {
        switch (view.getId()){
            case R.id.settingEditBut:
                updatePass();
                break;
            case R.id.settingDelBut:
                deleteUserPref();
                break;
            default:
                break;
        }
    }

    public void updatePass () {
        String passwordNew = newPass.getText().toString();

        if(password.equals(oldPass.getText().toString())) {
            new HandlerThr(handler, SettingsActivity.this).updPass(username, passwordNew);

            handleError("Password successfully changed");
            Intent intent = new Intent(SettingsActivity.this, AuthActivity.class);
            startActivity(intent);
        } else {
            handleError("Wrong current password");
        }
    }
    public void deleteUserPref () {
        new HandlerThr(handler, SettingsActivity.this).delUser(username);

        Intent intent = new Intent(SettingsActivity.this, AuthActivity.class);
        startActivity(intent);
    }
    public void handleError (String message) {
        TextView errorText = findViewById(R.id.errorText);
        errorText.setText(message);
    }

}