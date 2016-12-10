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
public enum Suit {
    
    HEARTS("h"), DIAMONDS("d"), CLUBS("c"), SPADES("s");

    
    private final String name;
    
    private Suit(String name) {
        this.name = name;
    }
    
    public String getName() {
        return this.name;
    }
            
}
