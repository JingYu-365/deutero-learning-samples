package me.jkong.data.structure.union;

/**
 * @author JKong
 * @version v1.0
 * @description 使用array实现 union find
 * @date 2020/1/3 6:41 上午.
 */
public class ArrayUnionFind implements UnionFind {
    
    private int[] id;
    
    public ArrayUnionFind(int size) {
        id = new int[size];
        for (int i = 0; i < id.length; i++) {
            id[i] = i;
        }
    }
    
    @Override
    public int getSize() {
        return id.length;
    }
    
    private int find(int p) {
        if (p < 0 || p >= id.length) {
            throw new IllegalArgumentException("index is out of bound.");
        }
        
        return id[p];
    }
    
    @Override
    public void union(int p, int q) {
        int pId = find(p);
        int qId = find(q);
        
        if (pId == qId) {
            return;
        }
        
        for (int i = 0; i < id.length; i++) {
            if (id[i] == qId) {
                id[i] = pId;
            }
        }
    }
    
    @Override
    public boolean isConnected(int p, int q) {
        return find(p) == find(q);
    }
}