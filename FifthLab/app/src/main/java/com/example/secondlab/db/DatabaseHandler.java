package com.example.secondlab.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Users.db";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_USERS_TABLE = "CREATE TABLE " + DBContract.UserEntry.TABLE_NAME + "("
                + DBContract.UserEntry.COLUMN_NAME_KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + DBContract.UserEntry.COLUMN_NAME_LOGIN + " TEXT,"
                + DBContract.UserEntry.COLUMN_NAME_PASS + " TEXT" + ")";

        sqLiteDatabase.execSQL(CREATE_USERS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DBContract.UserEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public String addUser(User user) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        String message = "Login available";
        List<User> userList;
        userList = getAllUsers();

        if(!userList.isEmpty()){
            for (User userGetDB : userList){
                String log = userGetDB.getLogin();

                Log.i("Loading...", log + " " + user.getLogin());
                if(log.equals(user.getLogin())){
                    message = "Login unavailable";
                    break;
                }
                else{
                    values.put(DBContract.UserEntry.COLUMN_NAME_LOGIN, user.getLogin());
                    values.put(DBContract.UserEntry.COLUMN_NAME_PASS, user.getPass());

                    sqLiteDatabase.insert(DBContract.UserEntry.TABLE_NAME, null, values);
                }
            }
        }
        else{
            values.put(DBContract.UserEntry.COLUMN_NAME_LOGIN, user.getLogin());
            values.put(DBContract.UserEntry.COLUMN_NAME_PASS, user.getPass());

            sqLiteDatabase.insert(DBContract.UserEntry.TABLE_NAME, null, values);
        }


        sqLiteDatabase.close();
        return message;
    }

    public List<User> getAllUsers() {
        List<User> usersList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + DBContract.UserEntry.TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                User user = new User();
                user.setID(Integer.parseInt(cursor.getString(0)));
                user.setLogin(cursor.getString(1));
                user.setPass(cursor.getString(2));
                usersList.add(user);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return usersList;
    }

    public User getUser(String login){

        String selectQuery = "SELECT * FROM " + DBContract.UserEntry.TABLE_NAME + " WHERE " + DBContract.UserEntry.COLUMN_NAME_LOGIN + " =?";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{login});

        if(cursor.getCount() != 0){
            if (cursor.moveToFirst()) {
                do {
                    User user = new User();
                    user.setLogin(cursor.getString(1));
                    user.setPass(cursor.getString(2));
                    return user;
                } while (cursor.moveToNext());
            }
        }
        cursor.close();
        return null;
    }

    public void updatePassword(String login, String newPass) {

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(DBContract.UserEntry.COLUMN_NAME_PASS, newPass);

        sqLiteDatabase.update(DBContract.UserEntry.TABLE_NAME, values, DBContract.UserEntry.COLUMN_NAME_LOGIN + "=?", new String[]{login});
        sqLiteDatabase.close();
    }
    public void deleteUser(String login){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        sqLiteDatabase.delete(DBContract.UserEntry.TABLE_NAME, DBContract.UserEntry.COLUMN_NAME_LOGIN + "=?", new String[]{login});
        sqLiteDatabase.close();

    }
}
