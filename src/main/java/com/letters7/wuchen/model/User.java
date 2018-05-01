package com.letters7.wuchen.model;

import java.io.Serializable;

/**
 * @author itw_wangjb03
 * @date 2018/5/1
 * sprint by itw_wangjb03：用于
 */
public class User implements Serializable {
    /**
     * 用户ID
     */
    private String userId;
    /**
     * 用户姓名
     */
    private String userName;
    /**
     * 名称
     */
    private String name;
    /**
     * 密码
     */
    private String password;

    /**
     * 最后登录时间
     */
    private String lastLoginTime;

    public User(){

    }

    public static User build(){
        return new User();
    }

    public String getLastLoginTime() {
        return lastLoginTime;
    }

    public User setLastLoginTime(String lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
        return this;
    }

    public String getUserId() {
        return userId;
    }

    public User setUserId(String userId) {
        this.userId = userId;
        return this;
    }

    public String getUserName() {
        return userName;
    }

    public User setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public String getName() {
        return name;
    }

    public User setName(String name) {
        this.name = name;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }
}
