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

//Acción usada para Call y Check

public class ActionCall extends Action {
    
    public ActionCall(String action) {
        this.action = action;
    }
    
    @Override
    public GameState executeAction(Board b, Player p) {
       
        //Calculamos la apuesta que tiene que realizar para hacer el call
        int actualBet = p.getBet();
        int betToCall = b.getHighBet() - actualBet;
        
        this.bet = betToCall;
        
        b.refreshBet(p, betToCall);
         
        if (b.getHandPlayersSize() == 1) return GameState.OVER_ALLIN;
        else return GameState.CONTINUE;
    }
    
    @Override
    public String toString() {
        if (this.action.equals("Check")) return action;
        else return action + " " + bet;
    }
}
