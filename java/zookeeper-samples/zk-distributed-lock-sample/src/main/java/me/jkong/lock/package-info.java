/**
 * 1.推荐使用ConnectionStateListener监控连接的状态，因为当连接LOST时你不再拥有锁
 * 2.分布式的锁全局同步， 这意味着任何一个时间点不会有两个客户端都拥有相同的锁。
 *
 * @author JKong
 * @version v0.0.1
 * @date 2020/5/22 9:40.
 */
package me.jkong.lock;