// @Description: 用户实体
// @Author: JKong
// @Update: 2020/10/27 8:14 上午
package model

import "time"

type Person struct {
	Name       string    `form:"name"`
	Address    string    `form:"address"`
	Birthday   time.Time `form:"birthday" time_format:"2006-01-02" time_utc:"1"`
	CreateTime time.Time `form:"createTime" time_format:"unixNano"`
}
