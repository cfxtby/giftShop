package com.example.tby.myapplication;

import android.telephony.TelephonyManager;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 * Created by tby on 2017/4/5.
 */

public final class userInfo {
    private static String username;
    private static String access_token;
    private static String refresh_token;
    private static String examtime;
    private static String token_type;
    public static String client_id;

    public static String getAESkey() {
        return AESkey;
    }

    public static void setAESkey(String AESkey) {
        userInfo.AESkey = AESkey;
    }

    public static String AESkey;

    public static String getClient_id() {
        return client_id;
    }

    public static void setClient_id(String client_id) {
        userInfo.client_id = client_id;
    }

    public static String getToken_type() {
        return token_type;

    }

    public static void setToken_type(String token_type) {
        userInfo.token_type = token_type;
    }

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        userInfo.username = username;
    }

    public static String getAccess_token() {
        return access_token;
    }

    public static void setAccess_token(String access_token) {
        userInfo.access_token = access_token;
    }

    public static String getRefresh_token() {
        return refresh_token;
    }

    public static void setRefresh_token(String refresh_token) {
        userInfo.refresh_token = refresh_token;
    }

    public static String getExamtime() {
        return examtime;
    }

    public static void setExamtime(String examtime) {
        userInfo.examtime = examtime;
    }

    private static boolean isLogin = false;

    public static boolean isLogin() {
        return isLogin;
    }

    public static void setIsLogin(boolean isLogin) {
        userInfo.isLogin = isLogin;
    }
}
