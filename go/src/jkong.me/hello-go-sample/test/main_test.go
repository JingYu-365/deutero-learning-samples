// @Description: TODO
// @Author: JKong
// @Update: 2020/10/4 2:33 下午
package test

import "testing"

func TestTriangle(t *testing.T) {
	// 定义数据集
	test := []struct {
		a, b, c int
	}{
		// 常规数据集
		{3, 4, 5},
		{5, 12, 13},
		{8, 15, 17},
		// 大数据
		{300000, 400000, 500000},
	}

	// 测试数据集
	for _, tt := range test {
		if actual := Triangle(tt.a, tt.b); actual != tt.c {
			t.Errorf("Triangle Test (%d, %d) actual: %d, expected: %d",
				tt.a, tt.b, actual, tt.c)
		}
	}
}

// 使用 pprof 查看耗时
// 1. go test -bench . -cpuprofile cpu.out
// 2. go tool pprof cpu.out

// 使用 go-torch 查看火焰图
// 1. 安装：go get -v github.com/uber/go-torch
//

func BenchmarkTriangle(b *testing.B) {
	aa, bb, cc := 30000, 40000, 50000

	// 重置统计时间
	b.ResetTimer()

	for i := 0; i <= b.N; i++ {
		if actual := Triangle(aa, bb); actual != cc {
			b.Errorf("Triangle Test (%d, %d) actual: %d, expected: %d",
				aa, bb, actual, cc)
		}
	}
}
