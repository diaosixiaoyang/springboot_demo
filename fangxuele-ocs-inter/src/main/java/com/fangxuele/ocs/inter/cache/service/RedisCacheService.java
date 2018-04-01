package com.fangxuele.ocs.inter.cache.service;

import com.fangxuele.ocs.common.bean.other.FxlTypedTuple;
import org.springframework.data.redis.connection.RedisZSetCommands;

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Redis数据库的操作service
 *
 * @author zgzhou 오빠
 * @Date 2016年12月25日
 * @Description 为各个模块提供redis缓存服务的各种方法
 */
public interface RedisCacheService {

    /**
     * @param key 存储的key值
     * @return
     * @throws Exception
     * @Title: getBytesByString
     * @Description: 查询缓存中key对应的value 存储结构默认为
     * fxl{order:{key1:value1,key2:value2},order2:{}}
     * @author zgzhou 오빠
     * @Date 2016年11月14日 下午1:49:08
     */
    public byte[] getBytesByString(final String key);

    /**
     * @param key   key的值
     * @param value 需要缓存的value
     * @return 保存成功返回ture 失败返回false
     * @Title: setBytesByString
     * @Description: 保存缓存
     * @author zgzhou 오빠
     * @Date 2016年11月14日 下午1:54:27
     */
    public Boolean setBytesByString(final String key, final byte[] value);

    /**
     * @param key 存储的key值
     * @return
     * @throws Exception
     * @Title: getValueByString
     * @Description: 查询缓存中key对应的value 存储结构默认为
     * fxl{order:{key1:value1,key2:value2},order2:{}}
     * @author zgzhou 오빠
     * @Date 2016年11月14日 下午1:49:08
     */
    public String getValueByString(final String key);

    /**
     * 不带前缀
     *
     * @param key
     * @return
     */
    public String getValueByStringWithNoPrefix(final String key);

    /**
     * 不带前缀
     *
     * @param key
     * @return
     */
    public Long getExpireWithNoPrefix(String key);

    /**
     * @param key   key的值
     * @param value 需要缓存的value
     * @return 保存成功返回ture 失败返回false
     * @Title: setValueByString
     * @Description: 保存缓存
     * @author zgzhou 오빠
     * @Date 2016年11月14日 下午1:54:27
     */
    public void setValueByString(final String key, final String value);

    /**
     * 不带前缀
     *
     * @param key
     * @param value
     */
    public void setValueByStringWithNoPrefix(final String key, final String value);

    /**
     * @param key   key的值
     * @param value 需要缓存的value
     * @return 保存成功返回ture 失败返回false
     * @Title: incrByValueByString
     * @Description: 将指定的值递增value
     * @author zgzhou 오빠
     * @Date 2016年11月14日 下午1:54:27
     */
    public Long incrByValueByString(final String key, final long value);

    /**
     * @param key   key的值
     * @param value 需要缓存的value
     * @return 保存成功返回ture 失败返回false
     * @Title: incrByValueByString
     * @Description: 将指定的值递增value
     * @author zgzhou 오빠
     * @Date 2016年11月14日 下午1:54:27
     */
    public Double incrByValueByString(final String key, final double value);

    /**
     * @param key 存储的key值
     * @return
     * @throws Exception
     * @Title: getValueByList
     * @Description: 查询缓存中key对应的value 存储结构默认为
     * fxl{order:{key1:value1,key2:value2},order2:{}}
     * @author zgzhou 오빠
     * @Date 2016年11月14日 下午1:49:08
     */
    public String getValueByList(final String key, final long index);

    /**
     * @param key 存储的key值
     * @return
     * @throws Exception
     * @Title: popLeftValueByList
     * @Description: 左弹出缓存中key对应的value 存储结构默认为
     * fxl{order:{key1:value1,key2:value2},order2:{}}
     * @author zhouy
     */
    public String popLeftValueByList(final String key);

    /**
     * @param key 存储的key值
     * @return
     * @throws Exception
     * @Title: popRightValueByList
     * @Description: 左弹出缓存中key对应的value 存储结构默认为
     * fxl{order:{key1:value1,key2:value2},order2:{}}
     * @author zhouy
     */
    public String popRightValueByList(final String key);

    /**
     * @param key 存储的key值
     * @return
     * @throws Exception
     * @Title: getValueOfStartEndByList
     * @Description: 查询缓存中key对应的value 存储结构默认为
     * fxl{order:{key1:value1,key2:value2},order2:{}}
     * @author zgzhou 오빠
     * @Date 2016年11月14日 下午1:49:08
     */
    public List<String> getValueOfRangeByList(final String key, final long start, final long end);

