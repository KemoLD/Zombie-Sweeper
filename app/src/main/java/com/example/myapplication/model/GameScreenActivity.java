package com.example.myapplication.model;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.PopupWindow;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

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

    TextView numScans;
    TextView numZombies;

    Button buttons[][];

    private Set<Integer> zombiecells;

    private PopupWindow POPUP_WINDOW = null;  //popup menu once game is won

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
            tableRow.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT, 1.0f));
            table.addView(tableRow);

            for (int col = 0; col < NUM_COLS; col++){
                final int FINAL_COL =col;
                final int FINAL_ROW = row;
                Button button = new Button(this);
                final Cell ButtonManager = new Cell(button, ((col * NUM_ROWS ) + row ));
                
                button.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT, 1.0f));

                button.setPadding(0, 0, 0, 0); //So small buttons don't cut text
                button.setBackground(getResources().getDrawable(R.drawable.cell));
                //button.setText(""+ ((col * NUM_ROWS  ) + row ));

                int number = (col * NUM_ROWS ) + row;
                if ( zombiecells.contains(number)) {
                        ButtonManager.setZombie(true);
                        button.setText("z");
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
        lockButtonSizes();


    }

    private void gridButtonClicked(int col, int row, Cell ButtonManager) {

        //Toast.makeText(this, "Button clicked: " + col + ", " + row, Toast.LENGTH_SHORT).show();
        Button button = buttons[row][col];

        //lockButtonSizes(); //lock button sizes


        if (ButtonManager.isZombie() == true) { //if button == zombie
            if (ButtonManager.isRevealed() == false) {
                button.setBackground(getResources().getDrawable(R.drawable.zombieincell));
                NUM_ZOMBS++;
                numZombies.setText("Found " + NUM_ZOMBS + " of " + NUM_ZOMBIES + " Zombies");
                ButtonManager.setRevealed(true);
                if(NUM_ZOMBS == NUM_ZOMBIES){
                    gameWon();
                }
            }
            else {
                if (ButtonManager.isScanned() == true) {
                    return;
                }

                if (ButtonManager.isScanned() == false) {
                    //Toast.makeText(this, "Scanning row " + row + " and column " + col, Toast.LENGTH_SHORT).show();
                    NUM_SCANS++;
                    numScans.setText("Number of tombstones tripped on:" + NUM_SCANS);
                    ButtonManager.setScanned(true);
                    scan(col,row);

                }
            }
        }

        if (ButtonManager.isZombie() == false) {
            if (ButtonManager.isRevealed() == true) {
                if (ButtonManager.isScanned() == true) {
                    return;
                }

                else {
                    NUM_SCANS++;
                    numScans.setText("Number of tombstones tripped on:" + NUM_SCANS);
                    ButtonManager.setScanned(true);
                    scan(col, row);

                }
            }
            else{
                ButtonManager.setRevealed(true);
                button.setBackgroundResource(R.drawable.emptycell);
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

        Toast.makeText(this, "There are " + scan_result + " zombies in that row and column", Toast.LENGTH_SHORT).show();
    }

    public void lockButtonSizes() {
        for (int row = 0; row < NUM_ROWS; row++){
            for (int col = 0; col < NUM_COLS; col++){
                Button button = buttons[row][col];

                int width = button.getWidth();
                //button.setMinWidth(width);
                button.setMaxWidth(10);

                int height = button.getHeight();
                //button.setMinHeight(height);
                button.setMaxHeight(10);
            }
        }
    }

    public void gameWon(){
        DisplayMetrics displayMetrics = this.getResources().getDisplayMetrics();
        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;

        LayoutInflater layoutInflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = layoutInflater.inflate(R.layout.popupmenu, null);

        final PopupWindow POPUP_WINDOW = new PopupWindow(this);
        POPUP_WINDOW.setContentView(layout);
        POPUP_WINDOW.setWidth(width);
        POPUP_WINDOW.setHeight(height);
        POPUP_WINDOW.setFocusable(true);

        POPUP_WINDOW.setBackgroundDrawable(null);
        POPUP_WINDOW.showAtLocation(layout, Gravity.CENTER, 1, 1);

        Button won = (Button)findViewById(R.id.popupbutton);
        won.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                POPUP_WINDOW.dismiss();
                finish();
            }
        });

    }
}