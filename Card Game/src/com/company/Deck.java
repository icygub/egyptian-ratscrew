package com.company;
import java.util.ArrayList;
import java.util.Collections;

public class Deck {

    private ArrayList deck = new ArrayList<Card>();

    public Deck(){
        for(int suit = 0; suit<4; suit++){
            for(int value = 1; value < 14; value++){
                deck.add(new Card(suit,value));
            }
        }
    }

    public Card getCard(int i) {
        return (Card) deck.get(i);
    }

    /**
     * Uses Collections to shuffle deck
     */
    public void shuffle(){
        int ping;
        int pong;
        for(int i=0; i<deck.size(); i++){
            ping = (int) (Math.random()*52);
            do{
                pong = (int) (Math.random()*52);
            }while(pong == ping);
            swap(ping,pong);
        }
    }

    public void swap(int sourceIndex, int destinationIndex){
        Collections.swap(deck, sourceIndex, destinationIndex);
    }

    public void wipe(){
        for(int i = deck.size()-1; i >= 0; i--){
            deck.remove(i);
        }

    }

    public int getSize(){
        return deck.size();
    }

    public void add(Card card){
        deck.add(card);
    }

    public void addAtIndex(int index){

    }

    public boolean isDoubleSandwich(){
        System.out.println("Were here");
        if(isDouble())
            return true;
        else if (isSandwich())
            return true;
        else
            return false;
    }

    public boolean isDouble(){
        try{
            if(( getCard(deck.size()-1).getValue() ).equals( getCard(deck.size()-2).getValue() )){
                return true;
            }
        }catch(Exception e){
        }
        return false;

    }

    public boolean isSandwich(){
        try{
            if( getCard(deck.size()-1).getValue().equals(getCard(deck.size()-3).getValue()) ){
                return true;
            }
        }catch(Exception e){

        }
        return false;
    }

//    public boolean isJAKQ(){
//        if( getCard(deck.size()-1).getValue().equals("Ace"))
//            return true;
//        else if ( getCard(deck.size()-1).getValue().equals("King"))
//            return true;
//        else if ( getCard(deck.size()-1).getValue().equals("Queen"))
//            return true;
//        else if ( getCard(deck.size()-1).getValue().equals("Jack"))
//            return true;
//        else
//            return false;
//    }

    public int howManyMoreCards(){
        if( getCard(deck.size()-1).getValue().equals("Ace"))
            return 3;
        else if ( getCard(deck.size()-1).getValue().equals("King"))
            return 2;
        else if ( getCard(deck.size()-1).getValue().equals("Queen"))
            return 1;
        else if ( getCard(deck.size()-1).getValue().equals("Jack"))
            return 0;
        else
            return 0;
    }

    public void remove(int index){
        deck.remove(deck.get(index));
    }


}
