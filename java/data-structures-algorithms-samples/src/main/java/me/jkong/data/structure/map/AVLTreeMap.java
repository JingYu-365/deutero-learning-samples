package me.jkong.data.structure.map;

import me.jkong.data.common.FileOperation;

import java.util.ArrayList;
import java.util.List;

/**
 * @author JKong
 * @version v1.0
 * @description 使用平衡二叉树实现map
 * @date 2020/1/4 8:24 上午.
 */
public class AVLTreeMap<K extends Comparable<K>, V> implements Map<K, V> {
    
    private Node<K, V> root;
    private int size;
    
    public AVLTreeMap() {
        this.root = null;
        this.size = 0;
    }
    
    @Override
    public void put(K key, V value) {
        this.root = put(root, key, value);
    }
    
    private Node<K, V> put(Node<K, V> node, K key, V value) {
        if (node == null) {
            size++;
            return new Node<>(key, value);
        }
        
        if (key.compareTo(node.key) < 0) {
            node.left = put(node.left, key, value);
        } else if (key.compareTo(node.key) > 0) {
            node.right = put(node.right, key, value);
        } else {
            // key.compareTo(node.key) == 0
            node.value = value;
        }
        
        return reBalanced(node);
    }
    
    /**
     * 平衡树结构
     *
     * @param node 节点
     * @return 平衡后的节点
     */
    private Node<K, V> reBalanced(Node<K, V> node) {
    
        if (node == null) {
            return null;
        }
        
        // 更新高度
        node.height = Math.max(getHeight(node.left), getHeight(node.right)) + 1;
        
        // 获取平衡因子
        int balanceFactor = getBalanceFactor(node);
        
        // 平衡维护
        if (balanceFactor > 1) {
            if (getBalanceFactor(node.left) >= 0) {
                // LL 情况
                return rightRotate(node);
            } else {
                // LR 情况
                node.left = leftRotate(node.left);
                return rightRotate(node);
            }
        } else if (balanceFactor < -1) {
            if (getBalanceFactor(node.right) <= 0) {
                // RR 情况
                return leftRotate(node);
            } else {
                // RL 情况
                node.right = rightRotate(node.right);
                return leftRotate(node);
            }
        }
        return node;
    }
    
    /**
     * 对节点y进行向左旋转操作，返回旋转后新的根节点x
     * |    y                             x
     * |  /  \                          /   \
     * | T1   x      向左旋转 (y)       y     z
     * |     / \   - - - - - - - ->   / \   / \
     * |   T2  z                     T1 T2 T3 T4
     * |      / \
     * |     T3 T4
     *
     * @param y 需要旋转的节点
     * @return 旋转后的节点
     */
    private Node<K, V> leftRotate(Node<K, V> y) {
        Node<K, V> x = y.right;
        Node<K, V> T2 = x.left;
        
        // 旋转
        x.left = y;
        y.right = T2;
        
        // 更新高度
        y.height = Math.max(getHeight(y.right), getHeight(y.left)) + 1;
        x.height = Math.max(getHeight(x.right), getHeight(x.left)) + 1;
        
        return x;
    }
    
    /**
     * 对节点y进行向右旋转操作，返回旋转后新的根节点x
     * |       y                              x
     * |      / \                           /   \
     * |     x   T4     向右旋转 (y)        z     y
     * |    / \       - - - - - - - ->    / \   / \
     * |   z   T3                       T1  T2 T3 T4
     * |  / \
     * |T1   T2
     *
     * @param y 需要旋转的节点
     * @return 旋转后的节点
     */
    private Node<K, V> rightRotate(Node<K, V> y) {
        Node<K, V> x = y.left;
        Node<K, V> T3 = x.right;
        
        // 旋转
        x.right = y;
        y.left = T3;
        
        // 更新高度
        y.height = Math.max(getHeight(y.right), getHeight(y.left)) + 1;
        x.height = Math.max(getHeight(x.right), getHeight(x.left)) + 1;
        
        return x;
    }
    
    @Override
    public void remove(K key) {
        root = remove(root, key);
    }
    
    private Node<K, V> remove(Node<K, V> node, K key) {
        if (node == null) {
            return null;
        }
        
        Node<K, V> retNode;
        if (key.compareTo(node.key) < 0) {
            node.left = remove(node.left, key);
            retNode = node;
        } else if (key.compareTo(node.key) > 0) {
            node.right = remove(node.right, key);
            retNode = node;
        } else { // e == node.e
            if (node.right == null) {
                // 只有左子树
                Node<K, V> leftNode = node.left;
                node.left = null;
                size--;
                retNode = leftNode;
            } else if (node.left == null) {
                // 只有右子树
                Node<K, V> rightNode = node.right;
                node.right = null;
                size--;
                retNode = rightNode;
            } else {
                // 双子节点 -> 将右子树的最小节点 替换 此节点
                Node<K, V> successor = minimum(node.right);
                successor.right = remove(node.right, successor.key);
                successor.left = node.left;
                node.left = node.right = null;
                retNode = successor;
            }
        }
        return reBalanced(retNode);
    }
    
    /**
     * 指定节点中的最小节点
     *
     * @param node 根节点
     * @return 最小节点
     */
    public Node<K, V> minimum(Node<K, V> node) {
        if(node.left == null) {
            return node;
        }
        return minimum(node.left);
    }
    
