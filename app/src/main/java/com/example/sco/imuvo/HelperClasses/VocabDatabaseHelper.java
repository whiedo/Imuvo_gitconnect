package com.example.sco.imuvo.HelperClasses;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.sco.imuvo.Model.Vocab;

import java.util.ArrayList;
import java.util.List;

import static com.example.sco.imuvo.HelperClasses.GeneralDatabaseHelper.DB_NAME;
import static com.example.sco.imuvo.HelperClasses.GeneralDatabaseHelper.DB_VERSION;

/**
 * Created by sco on 05.12.2016.
 */

public class VocabDatabaseHelper extends SQLiteOpenHelper{
    private static final String TABLE_NAME = "vocabs_imuvo";
    private static final String[] USER_COLUMNS = { "_id", "german", "translation", "lection", "speech", "picture" };
    private static final String USER_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS vocabs_imuvo "
            + "(_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, german TEXT, translation TEXT, lection INTEGER, speech BLOB, picture BLOB, FOREIGN KEY(lection) REFERENCES lection_imuvo(_id))";
    private static final String USER_DROP_Table = "DROP TABLE IF EXISTS vocabs_imuvo";

    private String DB_PATH = null;
    private static VocabDatabaseHelper instance;

    private VocabDatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        DB_PATH = "/data/data/" + context.getPackageName() + "/" + "databases/";
    }

    public static VocabDatabaseHelper getInstance(Context context) {
        if (instance == null) {
            instance = new VocabDatabaseHelper(context);
        }

        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
            db.execSQL(USER_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(USER_DROP_Table);
        onCreate(db);
    }

    public Vocab get(long id) {
        Vocab vocab = null;
        Cursor cursor = GeneralDatabaseHelper.getSQLDatabase().query(TABLE_NAME, USER_COLUMNS, "_id = ?",
                new String[] { String.valueOf(id) }, null, null, null);

        if (cursor.moveToFirst()) {
            vocab = new Vocab(cursor.getLong(0), cursor.getString(1), cursor.getString(2), cursor.getInt(3),cursor.getBlob(4),cursor.getBlob(5));
        }

        cursor.close();
        return vocab;
    }

    public ArrayList<Vocab> getFromLection(Integer lectionNo) {
        ArrayList<Vocab> vocabs = new ArrayList<Vocab>();
        Vocab vocab = null;
        Cursor cursor = GeneralDatabaseHelper.getSQLDatabase().query(TABLE_NAME, USER_COLUMNS, "lection=?",
                new String[] { Integer.toString(lectionNo)}, null, null, null);


        if (cursor.moveToFirst()) {
            do {
                byte[] test = cursor.getBlob(5);
                vocab = new Vocab(cursor.getLong(0), cursor.getString(1), cursor.getString(2), cursor.getInt(3),cursor.getBlob(4),test);
                vocabs.add(vocab);
            } while (cursor.moveToNext());

        }
        cursor.close();
        return vocabs;
    }

    public Cursor getAll() {
        Cursor cursor = GeneralDatabaseHelper.getSQLDatabase().query(TABLE_NAME, USER_COLUMNS, null,
               null, null, null, null);
        return cursor;
    }

    public Cursor getAll(int lectionNo) {
        Cursor cursor = GeneralDatabaseHelper.getSQLDatabase().query(TABLE_NAME, USER_COLUMNS, "lection=?",
                new String[] { Integer.toString(lectionNo)}, null, null, null);
        return cursor;
    }

    public long insert(Vocab vocab) {
        GeneralDatabaseHelper.getSQLDatabase().execSQL(USER_CREATE_TABLE);
        ContentValues values = new ContentValues();
        values.put("german",vocab.getGerman());
        values.put("translation",vocab.getForeign());
        values.put("speech",vocab.getSpeech());
        values.put("lection",vocab.getLection());
        values.put("picture",vocab.getPicture());
        long id = GeneralDatabaseHelper.getSQLDatabase().insert(TABLE_NAME, null, values);

        return id;
    }

    public int update(Vocab vocab) {
        ContentValues values = new ContentValues();
        values.put("german",vocab.getGerman());
        values.put("translation",vocab.getForeign());
        values.put("speech",vocab.getSpeech());
        values.put("lection",vocab.getLection());
        values.put("picture",vocab.getPicture());
        int rows = GeneralDatabaseHelper.getSQLDatabase().update(TABLE_NAME, values, "_id = ?", new String[] { String.valueOf(vocab.getSqlID()) });
        return rows;
    }

    public int delete(long id) {
        return GeneralDatabaseHelper.getSQLDatabase().delete(TABLE_NAME, "_id = ?", new String[]{String.valueOf(id)});
    }

    public void Create() {
        onCreate(GeneralDatabaseHelper.getSQLDatabase());
    }

    public ArrayList<Vocab> getFromMultipleLection(List<Integer> indices) {
        ArrayList<Vocab> vocabs = new ArrayList<Vocab>();
        Vocab vocab = null;
        String selectionStatement = "lection in (";
        String[] args = new String[indices.size()];
        Integer counter = 0;

        for (Integer i:
             indices) {

            args[counter] = String.valueOf(i + 1);
            counter++;
        }

        for (String s:
             args) {
            if (s != null){
                selectionStatement += s +",";
            }


        }

        selectionStatement = selectionStatement.substring(0,selectionStatement.length()-1);
        selectionStatement += ")";
        Cursor cursor = GeneralDatabaseHelper.getSQLDatabase().query(TABLE_NAME, USER_COLUMNS, selectionStatement,
                null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                vocab = new Vocab(cursor.getLong(0), cursor.getString(1), cursor.getString(2), cursor.getInt(3),cursor.getBlob(4),cursor.getBlob(5));
                vocabs.add(vocab);
            } while (cursor.moveToNext());

        }

        cursor.close();
        return vocabs;
    }
}
