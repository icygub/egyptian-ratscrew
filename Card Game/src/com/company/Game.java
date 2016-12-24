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

    public void printScore(){
        System.out.println(me.getName() + "\'s score: " + myDeck.getSize() + " || " + comp.getName() + "\'s score: " + compDeck.getSize() + "\n");
    }

    public void start(){
        int diceRoll = (int)(Math.random()*2);
        if(diceRoll == 0){
            System.out.println(me.getName() + " goes first.\n");
            while(true){
                System.out.println("----------" + me.getName() + "---------");
                move(0, myDeck, compDeck, me.getName(), comp.getName());
                System.out.println("----------" + comp.getName() + "---------");
                move(1, compDeck, myDeck, comp.getName(), me.getName());
            }
        }
        else{
            System.out.println(comp.getName() + " goes first.\n");
            while(true){
                System.out.println("----------" + comp.getName() + "---------");
                move(1, compDeck, myDeck, comp.getName(), me.getName());
                System.out.println("----------" + me.getName() + "---------");
                move(0, myDeck, compDeck, me.getName(), comp.getName());
            }
        }

    }

    public void move(int whoIsPlaying, Deck curDeck, Deck enemyDeck, String curName, String enemyName){
        int howManyMoreCards = 0;
        String input;
        int moveResult;

        if(gameDeck.topCardIsJAKQ()){ //if top is JAQK
            howManyMoreCards += gameDeck.howManyMoreCards(); //then they play more cards, depending on special card
            System.out.println("***"+ gameDeck.getTopCardName() + " is on top! " + curName + " has " + howManyMoreCards + " chance(s) to fight back!***");
            for(int i = 0; i < howManyMoreCards; i++){
                if(whoIsPlaying == 0)
                    input = getValidInput();
                else
                    input = getCompInput();

                moveResult = processMove(input, curDeck, curName);
                if( moveResult == 2 ) //when return value is false
                    break; //when they successfully slap a sandwich or double
                else if ( moveResult == 1)
                    howManyMoreCards++;
                else if (gameDeck.topCardIsJAKQ()) { //return value of 0. if said played card is special, then they end their turn
                    System.out.println("\t" + curName + "played the " + gameDeck.getTopCardName() + ". Nice save!");
                    break; //ends player turn
                }
                else if(i+1 == howManyMoreCards){ //if they didn't play special card, and they ran out of cards they were allowed to play
                    System.out.println("\t" + curName + " didn\'t play anything good, so " + enemyName + " gets the cards");
                    gameDeck.toHand(enemyDeck);
                }
            }
        }
        else{
            howManyMoreCards = 1;
            for(int i = 0; i < howManyMoreCards; i++){ //loop is mainly for when player incorrectly slaps, because they need to go again
                if(whoIsPlaying == 0)
                    input = getValidInput();
                else
                    input = getCompInput();
                moveResult = processMove(input, curDeck, curName); //no special card on gameDeck, so player can just play a single card and end turn
                if ( moveResult == 1) //when incorrectly slap for sandwich/double
                    howManyMoreCards++;
            }
        }

        //gameDeck.printDeck();
    }


    public int processMove(String enter, Deck curDeck, String curName){ //this method can only let you play one card per method call

        if(enter.equals("")) { //if they hit enter key
            curDeck.playCard(gameDeck); //just play one card normally
            System.out.println("\t" + curName + " played " + gameDeck.getTopCardName());
            return 0; //0 true. This return value doesn't do anything at all, but it returns 0 since it has to return at least something
        }
        else{ //if they hit "S" (slap)
            if(gameDeck.isDouble()){ //if they slap, and there IS a double
                System.out.println("\tDouble!");
                gameDeck.toHand(curDeck); //all cards in gameDeck go to bottom of myDeck
                return 2; //2 false
            }
            else if(gameDeck.isSandwich()){ //if they slap, and there IS a sandwich
                System.out.println("\tSandwich!");
                gameDeck.toHand(curDeck); //all cards in gameDeck go to bottom of myDeck
                return 2; //2 false
            }
            else{ //if there is no double or sandwich
                System.out.println("\t" + curName + " lost a card. There was no double/sandwich");
                loseOneCard(curDeck, gameDeck); //they lose a card
                didSomebodyLose();
                return 1; //1 true
            }

        }
    }

    public void didSomebodyLose(){
        if(myDeck.getSize() == 0){
            endGame(comp, me);
        }
        else if(compDeck.getSize() == 0){
            endGame(me, comp);
        }
    }

    public void endGame(Player winner, Player loser){
        System.out.println(winner.getName() + " wins! " + loser.getName() + " loses...");
        System.exit(0);
    }

    public String getValidInput(){
        String input;
        while(true){
            System.out.print("> ");
            input = sc.nextLine();
            didSomebodyLose();
            if( input.equalsIgnoreCase("")
                    ||  input.equalsIgnoreCase(("S")) ){
                break;
            }
            else
                System.out.println("Invalid input. Try again.");

        }
        return input;
    }

    public void loseOneCard(Deck source, Deck destination){ //curDeck to gameDeck
        destination.add(source.getCard(source.getSize()-1));
        for(int i = 0; i < destination.getSize(); i++){ //needs to be i > 0
            destination.swap(i,destination.getSize()-1); //will always be 0
        }
        source.remove(source.getSize()-1);
    }

    public String getCompInput(){
        int randInt = (int)(Math.random()*9);
        if(randInt <= 2) //Comp has a 15% chance of slapping (correctly or incorrectly)
            return "S";
        else
            return "";
    }
}

