package com.example.sco.imuvo.DatabaseHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;

import com.example.sco.imuvo.Model.Lection;

import java.util.ArrayList;
import java.util.List;

public class LectionDatabaseHelper extends SQLiteOpenHelper {
    public static final String TABLE_NAME = "lection";

    public static final String[] COLUMNS = {"_id", "number", "language"};
    public static final int ID_COLUMN_INDEX = 0;
    public static final int NUMBER_COLUMN_INDEX = 1;
    public static final int LANGUAGE_COLUMN_INDEX = 2;

    public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " " +
            "(" +
            COLUMNS[0] + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
            COLUMNS[1] + " INTEGER, " +
            COLUMNS[2] + " TEXT" +
            ")";
    public static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
    public static final String SELECT_ALL = "SELECT  * FROM ";

    private LectionDatabaseHelper(Context context) {
        super(context, GeneralDatabaseHelper.DB_NAME, null, GeneralDatabaseHelper.DB_VERSION);
        String DB_PATH = Environment.getExternalStorageDirectory() + context.getPackageName() + "/" + "databases/";
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public static Lection get(long id) {
        Lection Lection = null;
        Cursor cursor = GeneralDatabaseHelper.getSQLDatabase().query(TABLE_NAME, COLUMNS, COLUMNS[ID_COLUMN_INDEX] + " = ?",
                new String[]{String.valueOf(id)}, null, null, null);

        if (cursor.moveToFirst()) {
            Lection = new Lection(cursor.getInt(0), cursor.getInt(1), cursor.getString(2));
        }

        cursor.close();
        return Lection;
    }

    public Lection get(Integer number) {
        Lection Lection = null;
        Cursor cursor = GeneralDatabaseHelper.getSQLDatabase().query(TABLE_NAME, COLUMNS, COLUMNS[NUMBER_COLUMN_INDEX] + "=?",
                new String[]{number.toString()}, null, null, null);

        if (cursor.moveToFirst()) {
            Lection = new Lection(cursor.getInt(0), cursor.getInt(1), cursor.getString(2));
        }

        cursor.close();
        return Lection;
    }

    public static Cursor getAll() {
        return GeneralDatabaseHelper.getSQLDatabase().query(TABLE_NAME, COLUMNS, null, null, null, null, COLUMNS[NUMBER_COLUMN_INDEX]);
    }

    public static long insert(Lection Lection) {
        GeneralDatabaseHelper.getSQLDatabase().execSQL(CREATE_TABLE);
        ContentValues values = new ContentValues();

        values.put(COLUMNS[NUMBER_COLUMN_INDEX], Lection.getNumber());
        values.put(COLUMNS[LANGUAGE_COLUMN_INDEX], Lection.getLanguage());

        return GeneralDatabaseHelper.getSQLDatabase().insert(TABLE_NAME, null, values);
    }

    public int update(Lection Lection) {
        ContentValues values = new ContentValues();

        values.put(COLUMNS[NUMBER_COLUMN_INDEX], Lection.getNumber());
        values.put(COLUMNS[LANGUAGE_COLUMN_INDEX], Lection.getLanguage());

        return GeneralDatabaseHelper.getSQLDatabase().update(TABLE_NAME, values, COLUMNS[ID_COLUMN_INDEX] + "=?",
                new String[]{String.valueOf(Lection.getSqlID())});
    }

    public int delete(long id) {
        return GeneralDatabaseHelper.getSQLDatabase().delete(
                TABLE_NAME, COLUMNS[ID_COLUMN_INDEX] + "=?", new String[]{ String.valueOf(id) });
    }

    public static List<String> getAllLabels(){
        List<String> labels = new ArrayList<String>();
        String selectQuery = SELECT_ALL + TABLE_NAME;

        Cursor cursor = GeneralDatabaseHelper.getSQLDatabase().rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                labels.add(Integer.toString(cursor.getInt(1)));
            } while (cursor.moveToNext());
        }
        cursor.close();

        return labels;
    }

    public static boolean lectionExists() {
        String selectQuery = SELECT_ALL + TABLE_NAME;

        Cursor cursor = GeneralDatabaseHelper.getSQLDatabase().rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            cursor.close();
            return true;
        } else {
            cursor.close();
            return false;
        }
    }
}
