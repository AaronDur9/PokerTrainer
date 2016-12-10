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

//Accion usada para Raise, Bet y All-in

public class ActionRaise extends Action {

    public ActionRaise(int raiseBet, String action) {
        this.action = action;
        this.bet = raiseBet;
    }

    
    @Override
    public GameState executeAction(Board b, Player p) {
        GameState allin = GameState.CONTINUE;
        
        if(this.bet > b.getHighBet()) {
            b.setHighBet(this.bet);
            b.updateLastToSpeak();
        }
        
        //Restamos la apuesta total que habia realizado 
        this.bet -= p.getBet();
        
        b.refreshBet(p, this.bet);
        
        //Si solo quedaba yo por hablar o si yo voy allin y yo era el último jugador en hablar
        if((b.getHandPlayersSize() == 1) || (b.getHandPlayersSize() == 2 && p.getMoney() == 0 && b.isLastToSpeak()))
            //Compruebo si se debe dar el dinero a un jugador o si se debe mostrar el showdown
            allin = b.updateCurrentState();
        
        return allin;
    }

    @Override
    public String toString() {
        return action + " " + bet;
    }
    
}
