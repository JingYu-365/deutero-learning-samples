package me.jkong.data.structure.union;

import java.util.Random;

/**
 * @author JKong
 * @version v1.0
 * @description TODO
 * @date 2020/1/4 7:21 上午.
 */
public class MainTest {
    public static void main(String[] args) {
        int size = 10000000;
        int m = 10000000;
        
//        ArrayUnionFind uf1 = new ArrayUnionFind(size);
//        System.out.println("ArrayUnionFind: " + testUnionFind(uf1, m) + " s");
//
//        QuickUnionFind uf2 = new QuickUnionFind(size);
//        System.out.println("ArrayUnionFind: " + testUnionFind(uf2, m) + " s");
    
        QuickUnionFind2 uf3 = new QuickUnionFind2(size);
        System.out.println("ArrayUnionFind: " + testUnionFind(uf3, m) + " s");
    
        QuickUnionFind3 uf4 = new QuickUnionFind3(size);
        System.out.println("ArrayUnionFind: " + testUnionFind(uf4, m) + " s");
    
        QuickUnionFindPathCompression uf5 = new QuickUnionFindPathCompression(size);
        System.out.println("ArrayUnionFind: " + testUnionFind(uf5, m) + " s");
    }
    
    private static double testUnionFind(UnionFind unionFind, int m) {
        
        int size = unionFind.getSize();
        Random random = new Random();
        long start = System.nanoTime();
        
        for (int i = 0; i < m; i++) {
            int a = random.nextInt(size);
            int b = random.nextInt(size);
            unionFind.union(a, b);
        }
        
        for (int i = 0; i < m; i++) {
            int a = random.nextInt(size);
            int b = random.nextInt(size);
            unionFind.isConnected(a, b);
        }
        
        long end = System.nanoTime();
        
        return (end - start) / 1000000000.0;
    }
}