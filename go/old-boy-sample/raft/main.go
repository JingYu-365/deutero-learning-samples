// @Description: 入口程序
// @Author: JKong
// @Update: 2020/10/27 10:40 下午
package main

import (
	"math/rand"
	"raft/config"
	"raft/entity"
	"time"
)

var (
	leader = entity.Leader{LeaderId: -1}
)

func main() {
	// 1. 初始化节点，身份都为 follower
	// 初始化 Raft 实例
	for i := 0; i <= config.RaftCount; i++ {

	}

	// 若为 candidate 身份，则进行拉票

	// 产生 Leader
	select {}
}

func Make(me int) *entity.Raft {
	r := entity.Raft{
		Me:            me,                   // 当前节点编号
		VotedFor:      -1,                   // 初始化投票状态
		State:         0,                    // 初始化自己的状态 0：follower
		Timeout:       0,                    // 超时时间
		CurrentLeader: -1,                   // 当前 Leader
		CurrentTerm:   0,                    // 设置节点任期
		Message:       make(chan bool, 100), // 初始化消息通道
		ElectChan:     make(chan bool, 100), // 初始化选举通道
		HeartChan:     make(chan bool, 100), // 初始化心跳通道
		HeartBeatRe:   make(chan bool, 100), // 初始化心跳回复通道
	}

	// 设置随机种子 todo 为什么需要设置随机种子
	rand.Seed(time.Now().UnixNano())

	// 选举的协程

	// 心跳的协程

	return &r
}
