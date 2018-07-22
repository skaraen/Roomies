package com.example.karaens.roomies;

import java.util.List;
import java.util.Map;

public class User {
    String name,uid,email,phno,fb,twit,nickName;

    public String getFb() {
        return fb;
    }

    public void setFb(String fb) {
        this.fb = fb;
    }

    public String getTwit() {
        return twit;
    }

    public void setTwit(String twit) {
        this.twit = twit;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    Map<String,Boolean> groups;

    public Map<String, Boolean> getGroups() {
        return groups;
    }

    public void setGroups(Map<String, Boolean> groups) {
        this.groups = groups;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public User(String name, String uid, String email) {
        this.name = name;

        this.email = email;
        this.uid=uid;
    }

    public User(String name, String uid, String email, String phno, String fb, String twit, String nickName,Map<String,Boolean> groups) {
        this.name = name;
        this.uid = uid;
        this.email = email;
        this.phno = phno;
        this.fb = fb;
        this.twit = twit;
        this.nickName = nickName;
        this.groups=groups;
    }

    public User() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhno() {
        return phno;
    }

    public void setPhno(String phno) {
        this.phno = phno;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
