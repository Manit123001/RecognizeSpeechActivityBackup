package com.example.a70004504.myapplication.model;

import android.provider.BaseColumns;

/**
 * Created by MCNEWZ on 10-Oct-17.
 */

public class SpeakThai {

    //Database
    public static final String DATABASE_NAME = "speak_thai.db";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE = "text_thai";

    public class Column {
        public static final String ID = BaseColumns._ID;
        public static final String TEXT_SPEAK = "text_speak";
        public static final String TEXT_SOLVE = "text_solve";

    }


    private int id;
    private String textSpeak;
    private String textSolve;

    public SpeakThai() {
    }

    public SpeakThai(int id, String textSpeak, String textSolve) {
        this.id = id;
        this.textSpeak = textSpeak;
        this.textSolve = textSolve;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTextSpeak() {
        return textSpeak;
    }

    public void setTextSpeak(String textSpeak) {
        this.textSpeak = textSpeak;
    }

    public String getTextSolve() {
        return textSolve;
    }

    public void setTextSolve(String textSolve) {
        this.textSolve = textSolve;
    }


}
