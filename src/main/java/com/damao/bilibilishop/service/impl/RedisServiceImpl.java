package com.damao.bilibilishop.service.impl;

import com.damao.bilibilishop.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author 呆毛
 */
@Service
public class RedisServiceImpl implements RedisService {

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Override
    public void set(String key, String value) {
        stringRedisTemplate.opsForValue().set(key, value);
    }

    @Override
    public void set(String key, String value, long expire) {
        stringRedisTemplate.opsForValue().set(key, value, expire, TimeUnit.SECONDS);
    }

    @Override
    public String get(String key) {
        return stringRedisTemplate.opsForValue().get(key);
    }

    @Override
    public boolean expire(String key, long expire) {
        return stringRedisTemplate.expire(key, expire, TimeUnit.SECONDS);
    }

    @Override
    public void remove(String key) {
stringRedisTemplate.delete(key);
    }

    @Override
    public Long incr(String key, Long liveTime) {
        RedisAtomicLong entityIdCounter = new RedisAtomicLong(key, stringRedisTemplate.getConnectionFactory());
        Long increment = entityIdCounter.getAndIncrement();
        //初始设置过期时间
        if ((null == increment || increment.longValue() == 0) && liveTime > 0) {
            entityIdCounter.expire(liveTime, TimeUnit.SECONDS);
        }

        return increment;
    }
}
