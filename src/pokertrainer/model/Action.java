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

/**
 *
 * @author usuario_local
 */
public abstract class Action {
    
    protected String action;
    protected int bet;
    
    public abstract GameState executeAction(Board b, Player p);
    
    public String getNameAction(){
        return this.action;
    }
    
    public int getBetAction(){
        return this.bet;
    }
    
}
