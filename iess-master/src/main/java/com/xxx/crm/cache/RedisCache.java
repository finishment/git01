package com.xxx.crm.cache;

import com.xxx.crm.utils.ApplicationContextUtils;
import org.apache.ibatis.cache.Cache;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;


// 自定义Redis缓存实现
public class RedisCache implements Cache {

    // 当前放入缓存的mapper的namespace（即此时的缓存在被那个namespace使用）
    private final String id;

    // 必须存在该构造方法
    public RedisCache(String id) {
        //System.out.println("id：====================================> " +id);
        this.id = id;
    }

    // 返回cache的唯一标识
    @Override
    public String getId() {
        return this.id;
    }

    // 将值放入缓存  redis: RedisTemplate StringRedisTemplate
    @Override
    public void putObject(Object key, Object value) {

        //System.out.println("put key = " + key.toString());
        //System.out.println("put value = " + value);

        // 使用redis的hash类型作为缓存存储模型  key  hashkey  value
        getRedisTemplate().opsForHash().put(id,getKeyToMD5( key.toString()), value);
    }

    // 获取缓存中的数据
    @Override
    public Object getObject(Object key) {

        //System.out.println("get key = " + key.toString());

        // 根据key从redis的hash类型中获取数据
        Object object = getRedisTemplate().opsForHash().get(id, getKeyToMD5(key.toString()));

        return object;
    }

    /**
     * 根据指定的key删除缓存，该方法为mybatis的保留方法，默认没有实现。后续版本可能会实现
     * @param key
     * @return
     */
    @Override
    public Object removeObject(Object key) {

        //System.out.println("根据指定的key删除缓存");
        return null;
    }

    /**
     * 清空缓存，只要发生增删改的任意一个方法，立马就执行此方法清空缓存
     */
    @Override
    public void clear() {
        //System.out.println("清空缓存");

        // 清空这个namespace
        getRedisTemplate().delete(id);
    }

    /**
     * 用来计算缓存数量
     * @return
     */
    @Override
    public int getSize() {

        // 获取hash 中的 key-value 数量
        return getRedisTemplate().opsForHash().size(id).intValue();
    }


    /**
     * 封装redisTemplate
     * @return
     */
    private RedisTemplate getRedisTemplate() {

        // 通过 ApplicationContextUtils 工具类获取 RedisTemplate 对象
        RedisTemplate redisTemplate = (RedisTemplate) ApplicationContextUtils.getBean("redisTemplate");
        // 设置key的序列化方式
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());

        return redisTemplate;
    }

    /**
     * 封装一个对key进行MD5加密处理的方法
     * @param key
     * @return
     */
    private String getKeyToMD5(String key) {
        return DigestUtils.md5DigestAsHex(key.getBytes());
    }
}
