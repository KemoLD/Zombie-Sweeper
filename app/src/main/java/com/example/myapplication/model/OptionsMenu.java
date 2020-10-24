package com.example.myapplication.model;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

//settings(options) activity
public class OptionsMenu extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private int boardrows;
    private int boardcolumns;
    private int nmbofzombies;
    private ArrayList<Integer> rows; // = {4,5,6};
    private ArrayList<Integer> columns;// = {6,10,15};
    private ArrayList<Integer> minesnmb; // = {6,10,15,20};



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options_menu);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        final Intent result = new Intent();
        minesnmb = new ArrayList<>(); //create an arraylist to store the selection of mines
        columns = new ArrayList<>();
        rows = new ArrayList<>();

        boardsizespinner();
        zombiesspinner();


        Button back = (Button)findViewById(R.id.optionsback);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(boardcolumns != getIntent().getIntExtra("columns",0) && boardrows != getIntent().getIntExtra("rows",0)){
                    result.putExtra("columns",boardcolumns);
                    result.putExtra("rows",boardrows);
                }
                if(nmbofzombies != getIntent().getIntExtra("nmbzombies", 0)){
                    result.putExtra("zombies", nmbofzombies);
                }
                if(boardcolumns != getIntent().getIntExtra("columns",0) || nmbofzombies != getIntent().getIntExtra("nmbzombies", 0) ){ //if no settings are changed
                    setResult(RESULT_OK, result);
                    finish();
                }
                else{
                    setResult(RESULT_CANCELED, result);
                    finish();
                }

            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        //String text = parent.getItemAtPosition(position).toString();

        if (parent.getId() == R.id.boardsizespinner){
            if(position != 0) { //if it is a different option than the one selected
                boardcolumns = columns.get(position);
                boardrows = rows.get(position);
            }
        }
        else if(parent.getId() == R.id.minespinner){
            if(position != 0) {
                nmbofzombies = minesnmb.get(position);
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    //adds different number of zombies to the spinner
    public void zombiesspinner() {
        List<String> nmbzombies = new ArrayList<>();

        int selected = getIntent().getIntExtra("nmbzombies", 0);
        minesnmb.add(0, selected);

        if (selected != 6){
            minesnmb.add(6);
        }
        if (selected != 10){
            minesnmb.add(10);
        }
        if (selected != 15){
            minesnmb.add(15);
        }
        if (selected != 20){
            minesnmb.add(20);
        }

        for(int x : minesnmb){
            nmbzombies.add(x + "");
        }


        Spinner mines = findViewById(R.id.minespinner);
        ArrayAdapter<String> mine = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, nmbzombies);
        mine.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mines.setAdapter(mine);
        mines.setOnItemSelectedListener(this);
    }

    //sets a selections of board sizes to the spinner
    public void boardsizespinner(){
        List<String> boardsizelist = new ArrayList<>();

        int row = getIntent().getIntExtra("rows",0);
        int column = getIntent().getIntExtra("columns",0);
        rows.add(0,row);
        columns.add(0,column);

        if(row != 4 || column!= 6 ){
            rows.add(4);
            columns.add(6);
        }
        if(row != 5 || column!= 10 ){
            rows.add(5);
            columns.add(10);
        }
        if(row != 6 || column!= 15 ){
            rows.add(6);
            columns.add(15);
        }

        for(int x = 0; x < 3; x++){
            boardsizelist.add(rows.get(x) +" rows by " + columns.get(x));
        }

        Spinner boardsize = findViewById(R.id.boardsizespinner);
        ArrayAdapter<String> board = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, boardsizelist);
        board.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        boardsize.setAdapter(board);
        boardsize.setOnItemSelectedListener(this);

    }
}