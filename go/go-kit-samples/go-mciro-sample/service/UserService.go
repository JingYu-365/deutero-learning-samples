// @Description: 用户服务接口
// @Author: JKong
// @Update: 2020/10/31 10:17 上午
package service

type IUserService interface {
	GetName(uid int) string
	DelUser(uid int) bool
}

type UserService struct{}

func (s UserService) GetName(uid int) string {
	if uid == 1 {
		return "admin"
	}
	return "guest"
}

// 删除用户，仅能删除一般用户，不能删除admin
func (s UserService) DelUser(uid int) bool {
	if uid == 1 {
		return false
	}
	return true
}
