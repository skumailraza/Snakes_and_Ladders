package com.example.kumail.game;

import android.graphics.Rect;

/**
 * Created by Kumail on 11.3.2017.
 */
public class Block {
    private Rect coordinates; // Rect is a shape in Java that is drawn and holds the x, and y coordinate of each block
    private boolean player; // Boolean value which indicates if there is a player on the block or not
    private int value;  // Value of the block

    public Block(Rect drawing, int val){
        coordinates = drawing;
        value = val;
        player = false;
    }

    public Rect getCoordinates(){
        return coordinates;
    }

    public int getValue(){
        return  value;
    }

    public boolean getPlayer(){
        return player;
    }

    public void setPlayer( boolean newVal){
        player = true;
    }

}
