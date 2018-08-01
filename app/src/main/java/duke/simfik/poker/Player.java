package duke.simfik.poker;

import java.util.Random;

/**
 * Created by Duke on 30.07.2018.
 */

public class Player {
    public String name;
    int cash;
    int lastBet;
    boolean folded;
    public Player(String name, int cash){
        this.name = name;
        this.cash = cash;
        this.lastBet = 0;
    }
    public Player(){
        this.cash = 1000;
        Random random = new Random();
        this.name = "Player " + String.valueOf(random.nextInt(10000));
        this.lastBet = 0;
    }
    public void plusCash(int n){
        this.cash += n;
    }
    public boolean minusCash(int n){
        if(this.cash >= n){
            this.cash -= n;
            return true;
        }
        else return false;
    }
    public boolean isFolded(){
        return this.folded;
    }
    public void setFolded(boolean arg){
        this.folded = arg;
    }
    public int getLastBet(){
        return this.lastBet;
    }
    public void setLastBet(int bet){
        this.lastBet = bet;
    }
}
