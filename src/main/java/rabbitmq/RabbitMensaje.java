/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rabbitmq;

import queueadapter.Main;
import java.util.Date;

/**
 *
 * @author mgiannini
 */
public class RabbitMensaje {
    private String id_adapter;
    private String queueBind;
    private String idevento;
    private String channel;
    private String cola;
    private String linkedid;
    private Date fecha;
    private String parking;
    
    public RabbitMensaje() {
        id_adapter = Main.getRbNumAdapter();
        queueBind = Main.getRbQueueBind();
        idevento = "";
        channel = "";
        cola = "";
        linkedid = "";
        fecha = new Date();
        parking = "";
    }

    public void setId_adapter(String id_adapter) {
        this.id_adapter = id_adapter;
    }

    public void setIdevento(String idevento) {
        this.idevento = idevento;
    }
        
    public void setLinkedid(String linkedid) {
        this.linkedid = linkedid;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public void setParking(String parking) {
        this.parking = parking;
    }

    public String getId_adapter() {
        return id_adapter;
    }

    public String getIdevento() {
        return idevento;
    }
    
    public String getLinkedid() {
        return linkedid;
    }

    public Date getFecha() {
        return fecha;
    }

    public String getParking() {
        return parking;
    }

    public String getQueueBind() {
        return queueBind;
    }

    public void setQueueBind(String queuebind) {
        this.queueBind = queuebind;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getCola() {
        return cola;
    }

    public void setCola(String cola) {
        this.cola = cola;
    }
}
