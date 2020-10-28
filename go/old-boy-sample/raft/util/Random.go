// @Description: 随机数工具
// @Author: JKong
// @Update: 2020/10/28 7:00 上午
package util

import "math/rand"

func RandRange(min, max int64) (rangeRandNum int64) {
	return rand.Int63n(max-min) + min
}
