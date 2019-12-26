package me.jkong.data.structure.queue;

/**
 * @author JKong
 * @version v1.0
 * @description 使用二叉堆实现优先队列
 * @date 2019/12/27 6:24 上午.
 * ------------- 入队 ----------- 出队-------
 * 普通线性结构   O(1)            O(n)
 * 顺序线性结构   O(n)            O(1)
 * 堆           O(logn)         O(logn)
 */
public class PriorityQueue<E> implements Queue<E> {
    @Override
    public void enqueue(E e) {
    
    }
    
    @Override
    public E dequeue() {
        return null;
    }
    
    @Override
    public E getFront() {
        return null;
    }
    
    @Override
    public int getSize() {
        return 0;
    }
    
    @Override
    public boolean isEmpty() {
        return false;
    }
}