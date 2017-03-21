package com.example.sco.imuvo.HelperClasses;

import android.content.Context;

import com.example.sco.imuvo.DatabaseHelper.GeneralDatabaseHelper;
import com.example.sco.imuvo.DatabaseHelper.LectionDatabaseHelper;
import com.example.sco.imuvo.DatabaseHelper.UserDatabaseHelper;
import com.example.sco.imuvo.DatabaseHelper.VocabDatabaseHelper;
import com.example.sco.imuvo.Model.Lection;
import com.example.sco.imuvo.Model.User;
import com.example.sco.imuvo.Model.Vocab;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Simon Cox on 17.12.2016.
 */

public class InitData {

    public static void initSQLData(Context context) {
        GeneralDatabaseHelper generalDatabaseHelper = GeneralDatabaseHelper.getInstance(context);
        generalDatabaseHelper.Create();
    }
}
