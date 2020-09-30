// @Description: 寻找最长不含有重复字符的子串
// @Author: JKong
// @Update: 2020/9/30 7:20 上午
package main

import "fmt"

func main() {
	fmt.Println(longestSubstringWithoutRepeatingCharacters("asdasddd"))
	fmt.Println(longestSubstringWithoutRepeatingCharacters(""))
	fmt.Println(longestSubstringWithoutRepeatingCharacters("a"))
	fmt.Println(longestSubstringWithoutRepeatingCharacters("asdfgh"))
}

// 1.寻找最长不含有重复字符的子串
// asdasddd -> asd
func longestSubstringWithoutRepeatingCharacters(s string) int {
	lastOccurred := make(map[byte]int)
	start := 0
	maxLength := 0

	for i, ch := range []byte(s) {
		// 当前字符有没有存在
		lasIndex, ok := lastOccurred[ch]
		// 如果存在并且大于此次开始的start，将start重新赋值
		if ok && lasIndex >= start {
			start = lasIndex + 1
		}

		// 如果遍历到当前字符，并且不重复字符子串长度大于 maxLength，那么 maxLength 重新赋值
		if i-lasIndex+1 > maxLength {
			maxLength = i - start + 1
		}
		// 最后将当前的字符及其下表放在map中
		lastOccurred[ch] = i
	}
	return maxLength
}
