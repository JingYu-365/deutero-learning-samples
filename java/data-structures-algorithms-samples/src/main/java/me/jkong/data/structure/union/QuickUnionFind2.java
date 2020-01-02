package me.jkong.data.structure.union;

/**
 * @author JKong
 * @version v1.0
 * @description 使用树结构实现并查集
 * @date 2020/1/3 7:08 上午.
 */
public class QuickUnionFind2 implements UnionFind {
    
    private int[] parent;
    /**
     * 存放每个节点的元素个数
     */
    private int[] sz;
    
    public QuickUnionFind2(int size) {
        parent = new int[size];
        sz = new int[size];
        for (int i = 0; i < parent.length; i++) {
            parent[i] = i;
            sz[i] = 1;
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
        
        // 根据两个元素所在树的元素个数不同判断合并方向
        // 将元素少的集合合并到元素多的集合上
        if (sz[pRoot] < sz[qRoot]) {
            parent[pRoot] = qRoot;
            sz[qRoot] += sz[pRoot];
        } else {
            parent[qRoot] = pRoot;
            sz[pRoot] += sz[qRoot];
        }
    }
    
    @Override
    public boolean isConnected(int p, int q) {
        return find(p) == find(q);
    }
}