package me.jkong.data.structure.union;

/**
 * @author JKong
 * @version v1.0
 * @description 使用树结构实现并查集 实现rank优化
 * @date 2020/1/3 7:08 上午.
 */
public class QuickUnionFind3 implements UnionFind {
    
    private int[] parent;
    /**
     * rank[i] 表示已i为根的集合所表示的树的层数
     */
    private int[] rank;
    
    public QuickUnionFind3(int size) {
        parent = new int[size];
        rank = new int[size];
        for (int i = 0; i < parent.length; i++) {
            parent[i] = i;
            rank[i] = 1;
        }
    }
    
    @Override
    public int getSize() {
        return parent.length;
    }
    
    /**
     * 查找当前节点的根节点
     *
     * @param p 当前节点
     * @return 根节点 index
     */
    int find(int p) {
        if (p < 0 || p >= getSize()) {
            throw new IllegalArgumentException("index is out of bound.");
        }
        
        while (p != parent[p]) {
            p = parent[p];
        }
        return p;
    }
    
    @Override
    public void union(int p, int q) {
        int pRoot = find(p);
        int qRoot = find(q);
        
        if (pRoot == qRoot) {
            return;
        }
        
        // 根据两个元素所在树的 rank 不同判断合并方向
        // 将 rank 低的集合合并到 rank 高的集合上
        if (rank[pRoot] < rank[qRoot]) {
            parent[pRoot] = qRoot;
        } else if (rank[pRoot] > rank[qRoot]) {
            parent[qRoot] = pRoot;
        } else {
            // rank[pRoot] == rank[qRoot] 情况
            parent[qRoot] = pRoot;
            rank[pRoot] += 1;
        }
    }
    
    @Override
    public boolean isConnected(int p, int q) {
        return find(p) == find(q);
    }
}