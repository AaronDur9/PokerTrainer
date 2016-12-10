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

import static java.lang.Thread.sleep;
import pokertrainer.controller.GameController;

/**
 *
 * @author usuario_local
 */
public class BotMode implements PlayerMode {
    
    private Thread hebra;


    public BotMode () {
    }

    // Crea la hebra y la arranca
    public void start(GameController control) {
        this.hebra = new Thread() {
            public void run() {
                try {
                    sleep(2000);
                    
                    control.executeBotAction();
                    
                    
                    /*
                        Dentro del controlador:
                            - Action a = getPlayerTurn().getAction();
                            - a.executeAction
                    */
                } catch (InterruptedException e) {
                System.out.println("Ha habido un error");}
            }
        };
        this.hebra.start();
    }

    // Si la hebra existe se detiene
    public void finish() {
        if (this.hebra != null) {
            this.hebra.interrupt();
        }
    }
    
}
