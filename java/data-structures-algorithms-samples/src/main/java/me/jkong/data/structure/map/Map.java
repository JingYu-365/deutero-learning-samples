package me.jkong.data.structure.map;

/**
 * @author JKong
 * @version v1.0
 * @description Map
 * @date 2019/12/25 7:27 上午.
 */
public interface Map<K, V> {
    
    /**
     * 添加元素
     *
     * @param key key
     * @param value value
     */
    void put(K key, V value);
    
    /**
     * 移除元素
     *
     * @param key key
     * @return 原数据
     */
    V remove(K key);
    
    
    /**
     * 是否包含指定元素
     *
     * @param key key
     * @return true：存在，false：不存在
     */
    boolean contains(K key);
    
    /**
     * 根据key获取值
     *
     * @param key key
     * @return value
     */
    V get(K key);
    
    /**
     * 添加元素
     *
     * @param key key
     * @param value value
     * @return 原数据
     */
    V set(K key, V value);
    
    /**
     * 是否为空
     *
     * @return true：空，false：非空
     */
    boolean isEmpty();
    
    /**
     * 容器中元素的数量
     *
     * @return 元素数量
     */
    int getSize();
}