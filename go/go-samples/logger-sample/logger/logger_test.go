package logger

import (
	"testing"
)

// 单元测试
func TestConstLevel(t *testing.T) {
	t.Logf("%v %T \n", DebugLevel, DebugLevel)
	t.Logf("%v %T \n", InfoLevel, InfoLevel)
	t.Logf("%v %T \n", WarningLevel, WarningLevel)
	t.Logf("%v %T \n", ErrorLevel, ErrorLevel)
	t.Logf("%v %T \n", FatalLevel, FatalLevel)
}
