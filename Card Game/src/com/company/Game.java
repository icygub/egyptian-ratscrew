package com.company;

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
    private Scanner sc = new Scanner(System.in);

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
        int coinRoll = (int)(Math.random()*2);
        boolean continueMove;
        if(coinRoll == 0 || coinRoll == 1){
            System.out.println(me.getName() + " goes first.");
            do{
                gameDeck.printDeck();
                continueMove = myMove();
            }while(continueMove);
        }
        else
            System.out.println(comp.getName() + " goes first.");


    }

    public boolean myMove(){
        int howManyMoreCards = 0;
        boolean continueMove;

        if(gameDeck.topCardIsJAKQ()){ //if top is JAQK
            howManyMoreCards += gameDeck.howManyMoreCards(); //then they play more cards, depending on special card
            System.out.println(gameDeck.getTopCard().getValue() + " card is on top. You need to play maximum of " + howManyMoreCards + " card(s)!");
            for(int i = 0; i < howManyMoreCards; i++){
                continueMove = processMove(getInput());
                if(!continueMove) //don't continue when they successfully slap a sandwich or double
                    return false;
                else if (gameDeck.topCardIsJAKQ()) { //if said played card is special, then they end their turn
                    System.out.println("You just saved yourself by playing a JAQK!");
                    return false; //ends player turn
                }
            }
        }
        else{
            processMove(getInput());
        }
        return false;
    }

    public String getInput(){
        System.out.println(":Choose: ");
        return sc.nextLine();
    }

    public boolean processMove(String enter){

        if(enter.equals("")) { //if they hit enter
            myDeck.playCard(gameDeck); //just play one card normally
            System.out.println("You played " + gameDeck.getTopCard());
            return true; //though this return doesn't do anything
        }
        else if(enter.equalsIgnoreCase("S")){ //if they hit "S" (slap) instead of enter
            if(!gameDeck.isDoubleSandwich()){ //if they slap, but there is no double or sandwich
                System.out.println("You lost a card. There was no double/sandwich");
                loseOneCard(myDeck, gameDeck); //they lose a card
                //true
            }
            else if(gameDeck.isDouble()){ //if they slap, and there IS a double
                System.out.println("Double!");
                gameDeckToHand(gameDeck, myDeck); //all cards in gameDeck go to bottom of myDeck
                return false;
            }
            else if(gameDeck.isSandwich()){ //if they slap, and there IS a sandwich
                System.out.println("Sandwich!");
                gameDeckToHand(gameDeck, myDeck); //all cards in gameDeck go to bottom of myDeck
                return false;
            }
        }
        else if(enter.equalsIgnoreCase("P")){ //if they hit "P" instead of both
            System.out.println("*****My Deck*****");
            for(int i = 0; i <myDeck.getSize(); i++){ //this will print out myDeck
                System.out.print(myDeck.getCard(i).getValue());
                System.out.println(" " + myDeck.getCard(i).getSuit());
            }
            System.out.println("*****End My Deck*****\n");
            return false;
        }
        else{ //if they don't type any of the above
            System.out.println("What was that? Try again.");
            return false;
        }

        return true;
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
