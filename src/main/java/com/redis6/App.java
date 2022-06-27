package com.redis6;

import redis.clients.jedis.Jedis;

/**
 * @author szhang
 */
public class App {
    public static void main(String[] args) {
        Jedis jedis =new Jedis("127.0.0.1",16379);
        System.out.println(jedis.ping());
        jedis.close();
    }
}
