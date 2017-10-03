package com.example.a70004504.myapplication;

import android.content.Context;
import android.os.Build;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.widget.Toast;

import java.util.Locale;

/**
 * Created by 70004504 on 22/09/2560.
 */

public class MyTTS extends UtteranceProgressListener implements TextToSpeech.OnInitListener, TextToSpeech.OnUtteranceCompletedListener {
    public static MyTTS myTTS;


    public static MyTTS getInstance(Context context) {
        if (myTTS == null) {
            myTTS = new MyTTS(context);
        }
        return myTTS;
    }

    private Context context;
    private TextToSpeech tts;
//    private Locale locale = Locale.getDefault();
    private Locale locale = new Locale("th");
    private String enginePackageName;
    private String message;
    private boolean isRunning;
    private int speakCount;


    public MyTTS(Context context) {
        this.context = context;
    }

    public void speak(String message) {
        this.message = message;

        if (tts == null || !isRunning) {
            speakCount = 0;

            if (enginePackageName != null && !enginePackageName.isEmpty()) {
                tts = new TextToSpeech(context, this, enginePackageName);
            } else {
                tts = new TextToSpeech(context, this);
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1) {
                tts.setOnUtteranceProgressListener(this);
            } else {
                tts.setOnUtteranceCompletedListener(this);
            }

            isRunning = true;
        } else {
            startSpeak();
        }
    }

    public MyTTS setEngine(String packageName) {
        enginePackageName = packageName;
        return this;
    }

    public MyTTS setLocale(Locale locale) {
        this.locale = locale;
        return this;
    }


    private void startSpeak() {
        speakCount++;

        if (locale != null) {
            tts.setLanguage(locale);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            tts.speak(message, TextToSpeech.QUEUE_FLUSH, null, "");
        } else {
            tts.speak(message, TextToSpeech.QUEUE_FLUSH, null);
        }
    }
    private void clear() {
        speakCount--;

        if (speakCount == 0) {
            tts.shutdown();
            isRunning = false;
        }
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            startSpeak();
//            Toast.makeText(context, "onInit", Toast.LENGTH_SHORT).show();
        }
    }



    @Override
    public void onStart(String s) {
//        Toast.makeText(context, "onStart", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onDone(String s) {
//        Toast.makeText(context, "onDone", Toast.LENGTH_SHORT).show();

        clear();
    }

    @Override
    public void onError(String s) {
        clear();
    }

    @Override
    public void onUtteranceCompleted(String s) {
//        Toast.makeText(context, "onUtteranceCompleted", Toast.LENGTH_SHORT).show();

        clear();
    }

}
