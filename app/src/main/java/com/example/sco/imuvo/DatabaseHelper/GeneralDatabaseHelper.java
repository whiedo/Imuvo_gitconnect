package com.example.sco.imuvo.DatabaseHelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;

public class GeneralDatabaseHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "vocabulary_trainer";
    public static final int DB_VERSION = 1;

    private String DB_PATH = null;
    private static GeneralDatabaseHelper instance;
    private static SQLiteDatabase db;

    private GeneralDatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        DB_PATH = Environment.getExternalStorageDirectory() + context.getPackageName() + "/" + "databases/";
    }

    public static GeneralDatabaseHelper getInstance(Context context) {
        if (instance == null) {
            instance = new GeneralDatabaseHelper(context);
        }

        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(UserDatabaseHelper.CREATE_TABLE);
        db.execSQL(VocabDatabaseHelper.CREATE_TABLE);
        db.execSQL(LectionDatabaseHelper.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        onCreate(db);
    }

    private void open() {
        if (db == null)
            db = getWritableDatabase();
    }
    public void close() {
        if (db != null) {
            db.close();
            db = null;
        }
    }

    public void Create() {
        open();
        onCreate(db);
    }

    public boolean checkDatabase() {

        SQLiteDatabase checkDB = null;
        try {
            String myPath = DB_PATH + DB_NAME;
            checkDB = SQLiteDatabase.openDatabase(myPath, null,
                    SQLiteDatabase.OPEN_READONLY);
        } catch (SQLiteException e) {
            //database does't exist yet.
        }

        if (checkDB != null) {
            checkDB.close();
        }

        return checkDB != null ? true : false;
    }

    public void dropDatabase(Context context) {
        context.deleteDatabase(DB_NAME);
    }

    public static SQLiteDatabase getSQLDatabase() {
        return db;
    }
}
