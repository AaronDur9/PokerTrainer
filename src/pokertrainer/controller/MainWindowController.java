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

import pokertrainer.model.Board;

/**
 *
 * @author Javi
 */

//Este controlador esta dentro de la MainWindow y se llamará a
// empezar partida cuando pulsen el boton jugar
public class MainWindowController {
    
    public void start(int numPlayers) {
        Board board = new Board(numPlayers);
        GameController gameController = new GameController(board);
        
        //Board board = new Board();
    }
}
