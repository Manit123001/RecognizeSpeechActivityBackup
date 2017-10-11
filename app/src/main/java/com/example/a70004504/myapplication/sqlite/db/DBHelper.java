package com.example.a70004504.myapplication.sqlite.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.a70004504.myapplication.model.SpeakThai;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MCNEWZ on 10-Oct-17.
 */

public class DBHelper extends SQLiteOpenHelper {

    private final String TAG = getClass().getSimpleName();

    private SQLiteDatabase sqLiteDatabase;
    private SpeakThai speakThai;
    private ArrayList<SpeakThai> wordThai;
    int idIndex;
    int textSpeakIndex;
    int textSolveIndex;

    public DBHelper(Context context) {
        super(context, SpeakThai.DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TEXTTHAI_TABLE = String.format("CREATE TABLE %s " +
                        "(%s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT, %s TEXT)",

                SpeakThai.TABLE,
                SpeakThai.Column.ID,
                SpeakThai.Column.TEXT_SPEAK,
                SpeakThai.Column.TEXT_SOLVE);

        Log.i(TAG, CREATE_TEXTTHAI_TABLE);
        db.execSQL(CREATE_TEXTTHAI_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        String DROP_TEXTTHAI_TABLE = "DROP TABLE IF EXISTS " + SpeakThai.TABLE;

        db.execSQL(DROP_TEXTTHAI_TABLE);
        Log.i(TAG, "Upgrade Database from " + oldVersion + " to " + newVersion);

        onCreate(db);
    }



    public List<SpeakThai> getFriendList() {

        sqLiteDatabase = this.getWritableDatabase();

        wordThai = new ArrayList<SpeakThai>();
        Cursor cursor = sqLiteDatabase.query
                (SpeakThai.TABLE, null, null, null, null, null, null);


        if (cursor != null) {
            cursor.moveToFirst();

            while (!cursor.isAfterLast()) {
                SpeakThai speakThai = cursorToEntity(cursor);

                wordThai.add(speakThai);
                cursor.moveToNext();
            }
            cursor.close();
        }

        return wordThai;


    }

    protected SpeakThai cursorToEntity(Cursor cursor) {

        speakThai = new SpeakThai();



        if (cursor != null) {
            if (cursor.getColumnIndex("_id") != -1) {
                idIndex = cursor.getColumnIndexOrThrow(
                        "_id");

                speakThai.setId(cursor.getInt(idIndex));
            }
            if (cursor.getColumnIndex(SpeakThai.Column.TEXT_SPEAK) != -1) {
                textSpeakIndex = cursor.getColumnIndexOrThrow(
                        SpeakThai.Column.TEXT_SPEAK);

                speakThai.setTextSpeak(cursor.getString(textSpeakIndex));
            }
            if (cursor.getColumnIndex(SpeakThai.Column.TEXT_SOLVE) != -1) {
                textSolveIndex = cursor.getColumnIndexOrThrow(
                        SpeakThai.Column.TEXT_SOLVE);

                speakThai.setTextSolve(cursor.getString(textSolveIndex));
            }


        }
        return speakThai;
    }


    public List<String> getFriendListText() {
        List<String> wordThai = new ArrayList<String>();

        sqLiteDatabase = this.getWritableDatabase();

        Cursor cursor = sqLiteDatabase.query
                (SpeakThai.TABLE, null, null, null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }

        while (!(cursor != null && cursor.isAfterLast())) {

            wordThai.add(cursor.getLong(0) + " " +
                    cursor.getString(1) + " =   " +
                    cursor.getString(2));

            cursor.moveToNext();
        }

        sqLiteDatabase.close();

        return wordThai;
    }

    public void addFriend(SpeakThai speakThai) {
        sqLiteDatabase = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        //values.put(Friend.Column.ID, friend.getId());
        values.put(SpeakThai.Column.TEXT_SPEAK, speakThai.getTextSpeak());
        values.put(SpeakThai.Column.TEXT_SOLVE, speakThai.getTextSolve());

        sqLiteDatabase.insert(SpeakThai.TABLE, null, values);
        sqLiteDatabase.close();
    }

    public SpeakThai getTextThai(String id) {

        sqLiteDatabase = this.getReadableDatabase();

        Cursor cursor = sqLiteDatabase.query(SpeakThai.TABLE,
                null,
                SpeakThai.Column.ID + " = ? ",
                new String[]{id},
                null,
                null,
                null,
                null);


        if (cursor != null) {
            cursor.moveToFirst();
        }

        SpeakThai speakThai = new SpeakThai();
        if (cursor != null) {
            speakThai.setId((int) cursor.getLong(0));
            speakThai.setTextSpeak(cursor.getString(1));
            speakThai.setTextSolve(cursor.getString(2));
        }


        return speakThai;
    }

    public void updateFriend(SpeakThai speakThai) {

        sqLiteDatabase = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(SpeakThai.Column.ID, speakThai.getId());
        values.put(SpeakThai.Column.TEXT_SPEAK, speakThai.getTextSpeak());
        values.put(SpeakThai.Column.TEXT_SOLVE, speakThai.getTextSolve());

        int row = sqLiteDatabase.update(SpeakThai.TABLE,
                values,
                SpeakThai.Column.ID + " = ? ",
                new String[]{String.valueOf(speakThai.getId())});

        sqLiteDatabase.close();
    }


    public void deleteFriend(String id) {

        sqLiteDatabase = this.getWritableDatabase();

    /*sqLiteDatabase.delete(Friend.TABLE, Friend.Column.ID + " = ? ",
            new String[] { String.valueOf(friend.getId()) });*/
        sqLiteDatabase.delete(SpeakThai.TABLE, SpeakThai.Column.ID + " = " + id, null);

        sqLiteDatabase.close();
    }


}