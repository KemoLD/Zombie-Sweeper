package com.example.myapplication.logic;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;

public class Cell {

    private boolean isCLicked;
    private boolean isScanned;
    private boolean isRevealed;
    private boolean isZombie;

    private Button button;

    public Cell(Button Jbutton) {
        button = Jbutton;
        isCLicked = false;
        isScanned = false;
        isRevealed = false;
        isZombie= false;
    }

    public Button getButton() {
        return button;
    }

    public boolean isCLicked() {
        return isCLicked;
    }

    public boolean isScanned() {
        return isScanned;
    }

    public boolean isRevealed() {
        return isRevealed;
    }

    public boolean isZombie() {
        return isZombie;
    }
}

    /*

    //set up individual buttons as well as a few functions: scan() and reveal()



    public boolean isRevealed;
    public boolean isZombie;
    public boolean isScanned;

    public Cell(Context context, AttributeSet attrs) {
        super(context, attrs);

        this.isRevealed = false;

        //if cell should have a zombie - use Random rand();
        /*if (...) {
            this.isZombie = true;
        }
        else{
            this.isZombie = false;
        }


        this.isScanned = false;
    }


    revealCell(row, col){
        reveal cell;
        numberOfZombies ++;
        for (all scanned cells in row, col){
            numHiddenCells --;
        }
    }
    */


