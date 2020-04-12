/**
 * @author JKong
 * @description TODO
 * @version v1.0
 * @date 2020-04-12 10:16.
 */
package me.jkong.transformationandaction;
/*

transformation:
- transformation 操作会针对已有的RDD创建一个新的RDD，比如map、flatMap、mapToPair等等操作。
- transformation的特点就是lazy，也就是在不设置action操作的时候，是不是进行执行的。
- 常用 Transformation 操作
    - map
    - filer
    - flatMap
    - mapToPair
    - groupByKey
    - reduceByKey
    - sortByKey
    - join
    - cogroup

action:
- action主要是对RDD进行最后的操作，比如foreach、reduce、保存文件等等，并可以返回结果给Driver程序。
- action操作会一个spark job操作，进行任务执行。
- 常用 Action 操作
    - reduce：将RDD中的所有元素进行聚合
    - collect：将RDD中的所有数据获取到本地客户端
    - count：获取RDD元素总数
    - take(n)：获取RDD中的前n个元素
    - saveAsFile：将RDD元素保存到文件中去，并对每个元素调用toString方法
    - countByKey：对每个key对应的值进行农村他计数
    - foreach：遍历RDD中的每个元素
 */