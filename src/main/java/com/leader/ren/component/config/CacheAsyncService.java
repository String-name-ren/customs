package com.leader.ren.component.config;

import com.leader.ren.common.exception.CacheException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisStringCommands;
import org.springframework.data.redis.connection.ReturnType;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.types.Expiration;
import org.springframework.lang.Nullable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@ConditionalOnProperty(prefix = "cache", name = "enable", havingValue = "true")
public class CacheAsyncService {
    /**
     * 缓存标记
     */
    @Value("${cache.enable}")
    private Boolean enable = false;

    /**
     * 缓存
     */
    @Resource
    private RedisTemplate redisTemplate;

    public boolean exists(String key) {
        if (!enable) return false;
        try {
            return redisTemplate.hasKey(key);
        } catch (Exception ex) {
            return false;
        }
    }

    public boolean setNx(String key, Long expir) {
        boolean flag = false;
        try {
            flag = (Boolean) redisTemplate.execute(new RedisCallback() {
                @Nullable
                @Override
                public Object doInRedis(RedisConnection redisConnection) throws DataAccessException {
                    return redisConnection.setNX(key.getBytes(), longToByte(expir));
                }
            });
            return flag;
        } catch (Exception e) {
            log.error("取缓存异常, key = {}, ex = {}", key, e);
            return flag;
        }
    }

    public boolean setNx(String key, String value, long expires) {
        return setNx(key, value, expires, TimeUnit.SECONDS);
    }

    public boolean setNx(String key, String value, long expires, TimeUnit timeUnit) {
        boolean flag = false;
        try {
            flag = (boolean) redisTemplate.execute((RedisCallback<Boolean>) connection -> connection.set(key.getBytes(), value.getBytes(), Expiration.from(expires, timeUnit), RedisStringCommands.SetOption.ifAbsent()));
        } catch (Exception e) {
            log.error("设置缓存异常, key = {}", key, e);
        }
        return flag;
    }

    /**
     * long类型转成byte数组
     */
    private static byte[] longToByte(long number) {
        byte[] b = new byte[8];
        for (int i = 0; i < b.length; i++) {
            b[i] = new Long(number & 0xff).byteValue();// 将最低位保存在最低位 temp = temp
            // >> 8;// 向右移8位
        }
        return b;
    }

    public Long incrBy(String key, long val) {
        Long v = null;

        if (!enable) return v;

        try {
            v = redisTemplate.opsForValue().increment(key, val);
        } catch (Exception ex) {
            log.error("取缓存异常, key = {}, ex = {}", key, ex);
        }
        return v;
    }

    public Long incrBy(String key, String filed, long val) {
        Long v = null;

        if (!enable) return v;

        try {
            v = redisTemplate.opsForHash().increment(key, filed, val);
        } catch (Exception ex) {
            log.error("取缓存异常, key = {}, ex = {}", key, ex);
        }
        return v;
    }

    public Long incr(String key) {
        return this.incrBy(key, 1);
    }

    public Long incr(String key, long val, int interval, TimeUnit unit) {
        Long v = null;

        if (!enable) return v;

        try {
            v = this.incrBy(key, val);
            if (v == 1 && val > 0) { // val小于0表示回退，所以要忽略
                redisTemplate.expire(key, interval, unit);
            }
        } catch (Exception ex) {
            log.error("取缓存异常, key = {}, ex = {}", key, ex);
        }
        return v;
    }

    public Boolean expire(String key, int interval) {
        Boolean v = false;

        if (!enable) return v;

        try {
            redisTemplate.expire(key, interval, TimeUnit.SECONDS);
            v = true;
        } catch (Exception ex) {
            log.error("取缓存异常, key = {}, ex = {}", key, ex);
        }
        return v;
    }

    public Boolean expire(String key, int interval, TimeUnit unit) {
        Boolean v = false;

        if (!enable) return v;

        try {
            redisTemplate.expire(key, interval, unit);
            v = true;
        } catch (Exception ex) {
            log.error("取缓存异常, key = {}, ex = {}", key, ex);
        }
        return v;
    }

