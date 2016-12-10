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
public enum Value {
    
    TWO("2"), THREE("3"), FOUR("4"),FIVE("5"), SIX("6"), SEVEN("7"), EIGHT("8"), 
    NINE("9"), TEN("T"), JACK("J"), QUEEN("Q"), KING("K"), ACE("A");
    
    private String val;
    
    private Value(String val) {
        this.val = val;
    }
    
    public String getVal() {
        return this.val;
    }
    
}
