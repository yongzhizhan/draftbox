package main

import (
	// WARNING!
	// Change this to a fully-qualified import path
	// once you place this file into your project.
	// For example,
	//
	//    sw "github.com/myname/myrepo/go"
	//
	//sw "./go"
	//"log"
	//"net/http"
	"fmt"
)

//func main() {
//	log.Printf("Server started")
//
//	router := sw.NewRouter()
//
//	log.Fatal(http.ListenAndServe(":8080", router))
//}

type rect struct {
	width int
	height int
}

func (r *rect) area() int {
	return r.width * r.height
}

func main() {
	r := rect{width: 10, height: 5}
	fmt.Println("area: ", r.area())
}
