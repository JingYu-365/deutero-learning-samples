package config

// AppConfig 应用配置
type AppConfig struct {
	KafkaConfig   `ini:"kafka"`
	TailLogConfig `ini:"taillog"`
}

// KafkaConfig kafka 配置
type KafkaConfig struct {
	Address string `ini:"address"`
	Topic   string `ini:"topic"`
}

// TailLogConfig 日志配置
type TailLogConfig struct {
	Path string `ini:"path"`
}
