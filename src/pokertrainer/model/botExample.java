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
 * @author Carlos
 */
public class botExample extends PlayerBot{

    public botExample(String name, int money, int seat, int num) {
        super(name, money, seat, num);
    }

    @Override
    public Action createAction() {
         return new ActionCall("Call");
    }
    
}
