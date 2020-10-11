package main

import (
	"fmt"
	"reflect"
)

// reflect.ValueOf()返回的是reflect.Value类型，其中包含了原始值的值信息。
// reflect.Value与原始值之间可以互相转换。reflect.Value类型提供的获取原始值的方法如下：
// 方法							说明
// Interface() interface {}		将值以 interface{} 类型返回，可以通过类型断言转换为指定类型
// Int() int64					将值以 int 类型返回，所有有符号整型均可以此方式返回
// Uint() uint64				将值以 uint 类型返回，所有无符号整型均可以此方式返回
// Float() float64				将值以双精度（float64）类型返回，所有浮点数（float32、float64）均可以此方式返回
// Bool() bool					将值以 bool 类型返回
// Bytes() []bytes				将值以字节数组 []bytes 类型返回
// String() string				将值以字符串类型返回

func main() {

	reflectForGettingValue()

	reflectForSettingValue()
}

// 通过反射设置变量的值
// 反射中使用专有的Elem()方法来获取指针对应的值。
func reflectForSettingValue() {
	var a int64 = 100
	reflectSetValue(&a)
	fmt.Printf("before reflect setting a = 100, after reflect set value a = %d \n", a)
}

func reflectSetValue(x interface{}) {
	v := reflect.ValueOf(x)
	// 反射中使用 Elem()方法获取指针对应的值
	if v.Elem().Kind() == reflect.Int64 {
		v.Elem().SetInt(200)
	}
}

// 通过反射获取值
func reflectForGettingValue() {
	var a float32 = 3.14
	var b int64 = 100
	var c float64 = 123.123
	reflectValue(a) // type is float32, value is 3.140000
	reflectValue(b) // type is int64, value is 100
	reflectValue(c)
	// 将int类型的原始值转换为reflect.Value类型
	d := reflect.ValueOf(10)
	fmt.Printf("type c :%T\n", d) // type c :reflect.Value
}

func reflectValue(x interface{}) {
	v := reflect.ValueOf(x) // valueOf 返回的结果是：reflect.Value 类型
	fmt.Printf("reflect valueOf type: %T \n", v)

	switch v.Kind() {
	case reflect.Int64:
		// v.Int()	从反射中获取整型的原始值，然后通过int64()强制类型转换
		fmt.Printf("type is int64,   \tvalue is %d\n", int64(v.Int()))
	case reflect.Float32:
		// v.Float()从反射中获取浮点型的原始值，然后通过float32()强制类型转换
		fmt.Printf("type is float32, \tvalue is %f\n", float32(v.Float()))
	case reflect.Float64:
		// v.Float()从反射中获取浮点型的原始值，然后通过float64()强制类型转换
		fmt.Printf("type is float64, \tvalue is %f\n", float64(v.Float()))
	}
}
