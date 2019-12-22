package me.jkong.data.structure.stack;

import me.jkong.data.structure.list.LinkedList;

/**
 * @author JKong
 * @version v1.0
 * @description 使用 linkedList 实现 stack
 * @date 2019/12/22 9:36 上午.
 */
public class LinkedListStack<E> implements Stack<E> {
    
    private LinkedList<E> data;
    
    public LinkedListStack() {
        this.data = new LinkedList<>();
    }
    
    @Override
    public void push(E e) {
        data.addFirst(e);
    }
    
    @Override
    public E pop() {
        return data.removeFirst();
    }
    
    @Override
    public E peek() {
        return data.getFirst();
    }
    
    @Override
    public int getSize() {
        return data.getSize();
    }
    
    @Override
    public boolean isEmpty() {
        return data.isEmpty();
    }
    
    @Override
    public String toString() {
        return "Stack: top " + data.toString();
    }
}