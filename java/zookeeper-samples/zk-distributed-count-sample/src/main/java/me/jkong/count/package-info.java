/**
 * 计数器是用来计数的, 利用ZooKeeper可以实现一个集群共享的计数器。
 * 只要使用相同的path就可以得到最新的计数器值，这是由ZooKeeper的一致性保证的。
 * Curator有两个计数器：
 * 一个用int来计数(SharedCount)；
 * 一个用long来计数(DistributedAtomicLong)。
 *
 * @author JKong
 * @version v0.0.1
 * @date 2020/5/22 10:55.
 */
package me.jkong.count;