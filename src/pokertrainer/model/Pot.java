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
 * @author usuario_local
 */
public class Pot {
    
    
    private int totalPot;
    private int bet;
    private LinkedList<Player> players;
    private LinkedList<Player> outForRaise;

    public Pot(int totalPot, int bet, LinkedList<Player> players, LinkedList<Player> outRaise) {
        this.totalPot = totalPot;
        this.bet = bet;
        this.players = players;
        this.outForRaise = outRaise;
    }
    
    public void addPlayersOut(Player player) {
        this.outForRaise.add(player);
    }
    
    public void addListPlayersOut(LinkedList<Player> players) {
        this.outForRaise.addAll(players);
    }
    
    public void delPlayerOut(Player player) {
        this.outForRaise.remove(player);
        this.totalPot -= player.getBetLastPot();
    }
        
    public LinkedList<Player> getPlayersOutForRaise() {
        return this.outForRaise;
    }
    

    public int getTotalPot() {
        return totalPot;
    }

    public void addTotalPot(int totalPot) {
        this.totalPot += totalPot;
    }
    
    public void subTotalPot(int totalPot) {
        this.totalPot -= totalPot;
    }

    public int getBet() {
        return bet;
    }

    public void setBet(int bet) {
        this.bet = bet;
    }
    
     public void subBet(int bet) {
        this.bet -= bet;
    }

    public LinkedList<Player> getPlayers() {
        return players;
    }

    public void addPlayers(Player player) {
        this.players.add(player);
        if(this.outForRaise.contains(player))
            this.outForRaise.remove(player);
    }
    
    //Antes
    public void removePlayers() {
        this.players.clear();
    }
    
    //Antes de eliminar a los jugadores que había un bote por un raise de otro jugador
    //Almacenamos en outForRaise que han sido eliminados para luego poder saber cuantos jugadores pusieron dinero en este bote
    //Necesario para el caso en el que se parte un bote donde había 3 jugadores pero ahora solo queda uno por raise.
    public void removePlayersForRaise(Player play) {
        for(Player p: this.players) {
            if(!this.outForRaise.contains(p))
                this.outForRaise.add(p);
        }
        
        
        this.players.clear();
    }
    
    
    
    
    
    
   
}
