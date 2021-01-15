package main

import (
	"errors"
	"fmt"
)

// errorString is a trivial implementation of error.
type errorString struct {
	s string
}

func New(text string) error {
	return errorString{text}
}
func (e errorString) Error() string {
	return e.s
}

var ErrMyStructType = New("EOF")
var ErrStructType = errors.New("EOF")

func main() {
	if ErrMyStructType == New("EOF") {
		fmt.Println("my struct type error")
	}

	if ErrStructType == errors.New("EOF") {
		fmt.Println("struct type error")
	}
}
