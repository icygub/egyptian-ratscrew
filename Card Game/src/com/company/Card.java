package com.company;

/**
 * Created by icyhot on 12/20/16.
 */
public class Card {

    private String suit;
    private String value;
    private String suits[] = {"Clubs", "Hearts", "Spades", "Diamonds"};

    public Card(int suit, int value){
        this.suit = suits[suit];
        if (value == 1)
            this.value = "Ace";
        else if (value == 11)
            this.value = "Jack";
        else if (value == 12)
            this.value = "Queen";
        else if (value == 13)
            this.value = "King";
        else
            this.value = Integer.toString(value);
    }

    public String getSuit() {
        return suit;
    }

    public String getValue() {
        return value;
    }
}
