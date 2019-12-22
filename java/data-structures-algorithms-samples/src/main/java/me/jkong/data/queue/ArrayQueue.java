package me.jkong.data.queue;

import me.jkong.data.array.Array;

/**
 * @author JKong
 * @version v1.0
 * @description 使用数组实现 queue
 * @date 2019/12/21 9:34 下午.
 */
public class ArrayQueue<E> implements Queue<E> {
    
    private Array<E> data;
    
    public ArrayQueue(int capacity) {
        this.data = new Array<>(capacity);
    }
    
    public ArrayQueue() {
        this(16);
    }
    
    @Override
    public void enqueue(E e) {
        data.addLast(e);
    }
    
    @Override
    public E dequeue() {
        return data.removeFirst();
    }
    
    @Override
    public E getFront() {
        return data.getFirst();
    }
    
    @Override
    public int getSize() {
        return data.getSize();
    }
    
    public int getCapacity() {
        return data.getCapacity();
    }
    
    @Override
    public boolean isEmpty() {
        return data.isEmpty();
    }
    
    @Override
    public String toString() {
        StringBuilder dataStringBuilder = new StringBuilder("Queue: ");
        dataStringBuilder.append("front [");
        for (int i = 0; i < getSize(); i++) {
            dataStringBuilder.append(data.get(i));
            if (i != data.getSize() - 1) {
                dataStringBuilder.append(", ");
            }
        }
        dataStringBuilder.append("] tail");
        return dataStringBuilder.toString();
    }
    
    public static void main(String[] args) {
        ArrayQueue<Integer> queue = new ArrayQueue<>(4);
        for (int i = 0; i < 10; i++) {
            queue.enqueue(i);
            
            if (i % 3 == 2) {
                queue.dequeue();
            }
            System.out.println(queue);
        }
    }
}