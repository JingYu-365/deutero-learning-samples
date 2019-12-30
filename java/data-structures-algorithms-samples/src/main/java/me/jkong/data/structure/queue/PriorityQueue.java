package me.jkong.data.structure.queue;

import me.jkong.data.structure.heap.MaxHeap;

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
public class PriorityQueue<E extends Comparable<E>> implements Queue<E> {
    
    private MaxHeap<E> data;
    
    public PriorityQueue() {
        data = new MaxHeap<>();
    }
    
    @Override
    public void enqueue(E e) {
        data.add(e);
    }
    
    @Override
    public E dequeue() {
        return data.extractMax();
    }
    
    @Override
    public E getFront() {
        return data.findMax();
    }
    
    @Override
    public int getSize() {
        return data.getSize();
    }
    
    @Override
    public boolean isEmpty() {
        return data.isEmpty();
    }
}