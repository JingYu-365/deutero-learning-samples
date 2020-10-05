// @Description: 爬取珍爱网人员信息
// @Author: JKong
// @Update: 2020/10/5 12:35 下午
package main

import (
	"bufio"
	"fmt"
	"golang.org/x/net/html/charset"
	"golang.org/x/text/encoding"
	"golang.org/x/text/transform"
	"io"
	"io/ioutil"
	"net/http"
)

func main() {

	resp, err := http.Get("http://www.zhenai.com/zhenghun")
	if err != nil {
		panic(err)
	}
	defer resp.Body.Close()

	if resp.StatusCode != http.StatusOK {
		fmt.Printf("Error: status code: %d", resp.StatusCode)
		return
	}
	e := determineEncoding(resp.Body)
	encodedReader := transform.NewReader(resp.Body, e.NewDecoder())
	all, err := ioutil.ReadAll(encodedReader)
	if err != nil {
		panic(err)
	}
	fmt.Printf("%s \n", all)

}

func determineEncoding(r io.Reader) encoding.Encoding {
	peek, err := bufio.NewReader(r).Peek(1024)
	if err != nil {
		panic(nil)
	}
	e, _, _ := charset.DetermineEncoding(peek, "")
	return e
}
