package com.example.kumail.game;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

import java.util.ArrayList;

/**
 * Created by Kumail on 11.3.2017.
 */
public class Board extends View {
    public final int NUM_BLOCKS = 100;
    public final int SNAKE_LIST[] = {82,27,28,14,71,48,76,17,77,52,99,6,38,2};
    public final int LADDER_LIST[] = {25,17,73,67,83,79};
    public final int LADDER_NUM = 3;
    public final int SNAKE_NUM = 3;
    ArrayList<Block> blocks= new ArrayList<Block>();
    private Paint paint, paintFont;
    private int width, totWidth;
    private int blockVal = NUM_BLOCKS;
    private Player player1, player2;
    ArrayList<Snake> snakes = new ArrayList<Snake>();
    ArrayList<Ladder> ladders = new ArrayList<Ladder>();


    public Board(Context context, Player play1, Player play2, int type){

        super(context);

        paint = new Paint();
        paintFont = new Paint();
        player1 = play1;
        player2 = play2;

        //Initialising Snakes
        for (int i = 0; i < SNAKE_LIST.length; i+=2){

            snakes.add( new Snake(i, SNAKE_LIST[i], SNAKE_LIST[i+1],context));
        }
        //Initializing Ladders
        for (int i= 0; i < LADDER_LIST.length; i+=2){
            ladders.add(new Ladder(i, LADDER_LIST[i], LADDER_LIST[i+1],context));
        }


        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        width = display.getWidth();
        totWidth = width;
        width = width/12;
        int height = getHeight();
        for(int i = 0; i < 10; i++) {
            if( i %2 == 0){
                for(int j = 0; j < 10; j++) {

                    int left = j * (width + 15);
                    int top = i *(width + 10);
                    int right = left + width;
                    int bottom = top + width;

                    blocks.add( new Block(new Rect(left,top,right,bottom), blockVal));
                    blockVal--;
                }
            }
            else {
                for(int j = 9; j >= 0; j--) {

                    int left = j * (width + 15);
                    int top = i *(width + 10);
                    int right = left + width;
                    int bottom = top + width;

                    blocks.add( new Block(new Rect(left,top,right,bottom), blockVal));
                    blockVal--;
                }

            }
        }


        paint.setColor(Color.parseColor("#009688"));
        paintFont.setColor(Color.parseColor("#ffffff"));

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Bitmap background = BitmapFactory.decodeResource(getResources(),R.drawable.background);
        Bitmap player1Char = player1.getCharacter();
        Bitmap player2Char = player2.getCharacter();
        background = getResizedBitmap(background,width, width);
        canvas.drawBitmap(Bitmap.createScaledBitmap(background,totWidth, totWidth,false),0,0,null);
        for( int i = 0; i < 100; i++){

            canvas.drawRect(blocks.get(i).getCoordinates(), paint);
            canvas.drawText(((blocks.get(i).getValue()+ "")), blocks.get(i).getCoordinates().centerX(), blocks.get(i).getCoordinates().centerY(),paintFont);

        }

        int head;
        int tail;
        int x1,x2;
        int y1,y2;
        double snakeLength;
        int snakeWidth, snakeHeight;
        double angle;
        double factors[] = setSnakes(99,2);

        for  ( int i = 0 ; i < SNAKE_NUM; i++) {
            head = snakes.get(i).getHead();
            tail = snakes.get(i).getTail();

            Log.d("SnakeCheck", "i Snake: "+ "Head = " + head + " Tail = " + tail);

            x1 = blocks.get(100 - head).getCoordinates().centerX();
            x2 = blocks.get(100 - tail).getCoordinates().centerX();
            y1 = blocks.get(100 - head).getCoordinates().centerY();
            y2 = blocks.get(100 - tail).getCoordinates().centerY();

            snakeWidth = x1 - x2;
            snakeHeight = y1 - y2;
            snakeLength = Math.sqrt((snakeHeight * snakeHeight) + (snakeWidth * snakeWidth));

            angle = Math.toDegrees(Math.atan((double) (y2 - y1) / (x2 - x1)));
            Log.d("angle", angle + "");

            int m1 = blocks.get(100 - head).getCoordinates().centerX();
            int n1 = blocks.get(100 - head).getCoordinates().centerY();
            Log.d("checkxy", "x = " + m1 + " " + "y1 = " + n1);
            m1 = (int) (x1 * Math.cos(Math.toRadians(angle)) - y1 * Math.sin(Math.toRadians(-angle)));
            n1 = (int) (x1 * Math.cos(Math.toRadians(angle)) + y1 * Math.sin(Math.toRadians(-angle)));
            Log.d("checkNEWxy", "x = " + x1 + " " + "y1 = " + n1);


            m1 = (int) (Math.sin(Math.toRadians(angle)) * snakeLength / 2);

            Matrix transform = new Matrix();
            transform.setTranslate((x1) - 3*width/4, (y1));
            transform.preRotate(-(float) angle, width / 2, width / 2);
            canvas.drawBitmap(Bitmap.createScaledBitmap(snakes.get(i).getSnake(), (width), (int) snakeLength, false), transform, null);

        }

        for  ( int i = 0 ; i < LADDER_NUM; i++) {
            head = ladders.get(i).getHead();
            tail = ladders.get(i).getTail();

            Log.d("LadderCheck", "i Ladder: "+ "Head = " + head + " Tail = " + tail);

            x1 = blocks.get(100 - head).getCoordinates().centerX();
            x2 = blocks.get(100 - tail).getCoordinates().centerX();
            y1 = blocks.get(100 - head).getCoordinates().centerY();
            y2 = blocks.get(100 - tail).getCoordinates().centerY();

            snakeWidth = x1 - x2;
            snakeHeight = y1 - y2;
            snakeLength = Math.sqrt((snakeHeight * snakeHeight) + (snakeWidth * snakeWidth));

            angle = Math.toDegrees(Math.atan((double) (y2 - y1) / (x2 - x1)));
            Log.d("angle", angle + "");

            int m1 = blocks.get(100 - head).getCoordinates().centerX();
            int n1 = blocks.get(100 - head).getCoordinates().centerY();
            Log.d("checkxy", "x = " + m1 + " " + "y1 = " + n1);
            m1 = (int) (x1 * Math.cos(Math.toRadians(angle)) - y1 * Math.sin(Math.toRadians(-angle)));
            n1 = (int) (x1 * Math.cos(Math.toRadians(angle)) + y1 * Math.sin(Math.toRadians(-angle)));
            Log.d("checkNEWxy", "x = " + x1 + " " + "y1 = " + n1);


            m1 = (int) (Math.sin(Math.toRadians(angle)) * snakeLength / 2);

            Matrix transform = new Matrix();
            transform.setTranslate((x1) - 3*width/4, (y1));
            transform.preRotate(-(float) angle, width / 2, width / 2);
            canvas.drawBitmap(Bitmap.createScaledBitmap(ladders.get(i).getLadder(), (width), (int) snakeLength, false), transform, null);

        }

        //canvas.drawBitmap( Bitmap.createScaledBitmap(snakes.get(1).getSnake(), (width), (int)(factors[2]),false),(blocks.get(100 - head).getCoordinates().centerX()), (blocks.get(100 -head).getCoordinates().centerY()), null);


        //Create Player1
        canvas.drawBitmap(Bitmap.createScaledBitmap(player1Char,width,width, false),(blocks.get( 100 -player1.getBlock()).getCoordinates().centerX()-width/2),(blocks.get( 100 -player1.getBlock()).getCoordinates().centerY() - width/2), null);
        //Create Player2
        canvas.drawBitmap(Bitmap.createScaledBitmap(player2Char,width,width, false),(blocks.get( 100 -player2.getBlock()).getCoordinates().centerX()-width/2),(blocks.get( 100 -player2.getBlock()).getCoordinates().centerY() - width/2), null);

    }