    /**
     * @param key   key的值
     * @param value 需要缓存的value
     * @return 保存成功返回ture 失败返回false
     * @Title: leftPushAllCacheByList
     * @Description: 基于List接口保存缓存，从左端（前端）添加
     * @author zgzhou 오빠
     * @Date 2016年11月14日 下午1:54:27
     */
    public Long leftPushAllValueByList(final String key, final String... value);

    /**
     * @param key   key的值
     * @param value 需要缓存的value
     * @return 保存成功返回ture 失败返回false
     * @Title: rightPushAllCacheByList
     * @Description: 基于List接口保存缓存，从右端（后端）添加
     * @author zgzhou 오빠
     * @Date 2016年11月14日 下午1:54:27
     */
    public Long rightPushAllValueByList(final String key, final String... value);

    /**
     * @param key key的值
     * @Title: removeCacheByHash
     * @Description: 删除缓存内容
     * @author zgzhou 오빠
     * @Date 2016年11月14日 下午1:53:43
     */
    public Long removeValueByList(final String key, final long i, final Object value);

    /**
     * @param moudle 存储的modle
     * @return 所有KEY值SET
     * @throws Exception
     * @Title: getValue
     * @Description: 根据传入的缓存模块名获取模块名下的所有KEY值SET
     * @author zgzhou
     * @Date 2016年11月14日 下午1:49:08
     */
    public Set<String> getKeySetByHash(final String moudle);

    /**
     * @param key    存储的key值
     * @param moudle 存储的modle
     * @return 缓存的数据
     * @throws Exception
     * @Title: getValueByHash
     * @Description: 查询缓存中key对应的value 存储结构默认为
     * fxl{order:{key1:value1,key2:value2},order2:{}}
     * @author zgzhou 오빠
     * @Date 2016年11月14日 下午1:49:08
     */
    public String getValueByHash(final String moudle, final String key);

    /**
     * @param moudle 存储的modle
     * @return 缓存的数据
     * @throws Exception
     * @Title: getAllValueByHash
     * @Description: 查询缓存中key对应的value 存储结构默认为
     * fxl{order:{key1:value1,key2:value2},order2:{}}
     * @author zgzhou 오빠
     * @Date 2016年11月14日 下午1:49:08
     */
    public Map<String, String> getAllValueByHash(final String moudle);

    /**
     * @param moudle modle模块值
     * @param key    key的值
     * @Title: removeCacheByHash
     * @Description: 删除缓存内容
     * @author zgzhou 오빠
     * @Date 2016年11月14日 下午1:53:43
     */
    public void removeValueByHash(final String moudle, final String key);

    /**
     * @param moudle modle的值
     * @param key    key的值
     * @param value  需要缓存的value
     * @return 保存成功返回ture 失败返回false
     * @Title: incrByValueByHash
     * @Description: 将指定的值递增value
     * @author zgzhou 오빠
     * @Date 2016年11月14日 下午1:54:27
     */
    public Long incrByValueByHash(final String moudle, final String key, final long value);

    /**
     * @param moudle modle的值
     * @param key    key的值
     * @param value  需要缓存的value
     * @return 保存成功返回ture 失败返回false
     * @Title: incrByValueByHash
     * @Description: 将指定的值递增value
     * @author zgzhou 오빠
     * @Date 2016年11月14日 下午1:54:27
     */
    public Double incrByValueByHash(final String moudle, final String key, final double value);

    /**
     * @param moudle modle的值
     * @param key    key的值
     * @param value  需要缓存的value
     * @return 保存成功返回ture 失败返回false
     * @Title: putCache
     * @Description: 保存缓存
     * @author zgzhou 오빠
     * @Date 2016年11月14日 下午1:54:27
     */
    public void putValueByHash(final String moudle, final String key, final String value);

    /**
     * @param key key的值
     * @return 保存成功返回ture 失败返回false
     * @Title: putAllValueByHash
     * @Description: 保存缓存
     * @author zgzhou 오빠
     * @Date 2016年11月14日 下午1:54:27
     */
    public void putAllValueByHash(final String key, final Map<String, String> values);

    /**
     * @param key 存储的key值
     * @return 缓存的数据
     * @throws Exception
     * @Title: getValueBySet
     * @Description: 从Set集合中随机返回一个元素
     * @author zgzhou 오빠
     * @Date 2016年11月14日 下午1:49:08
     */
    public String getValueBySet(final String key);

