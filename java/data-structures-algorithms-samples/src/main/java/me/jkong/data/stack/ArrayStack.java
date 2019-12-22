package me.jkong.data.stack;

import me.jkong.data.array.Array;

import java.util.Objects;

/**
 * @author JKong
 * @version v1.0
 * @description 使用数组实现 stack
 * @date 2019/12/21 5:32 下午.
 */
public class ArrayStack<E> implements Stack<E> {

    private Array<E> data;

    public ArrayStack(int capacity) {
        this.data = new Array<>(capacity);
    }

    public ArrayStack() {
        this(16);
    }

    @Override
    public void push(E e) {
        data.addLast(e);
    }

    @Override
    public E pop() {
        return data.removeLast();
    }

    @Override
    public E peek() {
        return data.getLast();
    }

    @Override
    public int getSize() {
        return data.getSize();
    }

    public int getCapacity() {
        return data.getCapacity();
    }

    @Override
    public boolean isEmpty() {
        return data.isEmpty();
    }

    @Override
    public String toString() {
        StringBuilder dataStringBuilder = new StringBuilder("Stack: ");
        dataStringBuilder.append("[");
        for (int i = 0; i < getSize(); i++) {
            dataStringBuilder.append(data.get(i));
            if (i != data.getSize() - 1) {
                dataStringBuilder.append(", ");
            }
        }
        dataStringBuilder.append("] top");
        return dataStringBuilder.toString();
    }
    
    public static void main(String[] args) {
        ArrayStack<Integer> stack = new ArrayStack<>(4);
        
        for (int i = 0; i < 6; i++) {
            stack.push(i);
            System.out.println(stack);
        }
        
        stack.pop();
        System.out.println(stack);
    }
}