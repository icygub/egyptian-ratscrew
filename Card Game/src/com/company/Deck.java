package com.company;
import java.util.ArrayList;
import java.util.Collections;

public class Deck {

    private ArrayList deck = new ArrayList<Card>();

    public Deck(){
    }

    public void create(){
        for(int suit = 0; suit<4; suit++){
            for(int value = 1; value < 14; value++){
                deck.add(new Card(suit,value));
            }
        }
    }

    public void split(Deck deck1, Deck deck2){
        for(int i = deck.size()-1; i >= deck.size()/2; i--){
            deck1.add(getCard(i));
        }

        for(int i = (deck.size()/2)-1; i >= 0; i--){
            deck2.add(getCard(i));
        }
    }

    public Card getCard(int i) {
        return (Card) deck.get(i);
    }

    public String getCardName(int index){
        return getCard(index).getValue() + " of " + getCard(index).getSuit();
    }

    public Card getTopCard(){
        return getCard(deck.size()-1);
    }

    public String getTopCardName(){
        return getTopCard().getValue() + " of " + getTopCard().getSuit();
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

    public boolean isDouble(){
        try{
            if(( getCard(deck.size()-1).getValue() ).equals( getCard(deck.size()-2).getValue() )){
                return true;
            }
        }catch(Exception e){}
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

    public boolean topCardIsJAKQ(){
        if(deck.size() > 0){
            if( getCard(deck.size()-1).getValue().equals("Ace"))
                return true;
            else if ( getCard(deck.size()-1).getValue().equals("King"))
                return true;
            else if ( getCard(deck.size()-1).getValue().equals("Queen"))
                return true;
            else if ( getCard(deck.size()-1).getValue().equals("Jack"))
                return true;
            else
                return false;
        }
        else
            return false;
    }

    public int howManyMoreCards(){
        if( getCard(deck.size()-1).getValue().equals("Ace"))
            return 4;
        else if ( getCard(deck.size()-1).getValue().equals("King"))
            return 3;
        else if ( getCard(deck.size()-1).getValue().equals("Queen"))
            return 2;
        else if ( getCard(deck.size()-1).getValue().equals("Jack"))
            return 1;
        else
            return 0;
    }

    /**
     * This should only be used by gameDeck, i.e. gameDeck.toHand(playerDeck)
     * @param destination
     */
    public void toHand(Deck destination){
        for(int i = deck.size()-1; i >= 0; i--){ //moves all field cards to player's hand
            destination.add(getCard(i)); //places players top card to top of gameDeck
            for(int x = 0; x < destination.getSize(); x++){ //sorts gameDeck, moving top card to the bottom
                destination.swap(x, destination.getSize()-1); //will always be 0
            }
            deck.remove(i);
        }
        wipe(); //isn't this the same as remove(1) ???
    }

    public void printDeck(){
        System.out.println("\n---Game Deck---");
        for(int i = 0; i <deck.size(); i++){
            System.out.println(getCardName(i));
        }
        System.out.println("----End Game Deck----");
    }

    public void playCard(Deck gameDeck){
        gameDeck.add( (getCard(deck.size()-1)) );
        deck.remove(deck.size()-1);
    }

    public void remove(int index){ //this method is essentially copying the ArrayList method .remove(), so is this method really necessary????
        deck.remove(deck.get(index));
    }

}