    public Player getPlayer1(){
        return player1;
    }

    public Player getPlayer2(){
        return player2;
    }

    public Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);

        // "RECREATE" THE NEW BITMAP
        Bitmap resizedBitmap = Bitmap.createBitmap(
                bm, 0, 0, width, height, matrix, false);
        bm.recycle();
        return resizedBitmap;
    }

    public double [] setSnakes( int head, int tail){

        int x1,x2,y1,y2 ;


        double snakeLength;
        int snakeWidth, snakeHeight;
        double angle;
        //
        x1 = blocks.get(100 -head).getCoordinates().centerX();
        x2 = blocks.get(100-tail).getCoordinates().centerX();
        y1 = blocks.get(100-head).getCoordinates().centerY();
        y2 = blocks.get(100-tail).getCoordinates().centerY();

        snakeWidth = x1 - x2;
        snakeHeight = y1 - y2;
        snakeLength = Math.sqrt( (snakeHeight*snakeHeight) + (snakeWidth*snakeWidth) );

        angle = Math.toDegrees(Math.atan((double)(y2-y1)/(x2-x1)));
        Log.d("angle", angle +"");

        int m1 = blocks.get(100 - head).getCoordinates().centerX();
        int n1 = blocks.get(100 - head).getCoordinates().centerY();
        Log.d("checkxy", "x = " + m1 + " "+ "y1 = " + n1);
        m1 = (int)(x1*Math.cos(Math.toRadians(angle)) - y1*Math.sin(Math.toRadians(-angle)));
        n1 = (int)(x1*Math.cos(Math.toRadians(angle)) + y1*Math.sin(Math.toRadians(-angle)));
        Log.d("checkNEWxy", "x = " + x1 + " "+ "y1 = " + n1);
        double factors [] = {m1,n1,snakeLength, angle};

        return factors ;

    }
}
