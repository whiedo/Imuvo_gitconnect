package com.example.sco.imuvo.HelperClasses;

import android.content.Context;

import com.example.sco.imuvo.DatabaseHelper.GeneralDatabaseHelper;

public class InitData {

    public static void initSQLData(Context context) {
        GeneralDatabaseHelper generalDatabaseHelper = GeneralDatabaseHelper.getInstance(context);
        generalDatabaseHelper.Create();
    }
}
