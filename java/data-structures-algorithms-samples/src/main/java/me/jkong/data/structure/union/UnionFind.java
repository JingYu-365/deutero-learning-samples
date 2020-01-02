package me.jkong.data.structure.union;

/**
 * @author JKong
 * @version v1.0
 * @description 并查集能力
 * @date 2020/1/3 6:35 上午.
 */
public interface UnionFind {
    /**
     * 获取元素数量
     *
     * @return 数量
     */
    int getSize();
    
    /**
     * 将两个元素建立连接
     *
     * @param p 元素1
     * @param q 元素2
     */
    void union(int p, int q);
    
    /**
     * 判断这两个元素是否连接
     *
     * @param p 元素1
     * @param q 元素2
     * @return true：已连接，false：未连接
     */
    boolean isConnected(int p, int q);
}