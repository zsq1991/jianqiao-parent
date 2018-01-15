package com.zc.common.config.redisCache;


/**
 *@Author: Yaowei
 *@Description:
 *@Date: 2017/12/5 11:13
 */
public interface RedisService {
        /**
         * 批量删除对应的value
         *
         * @param keys
         */
        public void remove(String... keys);
        
        /**
         * 批量删除key
         *
         * @param pattern
         */
        public void removePattern(String pattern) ;
        
        /**
         * 删除对应的value
         *
         * @param key
         */
        public void remove(String key) ;
        
        /**
         * 判断缓存中是否有对应的value
         *
         * @param key
         * @return
         */
        public boolean exists(String key);
        
        /**
         * 读取缓存
         *
         * @param key
         * @return
         */
        public Object get(String key) ;
        
        /**
         * 写入缓存
         *
         * @param key
         * @param value
         * @return
         */
        public boolean set(String key, Object value) ;
        
        /**
         * 写入缓存
         *
         * @param key
         * @param value
         * @return
         */
        public boolean set(String key, Object value, Long expireTime) ;
}
