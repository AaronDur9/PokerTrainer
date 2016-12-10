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
public interface PlayerMode {
    
	public abstract void start(GameController control);
	public abstract void finish();
        
}
