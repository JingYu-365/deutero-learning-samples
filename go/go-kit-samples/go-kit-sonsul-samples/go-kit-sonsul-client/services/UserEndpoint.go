// @Description: 用户请求及响应信息
// @Author: la ba zhang
// @Update: 2020/10/31 10:17 上午
package services

type UserRequest struct {
	Uid    int `json:"uid"`
	Method string
}

type UserResponse struct {
	Result string `json:"result"`
}
