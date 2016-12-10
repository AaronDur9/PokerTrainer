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
public class ActionFold extends Action {

    public ActionFold() {
        this.action = "Fold";
    }
    
    

    @Override
    public GameState executeAction(Board b, Player p) {
         GameState state = GameState.CONTINUE;

        //Si tenemos 2 jugadores y uno se ha retirado
        b.removePlayerFromPots(p);
        int size = b.getHandPlayersSize();
        
        if(size == 2 && b.isLastToSpeak())
            state = b.updateCurrentState();
        else if(size == 2 && b.getNextPlayerTotalBet() >= b.getHighBet())
            state = b.updateCurrentState();
        return state;
    }
    
    @Override
    public String toString() {
        return action;
    }
    
}
