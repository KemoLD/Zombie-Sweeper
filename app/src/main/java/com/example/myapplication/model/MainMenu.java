package com.example.myapplication.model;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.example.myapplication.R;

//main menu activity
public class MainMenu extends AppCompatActivity {

    private Button playbutton;
    private Button optionsbutton;
    private Button helpButton;
    private int columns = 4;
    private int rows = 6;
    private int nmbzombies = 6;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //launches the game
        playbutton = (Button)findViewById(R.id.playbutton) ;
        playbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent start = new Intent(MainMenu.this, );
                //startActivity(start);
            }
        });

        //launches the options activity and sends the selected settings
        optionsbutton = (Button)findViewById(R.id.optionsbutton);
        optionsbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent options = new Intent(MainMenu.this,OptionsMenu.class );
                options.putExtra("columns",columns );
                options.putExtra("rows", rows);
                options.putExtra("nmbzombies", nmbzombies);
                startActivityForResult(options, 1);
            }
        });

        //launches the help activity
        helpButton = (Button)findViewById(R.id.helpbutton);
        helpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent help = new Intent(MainMenu.this, HelpMenu.class);
                startActivity(help);
            }
        });
    }

    //this method gets the new setting from the options activity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK){
            assert data != null;
            int col = data.getIntExtra("columns", 0);  //passes the size and number of mines variables
            int row = data.getIntExtra("rows", 0 );
            int nmb = data.getIntExtra("zombies",0);
            if(col != 0 && row!= 0){
                columns = col;
                rows = row;
            }
            if(nmb != 0){
               nmbzombies = nmb;
            }
        }
    }
}