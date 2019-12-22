package me.jkong.data.stack;

/**
 * @author JKong
 * @version v1.0
 * @description stack interface
 * @date 2019/12/21 5:45 下午.
 * <p>
 * 栈：First in First out
 * <p>
 * 场景：
 * - 可以实现Undo操作
 * - 程序调用的方法栈
 * - 浏览器实现前进后退
 * <p>
 * 能力：
 * - 泛型
 * - 动态扩容，动态缩容
 * - 多种底层实现方式
 */
public interface Stack<E> {
    /**
     * 添加元素
     *
     * @param e 元素
     */
    void push(E e);

    /**
     * 去除最后的元素
     *
     * @return 元素
     */
    E pop();

    /**
     * 查询最后的元素
     *
     * @return 元素
     */
    E peek();

    /**
     * 获取栈大小
     *
     * @return 大小
     */
    int getSize();

    /**
     * 栈是否为空
     *
     * @return true：空，false：不为空
     */
    boolean isEmpty();
}