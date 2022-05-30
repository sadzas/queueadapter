/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rtdb;

import queueadapter.Main;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 *
 * @author mgiannini
 */
public class LibJedis {
    
    private final static String RDSERVIDOR = Main.getRdServidor();
    private final static String RDPUERTO = Main.getRdPuerto();
    
    private final static JedisPool pool = new JedisPool(RDSERVIDOR,Integer.parseInt(RDPUERTO));

    public static Jedis getResource(){
        return pool.getResource();
    }
}
