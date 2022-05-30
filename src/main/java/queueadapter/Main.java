/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package queueadapter;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.TimeoutException;
import queuemanager.QueueManager;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import org.asteriskjava.live.ManagerCommunicationException;
import org.asteriskjava.manager.AuthenticationFailedException;
import rabbitmq.RabbitManager;
import static rabbitmq.RabbitManager.EnviaMsg;
import rabbitmq.RabbitMensaje;

/**
 *
 * @author mgiannini
 */
public class Main {

    private static String mngServidor;
    private static String mngUsuario;
    private static String mngPassword;
    private static String mngPuerto;
    private static String rbNumAdapter;
    private static String rbQueueBind;
    private static String rbServidor;
    private static String rbUsuario;
    private static String rbPassword;
    private static String rbPuerto;
    private static String rdServidor;
    private static String rdPuerto;

    private static final Properties prop = new Properties();
    private static InputStream input = null;

    /**
     *
     * @param args
     * @throws IOException
     * @throws TimeoutException
     * @throws InterruptedException
     * @throws ClassNotFoundException
     * @throws AuthenticationFailedException
     * @throws ManagerCommunicationException
     * @throws org.asteriskjava.manager.TimeoutException
     */
    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException, ClassNotFoundException, AuthenticationFailedException, ManagerCommunicationException, org.asteriskjava.manager.TimeoutException {
        try {
            System.setProperty(org.slf4j.impl.SimpleLogger.DEFAULT_LOG_LEVEL_KEY, "ON");
            input = new FileInputStream("/home/mgiannini/Desarrollos/misaplicaciones/asteradapter/asteradapter.config.properties");
            prop.load(input);

            mngServidor = prop.getProperty("mngServidor");
            mngUsuario = prop.getProperty("mngUsuario");
            mngPassword = prop.getProperty("mngPassword");
            mngPuerto = prop.getProperty("mngPuerto");
            rbNumAdapter = prop.getProperty("rbNumAdapter");
            rbQueueBind = prop.getProperty("rbQueueBind");
            rbServidor = prop.getProperty("rbServidor");
            rbUsuario = prop.getProperty("rbUsuario");
            rbPassword = prop.getProperty("rbPassword");
            rbPuerto = prop.getProperty("rbPuerto");
            rdServidor = prop.getProperty("rdServidor");
            rdPuerto = prop.getProperty("rdPuerto");

        } catch (IOException e) {
            Logger.getLogger(Main.class.getName()).log(Level.FATAL, "Main | No se puede iniciar la aplicacion. No se encuentra o no se puede leer correctamente el archivo de configuracion.Recuerde que el archivo debe ser: /opt/asteradapter/asteradapter.config.properties. | {0}", e);
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    Logger.getLogger(Main.class.getName()).log(Level.FATAL, "Main | No se puede cerrar FileInputStream. | {0}", e);
                }
            }
        }

        QueueManager conectar = new QueueManager();
        conectar.run();

        RabbitManager mensajeria = new RabbitManager();
        mensajeria.RecibeMsg();
        
        RabbitMensaje msg = new RabbitMensaje();
        msg.setIdevento("GW1000");
        EnviaMsg(msg);
        
    }

    /**
     * @return Retorna el numero de IP del servidor.
     */
    public static String getMNGServidor() {
        return mngServidor;
    }

    /**
     * @return Retorna el nombre de usuario para la conexion con el Manager.
     */
    public static String getMNGUsuario() {
        return mngUsuario;
    }

    /**
     * @return Retorna el password para la conexion con el Manager.
     */
    public static String getMNGPassword() {
        return mngPassword;
    }

    /**
     * @return Retorna el puerto para la conexion con el Manager.
     */
    public static String getMNGPuerto() {
        return mngPuerto;
    }

    /**
     * @return Retorna el ID unico del adaptador.
     */
    public static String getRbNumAdapter() {
        return rbNumAdapter;
    }

    /**
     * @return Retorna el host donde se encuentra RabbitMQ
     */
    public static String getRbServidor() {
        return rbServidor;
    }

    /**
     * @return Retorna el nombre de usuario para la conexion con RabbitMQ.
     */
    public static String getRbUsuario() {
        return rbUsuario;
    }

    /**
     * @return Retorna el password para la conexion con RabbitMQ.
     */
    public static String getRbPassword() {
        return rbPassword;
    }

    /**
     * @return Retorna el puerto para la conexion con RabbitMQ.
     */
    public static String getRbPuerto() {
        return rbPuerto;
    }

    /**
     * @return Retorna el host donde se encuentra Redis
     */
    public static String getRdServidor() {
        return rdServidor;
    }

    /**
     * @return Retorna el puerto para la conexion con Redis.
     */
    public static String getRdPuerto() {
        return rdPuerto;
    }

    /**
     * 
     * @return Retorna el BIND para identificar este nodo en Rabbit.
     */
    public static String getRbQueueBind() {
        return rbQueueBind;
    }

}