    /**
     * @param key 存储的key值
     * @return 缓存的数据
     * @throws Exception
     * @Title: getValuesBySet
     * @Description: 从Set集合中随机返回count个元素
     * @author zgzhou 오빠
     * @Date 2016年11月14日 下午1:49:08
     */
    public List<String> getValuesBySet(final String key, long count);

    /**
     * @param key 存储的key值
     * @return 缓存的数据
     * @throws Exception
     * @Title: getValuesBySet
     * @Description: 从Set集合中所有的值
     * @author zgzhou 오빠
     * @Date 2016年11月14日 下午1:49:08
     */
    public Set<String> getValuesBySet(final String key);

    /**
     * @param key 存储的key值
     * @return 缓存的数据
     * @throws Exception
     * @Title: addValuesBySet
     * @Description: 向Set集合中添加values元素
     * @author zgzhou 오빠
     * @Date 2016年11月14日 下午1:49:08
     */
    public Long addValuesBySet(final String key, String... values);

    /**
     * @param key 存储的key值
     * @return 缓存的数据
     * @throws Exception
     * @Title: removeValuesBySet
     * @Description: 向Set集合中删除values元素
     * @author zgzhou 오빠
     * @Date 2016年11月14日 下午1:49:08
     */
    public Long removeValuesBySet(final String key, String... values);

    /**
     * @param key 存储的key值
     * @return 缓存的数据
     * @throws Exception
     * @Title: isContainValueBySet
     * @Description: 判断Set集合中是否包含value元素
     * @author zgzhou 오빠
     * @Date 2016年11月14日 下午1:49:08
     */
    public Boolean isContainValueBySet(final String key, String value);

    /**
     * @param key 存储的key值
     * @return 缓存的数据
     * @throws Exception
     * @Title: getValueOfStartEndByZSet
     * @Description: 查询缓存中key对应的value 存储结构默认为
     * fxl{order:{key1:value1,key2:value2},order2:{}}
     * @author zgzhou 오빠
     * @Date 2016年11月14日 下午1:49:08
     */
    public Set<String> getValueOfRangeByZSet(final String key, final long start, final long end, int sore);

    /**
     * @param key 存储的key值
     * @return 缓存的数据
     * @throws Exception
     * @Title: getValueOfScoreByZSet
     * @Description: 查询缓存中key对应的value 存储结构默认为
     * fxl{order:{key1:value1,key2:value2},order2:{}}
     * @author zgzhou 오빠
     * @Date 2016年11月14日 下午1:49:08
     */
    public Set<String> getValueOfScoreByZSet(final String key, final double min, final double max, int sore);

    /**
     * @param key 存储的key值
     * @return 缓存的数据
     * @throws Exception
     * @Title: getValueAndScoreOfStartEndByZSet
     * @Description: 查询缓存中key对应的value 存储结构默认为
     * fxl{order:{key1:value1,key2:value2},order2:{}}
     * @author zgzhou 오빠
     * @Date 2016年11月14日 下午1:49:08
     */
    public Set<FxlTypedTuple> getValueAndScoreOfRangeByZSet(final String key, final long start, final long end,
                                                            int sore);

    /**
     * @param key 存储的key值
     * @return 缓存的数据
     * @throws Exception
     * @Title: getValueAndScoreOfScoreByZSet
     * @Description: 查询缓存中key对应的value 存储结构默认为
     * fxl{order:{key1:value1,key2:value2},order2:{}}
     * @author zgzhou 오빠
     * @Date 2016年11月14日 下午1:49:08
     */
    public LinkedHashSet<FxlTypedTuple> getValueAndScoreOfScoreByZSet(final String key, final double min, final double max,
                                                                      int sore);

    /**
     * @param value value模块值
     * @param key   key的值
     * @Title: removeValueByZSet
     * @Description: 删除缓存内容
     * @author zgzhou 오빠
     * @Date 2016年11月14日 下午1:53:43
     */
    public Long removeValueByZSet(final String key, final String... value);

    /**
     * @param key key的值
     * @Title: removeValueOfStartEndByZSet
     * @Description: 删除缓存内容
     * @author zgzhou 오빠
     * @Date 2016年11月14日 下午1:53:43
     */
    public Long removeValueOfRangeByZSet(final String key, final long start, final long end);

