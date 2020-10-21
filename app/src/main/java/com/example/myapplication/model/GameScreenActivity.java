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
import android.widget.Toast;

import com.example.myapplication.R;

import java.util.ArrayList;

public class GameScreenActivity extends AppCompatActivity {

    private int NUM_COLS; //getIntent().getIntExtra("columns",0); //chosen number of columns from OptionsMenu
    private int NUM_ROWS; //getIntent().getIntExtra("rows",0); //chosen number of rows from OptionsMenu

    int numOfZombies = 5; //chosen number of zombies from OptionsMenu

    Button buttons[][];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //NUM_COLS = getIntent().getIntExtra("columns",0);
        //NUM_ROWS = getIntent().getIntExtra("rows",0);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_screen);

        NUM_COLS = getIntent().getIntExtra("columns",0);
        NUM_ROWS = getIntent().getIntExtra("rows",0);

        buttons = new Button[NUM_ROWS][NUM_COLS];

        populateButtons();
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

                button.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT, 1.0f));

                button.setText("" + col + ", " + row);

                button.setPadding(0, 0, 0, 0); //So small buttons don't cut text

                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //boolean clicked = false;
                        gridButtonClicked(FINAL_COL,FINAL_ROW);
                    }
                });


                tableRow.addView(button);
                buttons[row][col] = button;
            }
        }
    }

    private void gridButtonClicked(int col, int row) {

        //Toast.makeText(this, "Button clicked: " + col + ", " + row, Toast.LENGTH_SHORT).show();
        Button button = buttons[row][col];

        lockButtonSizes(); //lock button sizes

        /*
         * Scale image to button on devices older than 4.1
         * button.setBackgroundResource(R.drawable.gameicon);
         * rescale starting bitmap to one that is small enough that it can stretch up but button won't change its size
         */

        //scale image to button in JellyBean (4.1 or newer)
        int newWidth = button.getWidth();
        int newHeight = button.getHeight();
        Bitmap originalBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.gameicon);
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(originalBitmap, newWidth, newHeight, true);
        Resources resource = getResources();

        if ((row == 2) && (col == 2)){ //if button == zombie
            Toast.makeText(this, "ZOMBIE FOUND", Toast.LENGTH_SHORT).show();
            button.setText("");
            button.setBackground(new BitmapDrawable(resource, scaledBitmap));
        }
        else{
            Toast.makeText(this, "Button clicked: " + col + ", " + row, Toast.LENGTH_SHORT).show();
            button.setText("Scan");
        }

    }

    private void lockButtonSizes() {
        for (int row = 0; row < NUM_ROWS; row++){
            for (int col = 0; col < NUM_COLS; col++){
                Button button = buttons[row][col];

                int width = button.getWidth();
                button.setMinWidth(width);
                button.setMinWidth(width);

                int height = button.getHeight();
                button.setMinHeight(height);
                button.setMinHeight(height);
            }
        }
    }
}