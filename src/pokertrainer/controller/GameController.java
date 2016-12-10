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

package pokertrainer.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import javax.swing.Timer;
import pokertrainer.model.Action;
import pokertrainer.model.ActionCall;
import pokertrainer.model.ActionFold;
import pokertrainer.model.ActionRaise;
import pokertrainer.model.Board;
import pokertrainer.model.GameState;
import pokertrainer.model.Player;
import pokertrainer.model.State;
import pokertrainer.view.GameWindow;

/**
 *
 * @author usuario_local
 */
public class GameController {
    
    private Board model;
    private GameWindow view;
    private Timer timer;
    
    public GameController(Board board) {
        this.model = board;
        this.view = new GameWindow(this, model.getPlayers(), this.model.getSeats(),
                this.model.getSumOfPots(), model.getPlayerTurn(), model.getBigBlind());
        this.view.setVisible(true);
        this.model.setControl(this);
    }
    
    private void isLastToSpeak(Player p, boolean playerFoldsOrAllin) {
        if (model.isLastToSpeak()){
            State t = model.changeState();
            view.paintAllInitialBets(model.getSeats());
            if (playerFoldsOrAllin) model.removePlayer(p);
            executeState(t);
           
        } else {
            if (playerFoldsOrAllin)
                model.removePlayer(p);
            else
                model.changeTurn();
        }
    }
    
    private void executeState(State t) {
         switch(t) {
            case PREFLOP:
                preflop();
                break;
            case FLOP:
                flop();
                break;
            case TURN:
                turn();
                break;
            case RIVER:
                river();
                break;
            case SHOWDOWN:
                showDown();
                break;
            case GAME_FINISHED:
                System.out.println("Partida acabada.");
            default:
                break;
            }
    }
    
    public void preflop(){
        stopGame();
        this.model.preflop();
        continueGame();
    }
    
    //Reparte 3 a la mesa
    public void flop() {
        view.paintFlop(model.flop());
    }
    
    //Reparte la 4 carta a la mesa
    public void turn() {
        view.paintTurn(model.turn());
    }
    
    //Reparte la ultima carta a la mesa
    public void river() {
        view.paintRiver(model.river());
    }
    
    private void showDown() {
        LinkedList<LinkedList<Player>> winners = model.showdown();
        view.paintAllBets(model.getPlayers());
        view.paintWinners(winners);
        
        State t = model.tryNewHand();
        if (t == State.PREFLOP) {
            stopGame();
            this.model.preflop();
            for(Player p: this.model.getPlayerLoses())
                view.disablePlayer(p.getSeat());
                view.prepareViewForNextHand(model.getPlayers(), model.getSeats(),
                model.getSumOfPots(), model.getPlayerTurn(), model.getBigBlind(),
                model.getNumberOfHand());
            continueGame();
            
        } else {
            view.paintLastWinners(winners.getLast());
            view.disableMenuPanel();
            view.setTextToTextArea("The game has finished.\n");
        }
    }

    //********************** PLAYER ACTIONS ************************************
    
    //Recibe action porque puede ser check o call
    public void playerCalls(String action) {
        Action a = new ActionCall(action);
        Player actualPlayer = model.getPlayerTurn();
        GameState gs = a.executeAction(model, actualPlayer);
        
        
        view.addActionToPlayerList(actualPlayer.getName(), a);
        view.setPlayerBetAndMoney(actualPlayer.getSeat(), actualPlayer.getMoney(), actualPlayer.getBet());
        view.updatePot(model.getSumOfPots());
        view.disableViewForPlayerTurn(actualPlayer);
        if(gs == GameState.OVER_ALLIN) {
            untilTheEnd();
        }
        else {
            stopGame();
            isLastToSpeak(actualPlayer, false);
            continueGame();
        }
    }
    
    public void playerFolds() {
        Action a = new ActionFold();
        Player actualPlayer = model.getPlayerTurn();
        GameState gs = a.executeAction(model, actualPlayer);
       
        view.addActionToPlayerList(actualPlayer.getName(), a);
        view.setFoldState(actualPlayer); 
        
        //if (null != only1player) 
        //En vez de showdown llamar funcion de repartir dinerios sin cartukis
        switch (gs) {
            case OVER:
                model.removePlayer(actualPlayer);
                executeState(State.SHOWDOWN);
                break;
            case OVER_ALLIN:
                untilTheEnd();
                break;
            default:
                stopGame();
                isLastToSpeak(actualPlayer, true);
                continueGame();
                break;
        }
    }

    //Recibe action porque puede ser bet, raise o allin
    public void playerRaises(int raiseBet, String action) {
        Action a = new ActionRaise(raiseBet, action);
        Player actualPlayer = model.getPlayerTurn();
        GameState gs = a.executeAction(model, actualPlayer);
       
        view.addActionToPlayerList(actualPlayer.getName(), a);
        view.setPlayerBetAndMoney(actualPlayer.getSeat(), actualPlayer.getMoney(), actualPlayer.getBet());
        view.updatePot(model.getSumOfPots());
        view.disableViewForPlayerTurn(actualPlayer);
        if(gs == GameState.OVER_ALLIN) {
           untilTheEnd();
        } else {
            stopGame();
            boolean playerTurnAllIn = (actualPlayer.getMoney() == 0);
            isLastToSpeak(actualPlayer, playerTurnAllIn);
            continueGame();
            
        }
    }
    
    
    
    private void untilTheEnd(){
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                State t = model.changeState();
                executeState(t);
                if (t == State.SHOWDOWN) timer.stop();
            }
        });
         timer.start();
        
    }

    public void executeBotAction() {
        Player p = model.getPlayerTurn();
        Action a = p.createAction();
        if(a.getNameAction().equals("Call")||a.getNameAction().equals("Check")){
            playerCalls(a.getNameAction());
        }else{
             if(a.getNameAction().equals("Fold")){
                 playerFolds();
             }else{
                 playerRaises(a.getBetAction(), a.getNameAction());
             }
        }
    }
    
    // Detiene la partida (usado para las hebras
    public void stopGame() {
	this.model.getPlayerTurn().getPlayerMode().finish();
    }
    
	
    // Continua la partida (usado para las hebras) 
    public void continueGame() {
        if (!this.model.isFinished()) {

            updateViewForNextTurn();

            this.model.getPlayerTurn().getPlayerMode().start(this);
        }
    }
    
    private void updateViewForNextTurn() {
        view.updateViewForPlayerTurn(model.getPlayerTurn(), model.getHighBet(),
                model.getBigBlind());
    }
}
