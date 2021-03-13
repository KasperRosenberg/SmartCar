package com.example.smartcar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Locale;


public class HenkeActivity extends AppCompatActivity implements View.OnClickListener {
    private TextToSpeech ttsEngine;
    private Button ttsButtonSpeak;
    private TextView txvResult;
    private ArrayList<String> result;
    private Button yesButton;
    private Button noButton;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_henke);
        yesButton = findViewById(R.id.button4);
        noButton = findViewById(R.id.button_speak);
        txvResult = findViewById(R.id.txvResult);
        noButton.setOnClickListener(this);
        yesButton.setOnClickListener(this);

        ttsEngine = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    int result = ttsEngine.setLanguage(Locale.US);

                    if (result == TextToSpeech.LANG_MISSING_DATA
                            || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Log.e("TTS", "Language not supported");
                    } //else {
                       // ttsButtonSpeak.setEnabled(true);
                   // }
                } else {
                    Log.e("TTS", "Initialization failed");
                }
            }
        });

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
               txvResult.setText("You have a meeting with Henrik in 10 minutes, you will be late. Do you want to call Henrik?");
                speak("You have a meeting with Henrik in 10 minutes, you will be late. Do you want to call Henrik?");
            }
        }, 500);

        final Handler speechHandler = new Handler();
        speechHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                getSpeechInput();
            }
        }, 7000);
    }

    public void doSomething(){
        for (Object o : result){
            String s = o.toString();
            if(s.contains("yes")){
                openCallActivity();
                //txvResult.setText("YES");
                //speak("Okay, calling Henrik now");
                break;
            }
            if (s.contains("no")){
                openMainActivity();
                //txvResult.setText("NO");
                //speak("Okay, suit yourself");
                break;
            }
            else {
                speak("Sorry, I did not quite catch that, try again");
              // txvResult.setText(result.toString());
                final Handler speechHandler = new Handler();
                speechHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getSpeechInput();
                    }
                }, 3000);
                break;
            }
        }

    }

    @Override
    public void onClick(View v){
        if(v.getId() == R.id.button4){
            openCallActivity();
        }
        if (v.getId() == R.id.button_speak){
            openMainActivity();
        }
    }

    public void openCallActivity(){
        //Start your second activity
        speak("Okay, calling Henrik now");
        Intent intent5 = new Intent(HenkeActivity.this, CallActivity.class);
        startActivity(intent5);
    }

    public void openMainActivity(){
        speak("Okay, suit yourself");
        Intent intent4 = new Intent(HenkeActivity.this, MainActivity.class);
        startActivity(intent4);
    }
    private void speak(String s){
        String text = s;
        ttsEngine.setPitch(1f);
        ttsEngine.setSpeechRate(1f);
        ttsEngine.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }

    @Override
    protected void onDestroy(){
        if(ttsEngine != null){
            ttsEngine.stop();
            ttsEngine.shutdown();
        }

        super.onDestroy();
    }

    public void getSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, 1);
        } else {
            Toast.makeText(this, "Your Device Don't Support Speech Input", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK && data != null) {
                    result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                }
                doSomething();
                break;
        }
    }


}
