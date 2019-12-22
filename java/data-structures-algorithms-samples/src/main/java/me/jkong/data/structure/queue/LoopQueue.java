package me.jkong.data.structure.queue;


/**
 * @author JKong
 * @version v1.0
 * @description 数组实现循环队列
 * @date 2019/12/21 9:55 下午.
 */
public class LoopQueue<E> implements Queue<E> {
    
    private E[] data;
    private int front, tail;
    private int size;
    
    public LoopQueue(int capacity) {
        // capacity + 1：是因为在队列满时，需要浪费一个空间
        data = (E[]) new Object[capacity + 1];
        front = 0;
        tail = 0;
        size = 0;
    }
    
    public LoopQueue() {
        this(16);
    }
    
    @Override
    public void enqueue(E e) {
        // 判断队列是否需要扩容
        if ((tail + 1) % data.length == front) {
            resize(getCapacity() * 2);
        }
        
        data[tail] = e;
        tail = (tail + 1) % data.length;
        size++;
    }
    
    private void resize(int newCapacity) {
        E[] newData = (E[]) new Object[newCapacity + 1];
        
        for (int i = 0; i < size; i++) {
            newData[i] = data[(front + i) % data.length];
        }
        
        data = newData;
        front = 0;
        tail = size;
    }
    
    @Override
    public E dequeue() {
        
        if (isEmpty()) {
            throw new IllegalArgumentException("Cannot dequeue from an empty queue");
        }
        
        E ret = data[front];
        data[front] = null;
        front = (front + 1) % data.length;
        size--;
        // 缩容
        if (size == getCapacity() / 4 && getCapacity() / 2 != 0) {
            resize(getCapacity() / 2);
        }
        return ret;
    }
    
    @Override
    public E getFront() {
        if (isEmpty()) {
            throw new IllegalArgumentException("Queue is empty");
        }
        return data[front];
    }
    
    @Override
    public int getSize() {
        return size;
    }
    
    public int getCapacity() {
        return data.length - 1;
    }
    
    @Override
    public boolean isEmpty() {
        return front == tail;
    }
    
    @Override
    public String toString() {
        StringBuilder dataStringBuilder = new StringBuilder("Queue: ");
        dataStringBuilder.append(String.format("Size: %d, Capacity: %d \n", size, getCapacity()));
        dataStringBuilder.append("front [");
        for (int i = front; i != tail; i = (i + 1) % data.length) {
            dataStringBuilder.append(data[i]);
            if ((i + 1) % data.length != tail) {
                dataStringBuilder.append(", ");
            }
        }
        dataStringBuilder.append("] tail");
        return dataStringBuilder.toString();
    }
    
    public static void main(String[] args) {
        LoopQueue<Integer> queue = new LoopQueue<>(4);
        for (int i = 0; i < 10; i++) {
            queue.enqueue(i);
            
            if (i % 3 == 2) {
                queue.dequeue();
            }
            System.out.println(queue);
        }
        
        while (queue.getSize() != 0) {
            queue.dequeue();
            System.out.println(queue);
        }
    }
}