package com.example.myapplication.logic;

//import ...

public class Board {
    //set up grid of buttons (from cell class) as well as a few functions: scan(int row, int col) = #bombs in corresponding row and col

    private int numZombiesFound;
    private int numScansUsed;

    public int numNeighbourZombies;

    /*
    int scan(buttons[row][col]);{

        numNeighbourZombies = 0;

        for (cell in buttons[row]){
            if ((cell.isZombie == true) && (cell.isRevealed == false)){
                numNeighbourZombies ++;
            }
        }

        return numNeighbourZombies;
    }
    */

}
