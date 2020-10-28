// @Description: 节点核心逻辑
// @Author: JKong
// @Update: 2020/10/27 10:31 下午
package entity

import (
	"fmt"
	"log"
	"raft/config"
	"raft/util"
	"sync"
	"time"
)

// Leader 主节点结构
// todo 本文逻辑有些混乱存在不完整性，暂且作废。
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
	Timeout int64
}

// Election 选举
func (rf *Raft) Election() {
	// 设置选举结果，是否选举出leader
	var electResult bool
	for {
		// 设置超时时间
		rf.Timeout = util.RandRange(300, 150)

		// 设置发送时间
		rf.LastMessageTime = time.Now().UnixNano() / int64(time.Millisecond)

		select {
		// 设置当前选举的超时时间，延时等待选举完成，如果没完成，则进行下一轮选举，并且上一轮作废
		case <-time.After(time.Duration(rf.Timeout) * time.Millisecond):
			log.Printf("this node status is: %d \n", rf.State)
		}
		electResult = false
		for !electResult {
			// 选主逻辑
			electResult = rf.ElectionOne()
		}

		// todo 结束选举
	}
}

// Election 选举
func (rf *Raft) ElectionOne() bool {
	// 设置超时时间
	var timeout int64 = 100

	// 投票数量
	var vote int

	// 设置心跳标识
	triggerHartBeat := false

	// 选主结果
	electSuccess := false

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
					if rf.CurrentLeader < 0 {
						rf.ElectChan <- true
					}
				}()
			}
		}

		// 设置投票数量
		vote = 1

		// 遍历进行选票统计
		for i := 0; i < config.RaftCount; i++ {
			select {
			case voteOk := <-rf.ElectChan:
				if voteOk {
					// 选票 加1
					vote++
					// 判断是否票数超出半数
					electSuccess = vote > config.RaftCount/2
					if electSuccess && !triggerHartBeat {
						// 选主成功
						// 设置心跳标识
						triggerHartBeat = true
						// 标识自己为Leader
						rf.Mu.Lock()
						rf.becomeLeader()
						rf.Mu.Unlock()
						// 由 Leader 向其他 node 发送心跳
						rf.HeartChan <- true
						log.Printf("node %d become leader!\n", rf.Me)
					}
				}
			}
		}
		// 若不超时，且选票大于半数，则选举成功，Break
		if timeout > (rf.LastMessageTime+rf.Timeout) && vote > config.RaftCount/2 && rf.CurrentLeader != -1 {
			break
		}
	}
	return electSuccess
}

// leader 发送心跳
// follower 回复心跳
// 同步数据
func (rf *Raft) SendHeartBeat() {
	for true {
		select {
		case <-rf.HeartChan:
			rf.sendAppendEntriesImpl()
		}
	}
}

// 用于给leader返回确认信号
func (rf *Raft) sendAppendEntriesImpl() {
	// 如果主节点是自己，就不需要回复
	if rf.CurrentLeader == rf.Me {
		return
	}
	// 如果当前自己是从节点

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
