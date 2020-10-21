package com.example.myapplication.logic;

//import ...

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;

public class Cell extends Button {
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
        */

        this.isScanned = false;
    }

    /*
    revealCell(row, col){
        reveal cell;
        numberOfZombies ++;
        for (all scanned cells in row, col){
            numHiddenCells --;
        }
    }
    */


}
