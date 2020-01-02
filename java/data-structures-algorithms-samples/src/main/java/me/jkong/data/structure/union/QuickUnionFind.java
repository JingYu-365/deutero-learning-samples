package me.jkong.data.structure.union;

/**
 * @author JKong
 * @version v1.0
 * @description 使用树结构实现并查集
 * @date 2020/1/3 7:08 上午.
 */
public class QuickUnionFind implements UnionFind {
    
    private int[] parent;
    
    public QuickUnionFind(int size) {
        parent = new int[size];
        for (int i = 0; i < parent.length; i++) {
            parent[i] = i;
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
        
        parent[pRoot] = qRoot;
    }
    
    @Override
    public boolean isConnected(int p, int q) {
        return find(p) == find(q);
    }
}