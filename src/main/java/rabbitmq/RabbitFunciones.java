/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rabbitmq;

import queuemanager.QueueManager;
import java.io.IOException;
import java.util.concurrent.TimeoutException;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 *
 * @author mgiannini
 */
public class RabbitFunciones {
    
    public static void rabbitMensajeClasifico(RabbitMensajeEntrante msg) {
        switch (msg.getCode()) {
            case "QU1000":
                Logger.getLogger(RabbitFunciones.class).log(Level.INFO, "Codigo QU1000 | Actualiza datos de las Queues.");
                RabbitMensajeTratamiento.mensajeTratamientoColasDatos(msg.getValue6());
                break;
            default:
                Logger.getLogger(RabbitFunciones.class).log(Level.WARN, "Llego un mensaje no esperado desde Rabbit y NO DEBERIA PASAR!.");
                break;
        }
    }
    
    synchronized public static void rabbitMensajeEnvio(RabbitMensaje llamada) throws TimeoutException {
        try {
            RabbitManager.EnviaMsg(llamada);
            Logger.getLogger(QueueManager.class.getName()).log(Level.DEBUG, "rabbitMensajeEnvio | Se envia la llamada");
        } catch (IOException e) {
            Logger.getLogger(QueueManager.class.getName()).log(Level.FATAL, "Envia Mensaje", e);
        }
    }
}
