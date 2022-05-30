/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rabbitmq;

import java.util.ArrayList;
import libgeneral.Cola;

/**
 *
 * @author mgiannini
 */
public class RabbitMensajeEntrante {
    
    private String code;
    private boolean value1;
    private String value2;
    private String value3;
    private String value4;
    private String value5;
    private ArrayList<Cola> value6;
    
    public RabbitMensajeEntrante() {
        code = "";
        value1 = false;
        value2 = "";
        value3 = "";
        value4 = "";
        value5 = "";
        value6 = new ArrayList();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public boolean isValue1() {
        return value1;
    }

    public void setValue1(boolean value1) {
        this.value1 = value1;
    }

    public String getValue2() {
        return value2;
    }

    public void setValue2(String value2) {
        this.value2 = value2;
    }

    public String getValue3() {
        return value3;
    }

    public void setValue3(String value3) {
        this.value3 = value3;
    }

    public String getValue4() {
        return value4;
    }

    public void setValue4(String value4) {
        this.value4 = value4;
    }

    public String getValue5() {
        return value5;
    }

    public void setValue5(String value5) {
        this.value5 = value5;
    }

    public ArrayList<Cola> getValue6() {
        return value6;
    }

    public void setValue6(ArrayList<Cola> value6) {
        this.value6 = value6;
    }
    
}
