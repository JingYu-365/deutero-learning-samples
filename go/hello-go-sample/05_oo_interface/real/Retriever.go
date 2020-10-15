// @Description: TODO
// @Author: JKong
// @Update: 2020/10/2 8:28 下午
package real

import (
	"net/http"
	"net/http/httputil"
)

// go 中如果struct 实现了某个接口，那么不需要声明实现了某个接口。
// 只需要实现了这个接口的方法就是实现了这个接口
type Retriever struct {
	UserAgent string
	Content   map[string]string
}

func (r *Retriever) Post(url string, form map[string]string) string {
	r.Content = form
	return "Ok"
}

func (r Retriever) Get(url string) string {
	resp, err := http.Get(url)
	if err != nil {
		panic(err)
	}

	response, err := httputil.DumpResponse(resp, true)
	_ = resp.Body.Close()

	if err != nil {
		panic(err)
	}
	return string(response)
}
