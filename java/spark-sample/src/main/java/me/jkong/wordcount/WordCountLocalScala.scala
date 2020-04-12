package me.jkong.wordcount

import org.apache.spark.{SparkConf, SparkContext}

/**
  * @author JKong
  * @description 使用scala实现wordcount
  * @version v1.0
  * @date 2020-04-11 21:04.
  */
object WordCountLocalScala {

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("Scala Word Count").setMaster("local")
    val sc = new SparkContext(conf)

    val lines = sc.textFile("hdfs://localhost:9000/pride-and-prejudice.txt")
    val words = lines.flatMap { line => line.split(" ") }
    val pairs = words.map { word => (word, 1) }
    val wordCounts = pairs.reduceByKey {
      _ + _
    }
    wordCounts.foreach(wordCount => println(wordCount._1 + "appeared " + wordCount._2 + " times."))
  }

}
