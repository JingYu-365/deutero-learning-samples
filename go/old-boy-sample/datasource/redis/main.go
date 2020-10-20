package main

import (
	"fmt"

	"github.com/go-redis/redis"
)

var redisClient *redis.Client

func init() {
	redisClient = redis.NewClient(&redis.Options{
		Addr:     "127.0.0.1:6379",
		Password: "",
		DB:       0,
	})

	_, err := redisClient.Ping().Result()
	if err != nil {
		fmt.Printf("connect redis failed, err: ", err)
	}
	fmt.Println("connect redis success!")
}

func main() {
	zsetOperation()
}

func zsetOperation() {
	zkey := "rank"
	items := []redis.Z{
		redis.Z{Score: 96, Member: "Java"},
		redis.Z{Score: 93, Member: "Javascript"},
		redis.Z{Score: 97, Member: "Python"},
		redis.Z{Score: 95, Member: "CSS"},
		redis.Z{Score: 99, Member: "Golang"},
	}

	redisClient.ZAdd(zkey, items...)

	newScore, err := redisClient.ZIncrBy(zkey, 10.0, "Golang").Result()
	if err != nil {
		fmt.Printf("zIncrBy failed, err: %v \n", err)
	}
	fmt.Printf("new score: %f \n", newScore)

	// 获取分数最高的三个
	ret, err := redisClient.ZRevRangeWithScores(zkey, 0, 2).Result()
	if err != nil {
		fmt.Printf("get best 3 failed, err: %v \n", err)
	}
	for _, z := range ret {
		fmt.Println(z.Member, z.Score)
	}
}