//    public void myMove(){
//        int howManyMoreCards = 0;
//        String input;
//        int moveResult;
//        if(gameDeck.topCardIsJAKQ()){ //if top is JAQK
//            howManyMoreCards += gameDeck.howManyMoreCards(); //then they play more cards, depending on special card
//            System.out.println(gameDeck.getTopCardName() + " is on top. You can play up to " + howManyMoreCards + " card(s)!");
//            for(int i = 0; i < howManyMoreCards; i++){
//                input = getValidInput();
//                moveResult = processMove(input, myDeck, me);
//                if( moveResult == 2 ) //when return value is false
//                    break; //when they successfully slap a sandwich or double
//                else if ( moveResult == 1)
//                    howManyMoreCards++;
//                else if (gameDeck.topCardIsJAKQ()) { //return value of 0. if said played card is special, then they end their turn
//                    System.out.println("You just saved yourself by playing the " + gameDeck.getTopCardName());
//                    break; //ends player turn
//                }
//                else if(i+1 == howManyMoreCards){ //if they didn't play special card, and they ran out of cards they were allowed to play
//                    System.out.println("You didn\'t play anything good, so computer gets the cards");
//                    gameDeck.toHand(compDeck);
//                }
//            }
//        }
//        else{
//            howManyMoreCards = 1;
//            for(int i = 0; i < howManyMoreCards; i++){ //loop is mainly for when player incorrectly slaps, because they need to go again
//                input = getValidInput();
//                moveResult = processMove(input, myDeck, me); //no special card on gameDeck, so player can just play a single card and end turn
//                if ( moveResult == 1) //when incorrectly slap for sandwich/double
//                    howManyMoreCards++;
//            }
//        }
//    }
//
//    public void compMove(){ //a method in progress...
//        int howManyMoreCards = 0;
//        String compInput;
//        int moveResult;
//        if(gameDeck.topCardIsJAKQ()){ //if top is JAQK
//            howManyMoreCards += gameDeck.howManyMoreCards(); //then they play more cards, depending on special card
//            System.out.println(gameDeck.getTopCardName() + " is on top. Computer can play up to " + howManyMoreCards + " card(s)!");
//            for(int i = 0; i < howManyMoreCards; i++){
//                compInput = getCompInput();
//                moveResult = processMove(compInput, compDeck, comp);
//                if( moveResult == 2)
//                    break; //when they successfully slap a sandwich or double
//                else if ( moveResult == 1)
//                    howManyMoreCards++;
//                else if (gameDeck.topCardIsJAKQ()) { //if said played card is special, then they end their turn
//                    System.out.println("Computer just saved himself by playing a " + gameDeck.getTopCardName());
//                    break; //ends computer turn
//                }
//                else if(i+1 == howManyMoreCards){ //if they didn't play special card, and they ran out of cards they were allowed to play
//                    System.out.println("Computer didn\'t play anything good, so you get the cards");
//                    gameDeck.toHand(myDeck);
//                }
//            }
//        }
//        else{
//            howManyMoreCards = 1;
//            for(int i = 0; i < howManyMoreCards; i++){
//                compInput = getCompInput();
//                moveResult = processMove(compInput, compDeck, comp); //no special card on gameDeck, so player can just play a single card and end turn
//                if( moveResult == 1)
//                    howManyMoreCards++;
//            }
//
//
//        }
//    }