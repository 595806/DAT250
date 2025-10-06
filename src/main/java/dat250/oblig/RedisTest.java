package dat250.oblig;


import redis.clients.jedis.UnifiedJedis;

import java.util.HashMap;
import java.util.Map;

public class RedisTest {
    static Map<String, Map<String, Integer>> db = Map.of(
            "poll:1", Map.of("yes", 269, "no", 268, "meh", 42)
    );

    static Map<String, Map<String, Integer>> cache = new HashMap<>();


    public static void main(String[] args) {
        UnifiedJedis jedis = new UnifiedJedis("redis://localhost:6379");

        //SET user bob
        String res1 = jedis.set("user", "bob");
        System.out.println(res1);

        //GET user bob
        String res2 = jedis.get("user");
        System.out.println(res2);

        //SET user alice
        String res3 = jedis.set("user", "alice");
        System.out.println(res3);

        //GET user alice
        String res4 = jedis.get("user");
        System.out.println(res4);

        //EXPIRE user 5
        long res5 = jedis.expire("user", 5);
        System.out.println(res5);

        //TTL user
        long res6 = jedis.ttl("user");
        System.out.println(res6);

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        //TTL user
        long res7 = jedis.ttl("user");
        System.out.println(res7);

        //GET user alice
        String res8 = jedis.get("user");
        System.out.println(res8);

        //Case 2 Complex information
        String key = "poll:1";
        long res9 = jedis.hset(key, "id", "test");
        System.out.println(res9);

        long res10 = jedis.hset(key, "title", "Pineapple on Pizza?");
        System.out.println(res10);

        long res11 = jedis.hset(key, "option:yes", "269");
        System.out.println(res11);
        long res12 = jedis.hset(key, "option:no",  "268");
        System.out.println(res12);
        long res13 = jedis.hset(key, "option:meh", "42");
        System.out.println(res13);

        Map<String,String> res14 = jedis.hgetAll(key);
        System.out.println(res14);

        long res15 = jedis.hincrBy(key, "option:yes", 1);
        System.out.println("option:yes -> " + res15);

        Map<String,String> res16 = jedis.hgetAll(key);
        System.out.println(res16);

        // To simulate that entry exists in cache
        //cache.put(key, Map.of("yes", 269, "no", 268, "meh", 42));

        if (cache.containsKey(key)) {
            System.out.println("Loaded from cache: " + cache.get(key));
        } else {
            System.out.println("Not in cache, loading from db");
            Map<String, Integer> votes = db.get(key);
            cache.put(key, votes);
            System.out.println("Loaded from DB: " + votes);
        }



        jedis.close();

    }
}
