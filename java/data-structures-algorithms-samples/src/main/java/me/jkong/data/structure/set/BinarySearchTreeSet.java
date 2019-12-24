package me.jkong.data.structure.set;

import me.jkong.data.structure.tree.BinarySearchTree;

/**
 * @author JKong
 * @version v1.0
 * @description 使用二叉树实现set
 * @date 2019/12/24 2:31 下午.
 */
public class BinarySearchTreeSet<E extends Comparable<E>> implements Set<E> {
    
    private BinarySearchTree<E> bst;
    
    private BinarySearchTreeSet(BinarySearchTree bst) {
        this.bst = bst;
    }
    
    @Override
    public void add(E e) {
        bst.add(e);
    }
    
    @Override
    public void remove(E e) {
        bst.remove(e);
    }
    
    @Override
    public boolean contains(E e) {
        return bst.contains(e);
    }
    
    @Override
    public int getSize() {
        return bst.getSize();
    }
    
    @Override
    public boolean isEmpty() {
        return bst.isEmpty();
    }
}