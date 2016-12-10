package pokertrainer.model;

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

/**
 *
 * @author usuario_local
 */
public class Card implements Comparable<Card> {
    
    private final Suit suit;
    private final Value val;
    private final int ord;
    
    
    public Card(Suit suit, Value val) {
        this.suit = suit;
        this.val = val;
        ord = val.ordinal();
    }
    
    public Suit getSuit() {
        return this.suit;
    }
    
    public Value getVal() {
        return this.val;
    }
    
    private int getOrd() {
        return this.val.ordinal();
    }
    
    @Override
    public String toString(){
        return (this.val.getVal() + this.suit.getName());
    }

    @Override
    public int compareTo(Card o) {
       return this.val.compareTo(o.getVal());
    }
}

