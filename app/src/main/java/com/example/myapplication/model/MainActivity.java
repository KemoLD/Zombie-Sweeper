package com.example.myapplication.model;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.R;

public class MainActivity extends AppCompatActivity {

    public static int timer = 6000;
    private TextView welcomeText;
    private ImageView Icon;
    private Button skip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        welcomeText = (TextView)findViewById(R.id.welcomeheader);
        Icon = (ImageView) findViewById(R.id.appicon);


        startanimation();
        final Intent intent = new Intent(MainActivity.this, MainMenu.class);

        Button skip = (Button)findViewById(R.id.skipbutton);
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);
                finish();
            }
        });

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(intent);;
                finish();
            }
        },timer);


    }

    public void startanimation(){
        Animation welcomeAnimation = AnimationUtils.loadAnimation(this, R.anim.welcomescreentext);
        welcomeText.startAnimation(welcomeAnimation);

        Animation icon = AnimationUtils.loadAnimation(this, R.anim.welcomeappicon);
        Icon.startAnimation(icon);

    }
}