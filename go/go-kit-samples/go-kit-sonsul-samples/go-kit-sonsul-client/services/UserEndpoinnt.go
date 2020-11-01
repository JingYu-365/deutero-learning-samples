// @Description: 用户 endpoint
// @Author: la ba zhang
// @Update: 2020/11/1 11:14 上午
package services

import (
	"context"
	"encoding/json"
	"errors"
	"net/http"
	"strconv"
)

// EncodeGetUserInfoReq 对请求参数进行编码
func EncodeGetUserInfoReq(_ context.Context, req *http.Request, user interface{}) error {
	uid := user.(UserRequest).Uid
	req.URL.Path += "/users/" + strconv.Itoa(uid)
	return nil
}

// DecodeGetUserInfoResp 对返回数据进行解码
func DecodeGetUserInfoResp(_ context.Context, resp *http.Response) (response interface{}, err error) {
	if resp.StatusCode != 200 {
		return nil, errors.New("no data")
	}

	var userResp UserResponse
	err = json.NewDecoder(resp.Body).Decode(&userResp)
	if err != nil {
		return nil, err
	}
	return userResp, nil
}
