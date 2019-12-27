package me.jkong.data.structure.heap;

import me.jkong.data.structure.array.Array;

/**
 * @author JKong
 * @version v1.0
 * @description 最大堆
 * @date 2019/12/27 6:46 上午.
 * <p>
 * 使用二叉树实现堆结构称为二叉堆
 * 二叉堆是一棵完全二叉树（完全二叉树是指把元素顺序排列成树的形状）
 * 堆中某个节点的值总是不大于其父节点的值（不管是左节点还是右节点），也称为最大堆。
 * <p>
 * 使用数组存储二叉堆，节点之间的关系如下：
 * parent(i) = (i - 1) / 2
 * left_child(i) = 2*i + 1
 * right_child(i) = 2*i + 2
 * 其中：i为元素在数组中的下标。
 */
public class MaxHeap<E extends Comparable<E>> {

    private Array<E> data;

    public MaxHeap(int capacity) {
        this.data = new Array<>(capacity);
    }

    public MaxHeap() {
        this.data = new Array<>();
    }

    /**
     * 返回堆中元素数量
     *
     * @return 元素数量
     */
    public int getSize() {
        return this.data.getSize();
    }

    /**
     * 当前堆是否为空
     *
     * @return true：空，false：非空
     */
    public boolean isEmpty() {
        return this.data.isEmpty();
    }

    /**
     * 获取父节点下标
     *
     * @param index 子节点下标
     * @return 父节点下标
     */
    public int parent(int index) {
        if (index == 0) {
            throw new IllegalArgumentException("index-0 doesn't have parent");
        }
        return (index - 1) >> 2;
    }

    /**
     * 获取当前节点左孩子节点下标
     *
     * @param index 当前节点下标
     * @return 左孩子节点下标
     */
    public int leftChild(int index) {
        return (index + 1) << 2 - 1;
    }

    /**
     * 获取当前节点右孩子节点下标
     *
     * @param index 当前节点下标
     * @return 右孩子节点下标
     */
    public int rightChild(int index) {
        return (index + 1) << 2;
    }

    /**
     * 向堆中添加元素
     * 1. 将元素添加到堆中
     * 2. 判断添加的元素与其父节点是否满足父节点大于所有自己节点的条件，如果不满则进行交换。
     *
     * @param e 被添加的元素
     */
    public void add(E e) {
        this.data.addLast(e);
        siftUp(data.getSize() - 1);
    }

    /**
     * 判断添加的元素与其父节点是否满足父节点大于所有自己节点的条件，如果不满则进行交换。
     *
     * @param i 当前元素索引
     */
    private void siftUp(int i) {
        while (i > 0 && data.get(parent(i)).compareTo(data.get(i)) < 0) {
            this.data.swap(parent(i), i);
            i = parent(i);
        }
    }
}