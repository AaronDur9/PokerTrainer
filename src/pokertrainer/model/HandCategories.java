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
public enum HandCategories {
    HIGH_CARD("HIGH_CARD"),ONE_PAIR("ONE_PAIR"), TWO_PAIR("TWO_PAIR"),THREE_KIND("THREE_KIND"),
    STRAIGHT("STRAIGHT"), FLUSH("FLUSH"), FULL_HOUSE("FULL_HOUSE"), POKER("POKER"),
    STRAIGHT_FLUSH("STRAIGHT_FLUSH");
        
        private String name; 
        private HandCategories(String name){
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
}
