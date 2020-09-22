package me.jkong.scala.basic.func

/**
 * 函数基础使用：
 * - 函数定义
 * - 函数调用
 *
 * @author JKong
 * @version v0.0.1
 * @date 2020/9/22 21:38.
 */
object BasicFunction {
    def main(args: Array[String]): Unit = {
        println(add(1, 2))
        println(remove(3, 4))
        
        // 参数默认值
        showName()
        showName("Zhang")
        
        // 命名参数
        println(speed(100, 10))
        println(speed(distance = 100, time = 10))
        println(speed(time = 10, distance = 100))
        
        // 可变参数
        println(sum(1, 2))
        println(sum(1, 2, 3))
        println(sum(1, 2, 3, 4))
        
        // 条件判断
        val num = 1
        val a = if (num > 0) true else false
        println(a)
        
        // 条件循环
        println(1 to 10)
        println(1.to(10))
        for (i <- 1 to 10) print(i)
        println()
        
        println(Range(1, 10, 2))
        println(Range(10, 1, -2))
        for (i <- Range(1, 10, 1)) print(i)
        println()
        
        println(1 until 10)
        println(1.until(10))
        for (i <- 1.until(10) if i % 2 == 0) print(i)
        println()
        
        1.until(100).filter(x => x % 7 == 0).foreach(x => println(x))
    }
    
    def add(a: Int, b: Int): Int = {
        // 最后一行就是返回值，不需要使用return
        a + b
    }
    
    /**
     * 方法只有一行时，可以不使用{}
     */
    def remove(c: Int, d: Int): Int = c - d
    
    /**
     * scala 默认参数使用
     */
    def showName(name: String = "JKong"): Unit = {
        println("Hello", name)
    }
    
    /**
     * 命名参数使用，即可以不按照参数的顺序，而是依据参数的名称进行参数传值
     */
    def speed(distance: Float, time: Float): Float = {
        return distance / time
    }
    
    /**
     * 可变参数
     */
    def sum(nums: Int*): Int = {
        var result = 0
        for (num <- nums) {
            result += num
        }
        result
    }
}
