package com.example.kumail.game;

import android.content.Context;
import android.graphics.Canvas;
import android.media.Image;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class GameManager extends AppCompatActivity {

    RelativeLayout layout; // XML Layout which lays the board and the game panel
    Button btnDice; //Android Button
    ImageButton btnReplay;
    Board gameBoard;    //Instance of Board that will be displayed
    Dice dice;  // Instance of Dice object
    int roll = 0;
    boolean turn = false; // Boolean variable which indicates whose turn it is to move
    Player player1, player2; //Playes intialised
    int type = 1; // Type of game where 1 is single player with computer and 2 is multiplayer vs another person

    @Override
    // This is like the constructor of the class and is called when the Activity is started
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        // Initialises the dice, players and game board according to the parameters passed from the previous Activity where user defines his character properties
        dice = new Dice();
        player1 = new Player("Kumail", true, this);
        player2 = new Player("Hassan", false, this);

        // Toast allows system to display message to user and is an Android feature
        //final Toast toast1 = Toast.makeText(this, "Computer's Turn", Toast.LENGTH_SHORT);



        // Game Board is intialised
        gameBoard = new Board(this, player1, player2, 1);

        // Layout is set
        layout = (RelativeLayout) View.inflate(this, R.layout.panel, null);
        layout.addView( gameBoard);
        setContentView(layout);

        btnDice = (Button)findViewById(R.id.btnDice);
        btnReplay = (ImageButton)findViewById(R.id.replay);


        //OnClickListener of the button is defined in the onCreate method which acts as a constructor fot this class
        btnDice.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // On the click of the dice button a random number from 1 to 6 is returned by the dice objec tand the system checks which player to move
                if (type == 2){
                    roll = dice.getNum();
                    turn = !turn;
                    if (turn){
                        gameBoard.getPlayer1().updatePos(roll);
                        Log.d("diceNum", roll +"");
                    }
                    else{
                        gameBoard.getPlayer2().updatePos(roll);
                    }

                    checkPosition();
                }

                else{

                    //if(turn) {

                        //roll = 1;
                        roll = dice.getNum();
                        Context context = getApplicationContext();
                        CharSequence text = "You Got " + roll;
                        int duration = Toast.LENGTH_SHORT;
                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();

                        if (roll == 6) {
                            roll = roll + dice.getNum();
                            text = "You now Got " + roll;
                            toast = Toast.makeText(context, text, duration);
                            toast.show();
                        }

                        turn = !turn;

                        gameBoard.getPlayer1().updatePos(roll);
                        Log.d("diceNum", roll + "");
                        checkPosition();
                        checkBlock();
                        if (checkWinner() == 0)
                            return;
                        btnDice.setEnabled(false);
                        gameBoard.invalidate();
                    //}
                    //toast1.show();  //to show computer's turn
                    Runnable r = new Runnable() {
                        @Override
                        public void run(){
                            //if(!turn) {
                                roll = dice.getNum();
                                //roll = 1;
                                Context context = getApplicationContext();
                                CharSequence text = "Computer Got " + roll;
                                int duration = Toast.LENGTH_SHORT;
                                Toast toast = Toast.makeText(context, text, duration);
                                toast.show();

                                if (roll == 6) {
                                    roll = roll + dice.getNum();
                                    text = "Computer now Got " + roll;
                                    toast = Toast.makeText(context, text, duration);
                                    toast.show();
                                }
                                turn = !turn;

                                gameBoard.getPlayer2().updatePos(roll);
                                checkPosition();
                                checkBlock();
                                if (checkWinner() == 0)
                                    return;
                                gameBoard.invalidate();
                                btnDice.setEnabled(true);
                            }
                       // }
                    };

                    Handler h = new Handler();
                    h.postDelayed(r, 2000);

                }



            }
        });
        btnReplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recreate();
            }
        });

    }

    // This method checks the position fo the players and updates them if necessary
    public void checkPosition(){
        if (gameBoard.getPlayer1().getBlock() == gameBoard.getPlayer2().getBlock()){
            if(turn){
                gameBoard.getPlayer2().updatePos(-3);
            }
            else{
                gameBoard.getPlayer1().updatePos(-3);
            }
        }
    }

    /**
     * 
     *
     * Method: public void checkBlock ----> Checks if there is an object on the block or if there is a Snake or a Ladder on the blokc that the player has just landed on
     * Method: public void pickItem ------> Updates the player's item inventory if the player has landed on a block with an item
     * Method: public void useItem ------> Empties the player's item inventory when the player uses the item.
     * Method: public void checkWinner ----> Checks if there is a winner of the game yet and is called wheneve the player's final position is set after the dice is rolled
     *
     */

    public int checkWinner(){
        if(gameBoard.getPlayer1().getBlock() == 100){
            TextView x = (TextView)findViewById(R.id.player_win);
            x.setVisibility(View.VISIBLE);
            findViewById(R.id.btnDice).setVisibility(View.INVISIBLE);
            findViewById(R.id.replay).setVisibility(View.VISIBLE);
            findViewById(R.id.replay).setEnabled(true);
            return 0;
            //setContentView(R.layout.activity_main);

        }
        else if(gameBoard.getPlayer2().getBlock() == 100){
            TextView x = (TextView)findViewById(R.id.player_win);
            x.setText("Jeez! Computer Won!");
            x.setVisibility(View.VISIBLE);
            findViewById(R.id.btnDice).setVisibility(View.INVISIBLE);
            findViewById(R.id.replay).setVisibility(View.VISIBLE);
            findViewById(R.id.replay).setEnabled(true);
            return 0;
        }
        return 1;
    }

    public void checkBlock(){

        //for player 1
        int blockVal = gameBoard.getPlayer1().getBlock();
        int pos = -1;

        for (int i = 0; i < 2*(gameBoard.SNAKE_NUM); i+=2) {
            if (gameBoard.SNAKE_LIST[i] == blockVal) {
                pos = i;
                break;
            }
        }
        if(pos != -1){
            int newPosition = gameBoard.SNAKE_LIST[pos+1];
            gameBoard.getPlayer1().setPos(newPosition);
        }

        //ladder

        for (int i = 1; i < 2*(gameBoard.LADDER_NUM); i+=2) {
            if (gameBoard.LADDER_LIST[i] == blockVal) {
                pos = i;
                break;
            }
        }
        if(pos != -1){
            int newPosition = gameBoard.LADDER_LIST[pos-1];
            gameBoard.getPlayer1().setPos(newPosition);
        }


        //for player 2
        blockVal = gameBoard.getPlayer2().getBlock();
        pos = -1;
        //snakes
        for (int i = 0; i < gameBoard.SNAKE_NUM; i+=2) {
            if (gameBoard.SNAKE_LIST[i] == blockVal) {
                pos = i;
                break;
            }
        }
        if(pos != -1){
            int newPosition = gameBoard.SNAKE_LIST[pos+1];
            gameBoard.getPlayer2().setPos(newPosition);
        }

        //ladder
        for (int i = 1; i < 2*(gameBoard.LADDER_NUM); i+=2) {
            if (gameBoard.LADDER_LIST[i] == blockVal) {
                pos = i;
                break;
            }
        }
        if(pos != -1){
            int newPosition = gameBoard.LADDER_LIST[pos-1];
            gameBoard.getPlayer2().setPos(newPosition);
        }

    }


}
