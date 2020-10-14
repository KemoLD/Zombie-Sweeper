package com.example.myapplication.model;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.example.myapplication.R;

public class MainMenu extends AppCompatActivity {

    private Button playbutton;
    private Button optionsbutton;
    private Button helpButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        playbutton = (Button)findViewById(R.id.playbutton) ;
        playbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent start = new Intent(MainMenu.this, );
                //startActivity(start);
            }
        });

        optionsbutton = (Button)findViewById(R.id.optionsbutton);
        optionsbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent options = new Intent(MainMenu.this,OptionsMenu.class );
                startActivityForResult(options, 1);
            }
        });

        helpButton = (Button)findViewById(R.id.helpbutton);
        helpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent help = new Intent(MainMenu.this, HelpMenu.class);
                startActivity(help);
            }
        });
    }
}