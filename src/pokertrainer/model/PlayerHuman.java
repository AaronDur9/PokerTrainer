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
public class PlayerHuman extends Player {
    
    public PlayerHuman(String name, int money, int seat, int num) {
        this.name = name;
        this.money = money;
        this.seat = seat;
        this.cards = null;
        this.role = Role.NONE;
        this.hand = null;
        this.numPlayer = num;
        this.betLastPot = 0;
        this.playerMode = new HumanMode();
    }

    @Override
    public boolean isBot() {
        return false;
    }

    @Override
    public Action createAction() {
        return null;
    }
     
}
