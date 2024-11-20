package com.chat.ChatApp.Models;

public class UpdateData {
        String email,pwd,public_key;

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    int active;

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

    public String getPublic_key() {
        return public_key;
    }

    public void setPublic_key(String public_key) {
        this.public_key = public_key;
    }

    public String getPwd() {
            return pwd;
        }

        public void setPwd(String pwd) {
            this.pwd = pwd;
        }

    public UpdateData(String email, String pwd, String public_key) {
        this.email = email;
        this.pwd = pwd;
        this.public_key = public_key;
    }
}
