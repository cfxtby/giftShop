package com.example.tby.myapplication.utils;

import android.util.Log;

import com.example.tby.myapplication.userInfo;

import org.xutils.DbManager;
import org.xutils.ex.DbException;

import java.util.List;

/**
 * Created by tby on 2017/4/12.
 */

public class tokenDB {
    private DbManager db;
    //接收构造方法初始化的DbManager对象
    public tokenDB(){
        db = DatabaseOpenHelper.getInstance();
    }
    /****************************************************************************************/
    //写两个测试方法
    public void saveToken(){
        try {
            user person=new user();
            person.setClient_id(userInfo.getClient_id());
            person.setAccess_token(userInfo.getAccess_token());
            person.setUsername(userInfo.getUsername());
            person.setRefresh_token(userInfo.getRefresh_token());
            person.setExamtime(userInfo.getExamtime());
            person.setToken_type(userInfo.getToken_type());
            db.save(person);
            Log.d("xyz","save succeed!");
        } catch (DbException e) {
            Log.d("xyz",e.toString());
        }
    }
    //将Person实例存进数据库
    public List<user> loadPerson(){
        List<user> list = null;
        try {
            list = db.selector(user.class).findAll();
        } catch (DbException e) {
            e.printStackTrace();
        }
        if(list!=null){
            userInfo.setClient_id(list.get(0).getClient_id());
            userInfo.setAccess_token(list.get(0).getAccess_token());
            userInfo.setUsername(list.get(0).getUsername());
            userInfo.setRefresh_token(list.get(0).getRefresh_token());
            userInfo.setExamtime(list.get(0).getExamtime());
            userInfo.setToken_type(list.get(0).getToken_type());
        }
        return list;
    }
    //读取所有Person信息

}
