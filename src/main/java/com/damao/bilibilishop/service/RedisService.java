package com.damao.bilibilishop.service;

/**
 * 封装redisTemplate
 * @author 呆毛
 */
public interface RedisService {

    /**
     * 存储数据
     *
     * @param key
     * @param value
     */
    void set(String key, String value);


    /**
     * 带上过期时间的存储数据
     *
     * @param key
     * @param value
     * @param expire
     */
    void set(String key, String value, long expire);

    /**
     * 获取数据
     *
     * @param key
     * @return
     */
    String get(String key);

    /**
     * 设置过期时间
     *
     * @param key
     * @param expire
     * @return
     */
    boolean expire(String key, long expire);

    /**
     * 删除数据
     *
     * @param key
     */
    void remove(String key);
}
