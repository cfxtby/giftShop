package com.example.tby.myapplication.activity;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.R;
import com.example.tby.myapplication.noscrollview.LoadingDialog;
import com.example.tby.myapplication.userInfo;
import com.example.tby.myapplication.utils.CountDownTimerUtils;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.HashMap;
import java.util.Map;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

import static cn.smssdk.SMSSDK.getVerificationCode;

/**
 * Created by tby on 2017/4/13.
 */

public class RegistActivity extends Activity {

    private ImageButton ibBack;
    private EditText etAccount,regist;
    private EditText etPassword,etPassword1;
    private Button btnRegister;
    private LoadingDialog ld;

    private TextView tv;
    private CountDownTimerUtils mCountDownTimerUtils;
    private String account;
    private  String password;

    private String APPKEY="1c98c87073338",APPSECRET="6ac28e49537cfd44f69e1a0e1a847325";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);
        SMSSDK.initSDK(this,APPKEY,APPSECRET);
        EventHandler eh=new EventHandler(){

            @Override
            public void afterEvent(int event, int result, Object data) {

                if (result == SMSSDK.RESULT_COMPLETE) {
                    //回调完成
                    if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                        Toast.makeText(RegistActivity.this,"提交验证码成功",Toast.LENGTH_SHORT).show();
                        //提交验证码成功
                    }else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE){
                        //获取验证码成功
                    }else if (event ==SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES){
                        //返回支持发送验证码的国家列表
                    }
                }else{
                    ((Throwable)data).printStackTrace();
                }
            }
        };
        SMSSDK.registerEventHandler(eh); //注册短信回调
        initView();
    }

    /**
     * 初始化视图
     */
    private void initView() {
        ibBack = (ImageButton) findViewById(R.id.regist_ib_back);
        ibBack.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });
        MyWatcher1 watcher = new MyWatcher1();
        etAccount = (EditText) findViewById(R.id.regist_et_account);
        etAccount.addTextChangedListener(watcher);
        regist= (EditText) findViewById(R.id.regist_et_regist);
        regist.addTextChangedListener(watcher);
        etPassword = (EditText) findViewById(R.id.regist_et_password);
        etPassword.addTextChangedListener(watcher);
        etPassword1= (EditText) findViewById(R.id.regist_re_password);
        etPassword1.addTextChangedListener(watcher);
        btnRegister = (Button) findViewById(R.id.regist_btn_register);
        tv= (TextView) findViewById(R.id.regist_et_valify);
        tv.setBackgroundResource(R.drawable.bg_identify_code_normal);
        tv.setClickable(true);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tv.getText().equals("获得验证码")){
                    getVerificationCode("+86", etAccount.getText().toString());
                    mCountDownTimerUtils = new CountDownTimerUtils(tv, 60000, 1000);
                    mCountDownTimerUtils.start();
                }
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                account=etAccount.getText().toString();
                password=etPassword.getText().toString();
                String r=etPassword1.getText().toString();
                String valicode=regist.getText().toString();
                if(!r.equals(password)||valicode.equals("")){
                    Toast.makeText(RegistActivity.this,"密码不同或者没有验证码",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(password.length()<7){
                    Toast.makeText(RegistActivity.this,"密码长度太短",Toast.LENGTH_SHORT).show();
                    return;
                }
                if((!account.matches("[\\d]+"))||account.length()!=11){
                    Toast.makeText(RegistActivity.this,"手机号有问题",Toast.LENGTH_SHORT).show();
                    return;
                }

                ld= new LoadingDialog(RegistActivity.this);
                ld.show();
                Map<String,String> map=new HashMap<String, String>();
                map.put("userid",account);
                map.put("password",password);
                map.put("grant_type","password");
                map.put("client_id", userInfo.getClient_id());
                map.put("valicode",valicode);
                get(getResources().getString(R.string.ip)+"register",map);
                // getAnswer("http://localhost:8080/giftshop/access_token",account,password);

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
                        Toast.makeText(RegistActivity.this,"账号或密码错误",Toast.LENGTH_SHORT).show();
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
                    userInfo.isLogin=true;
                    Toast.makeText(RegistActivity.this,"登陆成功",Toast.LENGTH_SHORT).show();
                    finish();
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
    private class MyWatcher1 implements TextWatcher {

        @Override
        public void onTextChanged(CharSequence arg0, int arg1, int arg2,
                                  int arg3) {
            if ("".equals(etAccount.getText().toString())
                    || "".equals(etPassword.getText().toString())) {
                //btnLogin.setEnabled(false);
                btnRegister.setEnabled(true);
            } else {
                //btnLogin.setEnabled(true);
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
