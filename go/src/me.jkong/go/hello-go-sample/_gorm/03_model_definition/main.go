package main

import (
	"database/sql"
	"github.com/jinzhu/gorm"
	"time"
)

func main() {

}

/* 支持的结构体标记（Struct tags）
   Column	        指定列名
   Type	        指定列数据类型
   Size	        指定列大小, 默认值255
   PRIMARY_KEY	    将列指定为主键
   UNIQUE	        将列指定为唯一
   DEFAULT	        指定列默认值
   PRECISION	    指定列精度
   NOT NULL	    将列指定为非 NULL
   AUTO_INCREMENT	指定列是否为自增类型
   INDEX	        创建具有或不带名称的索引, 如果多个索引同名则创建复合索引
   UNIQUE_INDEX	和 INDEX 类似，只不过创建的是唯一索引
   EMBEDDED	    将结构设置为嵌入
   EMBEDDED_PREFIX	设置嵌入结构的前缀
   -	            忽略此字段
*/

/* 关联 struct 的标记（tags）
   MANY2MANY	                        指定连接表
   FOREIGNKEY	                        设置外键
   ASSOCIATION_FOREIGNKEY	            设置关联外键
   POLYMORPHIC	                        指定多态类型
   POLYMORPHIC_VALUE	                指定多态值
   JOINTABLE_FOREIGNKEY	            指定连接表的外键
   ASSOCIATION_JOINTABLE_FOREIGNKEY	指定连接表的关联外键
   SAVE_ASSOCIATIONS	                是否自动完成 save 的相关操作
   ASSOCIATION_AUTOUPDATE	            是否自动完成 update 的相关操作
   ASSOCIATION_AUTOCREATE	            是否自动完成 create 的相关操作
   ASSOCIATION_SAVE_REFERENCE	        是否自动完成引用的 save 的相关操作
   PRELOAD	                            是否自动完成预加载的相关操作
*/

type User struct {
	gorm.Model
	Birthday   time.Time
	Age        int
	Name       string     `gorm:"size:255"`       // string默认长度为255, 使用这种tag重设。
	Num        int        `gorm:"AUTO_INCREMENT"` // 自增
	CreditCard CreditCard // One-To-One (拥有一个 - CreditCard表的UserID作外键)
	Emails     []Email    // One-To-Many (拥有多个 - Email表的UserID作外键)

	BillingAddress   Address // One-To-One (属于 - 本表的BillingAddressID作外键)
	BillingAddressID sql.NullInt64

	ShippingAddress   Address // One-To-One (属于 - 本表的ShippingAddressID作外键)
	ShippingAddressID int

	IgnoreMe  int        `gorm:"-"`                         // 忽略这个字段
	Languages []Language `gorm:"many2many:user_languages;"` // Many-To-Many , 'user_languages'是连接表
}

type Email struct {
	ID         int
	UserID     int    `gorm:"index"`                          // 外键 (属于), tag `index`是为该列创建索引
	Email      string `gorm:"type:varchar(100);unique_index"` // `type`设置sql类型, `unique_index` 为该列设置唯一索引
	Subscribed bool
}

type Address struct {
	ID       int
	Address1 string         `gorm:"not null;unique"` // 设置字段为非空并唯一
	Address2 string         `gorm:"type:varchar(100);unique"`
	Post     sql.NullString `gorm:"not null"`
}

type Language struct {
	ID   int
	Name string `gorm:"index:idx_name_code"` // 创建索引并命名，如果找到其他相同名称的索引则创建组合索引
	Code string `gorm:"index:idx_name_code"` // `unique_index` also works
}

type CreditCard struct {
	gorm.Model
	UserID uint
	Number string
}
