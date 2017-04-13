package com.example.tby.myapplication.utils;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 * Created by tby on 2017/4/12.
 */
@Table(name = "user")
public class user {
    @Column(name = "userid",isId = true)
    private String username;

    @Column(name = "access_token")

    private String access_token;
    @Column(name = "refresh_token")
    private String refresh_token;
    @Column(name = "examtime")
    private String examtime;
    @Column(name = "token_type")
    private String token_type;
    @Column(name = "client_id")
    public String client_id;
    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }

    public String getToken_type() {
        return token_type;
    }

    public void setToken_type(String token_type) {
        this.token_type = token_type;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public String getExamtime() {
        return examtime;
    }

    public void setExamtime(String examtime) {
        this.examtime = examtime;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
