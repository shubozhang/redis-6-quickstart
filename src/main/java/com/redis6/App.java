package com.redis6;

import redis.clients.jedis.Jedis;
import java.util.List;
import java.util.Set;

/**
 * @author szhang
 */
public class App {
    public static void main(String[] args) {
        Jedis jedis =new Jedis("127.0.0.1",16379);
        System.out.println(jedis.ping());
        jedis.close();
    }

    //操作zset
    public void demo5() {
        //创建Jedis对象
        Jedis jedis = new Jedis("192.168.44.168",6379);

        jedis.zadd("china",100d,"shanghai");

        Set<String> china = jedis.zrange("china", 0, -1);
        System.out.println(china);

        jedis.close();
    }

    //操作hash
    public void demo4() {
        //创建Jedis对象
        Jedis jedis = new Jedis("192.168.44.168",6379);

        jedis.hset("users","age","20");
        String hget = jedis.hget("users", "age");
        System.out.println(hget);
        jedis.close();
    }

    //操作set
    public void demo3() {
        //创建Jedis对象
        Jedis jedis = new Jedis("192.168.44.168",6379);

        jedis.sadd("names","lucy");
        jedis.sadd("names","mary");

        Set<String> names = jedis.smembers("names");
        System.out.println(names);
        jedis.close();
    }

}
