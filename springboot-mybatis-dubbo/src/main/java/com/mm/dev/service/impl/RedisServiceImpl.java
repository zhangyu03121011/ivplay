package com.mm.dev.service.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Service;

import com.common.util.ExceptionUtil;
import com.common.util.JSONUtil;
import com.mm.dev.service.IRedisService;

/**
 * @Description: redisServer
 * @author Jacky
 * @date 2017年7月29日 下午1:43:14
 */
@Service
public class RedisServiceImpl implements IRedisService {
	
	Logger logger = LoggerFactory.getLogger(RedisServiceImpl.class);

	@Autowired
	private RedisTemplate<Serializable, Serializable> redisTemplate;

	public boolean delete(Serializable key) throws Exception {
		boolean flag = false;
		try {
			redisTemplate.delete(key);
			flag = true;
			logger.info("[RedisServiceImpl][delete][key=" + key + "][flag=" + flag + "]");
		} catch (Exception e) {
			String msg = ExceptionUtil.getInstance().getExceptionMsg(e);
			logger.error(msg, e);
			throw new Exception(msg, e);
		}
		return flag;
	}

	public boolean delete(Collection<Serializable> keys) throws Exception {
		boolean flag = false;
		try {
			redisTemplate.delete(keys);
			flag = true;
			logger.info("[RedisServiceImpl][delete][keys=" + keys + "][flag=" + flag + "]");
		} catch (Exception e) {
			String msg = ExceptionUtil.getInstance().getExceptionMsg(e);
			logger.error(msg, e);
			throw new Exception(msg, e);
		}
		return flag;
	}
	
	public void set(Serializable key, Serializable value, long expire) throws Exception {
		try {
			redisTemplate.opsForValue().set(key, value, expire, TimeUnit.SECONDS);
			logger.info("[RedisServiceImpl][set][key=" + key + "][expire=" + expire + "]");
		} catch (Exception e) {
			String msg = ExceptionUtil.getInstance().getExceptionMsg(e);
			logger.error(msg, e);
			throw new Exception(msg, e);
		}
	}

	public void set(Serializable key, Serializable value) throws Exception {
		try {
			redisTemplate.opsForValue().set(key, value);
			logger.info("[RedisServiceImpl][set][key=" + key + "]");
		} catch (Exception e) {
			String msg = ExceptionUtil.getInstance().getExceptionMsg(e);
			logger.error(msg, e);
			throw new Exception(msg, e);
		}
	}

	public void set(Object objKey, Serializable key, Serializable value) throws Exception {
		try {
			redisTemplate.opsForHash().put(key, objKey, value);
			logger.info("[RedisServiceImpl][set][objKey=" + objKey + "][key=" + key + "]");
		} catch (Exception e) {
			String msg = ExceptionUtil.getInstance().getExceptionMsg(e);
			logger.error(msg, e);
			throw new Exception(msg, e);
		}
	}

	public Object get(Object objKey, Serializable key) throws Exception {
		Object obj = null;
		try {
			obj = redisTemplate.opsForHash().get(key, objKey);
			logger.info("[RedisServiceImpl][get][key=" + key + "]");
		} catch (Exception e) {
			String msg = ExceptionUtil.getInstance().getExceptionMsg(e);
			logger.error(msg, e);
			throw new Exception(msg, e);
		}
		return obj;
	}

	public Serializable get(Serializable key) throws Exception {
		Serializable obj = null;
		try {
			obj = redisTemplate.opsForValue().get(key);
			logger.info("[RedisServiceImpl][get][key=" + key + "]");
		} catch (Exception e) {
			String msg = ExceptionUtil.getInstance().getExceptionMsg(e);
			logger.error(msg, e);
			throw new Exception(msg, e);
		}
		return obj;
	}

	@Override
	public Set<Serializable> keys(String pattern) throws Exception {
		Set<Serializable> set = null;
		try {
			set = redisTemplate.keys(pattern);
			logger.info("[RedisServiceImpl][keys][pattern=" + pattern + "]");
		} catch (Exception e) {
			String msg = ExceptionUtil.getInstance().getExceptionMsg(e);
			logger.error(msg, e);
			throw new Exception(msg, e);
		}
		return set;
	}

	@Override
	public boolean exists(Serializable key) throws Exception {
		boolean flag = false;
		try {
			flag = redisTemplate.hasKey(key);
			logger.info("[RedisServiceImpl][exists][key=" + key + "][flag=" + flag + "]");
		} catch (Exception e) {
			String msg = ExceptionUtil.getInstance().getExceptionMsg(e);
			logger.error(msg, e);
			throw new Exception(msg, e);
		}
		return flag;
	}

