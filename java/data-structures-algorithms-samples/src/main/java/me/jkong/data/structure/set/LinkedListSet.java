package me.jkong.data.structure.set;

import me.jkong.data.structure.list.LinkedList;

/**
 * @author JKong
 * @version v1.0
 * @description 使用 linked list 实现 set
 * @date 2019/12/25 6:06 上午.
 */
public class LinkedListSet<E> implements Set<E> {
    
    private LinkedList<E> list;
    
    public LinkedListSet() {
        this.list = new LinkedList<>();
    }
    
    @Override
    public void add(E e) {
        if (!this.contains(e)) {
            this.list.addFirst(e);
        }
    }
    
    @Override
    public void remove(E e) {
        this.list.removeElement(e);
    }
    
    @Override
    public boolean contains(E e) {
        return this.list.contains(e);
    }
    
    @Override
    public int getSize() {
        return this.list.getSize();
    }
    
    @Override
    public boolean isEmpty() {
        return this.list.isEmpty();
    }
}