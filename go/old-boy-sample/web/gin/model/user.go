// @Description: 用户实体
// @Author: JKong
// @Update: 2020/10/27 6:55 上午
package model

type User struct {
	// 设置了 binding=required 后，如果不传 username，那么会抛出异常：
	// Key: 'User.Username' Error:Field validation for 'Username' failed on the 'required' tag
	Username string `form:"username" json:"username" xml:"username" binding:"required"`
	Password string `form:"password" json:"password" xml:"password" binding:"required"`
}
