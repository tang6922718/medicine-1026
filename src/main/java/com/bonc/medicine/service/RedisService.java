package com.bonc.medicine.service;

import java.util.List;


/**
 * @author boncajin
 *
 */
public interface RedisService {

     boolean set(String key, String value) throws Exception;

     String get(String key) throws Exception;

     /** 
     * @Title: expire 
     * @Description: 给KEY设置生存时间
     * @param key
     * @param expire
     * @return
     * @throws Exception
     * @return: boolean
     */
    boolean expire(String key, long expire) throws Exception;

     <T> boolean setList(String key, List<T> list) throws Exception;

     <T> List<T> getList(String key, Class<T> clz) throws Exception;

     /** 
     * @Title: lpush 
     * @Description: 将一个或多个值 value 插入到列表 key 的表头
     * @param key
     * @param obj
     * @return
     * @throws Exception
     * @return: 执行 LPUSH 命令后，列表的长度。
     */
    long lpush(String key, Object obj) throws Exception;

     /** 
     * @Title: rpush 
     * @Description: 将一个或多个值 value 插入到列表 key 的表尾(最右边)。
     * @param key
     * @param obj
     * @return
     * @throws Exception
     * @return: long 执行 RPUSH 操作后，表的长度。
     */
    long rpush(String key, Object obj) throws Exception;

     /** 
     * @Title: hmset 
     * @Description: 同时将多个 field-value (域-值)对设置到哈希表 key 中。
     * @param key
     * @param obj
     * @throws Exception
     * @return: void
     */
    void hmset(String key, Object obj) throws Exception;

     <T> T hget(String key, Class<T> clz) throws Exception;


     void del(String key) throws Exception;

     <T> List<T>  hmGetAll(String key, Class<T> clz) throws Exception;

     String lpop(String key) throws Exception;

     void   publishMessage(String channel, String message);

    /**
     * 操作redis中的set添加数据
     * @param key
     * @throws Exception
     */
    long setAdd(String key, Object obj) throws Exception;
    long setCard(final String key);
}
