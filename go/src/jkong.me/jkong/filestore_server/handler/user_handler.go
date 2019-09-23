package handler

import (
	"io"
	"io/ioutil"
	"jkong.me/jkong/filestore_server/db"
	"jkong.me/jkong/filestore_server/util"
	"net/http"
)

var salt string = "#JKong"

func UserSignUpHandler(w http.ResponseWriter, r *http.Request) {
	if r.Method == http.MethodGet {
		data, err := ioutil.ReadFile("src/jkong.me/jkong/filestore_server/static/view/signup.html")
		if err != nil {
			w.WriteHeader(http.StatusInternalServerError)
			return
		}
		_, _ = io.WriteString(w, string(data))
		return
	} else if r.Method == http.MethodPost {
		r.ParseForm()

		userName := r.FormValue("username")
		password := r.FormValue("password")

		user, err := db.GetUserInfo(userName)
		if err != nil {
			w.Write([]byte("REGISTER FAILED!"))
			return
		}
		if user != nil {
			w.Write([]byte("REGISTER FAILED, USER HAS EXISTED!"))
			return
		}

		enc_password := util.Sha1([]byte(password + salt))
		suc := db.UserSignUp(userName, enc_password)
		if suc {
			w.Write([]byte("SUCCESS!"))
			return
		} else {
			w.Write([]byte("REGISTER FAILED!"))
		}

	} else {
		w.WriteHeader(http.StatusMethodNotAllowed)
		return
	}
}

// 用户登陆
func UserSignInHandler(w http.ResponseWriter, r *http.Request) {
	if r.Method != http.MethodPost {
		w.WriteHeader(http.StatusMethodNotAllowed)
		return
	}

	r.ParseForm()

	username := r.FormValue("username")
	password := r.FormValue("password")

	enc_password := util.Sha1([]byte(password + salt))
	user, err := db.GetUserInfo(username)

	if err != nil {
		w.WriteHeader(http.StatusInternalServerError)
		return
	}

	if enc_password != user.UserPwd {
		w.Write([]byte("SIGN IN FAILED!"))
	}
	w.Write([]byte("SIGN IN SUCCESS!"))
}
