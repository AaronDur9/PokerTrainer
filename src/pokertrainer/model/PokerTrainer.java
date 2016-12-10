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
import pokertrainer.controller.MainWindowController;
import pokertrainer.view.MainWindow;

/**
 *
 * @author Javi
 */
public class PokerTrainer {
    
    /**
     * @param args the command line arguments
     */
 
    public static void main(String[] args) {
       
        MainWindowController mwc = new MainWindowController();
        MainWindow login = new MainWindow(null, true, mwc);
            
        login.setModal(true);
        login.setVisible(true);
        
        /*Board board = new Board(2);
        LinkedList<Card> hand;
        
        for (int i = 0; i < 100; i++) {
            hand = board.deal(7);

            for (int j = 0; j < 7; j++) 
                System.out.print(hand.get(j).toString());
            
            System.out.println("");
            
           board.addToDeck(hand);
        }*/
       
    }
   
}