    public Boolean add(String key, Serializable value) {
        Boolean v = false;

        if (!enable) return v;

        try {
            redisTemplate.opsForValue().set(key, value);
            v = true;
        } catch (Exception ex) {
            log.error("取缓存异常, key = {}, ex = {}", key, ex);
        }
        return v;
    }

    public Boolean add(String key, Serializable value, int minutes) throws CacheException {
        Boolean v = false;

        if (!enable) return v;

        try {
            redisTemplate.opsForValue().set(key, value, minutes, TimeUnit.MINUTES);
            //redisTemplate.expire(key, minutes, TimeUnit.MINUTES);
            v = true;
        } catch (Exception ex) {
            log.error("取缓存异常, key = {}, ex = {}", key, ex);
        }
        return v;
    }

    public Boolean add(String key, Serializable value, int interval, TimeUnit unit) {
        Boolean v = false;

        if (!enable) return v;

        try {
            redisTemplate.opsForValue().set(key, value, interval, unit);
            //redisTemplate.expire(key, interval, unit);
            v = true;
        } catch (Exception ex) {
            log.error("取缓存异常, key = {}, ex = {}", key, ex);
        }
        return v;
    }

    public Boolean expire(String key, long interval, TimeUnit unit) {
        Boolean v = false;
        if (!enable) return v;
        try {
            redisTemplate.expire(key, interval, unit);
            v = true;
        } catch (Exception ex) {
            log.error("取缓存异常, key = {}, ex = {}", key, ex);
        }
        return v;
    }

    @Async
    public void addAsyn(final String key, final Serializable value) {

        if (!enable) return;

        add(key, value);
    }

    @Async
    public void addAsyn(final String key, final Serializable value, final int minutes) {

        if (!enable) return;

        add(key, value, minutes);
    }

    @Async
    public void addAsyn(final String key, final Serializable value, final int interval, final TimeUnit unit) {

        if (!enable) return;

        add(key, value, interval, unit);
    }

    public Object getAndSet(String key, Serializable value, int interval, TimeUnit unit) {
        Object v = null;

        if (!enable) return v;

        try {
            v = redisTemplate.opsForValue().getAndSet(key, value);
            if (v != null) {
                redisTemplate.expire(key, interval, unit);
            }
        } catch (Exception ex) {
            log.error("取缓存异常, key = {}, ex = {}", key, ex);
        }
        return v;
    }

    public Boolean addList(String key, Collection<Serializable> values) {
        Boolean v = false;

        if (!enable) return v;

        try {
            if (values != null && values.size() > 0) {
                redisTemplate.opsForList().leftPushAll(key, values);
            }
            v = true;
        } catch (Exception ex) {
            log.error("取缓存异常, key = {}, ex = {}", key, ex);
        }
        return v;
    }

    public <T> Boolean addList(String key, List<T> values) {
        Boolean v = false;

        if (!enable) return v;

        try {
            if (values != null && values.size() > 0) {
                Collection datas = values;
                redisTemplate.opsForList().leftPushAll(key, datas);
            }
            v = true;
        } catch (Exception ex) {
            log.error("取缓存异常, key = {}, ex = {}", key, ex);
        }
        return v;
    }


    public <T> Boolean rightPushAll(String key, List<T> values) {
        Boolean v = false;

        if (!enable) return v;

        try {
            if (values != null && !values.isEmpty()) {
                redisTemplate.opsForList().rightPushAll(key, values);
            }
            v = true;
        } catch (Exception ex) {
            log.error("取缓存异常, key = {}, ex = {}", key, ex);
        }
        return v;
    }

    public Boolean addList(String key, Collection values, int minutes) {
        Boolean v = false;

        if (!enable) return v;

        try {
            redisTemplate.opsForList().leftPushAll(key, values);
            redisTemplate.expire(key, minutes, TimeUnit.MINUTES);
            v = true;
        } catch (Exception ex) {
            log.error("取缓存异常, key = {}, ex = {}", key, ex);
        }
        return v;
    }

