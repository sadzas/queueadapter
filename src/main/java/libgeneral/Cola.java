/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package libgeneral;

/**
 *
 * @author mgiannini
 */
public class Cola {

    private String id_cola;
    private String cola_nombre;
    private String cola_estrategia;
    private int cola_espera;
    private String cola_desborde;

    public Cola() {
        id_cola = "";
        cola_nombre = "";
        cola_estrategia = "";
        cola_espera = 0;
        cola_desborde = "";
    }

    public String getId_cola() {
        return id_cola;
    }

    public void setId_cola(String id_cola) {
        this.id_cola = id_cola;
    }

    public String getCola_nombre() {
        return cola_nombre;
    }

    public void setCola_nombre(String cola_nombre) {
        this.cola_nombre = cola_nombre;
    }

    public String getCola_estrategia() {
        return cola_estrategia;
    }

    public void setCola_estrategia(String cola_estrategia) {
        this.cola_estrategia = cola_estrategia;
    }

    public int getCola_espera() {
        return cola_espera;
    }

    public void setCola_espera(int cola_espera) {
        this.cola_espera = cola_espera;
    }

    public String getCola_desborde() {
        return cola_desborde;
    }

    public void setCola_desborde(String cola_desborde) {
        this.cola_desborde = cola_desborde;
    }
}
