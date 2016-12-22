package com.company;

import java.util.Collections;
import java.util.Scanner;

/**
 * Created by icyhot on 12/20/16.
 */
public class Game{
    private Player me;
    private Player comp;
    private Deck myDeck;
    private Deck compDeck;
    private Deck gameDeck;
    Scanner sc = new Scanner(System.in);

    public Game(Player me, Player comp, Deck myDeck, Deck compDeck, Deck gameDeck){
        this.me = me;
        this.comp = comp;
        this.myDeck = myDeck;
        this.compDeck = compDeck;
        this.gameDeck = gameDeck;
    }

    public void start(){
        myDeck.shuffle();
        compDeck.shuffle();
        gameDeck.wipe();
        System.out.println("You go first!");
        for(int i = 0; i <myDeck.getSize(); i++){
            System.out.print(myDeck.getCard(i).getValue());
            System.out.println(" " + myDeck.getCard(i).getSuit());
        }
        meGo();
        //compGo();
    }

    public void meGo(){
        String enter;

        while(true){
            System.out.println("---Game Deck---");
            for(int i = 0; i <gameDeck.getSize(); i++){
                System.out.print(gameDeck.getCard(i).getValue());
                System.out.println(" " + gameDeck.getCard(i).getSuit());
            }
            System.out.println("----End Game Deck----\n");
            System.out.println(":Choose: ");
            enter = sc.nextLine();
            if(enter.equals("")){
                gameDeck.add(myDeck.getCard(myDeck.getSize()-1));
                myDeck.remove(myDeck.getSize()-1);
                System.out.println("You played a card.");
                //break;
            }
            else if(enter.equalsIgnoreCase("S")){
                if(!gameDeck.isDoubleSandwich()){
                    System.out.println("You lost a card. There was no double/sandwich");
                    loseOneCard(myDeck, gameDeck);
                    //break;
                }
                else if(gameDeck.isDouble()){
                    System.out.println("Double!");
                    gameDeckToHand(gameDeck, myDeck);
                    //break;
                }
                else if(gameDeck.isSandwich()){
                    System.out.println("Sandwich!");
                    gameDeckToHand(gameDeck, myDeck);
                    //break;
                }
            }
            else if(enter.equalsIgnoreCase("P")){
                System.out.println("*****My Deck*****");
                for(int i = 0; i <myDeck.getSize(); i++){
                    System.out.print(myDeck.getCard(i).getValue());
                    System.out.println(" " + myDeck.getCard(i).getSuit());
                }
                System.out.println("*****End My Deck*****\n");
            }
            else{
                System.out.println("What was that? Try again.");
            }
        }


    }

    public void gameDeckToHand(Deck source, Deck destination){ //gameDeck to myDeck
        for(int i = source.getSize()-1; i >= 0; i--){ //moves all field cards to player's hand
            destination.add(source.getCard(i)); //places players top card to top of gameDeck
            for(int x = 0; x < destination.getSize(); x++){ //sorts gameDeck, moving top card to the bottom
                destination.swap(x, destination.getSize()-1); //will always be 0
            }
            source.remove(i);
        }
        gameDeck.wipe();
    }

    public void loseOneCard(Deck source, Deck destination){ //myDeck to gameDeck
        destination.add(source.getCard(source.getSize()-1));
        for(int i = 0; i < destination.getSize(); i++){ //needs to be i > 0
            destination.swap(i,destination.getSize()-1); //will always be 0
        }
        source.remove(source.getSize()-1);

        for(int i = 0; i <source.getSize(); i++){ //printing to see what the result is
            System.out.print(source.getCard(i).getValue());
            System.out.println(" " + source.getCard(i).getSuit());
        }
    }

    public void compGo(){
        do{

            gameDeck.add(compDeck.getCard(compDeck.getSize()-1));
        }while(true);
    }

    public Player getMe() {
        return me;
    }

    public Player getComp() {
        return comp;
    }
}
