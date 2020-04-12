spark-submit \
--class me.jkong.wordcount.WordCountCluster \
--num-executors 1 \
--driver-memory 512m \
--executor-memory 512m \
--executor-cores 1 \
/Users/zhdh/Documents/github/personal-samples/java/spark-sample/target/spark-sample-0.0.1-jar-with-dependencies.jar