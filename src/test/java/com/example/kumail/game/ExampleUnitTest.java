package com.example.kumail.game;

import android.content.Context;
import android.test.mock.MockContext;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }
    @Test
    public void blockCheck() throws Exception {
        Player testPlayer = new Player("Kumail",true, new MockContext());
        assertEquals(1, testPlayer.getBlock());
        testPlayer.updatePos(2);
        assertEquals(3,testPlayer.getBlock());
    }
}