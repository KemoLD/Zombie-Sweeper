package com.example.myapplication.model;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.os.Vibrator;
import com.example.myapplication.R;
import com.example.myapplication.logic.Cell;
import java.util.LinkedHashSet;
import java.util.Random;
import java.util.Set;

public class GameScreenActivity extends AppCompatActivity {

    private int NUM_COLS; //getIntent().getIntExtra("columns",0); //chosen number of columns from OptionsMenu
    private int NUM_ROWS; //getIntent().getIntExtra("rows",0); //chosen number of rows from OptionsMenu

    private int NUM_ZOMBIES; //chosen number of zombies from OptionsMenu

    private int NUM_SCANS = 0;  //for th display
    private int NUM_ZOMBS = 0;  //for the display

    private TextView numScans;
    private TextView numZombies;

    private Vibrator v;

    private Button buttons[][];          //holds the cells for the grid

    private Set<Integer> zombiecells;    //holds random numbers to determine which cells have a zombie

    private PopupWindow popupWindow;  //popup menu once game is won

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_screen);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);  //for the vibration effect

        NUM_COLS = getIntent().getIntExtra("columns",0);
        NUM_ROWS = getIntent().getIntExtra("rows",0);
        NUM_ZOMBIES = getIntent().getIntExtra("nmbzombies",0);

        buttons = new Button[NUM_ROWS][NUM_COLS];

        zombiecells = new LinkedHashSet<Integer>();   //assignes zombies to random cells
        while(zombiecells.size() < NUM_ZOMBIES){
            zombiecells.add( new Random().nextInt(NUM_COLS * NUM_ROWS));
        }


        populateButtons();

        numScans = (TextView)findViewById(R.id.textViewRight);
        numZombies = (TextView)findViewById(R.id.textViewLeft);
        numZombies.setText("Found " + NUM_ZOMBS + " of " + NUM_ZOMBIES + " Zombies");

        Button back = (Button)findViewById(R.id.gamebackbutton);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void populateButtons() {
        TableLayout table = (TableLayout) findViewById(R.id.tableForButtons);


        for (int row = 0; row < NUM_ROWS; row++){
            TableRow tableRow = new TableRow(this);
            tableRow.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,TableLayout.LayoutParams.MATCH_PARENT, 1.0f));
            table.addView(tableRow);

            for (int col = 0; col < NUM_COLS; col++){
                final int FINAL_COL =col;
                final int FINAL_ROW = row;
                Button button = new Button(this);
                final Cell ButtonManager = new Cell(button, ((col * NUM_ROWS ) + row ));
                
                button.setLayoutParams(new TableRow.LayoutParams(90, 90, 1.0f));

                button.setPadding(0, 0, 0, 0); //So small buttons don't cut text
                button.setBackground(ResourcesCompat.getDrawable(getResources(),R.drawable.cell, null));

                int number = (col * NUM_ROWS ) + row;
                if ( zombiecells.contains(number)) {
                        ButtonManager.setZombie(true);
                        button.setText("z");
                        button.setTextColor(getResources().getColor(android.R.color.transparent));
                }


                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ButtonManager.setClicked(true);
                        gridButtonClicked(FINAL_COL,FINAL_ROW, ButtonManager);
                    }
                });


                tableRow.addView(button);
                buttons[row][col] = button;

            }
        }

    }

    private void gridButtonClicked(int col, int row, Cell ButtonManager) {

        Button button = buttons[row][col];

        if (ButtonManager.isZombie()) { //if button == zombie
            if (ButtonManager.isRevealed() == false) {
                NUM_ZOMBS++;
                numZombies.setText("Found " + NUM_ZOMBS + " of " + NUM_ZOMBIES + " Zombies");
                ButtonManager.setRevealed(true);
                button.setText("");   //marks the zombie as seen so that it is not included in the scan
                button.setBackground(ResourcesCompat.getDrawable(getResources(),R.drawable.zombieincell, null));
                v.vibrate(400);   //vibrates for 400 milliseconds
                if(NUM_ZOMBS == NUM_ZOMBIES){
                    gameWon();
                }
            }
            else {
                if (ButtonManager.isScanned()) {
                    return;
                }

                else{
                    NUM_SCANS++;
                    numScans.setText("Number of scans: " + NUM_SCANS);
                    ButtonManager.setScanned(true);
                    scan(col,row);

                }
            }
        }

        else {
            if (ButtonManager.isRevealed() == true) {
                return;
            }
            else{
                ButtonManager.setRevealed(true);
                button.setBackground(ResourcesCompat.getDrawable(getResources(),R.drawable.emptycell, null));
                NUM_SCANS++;
                numScans.setText("Number of scans: " + NUM_SCANS);
                ButtonManager.setScanned(true);
                scan(col, row);

            }
        }
    }

    public void scan(int col, int row) {

        int scan_result = 0;

        //scan
        for (int i = 0; i < NUM_COLS; i++){
            Button button = buttons[row][i];
            if(button.getText() == "z"){
                scan_result++;
            }
        }
        for (int i = 0; i < NUM_ROWS; i++){
            Button button = buttons[i][col];
            if(button.getText() == "z"){
                scan_result++;
            }
        }
        Button Jbutton = buttons[row][col];
        Jbutton.setText("" + scan_result);
        Jbutton.setTextColor(Color.BLACK);


    }

    public void gameWon(){

        LayoutInflater layoutInflater = (LayoutInflater) GameScreenActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View customView = layoutInflater.inflate(R.layout.popupmenu,null);


        //instantiate popup window
        popupWindow = new PopupWindow(customView, WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);

        //display the popup window
        popupWindow.showAtLocation(customView, Gravity.CENTER, 0, 0);


        Button won = (Button)customView.findViewById(R.id.popupbutton);
        won.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                finish();
            }
        });

    }
}