	@Override
	public boolean flushDb() throws Exception {
		boolean flag = false;
		try {
			flag = redisTemplate.execute(new RedisCallback<Boolean>() {
				public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
					connection.flushDb();
					return true;
				}
			});
			logger.info("[RedisServiceImpl][flushDB][flag=" + flag + "]");
		} catch (Exception e) {
			String msg = ExceptionUtil.getInstance().getExceptionMsg(e);
			logger.error(msg, e);
			throw new Exception(msg, e);
		}
		return flag;
	}

	@Override
	public long dbSize() throws Exception {
		long size = 0;
		try {
			size = redisTemplate.execute(new RedisCallback<Long>() {
				public Long doInRedis(RedisConnection connection) throws DataAccessException {
					return connection.dbSize();
				}
			});
			logger.info("[RedisServiceImpl][dbSize][size=" + size + "]");
		} catch (Exception e) {
			String msg = ExceptionUtil.getInstance().getExceptionMsg(e);
			logger.error(msg, e);
			throw new Exception(msg, e);
		}
		return size;
	}

	@Override
	public boolean ping() throws Exception {
		boolean flag = false;
		try {
			flag = redisTemplate.execute(new RedisCallback<Boolean>() {
				public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
					String str = connection.ping();
					return str.equalsIgnoreCase("PONG");
				}
			});
			logger.info("[RedisServiceImpl][ping][flag=" + flag + "]");
		} catch (Exception e) {
			String msg = ExceptionUtil.getInstance().getExceptionMsg(e);
			logger.error(msg, e);
			throw new Exception(msg, e);
		}
		return flag;
	}

	@SuppressWarnings("rawtypes")
	public HashOperations redisHash() throws Exception {
		HashOperations hashOperations = null;
		try {
			hashOperations = redisTemplate.opsForHash();
			// logger.info("[RedisServiceImpl][redisHash]");
		} catch (Exception e) {
			String msg = ExceptionUtil.getInstance().getExceptionMsg(e);
			logger.error(msg, e);
			throw new Exception(msg, e);
		}
		return hashOperations;
	}

	@SuppressWarnings("rawtypes")
	public ListOperations redisList() throws Exception {
		ListOperations listOperations = null;
		try {
			listOperations = redisTemplate.opsForList();
			// logger.info("[RedisServiceImpl][redisList]");
		} catch (Exception e) {
			String msg = ExceptionUtil.getInstance().getExceptionMsg(e);
			logger.error(msg, e);
			throw new Exception(msg, e);
		}
		return listOperations;
	}

	@SuppressWarnings("rawtypes")
	public SetOperations redisSet() throws Exception {
		SetOperations setOperations = null;
		try {
			setOperations = redisTemplate.opsForSet();
			// logger.info("[RedisServiceImpl][redisSet]");
		} catch (Exception e) {
			String msg = ExceptionUtil.getInstance().getExceptionMsg(e);
			logger.error(msg, e);
			throw new Exception(msg, e);
		}
		return setOperations;
	}

	@SuppressWarnings("rawtypes")
	public ZSetOperations redisZSet() throws Exception {
		ZSetOperations zSetOperations = null;
		try {
			zSetOperations = redisTemplate.opsForZSet();
			// logger.info("[RedisServiceImpl][redisZSet]");
		} catch (Exception e) {
			String msg = ExceptionUtil.getInstance().getExceptionMsg(e);
			logger.error(msg, e);
			throw new Exception(msg, e);
		}
		return zSetOperations;
	}

	@SuppressWarnings("rawtypes")
	public ValueOperations redisValue() throws Exception {
		ValueOperations valueOperations = null;
		try {
			valueOperations = redisTemplate.opsForValue();
			// logger.info("[RedisServiceImpl][redisValue]");
		} catch (Exception e) {
			String msg = ExceptionUtil.getInstance().getExceptionMsg(e);
			logger.error(msg, e);
			throw new Exception(msg, e);
		}
		return valueOperations;
	}

	/**
	 * redisTemplate
	 * 
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public RedisTemplate redisTemplate() throws Exception {
		return redisTemplate;
	}
	
	/**
	 * @Description: 设值对象
	 * @Datatime 2017年7月29日 下午1:39:53 
	 * @param key
	 * @param value
	 * @return   
	 * @see com.mm.dev.service.IRedisService#set(java.lang.String, java.lang.String)
	 */
    public boolean set(final String key, final String value) {  
        boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {  
            @Override  
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {  
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();  
                connection.set(serializer.serialize(key), serializer.serialize(value));  
                return true;  
            }  
        });  
        return result;  
    }  
	
	/**
	 * @Description: 根据key获取对象
	 * @Datatime 2017年7月29日 下午1:39:29 
	 * @param key
	 * @return   
	 * @see com.mm.dev.service.IRedisService#get(java.lang.String)
	 */
    public String get(final String key){  
        String result = redisTemplate.execute(new RedisCallback<String>() {  
            @Override  
            public String doInRedis(RedisConnection connection) throws DataAccessException {  
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();  
                byte[] value =  connection.get(serializer.serialize(key));  
                return serializer.deserialize(value);  
            }  
        });  
        return result;  
    }  
	
	/**
	 * @Description: 根据key设值有效期
	 * @Datatime 2017年7月29日 下午1:30:40 
	 * @param key
	 * @param expire
	 * @return   
	 * @see com.mm.dev.service.IRedisService#expire(java.lang.String, long)
	 */
    public boolean expire(final String key, long expire) {  
        return redisTemplate.expire(key, expire, TimeUnit.SECONDS);  
    }  
  
    /**
     * @Description: 根据key设值list集合对象
     * @Datatime 2017年7月29日 下午1:32:11 
     * @param key
     * @param list
     * @return   
     * @see com.mm.dev.service.IRedisService#setList(java.lang.String, java.util.List)
     */
    public <T> boolean setList(String key, List<T> list) {  
        String value = JSONUtil.toJson(list);
        return set(key,value);  
    }  
  
    /**
     * @Description: 根据key获取list集合对象
     * @Datatime 2017年7月29日 下午1:32:44 
     * @param key
     * @param clz
     * @return   
     * @see com.mm.dev.service.IRedisService#getList(java.lang.String, java.lang.Class)
     */
    public <T> List<T> getList(String key,Class<T> clz) {  
        String json = get(key);  
        if(json!=null){  
            List<T> list = JSONUtil.toList(json, clz);  
            return list;  
        }  
        return null;  
    }  
  
    /**
     * @Description: 将所有指定的值插入到存于 key 的列表的头部。如果 key 不存在，那么在进行 push 操作前会创建一个空列表。 如果 key 对应的值不是一个 list 的话，那么会返回一个错误。
     * @Datatime 2017年7月29日 下午1:33:58 
     * @param key
     * @param obj
     * @return   
     * @see com.mm.dev.service.IRedisService#lpush(java.lang.String, java.lang.Object)
     */
    public long lpush(final String key, Object obj) {  
        final String value = JSONUtil.toJson(obj);  
        long result = redisTemplate.execute(new RedisCallback<Long>() {  
            @Override  
            public Long doInRedis(RedisConnection connection) throws DataAccessException {  
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();  
                long count = connection.lPush(serializer.serialize(key), serializer.serialize(value));  
                return count;  
            }  
        });  
        return result;  
    }  
  
    /**
     * @Description: 将一个或多个值插入到列表的尾部(最右边)。如果列表不存在，一个空列表会被创建并执行 RPUSH 操作。 当列表存在但不是列表类型时，返回一个错误。
     * @Datatime 2017年7月29日 下午1:36:21 
     * @param key
     * @param obj
     * @return   
     * @see com.mm.dev.service.IRedisService#rpush(java.lang.String, java.lang.Object)
     */
    public long rpush(final String key, Object obj) {  
        final String value = JSONUtil.toJson(obj);  
        long result = redisTemplate.execute(new RedisCallback<Long>() {  
            @Override  
            public Long doInRedis(RedisConnection connection) throws DataAccessException {  
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();  
                long count = connection.rPush(serializer.serialize(key), serializer.serialize(value));  
                return count;  
            }  
        });  
        return result;  
    }  
  
    /**
     * @Description: 删除，并返回保存列表在key的第一个元素
     * @Datatime 2017年7月29日 下午1:36:50 
     * @param key
     * @return   
     * @see com.mm.dev.service.IRedisService#lpop(java.lang.String)
     */
    public String lpop(final String key) {  
        String result = redisTemplate.execute(new RedisCallback<String>() {  
            @Override  
            public String doInRedis(RedisConnection connection) throws DataAccessException {  
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();  
                byte[] res =  connection.lPop(serializer.serialize(key));  
                return serializer.deserialize(res);  
            }  
        });  
        return result;  
    }  
}
