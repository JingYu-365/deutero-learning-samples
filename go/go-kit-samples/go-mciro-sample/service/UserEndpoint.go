// @Description: 用户请求及响应信息
// @Author: JKong
// @Update: 2020/10/31 10:17 上午
package service

import (
	"context"
	"github.com/go-kit/kit/endpoint"
)

type UserRequest struct {
	Uid int `json:"uid"`
}

type UserResponse struct {
	Result string `json:"result"`
}

func GenerateUserEndpoint(userService IUserService) endpoint.Endpoint {
	return func(ctx context.Context, request interface{}) (response interface{}, err error) {
		// 通过类型断言，确定数据类型，并获取传递参数（忽略参数验证 ）
		uid := request.(UserRequest).Uid
		result := userService.GetName(uid)
		return UserResponse{
			Result: result,
		}, nil
	}
}
