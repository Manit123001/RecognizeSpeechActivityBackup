package com.example.a70004504.myapplication;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ReadFileSDCardActivity extends AppCompatActivity {

    public String pathRoot = Environment.getExternalStorageDirectory().getAbsolutePath() + "/aaTutorial";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_file_sdcard);

        File dir = new File(pathRoot);
        dir.mkdirs();


/*** Read Text File SD Card ***/
        try {


            String path = pathRoot + "/savedFile.txt";
            // or path = "/mnt/sdcard/mydata/thaicreate.txt";


            File file = new File(path);

            BufferedReader br = new BufferedReader(new FileReader(file));
            ArrayList<String> myArr = new ArrayList<String>();

            String line;
            while ((line = br.readLine()) != null) {
                myArr.add(line);
            }

            br.close();
            file = null;

            // listView1
            final ListView lisView1 = (ListView) findViewById(R.id.listView1);

            // ArrayList to Array
            String[] myData = {};
            myData = myArr.toArray(new String[myArr.size()]);

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_1, myData);

            lisView1.setAdapter(adapter);


        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Toast.makeText(ReadFileSDCardActivity.this, "Failed! = " + e.getMessage(),
                    Toast.LENGTH_LONG).show();
        }
    }
}
