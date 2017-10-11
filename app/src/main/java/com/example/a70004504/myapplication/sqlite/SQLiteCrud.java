package com.example.a70004504.myapplication.sqlite;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.a70004504.myapplication.R;
import com.example.a70004504.myapplication.model.SpeakThai;
import com.example.a70004504.myapplication.sqlite.db.DBHelper;

import java.util.List;


public class SQLiteCrud extends AppCompatActivity {
    DBHelper mHelper;
    List<SpeakThai> listTextThai;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sqlite_crud);


        mHelper = new DBHelper(SQLiteCrud.this);
        listTextThai = mHelper.getFriendList();

        SpeakThai showText;

        Toast.makeText(this, "size"+listTextThai.size()+"", Toast.LENGTH_SHORT).show();

        for (SpeakThai member : listTextThai){
            Log.d("member", member.getId()+"");
            Log.d("member", member.getTextSpeak());
            Log.d("member", member.getTextSolve());
        }

//        for (int i = 0; i< listTextThai.size(); i++){
//            showText = listTextThai.get(i);
//            Log.d("member", showText.getTextSolve());
//        }
    }



}
