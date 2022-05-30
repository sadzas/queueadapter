/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package libgeneral;

import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author mgiannini
 */
public class ColaMapeos {
    
    private ConcurrentHashMap<String, Cola> mapa_StrIdCola_ObjCola = null;
    
    public ColaMapeos() {
        this.mapa_StrIdCola_ObjCola = new ConcurrentHashMap<>();
    }

    public ConcurrentHashMap<String, Cola> getMapa_StridCola_ObjCola() {
        return mapa_StrIdCola_ObjCola;
    }

    public void setMapa_StridCola_ObjCola(String idcola, Cola cola) {
        this.mapa_StrIdCola_ObjCola.put(idcola, cola);
    }
    
    public Cola get_StrIdCola_ObjCola(String idcola) {
        Cola retval = null;
        if (this.mapa_StrIdCola_ObjCola.containsKey(idcola)) {
            retval = this.getMapa_StridCola_ObjCola().get(idcola);
        }
        return retval;
    }
    
    
}
