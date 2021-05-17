package com.rlc.rlcbase.cache;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import com.rlc.rlcbase.listener.SpringInit;
import com.rlc.rlcbase.utils.SpringBeanUtil;
import com.rlc.rlcbase.utils.spring.SpringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
//import org.apache.shiro.cache.Cache;
//import org.apache.shiro.cache.CacheManager;
//import org.apache.shiro.cache.ehcache.EhCacheManager;

import net.sf.ehcache.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class CacheUtils{
    static Logger logger = LogManager.getLogger(CacheUtils.class);
    @Autowired
    @Lazy
    private static CacheManager cacheManager = SpringInit.getBean(CacheManager.class); //SpringUtils.getBean(CacheManager.class);

    private static final String SYS_CACHE = "sysCache";

    /**
     * 获取SYS_CACHE缓存
     *
     * @param key
     * @return
     */
    public static Object get(String key)
    {
        return get(SYS_CACHE, key);
    }

    /**
     * 获取SYS_CACHE缓存
     *
     * @param key
     * @param defaultValue
     * @return
     */
    public static Object get(String key, Object defaultValue)
    {
        Object value = get(key);
        return value != null ? value : defaultValue;
    }

    /**
     * 写入SYS_CACHE缓存
     *
     * @param key
     * @return
     */
    public static void put(String key, Object value)
    {
        put(SYS_CACHE, key, value);
    }

    /**
     * 从SYS_CACHE缓存中移除
     *
     * @param key
     * @return
     */
    public static void remove(String key)
    {
        remove(SYS_CACHE, key);
    }

    /**
     * 获取缓存
     *
     * @param cacheName
     * @param key
     * @return
     */
    public static Object get(String cacheName, String key)
    {
        return getCache(cacheName).get(getKey(key));
    }

    /**
     * 获取缓存
     *
     * @param cacheName
     * @param key
     * @param defaultValue
     * @return
     */
    public static Object get(String cacheName, String key, Object defaultValue)
    {
        Object value = get(cacheName, getKey(key));
        return value != null ? value : defaultValue;
    }

    /**
     * 写入缓存
     *
     * @param cacheName
     * @param key
     * @param value
     */
    public static void put(String cacheName, String key, Object value)
    {
        Element element = new Element(key, value);
        getCache(cacheName).put(element);
    }

    /**
     * 从缓存中移除
     *
     * @param cacheName
     * @param key
     */
    public static void remove(String cacheName, String key)
    {
        getCache(cacheName).remove(getKey(key));
    }
    /**
     * 获得一个Cache，没有则创建一个。
     * @param cacheName
     * @return
     */
    public static Cache getCache(String cacheName){
        Cache cache = cacheManager.getCache(cacheName);
        if (cache == null){
            cacheManager.addCache(cacheName);
            cache = cacheManager.getCache(cacheName);
            cache.getCacheConfiguration().setEternal(true);
        }
        return cache;
    }

    public static CacheManager getCacheManager() {
        return cacheManager;
    }
    /**
     * 从缓存中移除所有
     *
     * @param cacheName
     */
    public static void removeAll(String cacheName)
    {
        Cache cache = getCache(cacheName);
        List<String> keys = cache.getKeys();
        for (Iterator<String> it = keys.iterator(); it.hasNext();)
        {
            cache.remove(it.next());
        }
        logger.info("清理缓存： {} => {}", cacheName, keys);
    }

    /**
     * 从缓存中移除指定key
     *
     * @param keys
     */
    public static void removeByKeys(Set<String> keys)
    {
        removeByKeys(SYS_CACHE, keys);
    }

    /**
     * 从缓存中移除指定key
     *
     * @param cacheName
     * @param keys
     */
    public static void removeByKeys(String cacheName, Set<String> keys)
    {
        for (Iterator<String> it = keys.iterator(); it.hasNext();)
        {
            remove(it.next());
        }
        logger.info("清理缓存： {} => {}", cacheName, keys);
    }

    /**
     * 获取缓存键名
     *
     * @param key
     * @return
     */
    private static String getKey(String key)
    {
        return key;
    }

    /**
     * 获得一个Cache，没有则显示日志。
     *
     * @param cacheName
     * @return
     */
//    public static Cache<String, Object> getCache(String cacheName)
//    {
//        Cache<String, Object> cache = cacheManager.getCache(cacheName);
//        if (cache == null)
//        {
//            throw new RuntimeException("当前系统中没有定义“" + cacheName + "”这个缓存。");
//        }
//        return cache;
//    }
//
    /**
     * 获取所有缓存
     *
     * @return 缓存组
     */
    public static String[] getCacheNames()
    {
        String []cacheNames = cacheManager.getCacheNames();
        return  cacheNames;
//        return ((EhCacheManager) cacheManager).getCacheManager().getCacheNames();
    }
}