    public Boolean addList(String key, List values, int minutes) {
        Boolean v = false;

        if (!enable) return v;

        try {
            redisTemplate.opsForList().leftPushAll(key, values);
            redisTemplate.expire(key, minutes, TimeUnit.MINUTES);
            v = true;
        } catch (Exception ex) {
            log.error("取缓存异常, key = {}, ex = {}", key, ex);
        }
        return v;
    }


    public <T> Boolean listAppend(final String key, T value) {
        Boolean v = false;

        if (!enable) return v;

        try {
            if (null != value) {
                redisTemplate.opsForList().leftPush(key, value);
            }
            v = true;
        } catch (Exception ex) {
//            ex.printStackTrace();
//            throw new CacheException(ex, ex.getMessage());
            log.error("取缓存异常, key = {}, ex = {}", key, ex);
        }
        return v;
    }

    @Async
    public <T> void listAppendAsyn(final String key, T value) {
        listAppend(key, value);
    }

    @Async
    public void addListAsyn(final String key, final Collection<Serializable> values) {

        if (!enable) return;

        addList(key, values);
    }

    @Async
    public void addListAsyn(final String key, final Collection<Serializable> values, final int minutes) {

        if (!enable) return;

        addList(key, values, minutes);
    }

    public Boolean addMap(String key, Map<Object, Object> valueMap) {
        Boolean v = false;

        if (!enable) return v;

        try {
            if (valueMap != null && valueMap.keySet().size() > 0) {
                redisTemplate.opsForHash().putAll(key, valueMap);
            }
            v = true;
        } catch (Exception ex) {
            log.error("取缓存异常, key = {}, ex = {}", key, ex);
        }
        return v;
    }

    public Boolean addMap(String key, Map<Object, Object> valueMap, int minutes) {
        Boolean v = false;

        if (!enable) return v;

        try {
            redisTemplate.opsForHash().putAll(key, valueMap);
            redisTemplate.expire(key, minutes, TimeUnit.MINUTES);
            v = true;
        } catch (Exception ex) {
            log.error("取缓存异常, key = {}, ex = {}", key, ex);
        }
        return v;
    }

    @Async
    public void addMapAsyn(final String key, final Map<Object, Object> valueMap) {

        if (!enable) return;

        addMapAsyn(key, valueMap);
    }

    @Async
    public void addMapAsyn(final String key, final Map<Object, Object> valueMap, final int minutes) {

        if (!enable) return;

        addMapAsyn(key, valueMap, minutes);
    }

    public Map<Object, Object> getMap(String key) {
        Map<Object, Object> v = null;

        if (!enable) return v;

        try {
            v = redisTemplate.opsForHash().entries(key);
        } catch (Exception ex) {
            log.error("取缓存异常, key = {}, ex = {}", key, ex);
        }
        return v;
    }

    public Boolean addOneToList(String key, Serializable value) {
        Boolean v = false;

        if (!enable) return v;

        try {
            redisTemplate.opsForList().leftPush(key, value);
            v = true;
        } catch (Exception ex) {
            log.error("取缓存异常, key = {}, ex = {}", key, ex);
        }
        return v;
    }

    @Async
    public void addOneToListAsyn(final String key, final Serializable value) {

        if (!enable) return;

        addOneToList(key, value);
    }

    public Object get(String key) {
        Object v = null;

        if (!enable) return v;

        try {
            v = redisTemplate.opsForValue().get(key);
        } catch (Exception ex) {
            //ex.printStackTrace();
            //remove(key);
            log.error("取缓存异常, key = {}, ex = {}", key, ex);
        }
        return v;
    }

    public List<Serializable> getList(String key) {
        List<Serializable> v = null;

        if (!enable) return v;

        try {
            Long size = redisTemplate.opsForList().size(key);
            v = redisTemplate.opsForList().range(key, 0, size);
        } catch (Exception ex) {
//            remove(key);
//            throw new CacheException(ex, ex.getMessage());
            log.error("取缓存异常, key = {}, ex = {}", key, ex);
        }
        return v;
    }

    public <T> List<T> getObjectList(String key) {
        List<T> v = null;

        if (!enable) return v;

        try {
            Long size = redisTemplate.opsForList().size(key);
            v = redisTemplate.opsForList().range(key, 0, size);
        } catch (Exception ex) {
//            remove(key);
//            throw new CacheException(ex, ex.getMessage());
            log.error("取缓存异常, key = {}, ex = {}", key, ex);
        }
        return v;
    }

