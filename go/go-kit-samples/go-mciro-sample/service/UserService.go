// @Description: 用户服务接口
// @Author: JKong
// @Update: 2020/10/31 10:17 上午
package service

type IUserService interface {
	GetName(uid int) string
}

type UserService struct{}

func (s UserService) GetName(uid int) string {
	if uid == 1 {
		return "admin"
	}
	return "guest"
}
