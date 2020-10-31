// @Description: 用户请求及响应信息
// @Author: JKong
// @Update: 2020/10/31 10:17 上午
package service

import (
	"context"
	"fmt"
	"github.com/go-kit/kit/endpoint"
)

type UserRequest struct {
	Uid    int `json:"uid"`
	Method string
}

type UserResponse struct {
	Result string `json:"result"`
}

func GenerateUserEndpoint(userService IUserService) endpoint.Endpoint {
	return func(ctx context.Context, request interface{}) (response interface{}, err error) {
		// 通过类型断言，确定数据类型，并获取传递参数（忽略参数验证 ）
		userRequest := request.(UserRequest)
		uid := userRequest.Uid
		var result string
		// 根据不同请求方式执行不同逻辑
		if userRequest.Method == "GET" {
			result = userService.GetName(uid)
		} else if userRequest.Method == "DELETE" {
			if userService.DelUser(uid) {
				result = fmt.Sprintf("delete uesr %d success.", uid)
			} else {
				result = fmt.Sprint("forbidden.")
			}
		}
		return UserResponse{
			Result: result,
		}, nil
	}
}
