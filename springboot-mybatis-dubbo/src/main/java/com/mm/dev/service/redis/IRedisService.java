package com.mm.dev.service.redis;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations;

import com.common.base.constant.BaseConstant;

/**
 * @Description: redisServer
 * @author Jacky
 * @date 2017年7月29日 下午1:42:41
 */
public interface IRedisService extends BaseConstant {

    /**
     * 通过key删除
     * 
     * @param key
     */
    public abstract boolean delete(Serializable key) throws Exception;

    /**
     * 通过key删除
     * 
     * @param key
     */
    public abstract boolean delete(Collection<Serializable> keys) throws Exception;

    /**
     * 添加key value 并且设置存活时间(value)
     * 
     * @param key
     * @param value
     * @param expire
     *            单位秒
     */
    public abstract void set(Serializable key, Serializable value, long expire) throws Exception;

    /**
     * 添加key value(value)
     * 
     * @param key
     * @param value
     */
    public abstract void set(Serializable key, Serializable value) throws Exception;

    /**
     * 获取redis value (value)
     * 
     * @param key
     * @return
     */
    public abstract Serializable get(Serializable key) throws Exception;

    /**
     * 添加key value(hash)
     * 
     * @param key
     * @param value
     */
    public abstract void set(Object objKey, Serializable key, Serializable value) throws Exception;

    /**
     * 获取key value(hash)
     * 
     * @param key
     * @param value
     */
    public abstract Object get(Object objKey, Serializable key) throws Exception;

    /**
     * 通过正则匹配keys
     * 
     * @param pattern
     * @return
     */
    public abstract Set<Serializable> keys(String pattern) throws Exception;

    /**
     * 检查key是否已经存在
     * 
     * @param key
     * @return
     */
    public abstract boolean exists(Serializable key) throws Exception;

    /**
     * 清空redis 所有数据
     * 
     * @return
     */
    public abstract boolean flushDb() throws Exception;

    /**
     * 查看redis里有多少数据
     */
    public abstract long dbSize() throws Exception;

    /**
     * 检查是否连接成功
     * 
     * @return
     */
    public abstract boolean ping() throws Exception;

    /**
     * Hash对象
     * 
     * @return
     */
    @SuppressWarnings("rawtypes")
    public HashOperations redisHash() throws Exception;

    /**
     * List对象
     * 
     * @return
     */
    @SuppressWarnings("rawtypes")
    public ListOperations redisList() throws Exception;

    /**
     * Set对象
     * 
     * @return
     */
    @SuppressWarnings("rawtypes")
    public SetOperations redisSet() throws Exception;

    /**
     * ZSet对象
     * 
     * @return
     */
    @SuppressWarnings("rawtypes")
    public ZSetOperations redisZSet() throws Exception;

    /**
     * Value对象
     * 
     * @return
     */
    @SuppressWarnings("rawtypes")
    public ValueOperations redisValue() throws Exception;
    
    /**
     * redisTemplate
     * 
     * @return
     */
    @SuppressWarnings("rawtypes")
    public RedisTemplate redisTemplate() throws Exception;
    
    /**
     * @Description: 根据key设值
     * @Datatime 2017年7月29日 下午1:22:30 
     * @return boolean    返回类型
     */
    public boolean set(String key, String value) throws Exception;  
    
    /**
     * @Description: 根据key取值
     * @Datatime 2017年7月29日 下午1:23:11 
     * @return String    返回类型
     */
    public String get(String key) throws Exception; 
    
    /**
     * @Description: TODO(这里用一句话描述这个方法的作用)
     * @Datatime 2017年7月29日 下午1:23:38 
     * @return boolean    返回类型
     */
    public boolean expire(String key,long expire) throws Exception;  
    
    /**
     * @Description: 根据key设值list集合对象
     * @Datatime 2017年7月29日 下午1:32:11 
     * @param key
     * @param list
     * @return   
     * @see com.mm.dev.service.redis.IRedisService#setList(java.lang.String, java.util.List)
     */
    public <T> boolean setList(String key ,List<T> list) throws Exception;  
    
    /**
     * @Description: 根据key获取list集合对象
     * @Datatime 2017年7月29日 下午1:32:44 
     * @param key
     * @param clz
     * @return   
     * @see com.mm.dev.service.redis.IRedisService#getList(java.lang.String, java.lang.Class)
     */
    public <T> List<T> getList(String key,Class<T> clz) throws Exception;  
    
    /**
     * @Description: 将所有指定的值插入到存于 key 的列表的头部。如果 key 不存在，那么在进行 push 操作前会创建一个空列表。 如果 key 对应的值不是一个 list 的话，那么会返回一个错误。
     * @Datatime 2017年7月29日 下午1:33:58 
     * @param key
     * @param obj
     * @return   
     * @see com.mm.dev.service.redis.IRedisService#lpush(java.lang.String, java.lang.Object)
     */
    public long lpush(String key,Object obj) throws Exception;  
    
    /**
     * @Description: 将一个或多个值插入到列表的尾部(最右边)。如果列表不存在，一个空列表会被创建并执行 RPUSH 操作。 当列表存在但不是列表类型时，返回一个错误。
     * @Datatime 2017年7月29日 下午1:36:21 
     * @param key
     * @param obj
     * @return   
     * @see com.mm.dev.service.redis.IRedisService#rpush(java.lang.String, java.lang.Object)
     */
    public long rpush(String key,Object obj) throws Exception;  
    
    /**
     * @Description: 删除，并返回保存列表在key的第一个元素
     * @Datatime 2017年7月29日 下午1:36:50 
     * @param key
     * @return   
     * @see com.mm.dev.service.redis.IRedisService#lpop(java.lang.String)
     */
    public String lpop(String key) throws Exception;  
}
