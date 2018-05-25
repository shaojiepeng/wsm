package com.wsm.admin.redis;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class CustomRedisCache<K, V> implements Cache<K, V> {

    @Resource
    private RedisTemplate<String, V> redisTemplate;

    /**
     * 拼接上authorizationCache包名后的redis cache key的前缀
     */
    private final String cacheKeyPrefix;

    /**
     * doGetAuthorizationInfo 的过期时间, 10分钟。
     */
    private long expire = 600000L;

    /**
     * 同一包中的类可以访问
     *
     * @param name
     */
    protected CustomRedisCache(String name) {
        cacheKeyPrefix = name;
    }

    @Override
    public V get(K key) throws CacheException {
        return redisTemplate.opsForValue().get(this.cacheKeyPrefix + key);
    }

    @Override
    public V put(K key, V value) throws CacheException {
        V previos = get(key);
        redisTemplate.opsForValue().set(this.cacheKeyPrefix + key, value, this.expire, TimeUnit.MILLISECONDS);
        return previos;
    }

    @Override
    public V remove(K key) throws CacheException {
        V previos = get(key);
        redisTemplate.delete(this.cacheKeyPrefix + key);
        return previos;
    }

    @Override
    public void clear() throws CacheException {
        // 这里不用connection.flushDb(), 以免Session等其他缓存数据被连带删除
        Set<String> redisKeys = redisTemplate.keys(this.cacheKeyPrefix + "*");
        for (String redisKey : redisKeys) {
            redisTemplate.delete(redisKey);
        }
    }

    @Override
    public int size() {
        if (keys() == null)
            return 0;
        return keys().size();
    }

    @SuppressWarnings("unchecked")
    @Override
    public Set<K> keys() {
        Set<String> redisKeys = redisTemplate.keys(this.cacheKeyPrefix + "*");
        Set<K> keys = new HashSet<K>();
        for (String redisKey : redisKeys) {
            keys.add((K) redisKey.substring(this.cacheKeyPrefix.length()));
        }
        return keys;
    }

    @Override
    public Collection<V> values() {
        Set<String> redisKeys = redisTemplate.keys(this.cacheKeyPrefix + "*");
        Set<V> values = new HashSet<V>();
        for (String redisKey : redisKeys) {
            V value = redisTemplate.opsForValue().get(redisKey);
            values.add(value);
        }
        return values;
    }
}