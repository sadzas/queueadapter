/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package queuemanager;

import rabbitmq.RabbitMensaje;
import queueadapter.Main;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import static java.util.concurrent.TimeUnit.SECONDS;
import java.util.concurrent.TimeoutException;
import org.asteriskjava.live.DefaultAsteriskServer;
import org.asteriskjava.live.ManagerCommunicationException;
import org.asteriskjava.manager.AuthenticationFailedException;
import org.asteriskjava.manager.action.ParkAction;
import org.asteriskjava.manager.event.NewChannelEvent;
import org.asteriskjava.manager.event.ParkedCallEvent;
import org.asteriskjava.manager.event.ExtensionStatusEvent;

import org.asteriskjava.manager.AbstractManagerEventListener;
import org.asteriskjava.manager.ManagerConnection;
import org.asteriskjava.manager.ManagerConnectionFactory;
import org.asteriskjava.manager.ManagerEventListener;
import org.asteriskjava.manager.ManagerEventListenerProxy;
import org.asteriskjava.manager.event.ParkedCallGiveUpEvent;
import org.asteriskjava.manager.event.ParkedCallTimeOutEvent;
import org.asteriskjava.manager.event.ParkedCallsCompleteEvent;
import org.asteriskjava.manager.event.UnparkedCallEvent;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import rabbitmq.RabbitFunciones;
import rabbitmq.RabbitMensajeTratamiento;
import rtdb.JedisComandos;
import rtdb.JedisFunciones;

/**
 *
 * @author mgiannini
 */
public class QueueManager extends AbstractManagerEventListener implements ManagerEventListener {

    private final ManagerConnection managerConnection;
    private DefaultAsteriskServer asteriskServer;
    private final String asterServ;
    private final String asterUser;
    private final String asterPass;
    private final static String IDADAPTER = Main.getRbNumAdapter();

    public QueueManager() throws IOException, ManagerCommunicationException, InterruptedException {
        this.asterPass = Main.getMNGPassword();
        this.asterUser = Main.getMNGUsuario();
        this.asterServ = Main.getMNGServidor();

        ManagerConnectionFactory factory = new ManagerConnectionFactory(asterServ, asterUser, asterPass);
        this.managerConnection = factory.createManagerConnection();
    }

    public void run() throws IOException, AuthenticationFailedException, TimeoutException, InterruptedException, ManagerCommunicationException, org.asteriskjava.manager.TimeoutException {
        managerConnection.addEventListener(new ManagerEventListenerProxy(this));
        this.escuchar();
    }

    private void escuchar() throws ManagerCommunicationException, InterruptedException, org.asteriskjava.manager.TimeoutException {
        try {
            managerConnection.login();
            asteriskServer = new DefaultAsteriskServer(managerConnection);
            asteriskServer.initialize();
        } catch (IOException | IllegalStateException | AuthenticationFailedException e) {
            Logger.getLogger(QueueManager.class.getName()).log(Level.FATAL, "escuchar", e);
        }
        final Runnable keepGoing = () -> {
            try {
                kg();
            } catch (Exception e) {
                Logger.getLogger(QueueManager.class.getName()).log(Level.FATAL, "keepGoing", e);
            }
        };
        ScheduledExecutorService timer = Executors.newScheduledThreadPool(1);
        timer.scheduleAtFixedRate(keepGoing, 1, 1, SECONDS);
    }

    public void kg() {

    }

    @Override
    public void handleEvent(NewChannelEvent evento) {
        if (evento.getExten().equals("501")) {
            try {
                Logger.getLogger(QueueManager.class.getName()).log(Level.DEBUG, "NewChannelEvent | Nueva llamada | LinkedId: " + evento.getLinkedid() + " | Cola: 501");
                RabbitMensaje llamada = new RabbitMensaje();
                llamada.setId_adapter(IDADAPTER);
                llamada.setIdevento(Funciones.obtengoIDEvento(evento.getClass().getSimpleName()));
                llamada.setFecha(evento.getDateReceived());
                llamada.setLinkedid(evento.getLinkedid());
                llamada.setChannel(evento.getChannel());
                llamada.setCola("501");

                JedisFunciones.almacenoLlamada(evento.getLinkedid(), llamada);
                parkeoLlamada(evento.getChannel(), evento.getLinkedid(), "501");
            } catch (org.asteriskjava.manager.TimeoutException ex) {
                Logger.getLogger(QueueManager.class.getName()).log(Level.FATAL, "Parkeo de Llamada", ex);
            }
        }
    }

