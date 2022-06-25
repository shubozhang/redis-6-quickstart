package com.redis6

import redis.clients.jedis.Jedis
import spock.lang.Specification

import static org.junit.Assert.assertTrue

class AppTest extends Specification {
    private Jedis jedis

    void setup() {
        jedis = new Jedis("127.0.0.1",16379)
    }

    void cleanup() {
        jedis.close()
    }

    def "test_set_command"(){
        given:
        String nameKey = "nameKey"
        String nameValue = "lucy"
        jedis.set(nameKey, nameValue)

        when:
        String posName = jedis.get(nameKey)
        String negName = jedis.get("null")

        then:
        assertTrue (nameValue == posName)
        assertTrue (negName == null)

        cleanup:
        jedis.del(nameKey)
    }

    def "test_mset_mget_command"(){
        given:
        jedis.mset("k1", "v1", "k2", "v2", "k3", "v3")

        when:
        List<String> mget = jedis.mget("k1", "k3")

        then:
        assertTrue (mget.size() == 2)
        assertTrue (mget.get(0) == "v1")
        assertTrue (mget.get(1) == "v3")

        cleanup:
        jedis.del("k1")
        jedis.del("k2")
        jedis.del("k3")
    }

    def "test_keys_command"(){
        given:
        jedis.mset("key1", "v1", "key2", "v2", "key3", "v3")

        when:
        Set<String> keys = jedis.keys("key*")

        then:
        assertTrue (keys.size() == 3)

        cleanup:
        jedis.del(keys.toArray(new String[keys.size()]))
    }

    def "test_lpush_list"(){
        given:
        String key = "list_keys"
        String v1 = "Arron"
        String v2 = "Bob"
        String v3 = "Caleb"
        jedis.lpush(key,v1, v2, v3)

        when:
        List<String> values = jedis.lrange(key, 0, -1);

        then:
        assertTrue(values.size() == 3)
        assertTrue(values.get(0) == v3)
        assertTrue(values.get(1) == v2)
        assertTrue(values.get(2) == v1)

        cleanup:
        jedis.del(key)
    }


    def "test_rpush_list"(){
        given:
        String key = "list_keys_rpush"
        String v1 = "Arron"
        String v2 = "Bob"
        String v3 = "Caleb"
        jedis.rpush(key,v1, v2, v3)

        when:
        List<String> values = jedis.lrange(key, 0, -1);

        then:
        assertTrue(values.size() == 3)
        assertTrue(values.get(0) == v1)
        assertTrue(values.get(1) == v2)
        assertTrue(values.get(2) == v3)

        cleanup:
        jedis.del(key)
    }
}



