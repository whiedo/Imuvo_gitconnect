package com.example.sco.imuvo.DatabaseHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.sco.imuvo.Model.User;

public class UserDatabaseHelper extends SQLiteOpenHelper {
    public static final String TABLE_NAME = "user";

    public static final String[] COLUMNS = {"_id", "username", "password"};
    public static final int ID_COLUMN_INDEX = 0;
    public static final int USERNAME_COLUMN_INDEX = 1;
    public static final int PASSWORD_COLUMN_INDEX = 2;

    public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME +
            "(" +
            COLUMNS[0] + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
            COLUMNS[1] + " TEXT, " +
            COLUMNS[2] + " TEXT" +
            ")";
    public static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

    private UserDatabaseHelper(Context context) {
        super(context, GeneralDatabaseHelper.DB_NAME, null, GeneralDatabaseHelper.DB_VERSION);
        String DB_PATH = "/data/data/" + context.getPackageName() + "/" + "databases/";
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public User get(long id) {
        User user = null;
        Cursor cursor = GeneralDatabaseHelper.getSQLDatabase().query(TABLE_NAME, COLUMNS, COLUMNS[ID_COLUMN_INDEX] + "=?",
                new String[] { String.valueOf(id) }, null, null, null);

        if (cursor.moveToFirst()) {
            user = new User(cursor.getInt(0), cursor.getString(1), cursor.getString(2));
        }

        cursor.close();
        return user;
    }

    public static User get(String name) {
        User user = null;
        Cursor cursor = GeneralDatabaseHelper.getSQLDatabase().query(TABLE_NAME, COLUMNS, COLUMNS[USERNAME_COLUMN_INDEX] + "=?",
                new String[] { name }, null, null, null);

        if (cursor.moveToFirst()) {
            user = new User(cursor.getInt(0), cursor.getString(1), cursor.getString(2));
        }

        cursor.close();
        return user;
    }

    public static Cursor getAll() {
        return GeneralDatabaseHelper.getSQLDatabase().query(TABLE_NAME, COLUMNS, null, null, null, null, COLUMNS[USERNAME_COLUMN_INDEX]);
    }

    public static long insert(User user) {
        GeneralDatabaseHelper.getSQLDatabase().execSQL(CREATE_TABLE);
        ContentValues values = new ContentValues();

        values.put(COLUMNS[USERNAME_COLUMN_INDEX], user.getUserName());
        values.put(COLUMNS[PASSWORD_COLUMN_INDEX], user.getPassword());

        long id = GeneralDatabaseHelper.getSQLDatabase().insert(TABLE_NAME, null, values);
        return id;
    }

    public int update(User user) {
        ContentValues values = new ContentValues();

        values.put(COLUMNS[USERNAME_COLUMN_INDEX], user.getUserName());
        values.put(COLUMNS[PASSWORD_COLUMN_INDEX], user.getPassword());

        int rows = GeneralDatabaseHelper.getSQLDatabase().update(TABLE_NAME, values, COLUMNS[ID_COLUMN_INDEX] + "=?",
                new String[] { String.valueOf(user.getId()) });
        return rows;
    }

    public int delete(long id) {
        return GeneralDatabaseHelper.getSQLDatabase().delete(TABLE_NAME, COLUMNS[ID_COLUMN_INDEX] + "=?",
                new String[]{String.valueOf(id)});
    }

    public void Create() {
        onCreate(GeneralDatabaseHelper.getSQLDatabase());
    }
}
