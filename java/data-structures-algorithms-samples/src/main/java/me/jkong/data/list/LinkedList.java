package me.jkong.data.list;

import javax.xml.stream.events.NotationDeclaration;
import java.util.Objects;
import java.util.OptionalDouble;

/**
 * @author JKong
 * @version v1.0
 * @description 链表实现List
 * @date 2019/12/22 6:56 上午.
 */
public class LinkedList<E> {
    
    private Node dummyHead;
    private int size;
    
    public LinkedList(E e) {
        this();
        dummyHead.next = new Node(e);
        size++;
    }
    
    public LinkedList() {
        dummyHead = new Node(null);
        size = 0;
    }
    
    public int getSize() {
        return this.size;
    }
    
    public boolean isEmpty() {
        return size == 0;
    }
    
    /**
     * 添加元素
     *
     * @param index 索引
     * @param e     元素
     */
    public void add(int index, E e) {
        
        if (index < 0 || index > size) {
            throw new IllegalArgumentException("Add failed. Index is illegal.");
        }
        
        Node prev = dummyHead;
        for (int i = 0; i < index; i++) {
            prev = prev.next;
        }
        prev.next = new Node(e, prev.next);
        size++;
    }
    
    /**
     * 添加头元素
     *
     * @param e 元素
     */
    public void addFirst(E e) {
        add(0, e);
    }
    
    /**
     * 添加尾元素
     *
     * @param e 元素
     */
    public void addLast(E e) {
        this.add(size, e);
    }
    
    /**
     * 获取指定下标的元素
     *
     * @param index 下标
     * @return 元素
     */
    public E get(int index) {
        if (index < 0 || index > size) {
            throw new IllegalArgumentException("Get failed. Index is illegal.");
        }
        
        Node cur = dummyHead.next;
        for (int i = 0; i < index; i++) {
            cur = cur.next;
        }
        return cur.e;
    }
    
    /**
     * 获取头节点数据
     *
     * @return 元素
     */
    public E getFirst() {
        return get(0);
    }
    
    /**
     * 获取尾节点数据
     *
     * @return 元素
     */
    public E getLast() {
        return get(getSize());
    }
    
    /**
     * 替换指定位置元素
     *
     * @param index 下标
     * @param e     元素
     * @return 原数据
     */
    public E set(int index, E e) {
        if (index < 0 || index > size) {
            throw new IllegalArgumentException("Get failed. Index is illegal.");
        }
        Node cur = dummyHead.next;
        for (int i = 0; i < index; i++) {
            cur = cur.next;
        }
        E ret = cur.e;
        cur.e = e;
        return ret;
    }
    
    /**
     * 元素是否存在
     *
     * @param e 元素
     * @return true：存在，false：不存在
     */
    public boolean contains(E e) {
        Node cur = dummyHead.next;
        while (cur != null) {
            if (Objects.equals(cur.e, e)) {
                return true;
            }
            cur = cur.next;
        }
        return false;
    }
    
    /**
     * 移除元素
     *
     * @param index 下标
     * @return 原数据
     */
    public E remove(int index) {
        if (index < 0 || index > size) {
            throw new IllegalArgumentException("Get failed. Index is illegal.");
        }
        
        Node pre = dummyHead;
        for (int i = 0; i < index; i++) {
            pre = pre.next;
        }
        
        Node ret = pre.next;
        pre.next = ret.next;
        ret.next = null;
        size--;
        return ret.e;
    }
    
    /**
     * 删除第一个节点元素
     *
     * @return 原数据
     */
    public E removeFirst() {
        return remove(0);
    }
    
    /**
     * 删除最后节点元素
     *
     * @return 原数据
     */
    public E removeLast() {
        return remove(size - 1);
    }
    
    public void reverse() {
        // todo
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("LinkedList: ");
        for (Node cur = dummyHead.next; cur != null; cur = cur.next) {
            sb.append(cur.e != null ? cur : null).append(" -> ");
        }
        sb.append("NULL");
        return sb.toString();
    }
    
    private class Node {
        public E e;
        public Node next;
        
        public Node(E e, Node next) {
            this.e = e;
            this.next = next;
        }
        
        public Node(E e) {
            this(e, null);
        }
        
        public Node() {
            this(null, null);
        }
        
        @Override
        public String toString() {
            return e.toString();
        }
    }
    
    public static void main(String[] args) {
        LinkedList<Integer> linkedList = new LinkedList<>();
        for (int i = 0; i < 10; i++) {
            linkedList.addFirst(i);
            System.out.println(linkedList);
        }
        System.out.println("linkedList contains 4: " + linkedList.contains(4));
        
        linkedList.addLast(-1);
        System.out.println(linkedList);
        
        linkedList.add(2, 100);
        System.out.println(linkedList);
        
        
        linkedList.add(5, null);
        System.out.println(linkedList);
        System.out.println("linkedList contains null: " + linkedList.contains(null));
        
        linkedList.remove(5);
        System.out.println(linkedList);
        
        linkedList.removeFirst();
        System.out.println(linkedList);
        
        linkedList.removeLast();
        System.out.println(linkedList);
    }
}