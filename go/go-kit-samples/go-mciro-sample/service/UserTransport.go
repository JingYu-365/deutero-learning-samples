// @Description: 用户请求控制层
// @Author: JKong
// @Update: 2020/10/31 10:44 上午
package service

import (
	"context"
	"encoding/json"
	"errors"
	"fmt"
	"github.com/gorilla/mux"
	"net/http"
	"strconv"
)

func DecodeUserRequest(ctx context.Context, req *http.Request) (interface{}, error) {
	vars := mux.Vars(req)
	fmt.Println(vars)
	if uid, ok := vars["uid"]; ok {
		uid, _ := strconv.Atoi(uid)
		return UserRequest{Uid: uid, Method: req.Method}, nil
	}
	return nil, errors.New("invalid parameter uid")
}

// EncodeUserResponse todo 为什么 http.ResponseWriter 不是指针？？
func EncodeUserResponse(ctx context.Context, resp http.ResponseWriter, respData interface{}) error {
	resp.Header().Add("Content-Type", "application/json")
	return json.NewEncoder(resp).Encode(respData)
}
