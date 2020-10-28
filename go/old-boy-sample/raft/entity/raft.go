// @Description: TODO
// @Author: JKong
// @Update: 2020/10/27 10:31 下午
package entity

import (
	"fmt"
	"raft/config"
	"raft/util"
	"sync"
	"time"
)

// Leader 主节点结构
type Leader struct {
	// 任期
	Term int
	// Leader 编号
	LeaderId int
}

type Raft struct {
	// 锁
	Mu sync.Mutex
	// 节点编号
	Me int
	// 当前任期
	CurrentTerm int
	// 投票给的节点
	VotedFor int
	// 状态 => 0：follower 1：candidate 2：leader
	State int
	// 最后一条数据的时间
	LastMessageTime int64
	// 当前 Leader
	CurrentLeader int
	// 消息通道
	Message chan bool
	// 选举通道
	ElectChan chan bool
	// 心跳通道
	HeartChan chan bool
	// 心跳回复通道
	HeartBeatRe chan bool
	// 超时时间
	Timeout int
}

// Election 选举
func (rf *Raft) Election() {
	for {
		// 设置超时时间
		timeout := util.RandRange(300, 150)

		// 设置发送时间
		rf.LastMessageTime = time.Now().UnixNano() / int64(time.Millisecond)

		//
	}
}

// Election 选举
func (rf *Raft) ElectionOne() {
	for {
		// 设置超时时间
		var timeout int64 = 100

		// 投票数量
		var vote int

		// 将当前节点变为 candidate
		rf.Mu.Lock()
		rf.becomeCandidate()
		rf.Mu.Unlock()

		fmt.Println("start to elect leader.")
		// 开始选举
		for {
			// 遍历所有节点并拉选票
			for i := 0; i < config.RaftCount; i++ {
				if i != rf.Me {
					// 执行拉选票操作
					go func() {

					}()
				}
			}
		}

	}
}

// SeTerm 设置任期
func (r *Raft) SetTerm(term int) {
	r.CurrentTerm = term
}

// becomeCandidate 称为候选人
func (rf *Raft) becomeCandidate() {
	rf.State = 1
	rf.SetTerm(rf.CurrentTerm + 1)
	rf.VotedFor = rf.Me
	rf.CurrentLeader = -1
}

// becomeLeader  称为Leader
func (rf *Raft) becomeLeader() {
	rf.State = 2
	rf.CurrentLeader = rf.Me
}
