package me.jkong.data.structure.map;


/**
 * @author JKong
 * @version v1.0
 * @description 使用Linked list 实现 map
 * @date 2019/12/26 5:55 上午.
 */
public class LinkedListMap<K, V> implements Map<K, V> {
    
    private Node<K, V> dummyNode;
    private int size;
    
    public LinkedListMap() {
        this.dummyNode = new Node<>();
        this.size = 0;
    }
    
    private Node<K, V> getNode(K key) {
        Node<K, V> cur = dummyNode.next;
        while (cur != null) {
            if (cur.key == key || cur.key.equals(key)) {
                return cur;
            }
            cur = cur.next;
        }
        return null;
    }
    
    @Override
    public void put(K k, V v) {
        Node<K, V> node = getNode(k);
        if (node == null) {
            dummyNode.next = new Node<>(k, v, dummyNode.next);
            size++;
        } else {
            node.value = v;
        }
    }
    
    @Override
    public void remove(K k) {
        
        Node<K, V> prev = dummyNode;
        while (prev.next != null) {
            if (prev.next.key == k || prev.next.key.equals(k)) {
                Node<K, V> delNode = prev.next;
                prev.next = delNode.next;
                prev.next = null;
                size--;
            } else {
                prev = prev.next;
            }
        }
    }
    
    @Override
    public boolean contains(K k) {
        return getNode(k) != null;
    }
    
    @Override
    public V get(K k) {
        Node<K, V> node = getNode(k);
        return node == null ? null : node.value;
    }
    
    @Override
    public V set(K k, V v) {
        Node<K, V> node = getNode(k);
        if (node == null) {
            throw new IllegalArgumentException(k.toString() + " doesn't exist!");
        }
        V oldValue = node.value;
        node.value = v;
        return oldValue;
    }
    
    @Override
    public boolean isEmpty() {
        return size == 0;
    }
    
    @Override
    public int getSize() {
        return size;
    }
    
    private static class Node<K, V> {
        public K key;
        public V value;
        public Node<K, V> next;
        
        public Node(K key, V value, Node<K, V> next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }
        
        public Node(K key, Node<K, V> next) {
            this(key, null, next);
        }
        
        public Node() {
            this(null, null, null);
        }
        
        @Override
        public String toString() {
            return key.toString() + ":" + value.toString();
        }
    }
    
    public static void main(String[] args) {
        String s = "this is a beautiful flower , this is a happy dog .";
        LinkedListMap<String, Integer> map = new LinkedListMap<>();
        for (int i = 0; i < 5; i++) {
            String[] strings = s.split(" ");
            for (String key : strings) {
                if (map.contains(key)) {
                    map.set(key, map.get(key) + 1);
                } else {
                    map.put(key, 1);
                }
            }
        }
        System.out.println(map.getSize());
        System.out.println(map.get("this"));

        map.remove("this");
        System.out.println(map.get("this"));
    }
}