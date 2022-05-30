/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rtdb;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import rabbitmq.RabbitMensaje;
import redis.clients.jedis.Jedis;

/**
 *
 * @author mgiannini
 */
public class JedisComandos {
    
    public static void ingresaDato(String key, String value) {
        Jedis pool = LibJedis.getResource();
        pool.set(key, value);
    }
    
    public static RabbitMensaje obtieneDato(String key) throws JsonProcessingException {
        Jedis pool = LibJedis.getResource();
        RabbitMensaje llamada = new ObjectMapper().readValue(pool.get(key), RabbitMensaje.class);
        return llamada;
    }
    
    public static void eliminaDato(String key) {
        Jedis pool = LibJedis.getResource();
        pool.del(key);
    }
}
