/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rabbitmq;

import java.util.ArrayList;
import libgeneral.Cola;
import libgeneral.ColaMapeos;

/**
 *
 * @author mgiannini
 */
public class RabbitMensajeTratamiento {
    
    public static ColaMapeos mapeoIdColaObjCola = new ColaMapeos();
    
    public static void mensajeTratamientoColasDatos(ArrayList<Cola> listaColas) {
        listaColas.forEach(cola -> {
            mapeoIdColaObjCola.setMapa_StridCola_ObjCola(cola.getId_cola(), cola);
        });
    }
}
