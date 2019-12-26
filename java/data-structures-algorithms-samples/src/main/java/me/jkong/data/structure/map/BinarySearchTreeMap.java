package me.jkong.data.structure.map;

/**
 * @author JKong
 * @version v1.0
 * @description 使用二分搜索树实现map
 * @date 2019/12/26 6:42 上午.
 */
public class BinarySearchTreeMap<K extends Comparable<K>, V> implements Map<K, V> {

    private Node<K, V> root;
    private int size;

    public BinarySearchTreeMap() {
        this.root = null;
        this.size = 0;
    }

    @Override
    public void put(K key, V value) {
        this.root = put(root, key, value);
    }

    private Node<K, V> put(Node<K, V> node, K key, V value) {
        if (node == null) {
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
        return node;
    }

    @Override
    public void remove(K key) {
        root = remove(root, key);
    }

    private Node<K, V> remove(Node<K, V> node, K key) {
        if (node == null) {
            return null;
        }

        if (key.compareTo(node.key) < 0) {
            node.left = remove(node.left, key);
            return node;
        } else if (key.compareTo(node.key) > 0) {
            node.right = remove(node.right, key);
            return node;
        } else { // e == node.e
            if (node.right == null) {
                // 只有左子树
                Node<K, V> leftNode = node.left;
                node.left = null;
                size--;
                return leftNode;
            } else if (node.left == null) {
                // 只有右子树
                Node<K, V> rightNode = node.right;
                node.right = null;
                size--;
                return rightNode;
            } else {
                // 双子节点 -> 将右子树的最小节点 替换 此节点
                Node<K, V> successor = minimum(node.right);
                successor.right = removeMin(node.right);
                successor.left = node.left;
                node.left = node.right = null;
                return successor;
            }
        }
    }

    /**
     * 指定节点中的最小节点
     *
     * @param node 根节点
     * @return 最小节点
     */
    public Node<K, V> minimum(Node<K, V> node) {
        if (node == null) {
            return null;
        }

        Node<K, V> leftNode = node.left;
        while (node.left != null) {
            leftNode = node.left;
            node = node.left;
        }

        return leftNode;
    }

    /**
     * 指定节点中的最大节点
     *
     * @param node 根节点
     * @return 最大节点
     */
    public Node<K, V> maximum(Node<K, V> node) {
        if (node == null) {
            return null;
        }

        Node<K, V> rightNode = node.right;
        while (node.right != null) {
            rightNode = node.right;
            node = node.right;
        }

        return rightNode;
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
        node.left = removeMax(node.left);
        return node;
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
        return node;
    }

    @Override
    public boolean contains(K key) {
        return false;
    }

    private boolean contains(Node<K, V> node, K key, V value) {
        if (node == null) {
            return false;
        }

        if (key.compareTo(node.key) == 0) {
            return true;
        } else if (key.compareTo(node.key) < 0) {
            return contains(node.left, key, value);
        } else {
            return contains(node.right, key, value);
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
    public V set(K key, V value) {
        return null;
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
        private K key;
        private V value;
        private Node<K, V> left, right;

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
            left = null;
            right = null;
        }
    }
}