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

import pokertrainer.controller.GameController;

/**
 *
 * @author usuario_local
 */
public class PlayerBot extends Player{
    
     public PlayerBot(String name, int money, int seat, int num) {
        this.name = name;
        this.money = money;
        this.seat = seat;
        this.cards = null;
        this.role = Role.NONE;
        this.hand = null;
        this.numPlayer = num;
        this.betLastPot = 0;
        this.playerMode = new BotMode();
    }

    @Override
    public boolean isBot() {
       return true;
    }

    @Override
    public Action createAction(){
         return new ActionCall("Call");
    }
}
