package me.jkong.data.structure.set;

/**
 * @author JKong
 * @version v1.0
 * @description Set
 * @date 2019/12/24 2:28 下午.
 */
public interface Set<E> {
    
    /**
     * 添加数据
     *
     * @param e 元素
     */
    void add(E e);
    
    /**
     * 移除元素
     *
     * @param e 元素
     */
    void remove(E e);
    
    /**
     * 元素是否存在
     *
     * @param e 元素
     * @return true：存在，false：不存在
     */
    boolean contains(E e);
    
    /**
     * 元素个数
     *
     * @return 数量
     */
    int getSize();
    
    /**
     * 是否为空
     *
     * @return true：空，false：非空
     */
    boolean isEmpty();
    
}