package com.example.sco.imuvo.HelperClasses;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.sco.imuvo.Model.User;

public class UserDatabaseHelper extends SQLiteOpenHelper {
    public static final String TABLE_NAME = "user";
    public static final String[] COLUMNS = {"_id", "username", "password"};
    public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME +
            "(" +
            COLUMNS[0] + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
            COLUMNS[1] + " TEXT, " +
            COLUMNS[2] + " TEXT" +
            ")";
    public static final String DROP_TABLE = "DROP TABLE IF EXISTS user_imuvo";

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
        db.execSQL(DROP_TABLE);
        onCreate(db);
    }

    public User get(long id) {
        User user = null;
        Cursor cursor = GeneralDatabaseHelper.getSQLDatabase().query("user_imuvo", COLUMNS, "_id = ?",
                new String[] { String.valueOf(id) }, null, null, null);
        if (cursor.moveToFirst()) {
            user = new User(cursor.getInt(0), cursor.getString(1), cursor.getString(2));
        }
        cursor.close();
        return user;
    }

    public static User get(String name) {
        User user = null;
        Cursor cursor = GeneralDatabaseHelper.getSQLDatabase().query("user_imuvo", COLUMNS, "username = ?",
                new String[] { name }, null, null, null);
        if (cursor.moveToFirst()) {
            user = new User(cursor.getInt(0), cursor.getString(1), cursor.getString(2));
        }
        cursor.close();
        return user;
    }

    public static Cursor getAll() {
        return GeneralDatabaseHelper.getSQLDatabase().query("user_imuvo", COLUMNS, null, null, null, null, "username");
    }

    public static long insert(User user) {
        GeneralDatabaseHelper.getSQLDatabase().execSQL(CREATE_TABLE);
        ContentValues values = new ContentValues();
        values.put("username", user.getUserName());
        values.put("password", user.getPassword());
        long id = GeneralDatabaseHelper.getSQLDatabase().insert("user_imuvo", null, values);
        return id;
    }

    public int update(User user) {
        ContentValues values = new ContentValues();
        values.put("username", user.getUserName());
        values.put("password", user.getPassword());
        int rows = GeneralDatabaseHelper.getSQLDatabase().update("user_imuvo", values, "_id = ?", new String[] { String.valueOf(user.getId()) });
        return rows;
    }

    public int delete(long id) {
        return GeneralDatabaseHelper.getSQLDatabase().delete("user", "_id = ?", new String[]{String.valueOf(id)});
    }

    public void Create() {
        onCreate(GeneralDatabaseHelper.getSQLDatabase());
    }
}
