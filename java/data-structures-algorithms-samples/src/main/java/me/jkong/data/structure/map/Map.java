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
     * @param k key
     * @param v value
     */
    void put(K k, V v);
    
    /**
     * 移除元素
     *
     * @param k key
     * @return 原数据
     */
    V remove(K k);
    
    
    /**
     * 是否包含指定元素
     *
     * @param k key
     * @return true：存在，false：不存在
     */
    boolean contains(K k);
    
    /**
     * 根据key获取值
     *
     * @param k key
     * @return value
     */
    V get(K k);
    
    /**
     * 添加元素
     *
     * @param k key
     * @param v value
     * @return 原数据
     */
    V set(K k, V v);
    
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
    int size();
}