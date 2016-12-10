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
 * @author Mateo
 */
public enum Role {
    BIG_BLIND("bb"), SMALL_BLIND("sb"), NONE("");
     
    private final String role;
    
    private Role(String role){
        this.role = role;
    }
    
    @Override
    public String toString() {
        return role;
    }
    
    
}