    public Serializable getListFirstOne(String key) {
        Serializable v = null;

        if (!enable) return v;

        try {
            List<Serializable> le = redisTemplate.opsForList().range(key, 0, 1);
            if (le != null && le.size() > 0) {
                v = le.get(0);
            }
        } catch (Exception ex) {
//            remove(key);
//            throw new CacheException(ex, ex.getMessage());
            log.error("取缓存异常, key = {}, ex = {}", key, ex);
        }
        return v;
    }

    public Integer getCountLike(String keyPrefix) {
        Integer v = null;

        if (!enable) return v;

        try {
            if (StringUtils.isNotEmpty(keyPrefix)) {
                Set<String> matchedCacheKeys = redisTemplate.keys(keyPrefix + "*");
                v = matchedCacheKeys.size();
            }
        } catch (Exception ex) {
//            removeLike(keyPrefix);
//            throw new CacheException(ex, ex.getMessage());
            log.error("取缓存异常, keyPrefix = {}, ex = {}", keyPrefix, ex);
        }
        return v;
    }

    public Boolean remove(String key) {
        Boolean v = false;

        if (!enable) return v;

        try {
            redisTemplate.delete(key);
            v = true;
        } catch (Exception ex) {
//            ex.printStackTrace();
//            throw new CacheException(ex, ex.getMessage());
            log.error("取缓存异常, key = {}, ex = {}", key, ex);
        }
        return v;
    }

    public Boolean removeLike(String keyPrefix) {
        Boolean v = false;

        if (!enable) return v;

        try {
            if (StringUtils.isNotEmpty(keyPrefix)) {
                Set<String> matchedCacheKeys = redisTemplate.keys(keyPrefix + "*");
                for (String cacheKey : matchedCacheKeys) {
                    this.remove(cacheKey);
                }
            }
            v = true;
        } catch (Exception ex) {
            log.error("取缓存异常, keyPrefix = {}, ex = {}", keyPrefix, ex);
        }
        return v;
    }

    public Set<String> getAllKeyByKeyPrefix(String keyPrefix) {

        Set<String> matchedCacheKeys = null;
        try {
            if (StringUtils.isNotEmpty(keyPrefix)) {
                matchedCacheKeys = redisTemplate.keys(keyPrefix + "*");
            }
        } catch (Exception ex) {
            log.error("取缓存异常, keyPrefix = {}, ex = {}", keyPrefix, ex);
        }
        return matchedCacheKeys;
    }

    public void lpushList(String key, final Serializable value) {
        if (!enable) return;
        redisTemplate.opsForList().leftPush(key, value);
    }

    public Object rpopList(String key) {
        if (!enable) return null;
        return redisTemplate.opsForList().rightPop(key);
    }

    public Long getListSize(String key) {
        Long v = null;

        if (!enable) return v;

        try {
            v = redisTemplate.opsForList().size(key);
        } catch (Exception ex) {
//            remove(key);
//            ex.printStackTrace();
            log.error("取缓存异常, key = {}, ex = {}", key, ex);
        }
        return v;
    }

    public List<Serializable> popList(String key, int offset) {
        List<Serializable> v = null;

        if (!enable) return v;

        try {
            v = redisTemplate.opsForList().range(key, 0, offset - 1);
            for (Serializable item : v) {
                redisTemplate.opsForList().remove(key, 0, item);
            }
        } catch (Exception ex) {
            log.error("取缓存异常, key = {}, ex = {}", key, ex);
        }
        return v;
    }

    public void hset(String key, String field, Serializable value, int interval, TimeUnit timeUnit) {
        if (!enable) return;
        try {
            redisTemplate.opsForHash().put(key, field, value);
            if (interval > 0) {
                redisTemplate.expire(key, interval, timeUnit);
            }
        } catch (Exception ex) {
            log.error("取缓存异常, key = {}, ex = {}", key, ex);
        }
    }

