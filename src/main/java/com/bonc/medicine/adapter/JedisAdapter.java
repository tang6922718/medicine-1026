package com.bonc.medicine.adapter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.HashSet;
import java.util.Set;


@SuppressWarnings("Duplicates")
@Service
public class JedisAdapter implements InitializingBean {

	private static final Logger logger = LoggerFactory.getLogger(JedisAdapter.class);

	
	@Value("${spring.redis.host}")
	private String host;

	@Value("${spring.redis.port}")
	private String port;

    private Jedis jedis = null;
	
    private JedisPool jedisPool = null;
    

    @Override
    public void afterPropertiesSet() throws Exception {
        //初始化
        jedisPool = new JedisPool(host, 6379);
    }

        //获取一个Jedis
    private Jedis getJedis(){
        try{
            jedis =  jedisPool.getResource();
        }catch (Exception e){
            logger.error(" 获取jedis失败！" + e.getMessage());
        }finally {
            if(jedis != null){
                jedis.close();
            }
        }
        return jedis;
    }


     /* 获取Redis中集合中某个key值
     * @param key
     * @return
*/

    public String get(String key){
        Jedis jedis = null;
        try {
            jedis =  jedisPool.getResource();
            return jedis.get(key);
        
        }catch (Exception e){
            logger.error("Jedis get发生异常  "  + e.getMessage());
            return null;
        }finally {
            if(jedis != null){
                jedis.close();
            }
        }
    }

/*
     * 给Redis中Set集合中某个key值设值
     * @param key
     * @param value
*/

    public void set(String key, String value){
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.set(key, value);
        }catch (Exception e){
            logger.error("Jedis set  异常" + e.getMessage());
        }finally {
            if(jedis != null){
                jedis.close();
            }
        }
    }

/*
     * 向Redis中Set集合添加值:点赞
     * @return
*/

    public long sadd(String key, String... value){
        Jedis jedis = null;
        try{
            jedis =  jedisPool.getResource();
            return jedis.sadd(key, value);
        }catch (Exception e){
            logger.error("Jedis sadd 异常  ：" + e.getMessage());
            return 0;
        }finally {
            if (jedis != null){
                jedis.close();
            }
        }
    }

/*
     * 移除：取消点赞
     * @param key
     * @param value
     * @return
*/

    public long srem(String key, String value){
        Jedis jedis = null;
        try{
            jedis =  jedisPool.getResource();
            return jedis.srem(key, value);
        }catch (Exception e){
            logger.error("Jedis srem 异常：" + e.getMessage());
            return 0;
        }finally {
            if (jedis != null){
                jedis.close();
            }
        }
    }

/*
     *判断key,value是否是集合中值
     * @param key
     * @param value
     * @return
*/

    public boolean sismember(String key, String value){
        Jedis jedis = null;
        try{
            jedis =  jedisPool.getResource();
            return jedis.sismember(key, value);
        }catch (Exception e){
            logger.error("Jedis  sismember 异常：" + e.getMessage());
            return false;
        }finally {
            if (jedis != null){
                try{
                    jedis.close();
                }catch (Exception e){
                    logger.error("Jedis 关闭异常" + e.getMessage());
                }
            }
        }
    }

/*
     * 获取集合大小
     * @param key
     * @return
*/

    public long scard(String key){
        Jedis jedis = null;
        try{
            jedis =  jedisPool.getResource();
            return jedis.scard(key);
        }catch (Exception e){
            logger.error("Jedis  scard 异常：" + e.getMessage());
            return 0;
        }finally {
            if (jedis != null){
                jedis.close();
            }
        }
    }
    
    public Set<String> sdiff(String... key){
    	Jedis jedis = null;
    	try{
    		jedis =  jedisPool.getResource();
    		return jedis.sdiff(key);
    	}catch (Exception e){
    		logger.error("Jedis  sdiff 异常：" + e.getMessage());
    		return new HashSet<>();
    	}finally {
    		if (jedis != null){
    			jedis.close();
    		}
    	}
    }

    public long expire(String key){
        Jedis jedis = null;
        try{
            jedis =  jedisPool.getResource();
            return jedis.expire(key, 43200);
        }catch (Exception e){
            logger.error("Jedis expire 异常：" + e.getMessage());
            return 0L;
        }finally {
            if (jedis != null){
                jedis.close();
            }
        }
    }

    public boolean exists(String key){
        Jedis jedis = null;
        try{
            jedis =  jedisPool.getResource();
            return jedis.exists(key);
        }catch (Exception e){
            logger.error("Jedis exists 异常：" + e.getMessage());
            return false;
        }finally {
            if (jedis != null){
                jedis.close();
            }
        }
    }

    /**
     * @Description: 这个方式是测试用的
     * @Param: []
     * @return: java.util.Set<java.lang.String>
     * @Author: hejiajun
     * @Date: 2018/9/7
     */
    public Set<String> keys(){
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.keys("*");
        } catch (Exception e) {
            logger.error("Jedis keys 异常：" + e.getMessage());
            return new HashSet();
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    public long rpush(String key, String... values) {
        Jedis jedis = null;
        try{
            jedis =  jedisPool.getResource();
            return jedis.rpush(key, values);
        }catch (Exception e){
            logger.error("Jedis keys 异常：" + e.getMessage());
            return 0L;
        }finally {
            if (jedis != null){
                jedis.close();
            }
        }
    }

    public long del(String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.del(key);
        } catch (Exception e) {
            logger.error("Jedis keys 异常：" + e.getMessage());
            return 0L;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }
}
