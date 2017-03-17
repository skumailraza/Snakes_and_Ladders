package com.example.kumail.game;

import java.util.Random;

/**
 * Created by Kumail on 12.3.2017.
 */
public class Dice {
    int num;
    Random rand = new Random();

    //Very simple Constructor, simply sets the 'num' attribute that this class has, to 0.
    public Dice(){
        num = 0;
    }

    // Returns a random number between 1 and 6, like a dice
    public int getNum(){
        int rolled = 0;
        for (int i = 0; i < rand.nextInt(rand.nextInt(rand.nextInt(5) + 1) +1) + 1; i++)
            rolled = rand.nextInt(6) + 1;
        return rolled;
    }

}
