package com.example.a70004504.myapplication;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Environment;
import android.os.Handler;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import static java.lang.System.in;

public class MainActivity extends AppCompatActivity {
    private TextView txtSpeechInput;
    private TextView txtProvince;
    private ImageButton btnSpeak;
    private final int REQ_CODE_SPEECH_INPUT = 100;
    private final int REQ_CODE_SPEECH_INPUT_AGAIN = 200;

    final String[] ccThai = {"ก", "ข", "ฃ", "ค", "ฅ",
            "ฆ", "ง", "จ", "ฉ", "ช", "ซ", "ฌ", "ญ", "ฎ",
            "ฏ", "ฐ", "ฑ", "ฒ", "ณ", "ด", "ต", "ถ", "ท",
            "ธ", "น", "บ", "ป", "ผ", "ฝ", "พ", "ฟ", "ภ",
            "ม", "ย", "ร", "ล", "ว", "ศ", "ษ", "ส", "ห",
            "ฬ", "อ", "ฮ"};

    public String pathRoot = Environment.getExternalStorageDirectory().getAbsolutePath() + "/aaTutorial";
    private List<String> listProvinceThailand;


    String resultLicense = "";
    String textLicense = "";
    String textProvince = "";
    String textChangeChar = "";

    private final Boolean SPECK_CHECK_TRUE = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtSpeechInput = (TextView) findViewById(R.id.txtSpeechInput);
        txtProvince = (TextView) findViewById(R.id.txtProvince);
        btnSpeak = (ImageButton) findViewById(R.id.btnSpeak);


        File dir = new File(pathRoot);
        dir.mkdirs();


        // province Thailand
        //Get it in an array of strings
        String[] months = getResources().getStringArray(R.array.provinceThailand);

        //Convert it into a list
        List<String> monthsList = new ArrayList<String>();
        listProvinceThailand = Arrays.asList(months);

        String textS = "";
//
//        for (int i = 0; i < months.length; i++) {
//            textS += months[i]+'\n';
//        }
//        Log.d("list",textS);

//        for (int i = 0; i < listProvinceThailand.size(); i++) {
//            textS += listProvinceThailand.get(i) + '\n';
//            int index = -1;
//
//        }
//        Log.d("lists", textS);
//
//
//        if (listProvinceThailand.contains("นครปฐม")) {
//            // found a match to "software"
//            Toast.makeText(this, "Hello contains " + listProvinceThailand.contains("นครปฐม"), Toast.LENGTH_SHORT).show();
//        }
//
//        for (int i = 0; i < listProvinceThailand.size(); i++) {
//            if (listProvinceThailand.get(i).equals("นครปฐม")) {
//                Toast.makeText(this, "find indexOf" + listProvinceThailand.indexOf("นครปฐม"), Toast.LENGTH_SHORT).show();
//                break;
//            }
//        }
//
//
//        int indexString = listProvinceThailand.indexOf("นครปฐม");
//        Toast.makeText(this, "find" + listProvinceThailand.get(indexString), Toast.LENGTH_SHORT).show();
//
//
//        String str = "1234";
//        String str2 = String.format("%-7s", str).replace(' ', ' ');
//        Log.d("xxx", str2);
//
//
//        ArrayList<String> indexCutProvince = new ArrayList<String>();
//        Log.d("test", String.valueOf(indexCutProvince));

        //----------------------
//        replace text

//        String str="fgdfg12°59'50\" Nfr | gdfg: 80°15'25\" Efgd";
//        String str="1กฟ6314\" Nfr | gdfg: 80°15'25\" Efgd";
//        String[] spitStr= str.split("\"");
//        Toast.makeText(this, spitStr[0], Toast.LENGTH_SHORT).show();
//
//        String numberOne= spitStr[0].replaceAll("[^0-9]", "");
//        String numberSecond= spitStr[1].replaceAll("[^0-9]", "");
//
//        Toast.makeText(this, numberOne, Toast.LENGTH_SHORT).show();
//        Toast.makeText(this, numberSecond, Toast.LENGTH_SHORT).show();


        btnSpeak.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                promptSpeechInput(REQ_CODE_SPEECH_INPUT);
            }
        });
    }


    private void promptSpeechInput(int REQ_CODE_SPEECH_INPUT) {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);

