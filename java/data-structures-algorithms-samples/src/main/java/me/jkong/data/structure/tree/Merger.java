package me.jkong.data.structure.tree;

/**
 * @author JKong
 * @version v1.0
 * @description 线段树节点之间merge关系
 * @date 2019/12/30 3:31 下午.
 */
public interface Merger<E> {
    /**
     * 定义两个节点直接的merge方式
     *
     * @param a 左节点
     * @param b 右节点
     * @return merge后的数据
     */
    E merge(E a, E b);
}