    /**
     * @param key key的值
     * @Title: removeValueOfScoreByZSet
     * @Description: 删除缓存内容
     * @author zgzhou 오빠
     * @Date 2016年11月14日 下午1:53:43
     */
    public Long removeValueOfScoreByZSet(final String key, final double min, final double max);

    /**
     * @param key   key的值
     * @param value 需要缓存的value
     * @param score 缓存值的排序score
     * @return 保存成功返回ture 失败返回false
     * @Title: addValueAndScoreByZSet
     * @Description: 保存缓存
     * @author zgzhou 오빠
     * @Date 2016年11月14日 下午1:54:27
     */
    public void addValueAndScoreByZSet(final String key, final String value, final double score);

    /**
     * @param key   key的值
     * @param value 需要缓存的value
     * @param score 缓存值的排序score
     * @return 保存成功返回ture 失败返回false
     * @Title: incrScoreByZSet
     * @Description: 将ZSet中的value的分值进行相加score值
     * @author zgzhou 오빠
     * @Date 2016年11月14日 下午1:54:27
     */
    public Double incrScoreByZSet(final String key, final String value, final double score);

    /**
     * @param key   key的值
     * @param value 需要缓存的value
     * @return 保存成功返回ture 失败返回false
     * @Title: getScoreByZSet
     * @Description: 从ZSet集合中获取value对应的分值
     * @author zgzhou 오빠
     * @Date 2016年11月14日 下午1:54:27
     */
    public Double getScoreByZSet(final String key, final String value);

    /**
     * @Title: interZSet
     * @Description: 对多个有序集合进行交集运算到目标集合的方法
     * @author zgzhou 오빠
     * @Date 2016年11月14日 下午1:53:43
     */
    public Long interZSet(final String destKey, final RedisZSetCommands.Aggregate aggregate, final String key1,
                          final String key2);

    /**
     * @Title: interZSet
     * @Description: 对多个有序集合进行差集运算到目标集合的方法
     * @author zgzhou 오빠
     * @Date 2016年11月14日 下午1:53:43
     */
    public Long differSet(final String destKey, final String key1, final String key2);

    /**
     * @return 保存成功返回ture 失败返回false
     * @Title: isExistsKey
     * @Description: 设置缓存中KEY是否存在
     * @author zgzhou 오빠
     * @Date 2016年11月14日 下午1:54:27
     */
    public Boolean isExistsKey(final String key);

    /**
     * @param expire_seconds 该缓存的有效期（秒数）
     * @return 保存成功返回ture 失败返回false
     * @Title: setExpireByMoudle
     * @Description: 设置KEY有效时间
     * @author zgzhou 오빠
     * @Date 2016年11月14日 下午1:54:27
     */
    public void setExpireByKey(final String key, final long expire_seconds);

    /**
     * 不带前缀
     *
     * @param key
     * @param expire_seconds
     */
    public void setExpireByKeyWithNoPrefix(final String key, final long expire_seconds);

    /**
     * @param key        key的值
     * @param expireDate 该缓存的到期时间
     * @return 保存成功返回ture 失败返回false
     * @Title: setValueAndExpireByString
     * @Description: 设置KEY的到期时间
     * @author zgzhou 오빠
     * @Date 2016年11月14日 下午1:54:27
     */
    public void setExpireAtByKey(final String key, final Date expireDate);

    /**
     * @Title: persistByKey
     * @Description: 取消KEY有效时间，变成永久的KEY
     * @author zgzhou 오빠
     * @Date 2016年11月14日 下午1:54:27
     */
    public void persistByKey(final String key);

    /**
     * @param pattern 存储的pattern
     * @return 所有KEY值SET
     * @throws Exception
     * @Title: getKeySet
     * @Description: 根据传入的缓存模块名获取模块名下的所有KEY值SET
     * @author zgzhou
     * @Date 2016年11月14日 下午1:49:08
     */
    public Set<String> getKeySet(final String pattern);

    /**
     * @param key key模块值
     * @Title: getSizeByMoudel
     * @Description: 删除缓存模块名的全部内容
     * @author zgzhou 오빠
     * @Date 2016年11月14日 下午1:53:43
     */
    public Long getSizeByMoudel(final String key);

    /**
     * @param key key模块值
     * @Title: removeKeyByCache
     * @Description: 删除缓存模块名的全部内容
     * @author zgzhou 오빠
     * @Date 2016年11月14日 下午1:53:43
     */
    public void removeKeyByCache(final String key);

}