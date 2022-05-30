/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package queuemanager;

/**
 *
 * @author mgiannini
 */
public class Funciones {
    
    public static String obtengoIDEvento(String evento) {
        String retorno = "";
        switch(evento) {
            case "ParkedCallEvent": 
                retorno = "GW1001";
                break;
            case "ParkedCallGiveUpEvent": 
                retorno = "GW1002";
                break;
            case "ParkedCallTimeOutEvent":
                retorno = "GW1003";
                break;
            default:
                break;
            
        }
        return retorno;
    }        
}
