package com.example.sco.imuvo.HelperClasses;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.sco.imuvo.Model.Lection;

import java.util.ArrayList;
import java.util.List;

import static com.example.sco.imuvo.HelperClasses.GeneralDatabaseHelper.DB_NAME;
import static com.example.sco.imuvo.HelperClasses.GeneralDatabaseHelper.DB_VERSION;

/**
 * Created by sco on 05.12.2016.
 */

public class LectionDatabaseHelper extends SQLiteOpenHelper {
    private static final String[] LECTION_COLUMNS = {"_id", "number", "language"};
    private static final String LECTION_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS lection_imuvo " +
            "(_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, number INTEGER, language TEXT)";
    private static final String LECTION_DROP_TABLE = "DROP TABLE IF EXISTS lection_imuvo";

    private String DB_PATH = null;
    private static LectionDatabaseHelper instance;

    private LectionDatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        DB_PATH = "/data/data/" + context.getPackageName() + "/" + "databases/";
    }

    public static LectionDatabaseHelper getInstance(Context context) {
        if (instance == null) {
            instance = new LectionDatabaseHelper(context);
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(LECTION_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(LECTION_DROP_TABLE);
        onCreate(db);
    }

    public Lection get(long id) {
        Lection Lection = null;
        Cursor cursor = GeneralDatabaseHelper.getSQLDatabase().query("Lection_imuvo", LECTION_COLUMNS, "_id = ?",
                new String[]{String.valueOf(id)}, null, null, null);
        if (cursor.moveToFirst()) {
            Lection = new Lection(cursor.getInt(0), cursor.getInt(1), cursor.getString(2));
        }
        cursor.close();
        return Lection;
    }

    public Lection get(Integer number) {
        Lection Lection = null;
        Cursor cursor = GeneralDatabaseHelper.getSQLDatabase().query("Lection_imuvo", LECTION_COLUMNS, "number = ?",
                new String[]{number.toString()}, null, null, null);
        if (cursor.moveToFirst()) {
            Lection = new Lection(cursor.getInt(0), cursor.getInt(1), cursor.getString(2));
        }
        cursor.close();
        return Lection;
    }

    public Cursor getAll() {
        return GeneralDatabaseHelper.getSQLDatabase().query("Lection_imuvo", LECTION_COLUMNS, null, null, null, null, "number");
    }

    public long insert(Lection Lection) {
        GeneralDatabaseHelper.getSQLDatabase().execSQL(LECTION_CREATE_TABLE);
        ContentValues values = new ContentValues();
        values.put("number", Lection.getNumber());
        values.put("language", Lection.getLanguage());
        long id = GeneralDatabaseHelper.getSQLDatabase().insert("lection_imuvo", null, values);
        return id;
    }

    public int update(Lection Lection) {
        ContentValues values = new ContentValues();
        values.put("number", Lection.getNumber());
        values.put("language", Lection.getLanguage());
        int rows = GeneralDatabaseHelper.getSQLDatabase().update("lection_imuvo", values, "_id = ?", new String[]{String.valueOf(Lection.getSqlID())});
        return rows;
    }

    public int delete(long id) {
        return GeneralDatabaseHelper.getSQLDatabase().delete("Lection", "_id = ?", new String[]{String.valueOf(id)});
    }

    public void Create() {
        onCreate(GeneralDatabaseHelper.getSQLDatabase());
    }

    public List<String> getAllLabels(){
        List<String> labels = new ArrayList<String>();
        String selectQuery = "SELECT  * FROM lection_imuvo";

        Cursor cursor = GeneralDatabaseHelper.getSQLDatabase().rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                labels.add("Lektion " + Integer.toString(cursor.getInt(1)));
            } while (cursor.moveToNext());
        }
        cursor.close();

        return labels;
    }

}
