package com.example.secondlab.db;

import android.provider.BaseColumns;

public final class DBContract {

    private DBContract() {}

    public static class UserEntry implements BaseColumns {
        public static final String TABLE_NAME = "users";
        public static final String COLUMN_NAME_KEY_ID = "_id";
        public static final String COLUMN_NAME_LOGIN = "username";
        public static final String COLUMN_NAME_PASS = "password";
    }
}