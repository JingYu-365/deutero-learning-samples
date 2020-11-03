// TODO
// @author: Laba Zhang
package main

import (
	"io"
	"log"
	"net/http"
	"os"
	"os/signal"
)

func main() {
	signalChan := make(chan os.Signal)
	go (func() {
		helloHandler := func(w http.ResponseWriter, req *http.Request) { io.WriteString(w, "Hello, world-1!\n") }
		http.HandleFunc("/a/hello", helloHandler)
		log.Fatal(http.ListenAndServe(":8080", nil))
	})()

	go (func() {
		helloHandler2 := func(w http.ResponseWriter, req *http.Request) { io.WriteString(w, "Hello, world-2!\n") }
		http.HandleFunc("/b/hello", helloHandler2)
		log.Fatal(http.ListenAndServe(":8081", nil))
	})()

	signal.Notify(signalChan, os.Interrupt)
	log.Fatalln(<-signalChan)
}
