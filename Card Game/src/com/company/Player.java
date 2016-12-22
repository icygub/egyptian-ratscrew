package com.company;

/**
 * Created by icyhot on 12/20/16.
 */
public class Player {

    private String name;
    private int score;

    public Player(String name){
        this.name = name;
    }

    public void setScore(int score){
        this.score = score;
    }

    public String getName(){
        return name;
    }
}
