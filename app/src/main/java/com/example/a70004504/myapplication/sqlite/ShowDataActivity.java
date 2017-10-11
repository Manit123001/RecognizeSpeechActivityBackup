package com.example.a70004504.myapplication.sqlite;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.a70004504.myapplication.R;
import com.example.a70004504.myapplication.model.SpeakThai;
import com.example.a70004504.myapplication.sqlite.db.DBHelper;

import java.util.List;

public class ShowDataActivity extends AppCompatActivity {
    ListView lvShowText;
    private DBHelper mHelper;
    private List<String> listTextThai;
    private ArrayAdapter<String> adapter;

    public static ShowDataActivity ma;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_data);

        ma = this;
        RefreshList();

    }

    public void RefreshList() {
        lvShowText = (ListView) findViewById(R.id.lvShowText);

        mHelper = new DBHelper(this);
        listTextThai = mHelper.getFriendListText();

        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, listTextThai);

        lvShowText.setAdapter(adapter);

        lvShowText.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent detail = new Intent(ShowDataActivity.this, DetailActivity.class);

                String listName = listTextThai.get(i);

                int index = listName.indexOf(" ");
                String columnId = listName.substring(0, index);
                Log.d("s", columnId.toString());

                detail.putExtra(SpeakThai.Column.ID, columnId);

                startActivity(detail);
//                overridePendingTransition(android.R.anim.fade_in,
//                        android.R.anim.fade_out);

            }
        });

        ((ArrayAdapter) lvShowText.getAdapter()).notifyDataSetInvalidated();
    }


    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();
        ((ArrayAdapter) lvShowText.getAdapter()).notifyDataSetInvalidated();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_add) {
            Intent addTextThai = new Intent(this, AddFriendActivity.class);

            startActivity(addTextThai);

//            overridePendingTransition(android.R.anim.fade_in,
//                    android.R.anim.fade_out);
        }
        return super.onOptionsItemSelected(item);
    }

}
