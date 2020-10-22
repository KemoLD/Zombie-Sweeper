package com.example.myapplication.model;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.logic.Cell;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Random;
import java.util.Set;

public class GameScreenActivity extends AppCompatActivity {

    private int NUM_COLS; //getIntent().getIntExtra("columns",0); //chosen number of columns from OptionsMenu
    private int NUM_ROWS; //getIntent().getIntExtra("rows",0); //chosen number of rows from OptionsMenu

    private int NUM_ZOMBIES; //chosen number of zombies from OptionsMenu

    private int NUM_SCANS = 0;  //for th display
    private int NUM_ZOMBS = 0;  //for the display

    TextView numScans;
    TextView numZombies;

    Button buttons[][];

    private Set<Integer> zombiecells;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //NUM_COLS = getIntent().getIntExtra("columns",0);
        //NUM_ROWS = getIntent().getIntExtra("rows",0);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_screen);

        NUM_COLS = getIntent().getIntExtra("columns",0);
        NUM_ROWS = getIntent().getIntExtra("rows",0);
        NUM_ZOMBIES = getIntent().getIntExtra("nmbzombies",0);

        buttons = new Button[NUM_ROWS][NUM_COLS];

        zombiecells = new LinkedHashSet<Integer>();
        while(zombiecells.size() < NUM_ZOMBIES){
            zombiecells.add( new Random().nextInt(NUM_COLS * NUM_ROWS));
        }


        populateButtons();

        numScans = (TextView)findViewById(R.id.textViewRight);
        numZombies = (TextView)findViewById(R.id.textViewLeft);
        numZombies.setText("Found " + NUM_ZOMBS + " of " + NUM_ZOMBIES + " Zombies");
    }

    private void populateButtons() {
        TableLayout table = (TableLayout) findViewById(R.id.tableForButtons);


        for (int row = 0; row < NUM_ROWS; row++){
            TableRow tableRow = new TableRow(this);
            tableRow.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT, 1.0f));
            table.addView(tableRow);

            for (int col = 0; col < NUM_COLS; col++){
                final int FINAL_COL =col;
                final int FINAL_ROW = row;
                Button button = new Button(this);
                final Cell ButtonManager = new Cell(button, ((col * NUM_ROWS ) + row ));
                
                button.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT, 1.0f));

                button.setPadding(0, 0, 0, 0); //So small buttons don't cut text
                button.setBackgroundResource(R.drawable.cell);
                //button.setText(""+ ((col * NUM_ROWS  ) + row ));

                int number = (col * NUM_ROWS ) + row;
                if ( zombiecells.contains(number)) {
                        ButtonManager.setZombie(true);
                }


                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //boolean clicked = false;
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

        //Toast.makeText(this, "Button clicked: " + col + ", " + row, Toast.LENGTH_SHORT).show();
        Button button = buttons[row][col];

        lockButtonSizes(); //lock button sizes

        /*
         * Scale image to button on devices older than 4.1
         * button.setBackgroundResource(R.drawable.gameicon);
         * rescale starting bitmap to one that is small enough that it can stretch up but button won't change its size
         */


        if (ButtonManager.isZombie() == true) { //if button == zombie
            if (ButtonManager.isRevealed() == false) {
                Toast.makeText(this, "ZOMBIE FOUND", Toast.LENGTH_SHORT).show();
                button.setText("");
                button.setBackgroundResource(R.drawable.zombieincell);
                NUM_ZOMBS++;
                numZombies.setText("Found " + NUM_ZOMBS + " of " + NUM_ZOMBIES + " Zombies");
                ButtonManager.setRevealed(true);
            }
            if (ButtonManager.isRevealed() == true) {
                if (ButtonManager.isScanned() == true) {
                    return;
                }

                if (ButtonManager.isScanned() == false) {
                    Toast.makeText(this, "Scanning row " + row + " and column " + col, Toast.LENGTH_SHORT).show();
                    String tmp = String.valueOf(scan(col, row));
                    button.setText(tmp);
                    //button.setText(scan());
                    numScans.setText("Number of tombstones tripped on:" + NUM_SCANS);
                    ButtonManager.setScanned(true);

                }
            }
        }

        if (ButtonManager.isZombie() == false) {
            if (ButtonManager.isScanned() == true) {
                return;
            }

            if (ButtonManager.isScanned() == false) {
                Toast.makeText(this, "Scanning row " + row + " and column " + col, Toast.LENGTH_SHORT).show();
                String tmp2 = String.valueOf(scan(col, row));
                button.setText(tmp2);
                button.setBackgroundResource(R.drawable.emptycell);
                //button.setText(scan());
                numScans.setText("Number of tombstones tripped on:" + NUM_SCANS);
                ButtonManager.setScanned(true);
            }

        }
    }

    private int scan(int col, int row) {
        int scan_result = 0;

        //scan
        for (int i = 0; i < NUM_COLS; i++){
            //if (buttons[i][col] )
        }

        NUM_SCANS ++;
        return scan_result;
    }

    private void lockButtonSizes() {
        for (int row = 0; row < NUM_ROWS; row++){
            for (int col = 0; col < NUM_COLS; col++){
                Button button = buttons[row][col];

                int width = button.getWidth();
                //button.setMinWidth(width);
                button.setMaxWidth(width);

                int height = button.getHeight();
                //button.setMinHeight(height);
                button.setMaxHeight(height);
            }
        }
    }
}