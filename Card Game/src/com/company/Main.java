package com.company;

import java.util.Scanner;

/**
 * Created by icyhot on 12/20/16.
 * v1.0 basic Deck and Card classes
 * v1.1 Deck now creates ArrayList of Card objects
 * v1.2 Added Deck method shuffle()
 * v1.3 slapping works. loseOneCard() when slapped incorrectly
 * v1.4 a isDouble and isSandwich now work when slapped, using gameDeckToHand
 * v1.5
 */
public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Welcome to the card game Egyptian Ratscrew!");
        Player me = new Player("John");
        Player comp = new Player("Computer");
        Deck myDeck = new Deck();
        Deck compDeck = new Deck();
        Deck gameDeck = new Deck();
        gameDeck.create();
        gameDeck.shuffle();
        gameDeck.split(myDeck, compDeck);
        gameDeck.wipe();
        Game theGame = new Game(me, comp, myDeck, compDeck, gameDeck);
        System.out.println("Time to start!");
        theGame.start();


//        for(int i = 0; i <myDeck.getSize(); i++){
//            System.out.print(myDeck.getCardName(i) + "\n");
//        }
//        System.out.println("\n\n");
//        for(int i = 0; i <compDeck.getSize(); i++){
//            System.out.print(compDeck.getCardName(i) + "\n");
//        }
    }
}
