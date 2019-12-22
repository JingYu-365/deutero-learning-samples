package me.jkong.data.structure.array;

/**
 * @author JKong
 * @version v1.0
 * @description 自定义动态数组
 * @date 2019/12/21 10:25 上午.
 */
public class Array<E> {
    private E[] data;
    private int size;
    
    public Array(int capacity) {
        this.data = (E[]) new Object[capacity];
        this.size = 0;
    }
    
    /**
     * 无参构造器，默认大小为16
     */
    public Array() {
        this(16);
    }
    
    /**
     * 获取大小
     *
     * @return size
     */
    public int getSize() {
        return size;
    }
    
    /**
     * 获取容量大小
     *
     * @return data#length
     */
    public int getCapacity() {
        return data.length;
    }
    
    /**
     * 数组是否为空
     *
     * @return true：数组为空
     * false：数组不为空
     */
    public boolean isEmpty() {
        return size == 0;
    }
    
    /**
     * 向最后添加元素
     *
     * @param e 数据
     */
    public void addLast(E e) {
        add(getSize(), e);
    }
    
    /**
     * 添加第一个元素
     *
     * @param e 元素
     */
    public void addFirst(E e) {
        add(0, e);
    }
    
    /**
     * ￿
     * 在已有元素中的index位置插入一个新元素
     *
     * @param index 位置
     * @param e     元素
     */
    public void add(int index, E e) {
        
        if (index < 0 || index > size) {
            throw new IllegalArgumentException("Add failed. Required index >= 0 and index <= size.");
        }
        
        if (size == getCapacity()) {
            resize(2 * getCapacity());
        }
        System.arraycopy(data, index, data, index + 1, size - index);
        
        data[index] = e;
        size++;
    }
    
    private void resize(int newCapacity) {
        E[] newData = (E[]) new Object[newCapacity];
        System.arraycopy(data, 0, newData, 0, getSize());
        data = newData;
    }
    
    /**
     * 获取指定index位置的数据
     *
     * @param index 位置下标
     * @return 元素
     */
    public E get(int index) {
        if (index < 0 || index >= size) {
            throw new IllegalArgumentException("Get failed. Index is illegal.");
        }
        return data[index];
    }
    
    /**
     * 获取第一个数据
     *
     * @return 元素
     */
    public E getFirst() {
        return get(0);
    }
    
    /**
     * 获取最后一个数据
     *
     * @return 元素
     */
    public E getLast() {
        return get(getSize() - 1);
    }
    
    /**
     * 对指定位置元素赋新值
     *
     * @param index 下标
     * @param e     元素
     * @return 原来此位置的数据
     */
    public E set(int index, E e) {
        if (index < 0 || index >= size) {
            throw new IllegalArgumentException("Get failed. Index is illegal.");
        }
        E ret = data[index];
        data[index] = e;
        return ret;
    }
    
    /**
     * 验证是否包含某个元素
     *
     * @param e 元素
     * @return true：元素存在
     * false：元素不存在
     */
    public boolean contains(E e) {
        for (int i = 0; i < size; i++) {
            if (data[i] == e) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * 查找指定元素的下标
     *
     * @param e 元素
     * @return 下标，如果元素不存在，则返回-1；
     */
    public int find(E e) {
        for (int i = 0; i < size; i++) {
            if (data[i].equals(e)) {
                return i;
            }
        }
        return -1;
    }
    
    /**
     * 移除指定下标的元素
     *
     * @param index 下标
     * @return 被删除位置的元素
     */
    public E remove(int index) {
        if (index < 0 || index >= size) {
            throw new IllegalArgumentException("Remove failed. Index is illegal.");
        }
        
        E ret = data[index];
        if (size - index + 1 >= 0) {
            System.arraycopy(data, index + 1, data, index, size - index + 1);
        }
        size--;
        
        // 当剩余数据达到1/4时，将容量缩小为1/2，目的是为了减少复杂度震荡。
        if (size == getCapacity() / 4 && getCapacity() / 2 != 0) {
            resize(getCapacity() / 2);
        }
        data[size] = null;
        return ret;
    }
    
    /**
     * 删除第一个元素
     *
     * @return 被删除位置的元素
     */
    public E removeFirst() {
        return remove(0);
    }
    
    /**
     * 删除最后一个元素
     *
     * @return 最后一个元素值
     */
    public E removeLast() {
        return remove(size - 1);
    }
    
    /**
     * 删除一个指定元素
     *
     * @param e 元素
     */
    public void removeElement(E e) {
        int index = find(e);
        if (index != -1) {
            remove(index);
        }
    }
    
    @Override
    public String toString() {
        StringBuilder arrStringBuilder = new StringBuilder("Array: ");
        arrStringBuilder.append(String.format("size = %d , capacity = %d \n", size, data.length));
        arrStringBuilder.append("[");
        for (int i = 0; i < size; i++) {
            arrStringBuilder.append(data[i]);
            if (i != size - 1) {
                arrStringBuilder.append(", ");
            }
        }
        arrStringBuilder.append("]");
        return arrStringBuilder.toString();
    }
}