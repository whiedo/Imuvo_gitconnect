package com.example.sco.imuvo.DatabaseHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.sco.imuvo.Model.Vocab;

import java.util.ArrayList;
import java.util.List;

public class VocabDatabaseHelper extends SQLiteOpenHelper {
    public static final String TABLE_NAME = "vocabulary";

    public static final String[] COLUMNS = {"_id", "baseLanguage", "translation", "speech", "lection", "picture"};
    public static final int ID_COLUMN_INDEX = 0;
    public static final int BASELANGUAGE_COLUMN_INDEX = 1;
    public static final int TRANSLATION_COLUMN_INDEX = 2;
    public static final int SPEECH_COLUMN_INDEX = 3;
    public static final int LECTION_COLUMN_INDEX = 4;
    public static final int PICTURE_COLUMN_INDEX = 5;

    public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME +
            "(" +
            COLUMNS[0] + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
            COLUMNS[1] + " TEXT, " +
            COLUMNS[2] + " TEXT, " +
            COLUMNS[3] + " BLOB, " +
            COLUMNS[4] + " INTEGER, " +
            COLUMNS[5] + " BLOB" +
            ")";
    public static final String DROP_TABLE = "DROP TABLE IF EXISTS "+ TABLE_NAME;

    private VocabDatabaseHelper(Context context) {
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

    public Vocab get(long id) {
        Vocab vocab = null;
        Cursor cursor = GeneralDatabaseHelper.getSQLDatabase().query(TABLE_NAME, COLUMNS, COLUMNS[ID_COLUMN_INDEX] + "=?",
                new String[] { String.valueOf(id) }, null, null, null);

        if (cursor.moveToFirst()) {
            vocab = new Vocab(cursor.getLong(0), cursor.getString(1), cursor.getString(2), cursor.getBlob(3),
                    cursor.getInt(4),cursor.getBlob(5));
        }

        cursor.close();
        return vocab;
    }

    public static ArrayList<Vocab> getFromLection(Integer lectionNo) {
        ArrayList<Vocab> vocabs = new ArrayList<Vocab>();
        Vocab vocab = null;
        Cursor cursor = GeneralDatabaseHelper.getSQLDatabase().query(TABLE_NAME, COLUMNS, COLUMNS[LECTION_COLUMN_INDEX] + "=?",
                new String[] { Integer.toString(lectionNo)}, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                byte[] test = cursor.getBlob(5);
                vocab = new Vocab(cursor.getLong(0), cursor.getString(1), cursor.getString(2), cursor.getBlob(3),
                        cursor.getInt(4),test);
                vocabs.add(vocab);
            } while (cursor.moveToNext());

        }

        cursor.close();
        return vocabs;
    }

    public static Cursor getAll() {
        Cursor cursor = GeneralDatabaseHelper.getSQLDatabase().query(TABLE_NAME, COLUMNS, null,
               null, null, null, null);
        return cursor;
    }

    public static Cursor getAll(int lectionNo) {
        Cursor cursor = GeneralDatabaseHelper.getSQLDatabase().query(TABLE_NAME, COLUMNS, COLUMNS[LECTION_COLUMN_INDEX] + "=?",
                new String[] { Integer.toString(lectionNo)}, null, null, null);
        return cursor;
    }

    public static long insert(Vocab vocab) {
        GeneralDatabaseHelper.getSQLDatabase().execSQL(CREATE_TABLE);
        ContentValues values = new ContentValues();

        values.put(COLUMNS[BASELANGUAGE_COLUMN_INDEX],vocab.getGerman());
        values.put(COLUMNS[TRANSLATION_COLUMN_INDEX],vocab.getForeign());
        values.put(COLUMNS[SPEECH_COLUMN_INDEX],vocab.getSpeech());
        values.put(COLUMNS[LECTION_COLUMN_INDEX],vocab.getLection());
        values.put(COLUMNS[PICTURE_COLUMN_INDEX],vocab.getPicture());

        long id = GeneralDatabaseHelper.getSQLDatabase().insert(TABLE_NAME, null, values);

        return id;
    }

    public static int update(Vocab vocab) {
        ContentValues values = new ContentValues();

        values.put(COLUMNS[BASELANGUAGE_COLUMN_INDEX],vocab.getGerman());
        values.put(COLUMNS[TRANSLATION_COLUMN_INDEX],vocab.getForeign());
        values.put(COLUMNS[SPEECH_COLUMN_INDEX],vocab.getSpeech());
        values.put(COLUMNS[LECTION_COLUMN_INDEX],vocab.getLection());
        values.put(COLUMNS[PICTURE_COLUMN_INDEX],vocab.getPicture());

        int rows = GeneralDatabaseHelper.getSQLDatabase().update(TABLE_NAME, values, COLUMNS[ID_COLUMN_INDEX] + "=?",
                new String[] { String.valueOf(vocab.getSqlID()) });
        return rows;
    }

    public int delete(long id) {
        return GeneralDatabaseHelper.getSQLDatabase().delete(TABLE_NAME, COLUMNS[ID_COLUMN_INDEX] + "=?",
                new String[]{String.valueOf(id)});
    }

    public void Create() {
        onCreate(GeneralDatabaseHelper.getSQLDatabase());
    }

    public static ArrayList<Vocab> getFromMultipleLection(List<Integer> indices) {
        ArrayList<Vocab> vocabs = new ArrayList<Vocab>();
        Vocab vocab = null;
        String selectionStatement = COLUMNS[LECTION_COLUMN_INDEX] + " in (";
        String[] args = new String[indices.size()];
        Integer counter = 0;

        for (Integer i : indices) {

            args[counter] = String.valueOf(i + 1);
            counter++;
        }

        for (String s : args) {
            if (s != null){
                selectionStatement += s + ",";
            }
        }

        selectionStatement = selectionStatement.substring(0,selectionStatement.length()-1);
        selectionStatement += ")";
        Cursor cursor = GeneralDatabaseHelper.getSQLDatabase().query(TABLE_NAME, COLUMNS, selectionStatement,
                null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                vocab = new Vocab(cursor.getLong(0), cursor.getString(1), cursor.getString(2), cursor.getBlob(3),
                        cursor.getInt(4),cursor.getBlob(5));
                vocabs.add(vocab);
            } while (cursor.moveToNext());

        }

        cursor.close();
        return vocabs;
    }
}
