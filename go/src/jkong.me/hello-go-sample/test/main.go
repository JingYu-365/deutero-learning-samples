// @Description: TODO
// @Author: JKong
// @Update: 2020/10/4 2:32 下午
package test

import (
	"math"
)

func Triangle(a, b int) int {
	return int(math.Sqrt(float64(a*a + b*b)))
}
