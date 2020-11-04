// Http util
// @author: Laba Zhang
package util

import (
	"go-proxy-sample/config"
	"io/ioutil"
	"net"
	"net/http"
	"strings"
)

func DoRequest(req *http.Request, resp http.ResponseWriter) error {
	response, err := http.DefaultClient.Do(req)
	defer response.Body.Close()
	if err != nil {
		return err
	}

	// 将实际返回的 Header 及 StatusCode 返回
	header := resp.Header()
	CopyHeader(response.Header, &header)
	resp.WriteHeader(response.StatusCode)
	result, err := ioutil.ReadAll(response.Body)
	if err != nil {
		return err
	}
	_, err = resp.Write(result)
	return err
}

// CopyHeader 拷贝HEADER信息
func CopyHeader(src http.Header, des *http.Header) {
	for key, value := range src {
		for _, v := range value {
			des.Add(key, v)
		}
	}
}

// SettingXForwardedFor 设置真实请求地址
func SettingXForwardedFor(src http.Request, des *http.Request) {
	if clientIP, _, err := net.SplitHostPort(src.RemoteAddr); err == nil {
		if prior, ok := des.Header[config.X_FORWARADED_FOR]; ok {
			clientIP = strings.Join(prior, ", ") + ", " + clientIP
		}
		des.Header.Set(config.X_FORWARADED_FOR, clientIP)
	}
}

func GetRealRemoteIp(req *http.Request) string {
	ips := req.Header.Get(config.X_FORWARADED_FOR)
	if ips == "" {
		ips = req.RemoteAddr
	}
	return ips
}
