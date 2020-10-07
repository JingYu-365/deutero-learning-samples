package main

import (
	"fmt"
	"time"
)

/*
const (
    Nanosecond  Duration = 1
    Microsecond          = 1000 * Nanosecond
    Millisecond          = 1000 * Microsecond
    Second               = 1000 * Millisecond
    Minute               = 60 * Second
    Hour                 = 60 * Minute
)
*/
func main() {
	// 获取当前时间
	now := time.Now()
	fmt.Println(now)

	// 获取 年月日时分秒
	showTime(now)

	// 获取时间戳
	showTimestamp(now)

	// 时间计算
	timeOperation(now)

	// 时间格式化
	timeFormat(now)

	// 字符串转时间
	stringToTime(now.Format("2006-01-02 15:04:05"))
}

func stringToTime(timeStr string) {
	// 加载时区
	loc, err := time.LoadLocation("Asia/Shanghai")
	if err != nil {
		fmt.Println(err)
		return
	}
	// 按照时区和指定字符串时间格式
	timeObj, err := time.ParseInLocation("2006-01-02 15:04:05", timeStr, loc)
	if err != nil {
		fmt.Println(err)
		return
	}
	fmt.Println(timeObj)
}

func timeFormat(now time.Time) {
	// 格式化的模板为Go的出生时间2006年1月2号15点04分 Mon Jan
	// 24小时制
	fmt.Println(now.Format("2006-01-02 15:04:05"))
	// 12小时制
	fmt.Println(now.Format("2006-1-2 03:04:05"))
	// 其他格式
	fmt.Println(now.Format("2006/01/02 15:04"))
	fmt.Println(now.Format("15:04 2006/01/02"))
	fmt.Println(now.Format("2006/01/02"))
}

func timeOperation(now time.Time) {
	// 当前时间 加 一天时间
	later := now.Add(time.Duration(24 * time.Hour))
	fmt.Println(now)
	fmt.Println(later)

	// 两个时间之间的差值
	fmt.Println(later.Sub(now))

	// 判断两个时间是否相同
	fmt.Println(now.Equal(later))

	// 验证当前时间是否在另一个时间之前
	fmt.Println(now.Before(later))
	fmt.Println(now.After(later))
}

func showTimestamp(now time.Time) {

	// 纳秒 时间戳
	stamp := now.UnixNano()
	fmt.Println(stamp)

	// 秒 时间戳
	stamp = now.Unix()
	fmt.Println(stamp)

	// 将时间戳转为时间格式
	timeObj := time.Unix(stamp, 0)
	showTime(timeObj)

}

func showTime(now time.Time) {
	// 年
	fmt.Printf("Year: %d \n", now.Year())
	// 月
	fmt.Printf("Month: %d \n", now.Month())
	// 日
	fmt.Printf("Day: %d \n", now.Day())
	// 小时
	fmt.Printf("Hour: %d \n", now.Hour())
	// 分钟
	fmt.Printf("Minute: %d \n", now.Minute())
	// 秒
	fmt.Printf("Second: %d \n", now.Second())
}
