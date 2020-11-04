// read config from ini config
// @author: Laba Zhang
package config

import (
	"fmt"
	"gopkg.in/ini.v1"
	"log"
	"os"
)

var ProxyConfig map[string]string

const (
	PROXY_INI = "config/proxy.ini"
	PROXY     = "proxy"
	PATH      = "path"
	PASS      = "pass"
)

func init() {
	ProxyConfig = make(map[string]string)
	cfg, err := ini.Load(PROXY_INI)
	if err != nil {
		fmt.Printf("Fail to read file: %v", err)
		os.Exit(1)
	}

	section, err := cfg.GetSection(PROXY)
	for _, sec := range section.ChildSections() {
		path, _ := sec.GetKey(PATH)
		pass, _ := sec.GetKey(PASS)
		if path != nil && pass != nil {
			ProxyConfig[path.Value()] = pass.Value()
		}
	}
	log.Println("init proxy config complete.")
}