//        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "th-TH");
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, getString(R.string.speech_prompt));

        try {

            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);

        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(),
                    getString(R.string.speech_not_supported),
                    Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

                    textChangeChar = ChangeCharector(result.get(0)).replace(" ", "");
                    Toast.makeText(this, textChangeChar, Toast.LENGTH_LONG).show();


                    // check End speech จบ
                    if (textChangeChar.length() >= 9) {
                        if (textChangeChar.contains("จบ")) {

                            ShowProcessLicensePlate(true);
                        } else {

                            Toast.makeText(this, "กรุณาพูดจบ ด้วย", Toast.LENGTH_SHORT).show();
                            MyTTS.getInstance(MainActivity.this).speak("กรุณาพูดจบ ด้วย");

                            ShowProcessLicensePlate(false);
                        }
                    }
                }
                break;
            }
            case REQ_CODE_SPEECH_INPUT_AGAIN: {

                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

                    // check End speech จบ
                    if (result.get(0).contains("จบ")) {
                        txtSpeechInput.setText(textLicense);
                        txtProvince.setText(textProvince);
                        SpeakAndSearch();
                    } else {
                        Toast.makeText(this, "กรุณาพูดจบ ด้วย", Toast.LENGTH_SHORT).show();
                        promptSpeechInput(REQ_CODE_SPEECH_INPUT_AGAIN);
                    }
                }
                break;
            }

        }
    }

    private void ShowProcessLicensePlate(Boolean SPECK_CHECK) {

        //Process Province Thailand
        ArrayList<String> provinceTextOnly = CutProvinceText(textChangeChar);

//        Toast.makeText(this, "" + provinceTextOnly.get(0) + " is province", Toast.LENGTH_SHORT).show();
//        Toast.makeText(this, "" + provinceTextOnly.get(1) + "is license Plate", Toast.LENGTH_SHORT).show();


        if (provinceTextOnly.size() != 0) {
            textLicense = provinceTextOnly.get(1);
            textProvince = provinceTextOnly.get(0);


            //add space " " char text
//                    for (int i = 0; i < textChangeChar.length(); i++) {
//                        char c = textChangeChar.charAt(i);
//                        result22 += " " + c;
//                    }

            for (int i = 0; i < textLicense.length(); i++) {
                char c = textLicense.charAt(i);
                resultLicense += " " + c;
            }

            if (SPECK_CHECK == SPECK_CHECK_TRUE) {
                txtSpeechInput.setText(textLicense);
                txtProvince.setText(textProvince);

                SpeakAndSearch();

            } else {
                // speak again please
                promptSpeechInput(REQ_CODE_SPEECH_INPUT_AGAIN);

            }


        } else {
            txtSpeechInput.setText("");
            txtProvince.setText("");

            Toast.makeText(this, "กรุณาระบุ จังหวัด", Toast.LENGTH_SHORT).show();
            MyTTS.getInstance(MainActivity.this).speak("กรุณาระบุ จังหวัด");

        }
    }

    private void SpeakAndSearch() {
        // Speech Text...
        MyTTS.getInstance(MainActivity.this).speak(resultLicense + textProvince);

        // Check text File Local of Mobile
        SearchTextLicensePlate((resultLicense + textProvince).replace(" ", ""));

        resultLicense = "";

    }


    private ArrayList<String> CutProvinceText(String textAllSearch) {

        ArrayList<String> indexCutProvince = new ArrayList<String>();
        String licensePlate = textAllSearch;
        String provinceThai = textAllSearch;

        if (indexCutProvince.size() != 0) {
            Iterator<String> removeList = indexCutProvince.iterator();
            while (removeList.hasNext()) {
                String s = removeList.next(); // must be called before you can call i.remove()
                // Do something
                removeList.remove();
            }
        }


        for (int i = 0; i < listProvinceThailand.size(); i++) {
            indexCutProvince.clear();

            String getProvince = listProvinceThailand.get(i);
            if (provinceThai.contains(getProvince)) {
                int index = provinceThai.indexOf(getProvince);
                provinceThai = provinceThai.substring(index);

//                Toast.makeText(this, "จังหวัดคือ " + getProvince, Toast.LENGTH_SHORT).show();

                indexCutProvince.add(getProvince);
                indexCutProvince.add(licensePlate.substring(0, index));

                break;
            }
        }


        return indexCutProvince;
    }


    private void SearchTextLicensePlate(String txtSearch) {
        String line = "";

//        Toast.makeText(this, "Hi" + txtSearch, Toast.LENGTH_SHORT).show();
        //*** Read Text File SD Card ***/
        try {
            String path = pathRoot + "/savedFile.txt";
            File file = new File(path);

            BufferedReader br = new BufferedReader(new FileReader(file));
            ArrayList<String> myArr = new ArrayList<String>();
            final MediaPlayer mp = MediaPlayer.create(MainActivity.this, R.raw.wife);
            int notFound = 0;

            while ((line = br.readLine()) != null) {
                myArr.add(line);
                //Toast.makeText(this, "........"+line, Toast.LENGTH_SHORT).show();
                if (line.equals(txtSearch)) {
                    Toast.makeText(this, "เจอเลขทะเบียน" + line, Toast.LENGTH_SHORT).show();

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            //Do something after 100ms
                            mp.start();
//                            MyTTS.getInstance(MainActivity.this).speak("เจอเลขทะเบียน");
                        }
                    }, 4500);


                    notFound = 1;
                    break;
                }
