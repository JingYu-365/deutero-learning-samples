package me.jkong.data.structure.queue;


/**
 * @author JKong
 * @version v1.0
 * @description 使用 linkedList 实现 Queue
 * @date 2019/12/22 9:53 上午.
 */
public class LinkedListQueue<E> implements Queue<E> {
    
    
    private Node head, tail;
    private int size;
    
    public LinkedListQueue() {
        head = null;
        tail = null;
        size = 0;
    }
    
    @Override
    public void enqueue(E e) {
        if (tail == null) {
            tail = new Node(e);
            head = tail;
        } else {
            tail.next = new Node(e);
            tail = tail.next;
        }
        size++;
    }
    
    @Override
    public E dequeue() {
        if (isEmpty()) {
            throw new IllegalArgumentException("Cannot dequeue from empty queue");
        }
        
        Node retNode = head;
        head = head.next;
        if (head == null) {
            tail = null;
        }
        retNode.next = null;
        size--;
        return retNode.e;
    }
    
    @Override
    public E getFront() {
        if (isEmpty()) {
            throw new IllegalArgumentException("Cannot get from empty queue");
        }
        return head.e;
    }
    
    @Override
    public int getSize() {
        return size;
    }
    
    @Override
    public boolean isEmpty() {
        return size == 0;
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Queue: front ");
        for (Node cur = head; cur != null; cur = cur.next) {
            sb.append(cur).append(" -> ");
            
        }
        sb.append(" NULL tail");
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
        LinkedListQueue<Integer> queue = new LinkedListQueue<>();
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