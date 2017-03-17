package com.example.kumail.game;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Created by skrk on 3/16/17.
 */

public class Ladder {
    private int ladderNum = -1; //Snake Number
    private int headPos = -1; //Start of snake
    private int tailPos = -1; // End of Snake
    Context context;     // Context allows us to access the android system resources and will allow us to get the picture of the snake
    Bitmap ladderChar;   // Snake picture

    //Constructor
    public Ladder(int num, int head, int tail, Context current){
        context = current;
        ladderNum = num;
        headPos = head;
        tailPos = tail;
        setLadder(); // setSnake method is called in constructor
    }

    public int getHead(){
        return headPos;
    }

    public int getTail(){
        return tailPos;
    }

    //setSnake method gets the picture from the Resources file of android and sets the right picture of the snake according to where the snake points
    public void setLadder(){
        if((headPos >= 26 && headPos < 30) || (headPos >= 70 && headPos < 76)){
            ladderChar = BitmapFactory.decodeResource(context.getResources(),R.drawable.ladder1);
        }
        else if((headPos >= 30 && headPos < 40) || (headPos >= 76 && headPos < 80)){
            ladderChar = BitmapFactory.decodeResource(context.getResources(),R.drawable.ladder1);
        }
        else
            ladderChar = BitmapFactory.decodeResource(context.getResources(),R.drawable.ladder1);
    }

    public Bitmap getLadder(){
        return ladderChar;
    }
}