    public void hset(String key, String field, Serializable value, int minutes) {
        if (!enable) return;
        try {
            redisTemplate.opsForHash().put(key, field, value);
            if (minutes > 0) {
                redisTemplate.expire(key, minutes, TimeUnit.MINUTES);
            }
        } catch (Exception ex) {
            log.error("取缓存异常, key = {}, ex = {}", key, ex);
        }
    }

    public void hset(String key, String field, Serializable value) {
        if (!enable) return;
        try {
            redisTemplate.opsForHash().put(key, field, value);
        } catch (Exception ex) {
            log.error("取缓存异常, key = {}, ex = {}", key, ex);
        }
    }

    public Object hget(String key, String field) {
        if (!enable) return null;
        try {
            return redisTemplate.opsForHash().get(key, field);
        } catch (Exception ex) {
            log.error("取缓存异常, key = {}, ex = {}", key, ex);
        }
        return null;
    }

    @Async
    public void hsetAsyn(final String key, final String field, final Serializable value,
                         final int interval, final TimeUnit timeUnit) {
        if (!enable) return;

        redisTemplate.opsForHash().put(key, field, value);
        if (interval > 0) {
            redisTemplate.expire(key, interval, timeUnit);
        }
    }

    @Async
    public void hsetAsyn(final String key, final String field, final Serializable value, final int minutes) {
        if (!enable) return;

        redisTemplate.opsForHash().put(key, field, value);
        if (minutes > 0) {
            redisTemplate.expire(key, minutes, TimeUnit.MINUTES);
        }
    }

    @Async
    public void hsetAsyn(final String key, final String field, final Serializable value) {
        if (!enable) return;

        redisTemplate.opsForHash().put(key, field, value);
    }

    public Object hgetAsyn(final String key, final String field) {
        if (!enable) return null;

        redisTemplate.opsForHash().get(key, field);

        return null;
    }

    public Boolean hdel(String key, String field) {
        if (!enable) return null;
        try {
            Long cnt = redisTemplate.opsForHash().delete(key, field);

            return true;
        } catch (Exception ex) {
            log.error("取缓存异常, key = {}, ex = {}", key, ex);
        }
        return false;
    }

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    public RedisTemplate<String, Serializable> getRedisCacheTemplate() {
        return redisTemplate;
    }

    public void setRedisCacheTemplate(RedisTemplate<String, Serializable> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public <V> Boolean zadd(String key, V value, double score) {
        return redisTemplate.opsForZSet().add(key, value, score);
    }

    public <V> Set<V> rangeByScore(String key, double min, double max, long offset, long count) {
        return redisTemplate.opsForZSet().rangeByScore(key, min, max, offset, count);
    }

    public Long zRemove(String key, Object... values) {
        return this.redisTemplate.opsForZSet().remove(key, values);
    }

    public void subscribe(String channel, Object message) {
        redisTemplate.convertAndSend(channel, message);
    }

    public Object deserialize(byte[] body) {
        return redisTemplate.getValueSerializer().deserialize(body);
    }

    /**
     * 添加分布式锁
     *
     * @param key      key
     * @param value    value
     * @param expires  过期时间
     * @param timeUnit 过期时间单位
     * @return 是否添加成功
     */
    public boolean lock(String key, String value, long expires, TimeUnit timeUnit) {
        return setNx(key, value, expires, timeUnit);
    }

    /**
     * 释放分布式锁
     *
     * @param key   key
     * @param value value
     * @return 是否释放成功
     */
    public boolean releaseLock(String key, String value) {
        boolean flag = false;
        String releaseScript = "if redis.call(\"get\",KEYS[1]) == ARGV[1] then return redis.call(\"del\",KEYS[1]) else return 0 end";
        Long success = 1L;
        try {
            Long result = (Long) redisTemplate.execute(
                    (RedisConnection connection) -> connection.eval(
                            releaseScript.getBytes(),
                            ReturnType.INTEGER,
                            1,
                            key.getBytes(),
                            value.getBytes())
            );
            flag = success.equals(result);
        } catch (Exception e) {
            log.error("释放锁异常, key = {}", key, e);
        }
        return flag;
    }
}
