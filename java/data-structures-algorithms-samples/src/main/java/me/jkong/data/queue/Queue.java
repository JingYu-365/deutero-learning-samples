package me.jkong.data.queue;

/**
 * @author JKong
 * @version v1.0
 * @description queue
 * @date 2019/12/21 9:27 下午.
 */
public interface Queue<E> {
    
    /**
     * 队列中添加元素
     *
     * @param e 元素
     */
    void enqueue(E e);
    
    /**
     * 从队列中取出元素
     *
     * @return 元素
     */
    E dequeue();
    
    /**
     * 获取队列前面的一个元素
     *
     * @return 元素
     */
    E getFront();
    
    /**
     * 获取队列的大小
     *
     * @return 大小
     */
    int getSize();
    
    /**
     * 队列是否为空
     *
     * @return true：空，false：不为空
     */
    boolean isEmpty();
    
}