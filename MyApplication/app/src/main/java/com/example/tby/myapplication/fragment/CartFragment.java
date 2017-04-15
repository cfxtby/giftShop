package com.example.tby.myapplication.fragment;

import java.util.ArrayList;
import java.util.Map;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.example.R;
import com.example.tby.myapplication.activity.LoginActivity;
import com.example.tby.myapplication.adapter.CartListAdapter;
import com.example.tby.myapplication.adapter.CartListAdapter.OnPriceChangedListener;
import com.example.tby.myapplication.model.Global;
import com.example.tby.myapplication.model.ProductData;
import com.example.tby.myapplication.userInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;


/**
 * 购物车Fragment
 */
public class CartFragment extends Fragment {

    /**
     * 顶部
     */
    private Button btnEdit;
    /**
     * 提示登录
     */
    private RelativeLayout suggestLayout;
    private Button btnLogin;
    /**
     * 购物车为空
     */
    private TextView tvEmpty;
    /**
     * 购物车列表
     */
    private LinearLayout listLayout;
    private ListView listView;
    private CartListAdapter adapter;
    private ArrayList<ProductData> products;
    /**
     * 底部付款
     */
    private RelativeLayout checkLayout;
    private CheckBox cbCheckAll;
    private TextView tvTotal;
    private Button btnBuy;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        initData();
        initView(view);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (userInfo.isLogin()) {
            // 已登录
            // 隐藏提示登录的布局
            suggestLayout.setVisibility(View.GONE);
            getCartProducts();
        } else {
            // 未登录
            // 隐藏编辑按钮
            btnEdit.setVisibility(View.GONE);
            // 隐藏购物车列表
            listLayout.setVisibility(View.GONE);
            // 隐藏底部付款布局
            checkLayout.setVisibility(View.GONE);
            // 显示提示登录的布局
            suggestLayout.setVisibility(View.VISIBLE);
            // 显示购物车为空的布局
            tvEmpty.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 初始化视图
     *
     * @param view 父视图
     */
    private void initView(View view) {
        btnEdit = (Button) view.findViewById(R.id.cart_btn_edit);
        btnEdit.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // 编辑按钮
                if (btnEdit.getText().toString()
                        .equals(getResources().getString(R.string.edit))) {
                    btnEdit.setText(getResources().getString(
                            R.string.accomplish));
                    // 全部取消选中
                    cbCheckAll.setChecked(false);
                    // 隐藏价格总数文本框
                    tvTotal.setVisibility(View.INVISIBLE);
                    // 将结算按钮改成删除按钮
                    btnBuy.setText(getResources().getString(R.string.delete));
                    // 完成按钮
                } else {
                    btnEdit.setText(getResources().getString(R.string.edit));
                    // 全部选中
                    cbCheckAll.setChecked(true);
                    // 显示价格总数文本框
                    tvTotal.setVisibility(View.VISIBLE);
                    // 将删除按钮改成结算按钮
                    btnBuy.setText(getResources().getString(R.string.buy));
                }
            }
        });
        suggestLayout = (RelativeLayout) view
                .findViewById(R.id.cart_suggest_layout);
        btnLogin = (Button) view.findViewById(R.id.cart_btn_login);
        btnLogin.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // 跳转登录界面
                startActivity(new Intent(getActivity(), LoginActivity.class));
                // ////////////////////////////
                // //////////模拟登录///////////
                //Global.isLogin = true;
            }
        });
        tvEmpty = (TextView) view.findViewById(R.id.cart_tv_empty);
        listLayout = (LinearLayout) view.findViewById(R.id.cart_list_layout);
        listView = (ListView) view.findViewById(R.id.cart_lv);
        listView.setAdapter(adapter);
        checkLayout = (RelativeLayout) view
                .findViewById(R.id.cart_check_layout);
        cbCheckAll = (CheckBox) view.findViewById(R.id.cart_cb_all);
        cbCheckAll.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                // 全选监听
        for (int i = 0; i < products.size(); i++) {
            adapter.getIsChecked().put(i, isChecked);
        }
        adapter.notifyDataSetChanged();
    }
});
        tvTotal = (TextView) view.findViewById(R.id.cart_tv_total);
        btnBuy = (Button) view.findViewById(R.id.cart_btn_buy);
        btnBuy.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // 结算按钮
                if (btnBuy.getText().toString()
                        .equals(getResources().getString(R.string.buy))) {
                    //设置提交订单
                    // 删除按钮
                } else {
                    //设置删除购物车内商品
                }
            }
        });
        adapter.setOnPriceChangedListener(new OnPriceChangedListener() {

            @Override
            public void onPriceChanged(final float price) {
                tvTotal.post(new Runnable() {

                    @Override
                    public void run() {
                        tvTotal.setText("合计：￥" + price);
                    }
                });
            }
        });
    }

    /**
     * 初始化数据
     */
    private void initData() {
        products = new ArrayList<ProductData>();
        adapter = new CartListAdapter(getActivity(), products,
                R.layout.item_cart_lv);
    }

    private void getCarts(){
        String url=getResources().getString(R.string.ip);
        RequestParams params = new RequestParams (url);
        params.addBodyParameter("access_token",userInfo.getAccess_token());
        params.addBodyParameter("userid",userInfo.getUsername());
        x.http().post(params, new Callback.CommonCallback<String>(){
            @Override
            public void onSuccess(String result) {
                System.out.println("获得的购物车为："+result);
                try {
                    JSONArray ja=new JSONArray(result);
                    products.clear();
                    for(int i=0;i<ja.length();i++){
                        JSONObject jo=ja.getJSONObject(i);
                        ProductData pd=new ProductData();
                        pd.setId(jo.getInt("goodAccount"));
                        pd.setImgUrl(jo.getString("photoAddr"));
                        pd.setInfo(jo.getString("name"));
                        pd.setPrice((float)jo.getDouble("price"));
                        products.add(pd);
                    }
                    getCartProducts();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Toast.makeText(x.app(), result, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Toast.makeText(x.app(), ex.getMessage(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(CancelledException cex) {
                Toast.makeText(x.app(), "cancelled", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFinished() {
                Toast.makeText(x.app(), "Finish", Toast.LENGTH_LONG).show();
            }
        });
    }

    /**
     * 从服务器获取购物车商品数据
     */
    private void getCartProducts() {
        for (int i = 0; i < products.size(); i++) {
            adapter.getIsChecked().put(i, true);
            adapter.getNums().put(i, 1);
        }
        cbCheckAll.setChecked(true);
        if (products.size() == 0) {
            // 购物车为空
            // 隐藏编辑按钮
            btnEdit.setVisibility(View.GONE);
            // 隐藏购物车列表布局
            listLayout.setVisibility(View.GONE);
            // 隐藏底部结算布局
            checkLayout.setVisibility(View.GONE);
            // 显示购物车为空的布局
            tvEmpty.setVisibility(View.VISIBLE);
        } else {
            // 购物车不为空
            // 隐藏购物车为空的布局
            tvEmpty.setVisibility(View.GONE);
            // 显示编辑按钮
            btnEdit.setVisibility(View.VISIBLE);
            // 显示购物车列表布局
            listLayout.setVisibility(View.VISIBLE);
            // 显示底部结算布局
            checkLayout.setVisibility(View.VISIBLE);
            adapter.notifyDataSetChanged();
            tvTotal.setText("合计：￥" + adapter.calculatePrice());
        }
    }

}
