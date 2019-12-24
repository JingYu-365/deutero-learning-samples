package me.jkong.data.structure.tree;

import com.sun.org.apache.bcel.internal.generic.RET;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * @author JKong
 * @version v1.0
 * @description Tree
 * @date 2019/12/24 1:12 下午.
 * tips:
 */
public class BinarySearchTree<E extends Comparable<E>> {
    
    private Node<E> root;
    private int size;
    
    public BinarySearchTree(Node<E> root) {
        this.root = root;
        size = 0;
    }
    
    /**
     * 树大小
     *
     * @return 大小
     */
    public int getSize() {
        return size;
    }
    
    /**
     * 是否为空
     *
     * @return true：空，false：非空
     */
    public boolean isEmpty() {
        return size == 0;
    }
    
    /**
     * 添加元素
     *
     * @param e 元素
     */
    public void add(E e) {
        root = add(root, e);
    }
    
    /**
     * 递归查找插入位置
     *
     * @param node 节点
     * @param e    元素
     */
    private Node<E> add(Node<E> node, E e) {
        if (node == null) {
            return new Node<>(e);
        }
        
        if (e.compareTo(node.e) < 0) {
            node.left = add(node.left, e);
        } else {
            node.right = add(node.right, e);
        }
        return node;
    }
    
    /**
     * 设置元素
     *
     * @param e 元素
     */
    public E set(E e) {
        
        return null;
    }
    
    /**
     * 移除数据指定元素的节点
     *
     * @param e 元素
     */
    public void remove(E e) {
        root = remove(root, e);
    }
    
    private Node<E> remove(Node<E> node, E e) {
        
        if (node == null) {
            return null;
        }
        
        if (e.compareTo(node.e) < 0) {
            node.left = remove(node.left, e);
            return node;
        } else if (e.compareTo(node.e) > 0) {
            node.right = remove(node.right, e);
            return node;
        } else { // e == node.e
            if (node.right == null) {
                // 只有左子树
                Node<E> leftNode = node.left;
                node.left = null;
                size--;
                return leftNode;
            } else if (node.left == null) {
                // 只有右子树
                Node<E> rightNode = node.right;
                node.right = null;
                size--;
                return rightNode;
            } else {
                // 双子节点 -> 将右子树的最小节点 替换 此节点
                Node<E> successor = minimum(node.right);
                successor.right = removeMin(node.right);
                successor.left = node.left;
                node.left = node.right = null;
                return successor;
            }
        }
    }
    
    /**
     * 删除最小值
     *
     * @return 移除最小值的根节点
     */
    public Node<E> removeMin() {
        if (root == null) {
            throw new IllegalArgumentException("RemoveMax failed, empty cannot operation.");
        }
        root = removeMin(root);
        return root;
    }
    
    private Node<E> removeMin(Node<E> node) {
        
        if (node.left == null) {
            Node<E> rightNode = node.right;
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
    public Node<E> removeMax() {
        if (root == null) {
            throw new IllegalArgumentException("RemoveMax failed, empty cannot operation.");
        }
        root = removeMax(root);
        return root;
    }
    
    private Node<E> removeMax(Node<E> node) {
        
        if (node.right == null) {
            Node<E> leftNode = node.left;
            node.left = null;
            size--;
            return leftNode;
        }
        node.right = removeMax(node.right);
        return node;
    }
    
    
    /**
     * 指定节点中的最小节点
     *
     * @param node 根节点
     * @return 最小节点
     */
    public Node<E> minimum(Node<E> node) {
        if (node == null) {
            return null;
        }
        
        Node<E> leftNode = node.left;
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
    public Node<E> maximum(Node<E> node) {
        if (node == null) {
            return null;
        }
        
        Node<E> rightNode = node.right;
        while (node.right != null) {
            rightNode = node.right;
            node = node.right;
        }
        
        return rightNode;
    }
    
    /**
     * 比当前元素小的节点的数据
     *
     * @param e 指定元素
     * @return 比指定元素小的节点值
     */
    public E floor(E e) {
        
        
        return null;
    }
    
    /**
     * 比当前元素大的节点的数据
     *
     * @param e 指定元素
     * @return 比指定元素大的节点值
     */
    public E ceil(E e) {
        
        
        return null;
    }
    
    /**
     * 找出指定节点的后置节点
     *
     * @return 后置节点
     */
    public Node<E> successor(Node<E> node) {
        
        
        return null;
    }
    
    /**
     * 找出指定节点的前驱节点
     *
     * @return 前驱节点
     */
    public Node<E> predecessor(Node<E> node) {
        
        
        return null;
    }
    
    /**
     * 查询节点的排名
     *
     * @param e 元素
     * @return 排名
     */
    public int rank(E e) {
        
        
        return -1;
    }
    
    /**
     * 查询树的深度
     *
     * @return 深度
     */
    public int depth() {
        
        
        return -1;
    }
    
    /**
     * 元素是否存在
     *
     * @param e 元素
     * @return true：存在，false：不存在
     */
    public boolean contains(E e) {
        return contains(root, e);
    }
    
    private boolean contains(Node<E> node, E e) {
        if (node == null) {
            return false;
        }
        
        if (e.compareTo(node.e) == 0) {
            return true;
        } else if (e.compareTo(node.e) < 0) {
            return contains(node.left, e);
        } else {
            return contains(node.right, e);
        }
    }
    
    /**
     * 深度优先遍历：前序遍历，中序遍历，后序遍历
     * <p>
     * 前序遍历
     */
    public void preOrder() {
        preOrder(root);
    }
    
    private void preOrder(Node<E> node) {
        if (node == null) {
            return;
        }
        System.out.println(node.e);
        preOrder(node.left);
        preOrder(node.right);
    }
    
    /**
     * 非递归前序遍历
     */
    public void preOrderNR() {
        
        Stack<Node<E>> contain = new Stack<>();
        contain.push(root);
        while (!contain.isEmpty()) {
            Node<E> cur = contain.pop();
            System.out.println(cur.e);
            
            if (cur.right != null) {
                contain.push(cur.right);
            }
            if (cur.left != null) {
                contain.push(cur.left);
            }
        }
    }
    
    /**
     * 中序遍历
     */
    public void inOrder() {
        inOrder(root);
    }
    
    private void inOrder(Node<E> node) {
        if (node == null) {
            return;
        }
        
        inOrder(node.left);
        System.out.println(node.e);
        inOrder(node.right);
    }
    
    /**
     * 后序遍历
     */
    public void postOrder() {
        postOrder(root);
    }
    
    private void postOrder(Node<E> node) {
        if (node == null) {
            return;
        }
        
        inOrder(node.left);
        inOrder(node.right);
        System.out.println(node.e);
    }
    
    /**
     * 广度优先遍历：层序遍历
     * <p>
     * 层序遍历
     * <p>
     * - 更快找到问题的解
     * - 常用于算法设计中 - 最短路径
     */
    private void levelOrder() {
        if (root == null) {
            return;
        }
        
        Queue<Node<E>> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            Node<E> node = queue.remove();
            System.out.println(node.e);
            
            if (node.left != null) {
                queue.add(node.left);
            }
            if (node.right != null) {
                queue.add(node.right);
            }
        }
    }
    
    private static class Node<E> {
        private E e;
        private Node<E> left, right;
        
        public Node(E e) {
            this.e = e;
            left = null;
            right = null;
        }
    }
}