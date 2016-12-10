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
 * @author Aarón
 */
public class HandPlayer {

    private HandCategories hand;
    private Value mainV;
    private Value secondV;
    private Suit suit;
    //Cartas que completan la mano
    private LinkedList<Value> cards;

    public HandPlayer(HandCategories name) {
        this.hand = name;
        this.mainV = null;
        this.secondV = null;
        this.suit = null;
        this.cards = null;
    }

    public HandCategories getHand() {
        return hand;
    }

    public void setHand(HandCategories hand) {
        this.hand = hand;
    }
    
    @Override
    public String toString() {
        String str = hand.getName();
        if(this.mainV != null) {
            str += " " + this.mainV;        
            if(this.secondV != null)
                str += " - " + this.secondV;
        }
        if(this.suit != null)
            str += " " + this.suit;
        if(this.cards != null){
            for(int i = 0; i < this.cards.size(); i++){
                str += " " + cards.get(i);
            }          
        }           
        return str;
        
    }

    public Suit getSuit() {
        return suit;
    }

    public void setSuit(Suit suit) {
        this.suit = suit;
    }

    public void setCards(LinkedList<Value> cards) {
        this.cards = cards;
    }
    
    public void pushCards(Value v) {
        if(this.cards == null)
            this.cards = new LinkedList<>();
        this.cards.addFirst(v);
    }
    
    public void addLastCards(Value v) {
        if(this.cards == null)
            this.cards = new LinkedList<>();
        this.cards.addLast(v);
    }
    
    public LinkedList<Value> getCards() {
        return cards;
    }
    
     public void setMainV(Value mainV) {
        this.mainV = mainV;
    }

    public void setSecondV(Value secondV) {
        this.secondV = secondV;
    }

    public Value getMainV() {
        return mainV;
    }

    public Value getSecondV() {
        return secondV;
    }
 
}
