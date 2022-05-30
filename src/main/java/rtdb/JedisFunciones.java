/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rtdb;

import queuemanager.QueueManager;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import rabbitmq.RabbitMensaje;

/**
 *
 * @author mgiannini
 */
public class JedisFunciones {
    
    public static void almacenoLlamada(String linkedid, RabbitMensaje llamada) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            String mensajeJSON = mapper.writeValueAsString(llamada);
            rtdb.JedisComandos.ingresaDato(linkedid, mensajeJSON);
            Logger.getLogger(QueueManager.class.getName()).log(Level.DEBUG, "almacenoLlamada | Se almacena el dato: "+ mensajeJSON);
        } catch (JsonProcessingException e) {
             Logger.getLogger(QueueManager.class.getName()).log(Level.FATAL, "almacenoLlamada", e);
        }
    }
    
    public static void eliminoLlamada(String linkedid) {
        Logger.getLogger(QueueManager.class.getName()).log(Level.DEBUG, "eliminoLlamada | Se elimina el dato: "+ linkedid);
        rtdb.JedisComandos.eliminaDato(linkedid);
    }
    
}
