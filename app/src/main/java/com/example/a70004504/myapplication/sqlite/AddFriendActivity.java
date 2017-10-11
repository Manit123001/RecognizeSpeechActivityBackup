package com.example.a70004504.myapplication.sqlite;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.a70004504.myapplication.R;
import com.example.a70004504.myapplication.model.SpeakThai;
import com.example.a70004504.myapplication.sqlite.db.DBHelper;

public class AddFriendActivity extends AppCompatActivity {
    private EditText text1;
    private EditText text2;
    private Button btnOk;

    private DBHelper mHelper;

    private int ID = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend);

        Bundle bundle = getIntent().getExtras();



        // enabling action bar app icon and behaving it as toggle button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        text1 = (EditText) findViewById(R.id.editText1);
        text2 = (EditText) findViewById(R.id.editText2);
        btnOk = (Button) findViewById(R.id.btnOk);


        mHelper = new DBHelper(this);

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder =
                        new AlertDialog.Builder(AddFriendActivity.this);

                builder.setTitle(getString(R.string.add_data_title));
                builder.setMessage(getString(R.string.add_data_message));

                builder.setPositiveButton(getString(android.R.string.ok),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                SpeakThai speakThai = new SpeakThai();

                                speakThai.setTextSpeak(text1.getText().toString());
                                speakThai.setTextSolve(text2.getText().toString());
//
                                Log.d("test", text1.getText().toString());

                                if (ID == -1) {
                                    mHelper.addFriend(speakThai);
                                } else {
                                    speakThai.setId(ID);
                                    mHelper.updateFriend(speakThai);
                                }

                                ShowDataActivity.ma.RefreshList();
                                finish();
                            }
                        });

                builder.setNegativeButton(getString(android.R.string.cancel),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });


                builder.show();
            }
        });




        if (bundle != null) {
            ID = bundle.getInt(SpeakThai.Column.ID);
            String txtSpeak = bundle.getString(SpeakThai.Column.TEXT_SPEAK);
            String txtSolve = bundle.getString(SpeakThai.Column.TEXT_SOLVE);

            text1.setText(txtSpeak);
            text2.setText(txtSolve);
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        ShowDataActivity.ma.RefreshList();

        finish();
        overridePendingTransition(android.R.anim.fade_in,
                android.R.anim.fade_out);

        return super.onOptionsItemSelected(item);
    }


}

