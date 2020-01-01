package me.jkong.data.structure.tree;

import java.util.TreeMap;

/**
 * @author JKong
 * @version v1.0
 * @description 使用treeMap实现trie
 * @date 2020/1/2 6:09 上午.
 */
public class Trie {
    
    private static class Node {
        private boolean isWord;
        private TreeMap<Character, Node> next;
        
        public Node(boolean isWord) {
            this.isWord = isWord;
            next = new TreeMap<>();
        }
        
        public Node() {
            this(false);
        }
        
    }
    
    private Node root;
    private int size;
    
    public Trie() {
        this.root = new Node();
        this.size = 0;
    }
    
    /**
     * 获取trie中Word的数量
     *
     * @return 数量
     */
    public int getSize() {
        return size;
    }
    
    /**
     * 当前trie是否为空
     *
     * @return true：空，false：非空
     */
    public boolean isEmpty() {
        return size == 0;
    }
    
    /**
     * 向当前trie中添加word
     *
     * @param word 被添加的元素
     */
    public void add(String word) {
        
        Node cur = root;
        Character chr;
        for (int i = 0; i < word.length(); i++) {
            chr = word.charAt(i);
            if (!root.next.containsKey(chr)) {
                root.next.put(chr, new Node());
            }
            cur = root.next.get(chr);
        }
        
        // 判断此Word是否已经存在
        if (!cur.isWord) {
            cur.isWord = true;
            size++;
        }
    }
    
    /**
     * 是否存在某个word
     *
     * @param word word
     * @return true：存在，false：不存在
     */
    public boolean contains(String word) {
        Node cur = getLastNext(word);
        if (cur == null) {
            return false;
        }
        return cur.isWord;
    }
    
    /**
     * 是否存在以某前缀开头的word
     *
     * @param prefix 前缀
     * @return true：存在，false：不存在
     */
    public boolean isPrefix(String prefix) {
        return getLastNext(prefix) != null;
    }
    
    /**
     * 删除指定word
     *
     * @param word 被删除的word
     */
    public void remove(String word) {
    
    }
    
    private Node getLastNext(String prefix) {
        Node cur = root;
        Character chr;
        for (int i = 0; i < prefix.length(); i++) {
            chr = prefix.charAt(i);
            if (!cur.next.containsKey(chr)) {
                return null;
            }
            cur = cur.next.get(chr);
        }
        return cur;
    }
}