package com.example.tby.myapplication.activity;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;


import com.example.R;
import com.example.tby.myapplication.noscrollview.LoadingDialog;
import com.example.tby.myapplication.userInfo;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.HashMap;
import java.util.Map;


/**
 * 注册和登录Activity
 */
public class LoginActivity extends Activity {

    private ImageButton ibBack;
    private EditText etAccount;
    private EditText etPassword;
    private Button btnLogin;
    private Button btnRegister;
    private LoadingDialog ld;


    private String account;
    private  String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }

    /**
     * 初始化视图
     */
    private void initView() {
        ibBack = (ImageButton) findViewById(R.id.login_ib_back);
        ibBack.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });
        final MyWatcher watcher = new MyWatcher();
        etAccount = (EditText) findViewById(R.id.login_et_account);
        etAccount.addTextChangedListener(watcher);
        etPassword = (EditText) findViewById(R.id.login_et_password);
        etPassword.addTextChangedListener(watcher);
        btnLogin = (Button) findViewById(R.id.login_btn_login);

        btnLogin.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                ld= new LoadingDialog(LoginActivity.this);
                ld.show();
                account=etAccount.getText().toString();
                password=etPassword.getText().toString();
                Map<String,String> map=new HashMap<String, String>();
                map.put("userid",account);
                map.put("password",password);
                map.put("grant_type","password");
                map.put("client_id",userInfo.getClient_id());
                get(getResources().getString(R.string.ip)+"access_token",map);
               // getAnswer("http://localhost:8080/giftshop/access_token",account,password);

            }
        });
        btnRegister = (Button) findViewById(R.id.login_btn_register);
        btnRegister.setEnabled(true);
        btnRegister.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent in = new Intent(LoginActivity.this,RegistActivity.class);
                startActivity(in);
                LoginActivity.this.finish();
            }
        });


        /**
         * 普通get请求
         * @param url
         * @param maps
         */

    }
    public void get(String url, Map<String,String> maps){
        RequestParams params = new RequestParams(url);
        System.out.println(url);
        if (null != maps && !maps.isEmpty()){
            for (Map.Entry<String,String> entry : maps.entrySet()){
                params.addQueryStringParameter(entry.getKey(),entry.getValue());
            }
        }
        x.http().get(params, new Callback.CommonCallback<String>() {
            private boolean hasError = false;
            private String result = null;
            @Override
            public void onSuccess(String result) {
                ld.dismiss();

                if (result != null) {
                    System.out.println(result);
                    if(result.compareTo("ERROR")==0){
                        Toast.makeText(LoginActivity.this,"账号或密码错误",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    try {
                        JSONObject jo=new JSONObject(result);
                        userInfo.setAccess_token(jo.getString("access_token"));
                        userInfo.setRefresh_token(jo.getString("refresh_token"));
                        userInfo.setExamtime(jo.getString("modifiedtime"));
                        userInfo.setUsername(jo.getString("username"));
                        userInfo.setToken_type(jo.getString("token_type"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    Toast.makeText(LoginActivity.this,"登陆成功",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                ld.dismiss();
                System.out.println(hasError+result+ex.getMessage());
                hasError = true;
                Toast.makeText(x.app(),"", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {
                if (!hasError && result != null) {
                    System.out.println(result);
                    //onSuccessResponse(result,callback);
                }
            }
        });
    }
    /**
     * 监听文本框
     */
    private class MyWatcher implements TextWatcher {

        @Override
        public void onTextChanged(CharSequence arg0, int arg1, int arg2,
                                  int arg3) {
            if ("".equals(etAccount.getText().toString())
                    || "".equals(etPassword.getText().toString())) {
                btnLogin.setEnabled(false);
                btnRegister.setEnabled(true);
            } else {
                btnLogin.setEnabled(true);
                btnRegister.setEnabled(true);
            }
        }

        @Override
        public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                      int arg3) {
        }

        @Override
        public void afterTextChanged(Editable arg0) {
        }

    }


}
