/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rabbitmq;

import queueadapter.Main;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import java.io.IOException;
import java.util.concurrent.TimeoutException;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 *
 * @author mgiannini
 */
public class RabbitManager {

    private final static String QUEUE_ENVIA = "asteradapterOrigen";
    private final static String EXCHANGE_NAME = "asteradapter";

    private final static String RBSERVIDOR = Main.getRbServidor();
    private final static String RBUSUARIO = Main.getRbUsuario();
    private final static String RBPASSWORD = Main.getRbPassword();
    private final static String RBPUERTO = Main.getRbPuerto();

    private final static ObjectMapper mapper = new ObjectMapper();
    
    ConnectionFactory factory = new ConnectionFactory();
    
    public RabbitManager() throws IOException, TimeoutException {
        factory.setHost(RBSERVIDOR);
        factory.setUsername(RBUSUARIO);
        factory.setPassword(RBPASSWORD);
        factory.setPort(Integer.parseInt(RBPUERTO));
    }

    public static void EnviaMsg(RabbitMensaje mensaje) throws IOException, TimeoutException {
        RabbitManager conn = new RabbitManager();
        String mensajeJSON = mapper.writeValueAsString(mensaje);

        try {
            Connection connection = conn.factory.newConnection();
            Channel channel = connection.createChannel();
            channel.queueDeclare(QUEUE_ENVIA, false, false, false, null);
            channel.basicPublish("", QUEUE_ENVIA, null, mensajeJSON.getBytes());
        } catch (IOException | TimeoutException e) {
            Logger.getLogger(RabbitManager.class.getName()).log(Level.FATAL, "EnviaMsg", e);
        }
    }

    public void RecibeMsg() throws IOException, TimeoutException {
        RabbitManager conn = new RabbitManager();

        try {

            Connection connection = conn.factory.newConnection();
            Channel channel = connection.createChannel();
            
            channel.exchangeDeclare(EXCHANGE_NAME, "direct");
            String queueName = channel.queueDeclare().getQueue();

            channel.queueBind(queueName, EXCHANGE_NAME, Main.getRbQueueBind());
            
            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String message = new String(delivery.getBody(), "UTF-8");
                System.out.println("El mensaje entrante es: "+message);
                RabbitMensajeEntrante msg = new ObjectMapper().readValue(message, RabbitMensajeEntrante.class);
                RabbitFunciones.rabbitMensajeClasifico(msg);
            };
            channel.basicConsume(queueName, true, deliverCallback, consumerTag -> { });
            
        } catch (IOException | TimeoutException e) {
            Logger.getLogger(RabbitManager.class.getName()).log(Level.FATAL, "RecibeMsg", e);
        }
    }
}