    @Override
    public void handleEvent(ExtensionStatusEvent evento) {
        //System.out.println("estado: " + evento.getStatus());
    }

    @Override
    public void handleEvent(UnparkedCallEvent evento) {
        System.out.println("-------------------------------------------");
        System.out.println("UnparkedCallEvent");
        System.out.println(evento.toString());
        System.out.println("-------------------------------------------");
    }

    @Override
    public void handleEvent(ParkedCallEvent evento) {
        Logger.getLogger(QueueManager.class.getName()).log(Level.DEBUG, "ParkedCallEvent | LinkedId: " + evento.getParkeelinkedid());

        try {
            RabbitMensaje llamada = JedisComandos.obtieneDato(evento.getParkeelinkedid());
            llamada.setIdevento(Funciones.obtengoIDEvento(evento.getClass().getSimpleName()));
            llamada.setParking(evento.getParkingSpace());

            JedisFunciones.almacenoLlamada(evento.getParkeelinkedid(), llamada);
            RabbitFunciones.rabbitMensajeEnvio(llamada);
        } catch (JsonProcessingException ex) {
            Logger.getLogger(QueueManager.class.getName()).log(Level.FATAL, "ParkedCallEvent | Error JSON", ex);
        } catch (TimeoutException ex) {
            Logger.getLogger(QueueManager.class.getName()).log(Level.FATAL, "ParkedCallEvent | Error TimeOut", ex);
        }
    }

    public void handleEvent(ParkedCallGiveUpEvent evento) throws TimeoutException {
        Logger.getLogger(QueueManager.class.getName()).log(Level.DEBUG, "ParkedCallGiveUpEvent | LinkedId: " + evento.getParkeeLinkedid());

        try {
            RabbitMensaje llamada = JedisComandos.obtieneDato(evento.getParkeeLinkedid());
            llamada.setIdevento(Funciones.obtengoIDEvento(evento.getClass().getSimpleName()));

            JedisFunciones.eliminoLlamada(evento.getParkeeLinkedid());
            RabbitFunciones.rabbitMensajeEnvio(llamada);
        } catch (JsonProcessingException ex) {
            Logger.getLogger(QueueManager.class.getName()).log(Level.FATAL, "ParkedCallEvent", ex);
        }
    }

    @Override
    public void handleEvent(ParkedCallsCompleteEvent evento) {
        System.out.println("");
        System.out.println("-------------------------------------------");
        System.out.println("ParkedCallCompleteEvent");
        System.out.println("-------------------------------------------");
        System.out.println("");
    }

    @Override
    public void handleEvent(ParkedCallTimeOutEvent evento) {
        Logger.getLogger(QueueManager.class.getName()).log(Level.DEBUG, "ParkedCallTimeOutEvent | LinkedId: " + evento.getParkeeLinkedid());

        try {
            RabbitMensaje llamada = JedisComandos.obtieneDato(evento.getParkeeLinkedid());
            llamada.setIdevento(Funciones.obtengoIDEvento(evento.getClass().getSimpleName()));

            JedisFunciones.eliminoLlamada(evento.getParkeeLinkedid());
            RabbitFunciones.rabbitMensajeEnvio(llamada);
        } catch (JsonProcessingException ex) {
            Logger.getLogger(QueueManager.class.getName()).log(Level.FATAL, "ParkedCallTimeOutEvent | Error JSON", ex);
        } catch (TimeoutException ex) {
            Logger.getLogger(QueueManager.class.getName()).log(Level.FATAL, "ParkedCallTimeOutEvent | Error TimeOut", ex);
        }
    }

    public void parkeoLlamada(String canal, String linkedid, String cola) throws org.asteriskjava.manager.TimeoutException {
        ParkAction nuevoParkeo = new ParkAction();
        nuevoParkeo.setChannel(canal);
        nuevoParkeo.setActionId(linkedid);
        nuevoParkeo.setTimeout(RabbitMensajeTratamiento.mapeoIdColaObjCola.get_StrIdCola_ObjCola(cola).getCola_espera());
        try {
            Logger.getLogger(QueueManager.class.getName()).log(Level.DEBUG, "parkeoLlamada | Se intenta el parkeo | LinkedId: " + linkedid);
            this.managerConnection.sendAction(nuevoParkeo);
        } catch (IOException | IllegalArgumentException | IllegalStateException ex) {
            Logger.getLogger(QueueManager.class.getName()).log(Level.FATAL, "Parkeo Llamada", ex);
        }
    }
}
