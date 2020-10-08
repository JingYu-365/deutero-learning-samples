# Go 语言命名规范
## 文件名规范

- 文件名_平台

    平台可选为：`windows, unix, posix, plan9, darwin, bsd, linux, freebsd, nacl, netbsd, openbsd, solaris, dragonfly, bsd, notbsd, android, stubs`

    例： `file_windows.go`, `file_unix.go`

- 单元测试
    `文件名_test.go`(包含 _test.go) 或者 `文件名_平台_test.go`。
 
    例： `_test.go`, `path_test.go`,  `path_windows_test.go`

- CPU类型区分, 汇编用的多

    `文件名_(平台:可选)_CPU类型`

    例：`vdso_linux_amd64.go`

    可选：`amd64, none, 386, arm, arm64, mips64, s390,mips64x,ppc64x, nonppc64x, s390x, x86,amd64p32`