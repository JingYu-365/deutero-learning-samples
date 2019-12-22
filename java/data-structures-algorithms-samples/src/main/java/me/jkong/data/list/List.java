package me.jkong.data.list;

/**
 * @author JKong
 * @version v1.0
 * @description List
 * @date 2019/12/22 6:59 上午.
 */
public interface List<E> {
    
    /**
     * 获取大小
     *
     * @return 大小
     */
    int getSize();
    
    int add(int index, E e);
}