    /**
     * 指定节点中的最大节点
     *
     * @param node 根节点
     * @return 最大节点
     */
    public Node<K, V> maximum(Node<K, V> node) {
        if(node.right == null) {
            return node;
        }
        return maximum(node.right);
    }
    
    /**
     * 删除最小值
     *
     * @return 移除最小值的根节点
     */
    public Node<K, V> removeMin() {
        if (root == null) {
            throw new IllegalArgumentException("RemoveMax failed, empty cannot operation.");
        }
        root = removeMin(root);
        return root;
    }
    
    private Node<K, V> removeMin(Node<K, V> node) {
        
        if (node.left == null) {
            Node<K, V> rightNode = node.right;
            node.right = null;
            size--;
            return rightNode;
        }
        node.left = removeMin(node.left);
        return reBalanced(node);
    }
    
    /**
     * 删除最大值
     *
     * @return 移除最大值后的根节点
     */
    public Node<K, V> removeMax() {
        if (root == null) {
            throw new IllegalArgumentException("RemoveMax failed, empty cannot operation.");
        }
        root = removeMax(root);
        return root;
    }
    
    private Node<K, V> removeMax(Node<K, V> node) {
        
        if (node.right == null) {
            Node<K, V> leftNode = node.left;
            node.left = null;
            size--;
            return leftNode;
        }
        node.right = removeMax(node.right);
        return reBalanced(node);
    }
    
    @Override
    public boolean contains(K key) {
        return contains(root, key);
    }
    
    private boolean contains(Node<K, V> node, K key) {
        if (node == null) {
            return false;
        }
        
        if (key.compareTo(node.key) == 0) {
            return true;
        } else if (key.compareTo(node.key) < 0) {
            return contains(node.left, key);
        } else {
            return contains(node.right, key);
        }
    }
    
    @Override
    public V get(K key) {
        Node<K, V> node = getNode(root, key);
        return node != null ? node.value : null;
    }
    
    private Node<K, V> getNode(Node<K, V> node, K key) {
        if (node == null) {
            return null;
        }
        
        if (key.compareTo(node.key) == 0) {
            return node;
        } else if (key.compareTo(node.key) < 0) {
            return getNode(node.left, key);
        } else {
            return getNode(node.right, key);
        }
    }
    
    @Override
    public V set(K key, V newValue) {
        Node<K, V> node = getNode(root, key);
        if (node == null) {
            throw new IllegalArgumentException(key + " doesn't exist!");
        }
        V oldValue = node.value;
        node.value = newValue;
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
    
    /**
     * 获取当前节点的层高
     *
     * @param node 指定节点
     * @return 层高
     */
    private int getHeight(Node<K, V> node) {
        if (node == null) {
            return 0;
        }
        return node.height;
    }
    
    /**
     * 获取指定元素的平衡因子
     *
     * @param node 指定元素
     * @return 平衡因子
     */
    private int getBalanceFactor(Node<K, V> node) {
        if (node == null) {
            return 0;
        }
        
        return getHeight(node.left) - getHeight(node.right);
    }
    
    /**
     * 判断此树是否为二分搜索树
     *
     * @return true：是，false：不是
     */
    public boolean isBinarySearchTree() {
        List<K> list = new ArrayList<>();
        inOrder(root, list);
        for (int i = 1; i < list.size(); i++) {
            if (list.get(i - 1).compareTo(list.get(i)) > 0) {
                return false;
            }
        }
        return true;
    }
    
    private void inOrder(Node<K, V> node, List<K> list) {
        if (node == null) {
            return;
        }
        
        inOrder(node.left, list);
        list.add(node.key);
        inOrder(node.right, list);
    }
    
    /**
     * 判断当前树结构是否为平衡二叉树
     *
     * @return true：平衡二叉树，false：非平衡二叉树
     */
    public boolean isBalanced() {
        return isBalanced(root);
    }
    
    private boolean isBalanced(Node<K, V> node) {
        if (node == null) {
            return true;
        }
        
        if (Math.abs(getBalanceFactor(node)) > 1) {
            return false;
        }
        return isBalanced(node.left) && isBalanced(node.right);
    }
    
    private static class Node<K, V> {
        private K key;
        private V value;
        private Node<K, V> left, right;
        private int height;
        
        public Node(K key, V value) {
            this.key = key;
            this.value = value;
            left = null;
            right = null;
            this.height = 1;
        }
    }
    
    public static void main(String[] args) {
        
        System.out.println("Pride and Prejudice");
        
        ArrayList<String> words = new ArrayList<>();
        if (FileOperation.readFile("pride-and-prejudice.txt", words)) {
            System.out.println("Total words: " + words.size());
            
            AVLTreeMap<String, Integer> map = new AVLTreeMap<>();
            for (String word : words) {
                if (map.contains(word)) {
                    map.set(word, map.get(word) + 1);
                } else {
                    map.put(word, 1);
                }
            }
            
            System.out.println("Total different words: " + map.getSize());
            System.out.println("Frequency of PRIDE: " + map.get("pride"));
            System.out.println("Frequency of PREJUDICE: " + map.get("prejudice"));
            
            System.out.println("is BST : " + map.isBinarySearchTree());
            System.out.println("is Balanced : " + map.isBalanced());
            
            for (String word : words) {
                map.remove(word);
                if (!map.isBinarySearchTree() || !map.isBalanced()) {
                    throw new IllegalArgumentException("Error");
                }
            }
            System.out.println("success!");
        }
        
        System.out.println();
    }
}