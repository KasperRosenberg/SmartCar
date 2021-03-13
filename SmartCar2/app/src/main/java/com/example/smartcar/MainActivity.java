package com.example.smartcar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button henkeButton;
    Button rahimButton;
    Button jozefButton;
   // TextView txtView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        henkeButton=findViewById(R.id.button);
        rahimButton=findViewById(R.id.button2);
        jozefButton=findViewById(R.id.button3);
       // txtView=findViewById(R.id.textView);
        henkeButton.setOnClickListener(this);
        rahimButton.setOnClickListener(this);
        jozefButton.setOnClickListener(this);
    }
    @Override
    public void onClick(View v){
        if(v.getId() == R.id.button){
            openHenkeActivity();
        }
        if (v.getId() == R.id.button2){
            openRahimActivity();
        }

        if (v.getId() == R.id.button3){
            openJozefActivity();
        }
    }

    public void openHenkeActivity(){
        //Start your second activity
        Intent intent1 = new Intent(MainActivity.this, HenkeActivity.class);
        startActivity(intent1);
    }

    public void openRahimActivity(){
        //Start your second activity
        Intent intent2 = new Intent(MainActivity.this, RahimActivity.class);
        startActivity(intent2);
    }

    public void openJozefActivity(){
        //Start your second activity
        Intent intent3 = new Intent(MainActivity.this, JozefActivity.class);
        startActivity(intent3);
    }

}
