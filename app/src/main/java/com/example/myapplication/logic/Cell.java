package com.example.myapplication.logic;

import android.widget.Button;

public class Cell {

    private boolean isClicked;
    private boolean isScanned;
    private boolean isRevealed;
    private boolean isZombie;
    private int number;

    private Button button;

    public Cell(Button Jbutton, int Jnumber) {
        button = Jbutton;
        isClicked = false;
        isScanned = false;
        isRevealed = false;
        isZombie= false;
        number = Jnumber;
    }

    public Button getButton() {
        return button;
    }

    public boolean isClicked() {
        return isClicked;
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

    public void setClicked(boolean clicked) {
        isClicked = clicked;
    }

    public void setScanned(boolean scanned) {
        isScanned = scanned;
    }

    public void setRevealed(boolean revealed) {
        isRevealed = revealed;
    }

    public void setZombie(boolean zombie) {
        isZombie = zombie;
    }

    public void setButton(Button button) {
        this.button = button;
    }

    public int getNumber() {
        return number;
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


