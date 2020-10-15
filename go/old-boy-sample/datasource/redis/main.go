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

}