//                Toast.makeText(this, line + txtSearch, Toast.LENGTH_SHORT).show();
            }

            if (notFound == 0) {
//                Toast.makeText(this, "Not Math License Plate", Toast.LENGTH_SHORT).show();
                MyTTS.getInstance(MainActivity.this).speak("ไม่เจอเลขทะเบียนนี้");
            }


            br.close();
            file = null;

            // TODO: Search file here


//            if (Arrays.asList(myArr).contains(txtSearch)) {
//                // true
//                Toast.makeText(this, "Found Text ***********" + txtSearch, Toast.LENGTH_SHORT).show();
//            }

//            for (int i = 0; i < myArr.size(); i++) {
//                String[] myData = {};
//                myData = myArr.toArray(new String[myArr.size()]);
//            }
//            for (String mathText : myArr) {
//                if (mathText.equals(txtSearch)) {
//                    Toast.makeText(this, "Found Text ***********" + line, Toast.LENGTH_SHORT).show();
//                    break;
//                } else {
//
//                }
//            }
//            Toast.makeText(this, "Not found : " + line, Toast.LENGTH_SHORT).show();


            // listView1
//            final ListView lisView1 = (ListView) findViewById(R.id.listView1);
//
//            // ArrayList to Array
//            String[] myData = {};
//            myData = myArr.toArray(new String[myArr.size()]);
//
//            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
//                    android.R.layout.simple_list_item_1, myData);
//
//            lisView1.setAdapter(adapter);


        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Toast.makeText(MainActivity.this, "Failed! = " + e.getMessage(),
                    Toast.LENGTH_LONG).show();
        }
    }

    private String ChangeCharector(String charText) {
        String text2 = charText
                .replace(getString(R.string.charThai1), ccThai[0])
                .replace(getString(R.string.charThai2), ccThai[1])
                .replace(getString(R.string.charThai3), ccThai[2])
                .replace(getString(R.string.charThai4), ccThai[3])
                .replace(getString(R.string.charThai5), ccThai[4])
                .replace(getString(R.string.charThai6), ccThai[5])
                .replace(getString(R.string.charThai7), ccThai[6])
                .replace(getString(R.string.charThai8), ccThai[7])
                .replace(getString(R.string.charThai9), ccThai[8])
                .replace(getString(R.string.charThai10), ccThai[9])
                .replace(getString(R.string.charThai11), ccThai[10])
                .replace(getString(R.string.charThai12), ccThai[11])
                .replace(getString(R.string.charThai13), ccThai[12])
                .replace(getString(R.string.charThai14), ccThai[13])
                .replace(getString(R.string.charThai15), ccThai[14])
                .replace(getString(R.string.charThai16), ccThai[15])
                .replace(getString(R.string.charThai17), ccThai[16])
                .replace(getString(R.string.charThai18), ccThai[17])
                .replace(getString(R.string.charThai19), ccThai[18])
                .replace(getString(R.string.charThai20), ccThai[19])
                .replace(getString(R.string.charThai21), ccThai[20])
                .replace(getString(R.string.charThai22), ccThai[21])
                .replace(getString(R.string.charThai23), ccThai[22])
                .replace(getString(R.string.charThai24), ccThai[23])
                .replace(getString(R.string.charThai25), ccThai[24])
                .replace(getString(R.string.charThai26), ccThai[25])
                .replace(getString(R.string.charThai27), ccThai[26])
                .replace(getString(R.string.charThai28), ccThai[27])
                .replace(getString(R.string.charThai29), ccThai[28])
                .replace(getString(R.string.charThai30), ccThai[29])
                .replace(getString(R.string.charThai31), ccThai[30])
                .replace(getString(R.string.charThai32), ccThai[31])
                .replace(getString(R.string.charThai33), ccThai[32])
                .replace(getString(R.string.charThai34), ccThai[33])
                .replace(getString(R.string.charThai35), ccThai[34])
                .replace(getString(R.string.charThai36), ccThai[35])
                .replace(getString(R.string.charThai37), ccThai[36])
                .replace(getString(R.string.charThai38), ccThai[37])
                .replace(getString(R.string.charThai39), ccThai[38])
                .replace(getString(R.string.charThai40), ccThai[39])
                .replace(getString(R.string.charThai41), ccThai[40])
                .replace(getString(R.string.charThai42), ccThai[41])
                .replace(getString(R.string.charThai43), ccThai[42])
                .replace(getString(R.string.charThai44), ccThai[43]);

        return text2;
    }
}

