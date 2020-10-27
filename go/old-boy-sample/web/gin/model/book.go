// @Description: 订书实体
// @Author: JKong
// @Update: 2020/10/27 8:00 上午
package model

import "time"

type Book struct {
	CheckIn  time.Time `form:"checkIn" binding:"required,bookabledate" time_format:"2006-01-02"`
	CheckOut time.Time `form:"checkOut" binding:"required,gtfield=CheckIn" time_format:"2006-01-02"`
}
