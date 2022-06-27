package com.redis6;

import redis.clients.jedis.Jedis;

import java.util.Random;

public class PhoneCode {
    private static final int DIGIT_LENGTH = 6;
    private static final Random random = new Random();

    private static final int TTL = 120;

    private static final int MAX_SUBMISSION = 3;
    private static final int ONE_DAY = 24 * 60 * 60;

    private static final Jedis jedis = new Jedis("192.168.44.168", 6379);

    public static void main(String[] args) {
        //模拟验证码发送
        setCode("13678765435");

        //模拟验证码校验
        //getRedisCode("13678765435","4444");
    }


    public static boolean verifyCode(String phone, String code) {
        String codeKey = "VerifyCode" + phone + ":code";
        String redisCode = jedis.get(codeKey);
        
        return redisCode.equals(code);
    }

    // 1) TTL: 120 seconds
    // 2) one phone can submit maximum 3 times
    public static void setCode(String phone) {

        String countKey = "VerifyCode" + phone + ":count";
        String codeKey = "VerifyCode" + phone + ":code";

        String count = jedis.get(countKey);
        if (count == null) {
            // first time submission
            jedis.setex(countKey, ONE_DAY, "1");
        } else if (Integer.parseInt(count) < MAX_SUBMISSION) {
            jedis.incr(countKey);
        } else if (Integer.parseInt(count) >= MAX_SUBMISSION) {
            System.out.println("This phone has reached " + MAX_SUBMISSION + " max submissions.");
        }

        //persist vcode to Redis
        String vcode = getCode();
        jedis.setex(codeKey, TTL, vcode);
    }

    //1 generate a random code
    public static String getCode() {
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < DIGIT_LENGTH; i++) {
            int rand = random.nextInt(10);
            code.append(rand);
        }
        return code.toString();
    }
}

