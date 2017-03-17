package com.example.kumail.game;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Created by Kumail on 13.3.2017.
 */
public class Snake {

    private int snakeNum = -1; //Snake Number
    private int headPos = -1; //Start of snake
    private int tailPos = -1; // End of Snake
    Context context;     // Context allows us to access the android system resources and will allow us to get the picture of the snake
    Bitmap snakeChar;   // Snake picture

    //Constructor
    public Snake(int num, int head, int tail, Context current){
        context = current;
        snakeNum = num;
        headPos = head;
        tailPos = tail;
        setSnake(); // setSnake method is called in constructor
    }

    public int getHead(){
        return headPos;
    }

    public int getTail(){
        return tailPos;
    }

    //setSnake method gets the picture from the Resources file of android and sets the right picture of the snake according to where the snake points
    public void setSnake(){
        if((headPos >= 26 && headPos < 30) || (headPos >= 70 && headPos < 76)){
            snakeChar = BitmapFactory.decodeResource(context.getResources(),R.drawable.snake1);
        }
        else if((headPos >= 30 && headPos < 40) || (headPos >= 76 && headPos < 80)){
            snakeChar = BitmapFactory.decodeResource(context.getResources(),R.drawable.snake2);
        }
        else
            snakeChar = BitmapFactory.decodeResource(context.getResources(),R.drawable.snake3);
    }

    public Bitmap getSnake(){
        return snakeChar;
    }
}
