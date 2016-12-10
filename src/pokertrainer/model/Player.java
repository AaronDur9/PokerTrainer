/*
Autores:
-Aarón Durán Sánchez
-Javier López de Lerma
-Mateo García Fuentes
-Carlos López Martínez


 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokertrainer.model;

import java.util.LinkedList;

/**
 *
 * @author Mateo
 */
public abstract class Player {
    protected Role role;
    protected int seat;
    protected LinkedList<Card> cards;
    protected HandPlayer hand;
    protected PlayerMode playerMode;
    
    protected int bet;
    
    protected String name;
    protected int money;
    protected int numPlayer;
    protected int totalBet;
    
    //Atributo que indica cuanto dinero puso el jugador en el último bote.
    //Sirve para el caso en que haya que repartir su dinero entre 2 botes en los que el no está
    //porque le hicieron un raise
    protected int betLastPot;
    

    protected int getBetLastPot() {
        return betLastPot;
    }

    protected void setBetLastPot(int betLastPot) {
        this.betLastPot = betLastPot;
    }
    

    protected int getTotalBet() {
        return totalBet;
    }
    
    
    protected HandPlayer getHand() {
        return hand;
    }

    protected void setHand(HandPlayer hand) {
        this.hand = hand;
    }
    
    
    public int getSeat() {
        return seat;
    }

    protected void setSeat(int seat) {
        this.seat = seat;
    }

    public LinkedList<Card> getCards() {
        return cards;
    }

    protected void setCards(LinkedList<Card> cards) {
        this.cards = cards;
    }

    public int getBet() {
        return bet;
    }

    protected void setBet(int bet) {
        this.bet = bet;
    }

    public String getName() {
        return name;
    }

    public int getMoney() {
        return money;
    }

    protected void setMoney(int money) {
        this.money = money;
    }
    
    protected void addMoney(int money) {
        this.money += money;
    }
    
    public Role getRole() {
        return role;
    }

    protected void setRole(Role role) {
        this.role = role;
    }

    protected int getNumPlayer() {
        return numPlayer;
    }
    
    //Devuelve el dinero qeu tiene que poner cada jugador( o el que puede)
    protected int bet(int bet) {
        int b;
        
        if (bet < this.money) 
            b = bet;
         
        
        else 
            b = this.money;
            
                
       
        
        return b;
    }
    
    //Modifica el dinero y apuesta del jugador.
    protected void updatePlayer(int b) {
        this.bet += b;
        this.money -= b;
        this.totalBet += b;
    }
    
    protected void incrMoney(int money){
        this.money +=money;
    }
    
    protected void initializePlayer() {
        this.bet = 0;
        this.betLastPot = 0;
        this.totalBet = 0;
    }
    
    public abstract boolean isBot();


    public PlayerMode getPlayerMode() {
        return this.playerMode;
    }

    public abstract Action createAction();
    
}
