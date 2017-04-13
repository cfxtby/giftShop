package com.example.tby.myapplication.activity;

import java.util.ArrayList;

import cn.trinea.android.view.autoscrollviewpager.AutoScrollViewPager;

import com.example.R;
import com.example.tby.myapplication.network.NetworkManager;
import com.example.tby.myapplication.userInfo;
import com.example.tby.myapplication.utils.beans;
import com.viewpagerindicator.CirclePageIndicator;


import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import static android.R.attr.id;
import static java.security.AccessController.getContext;

public class ProductDetailsThirdLayerActivity extends Activity {

    /**
     * 显示商品详情图片的viewpager
     */
    private AutoScrollViewPager viewPager;
    private CirclePageIndicator indicator;
    private ArrayList<View> viewContainer;
    private PagerAdapter pagerAdapter;
    private ImageButton btnBack;
    private TextView name,decs,price;
    private beans gift;
    private Button cart,buy;

    /**
     * 存储图片url地址
     */
    private ArrayList<String> urls;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details_third_layer);
        initView();
    }

    /**
     * 初始化界面
     */
    private void initView() {
        name= (TextView) findViewById(R.id.gift_name);
        price= (TextView) findViewById(R.id.gift_price);
        decs= (TextView) findViewById(R.id.gift_desc);
        gift=new beans();
        Intent in=getIntent();
        gift.setId(in.getExtras().getLong("ID"));
        initButtons();
        viewPager = (AutoScrollViewPager) findViewById(R.id.pd_viewPager);
        indicator = (CirclePageIndicator) findViewById(R.id.pd_indicator);
        getViewImage(); // 得到要予以展示的图片
        viewContainer = new ArrayList<View>();
        pagerAdapter = new PagerAdapter() {

            @Override
            public boolean isViewFromObject(View arg0, Object arg1) {
                return arg0 == arg1;
            }

            @Override
            public int getCount() {
                return viewContainer.size();
            }

            @Override
            public void destroyItem(View container, int position, Object object) {
                ((AutoScrollViewPager) container).removeView(viewContainer
                        .get(position));
            }

            @Override
            public Object instantiateItem(View container, int position) {
                ((AutoScrollViewPager) container).addView(viewContainer
                        .get(position));
                return viewContainer.get(position);
            }

        };

        viewPager.setAdapter(pagerAdapter);
        indicator.setViewPager(viewPager);
    }

    private void initButtons() {
        buy= (Button) findViewById(R.id.pd_btn_buy);
        cart= (Button) findViewById(R.id.pd_btn_addIntoCart);
        btnBack = (ImageButton) findViewById(R.id.pd_btn_back);
        OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    switch (v.getId()){
                        case R.id.pd_btn_addIntoCart:
                            break;
                        case R.id.cart_btn_buy:
                            buyGift();
                            break;
                        default:ProductDetailsThirdLayerActivity.this.finish();
                    }
                }
        };
        btnBack.setOnClickListener(listener);
        cart.setOnClickListener(listener);
        buy.setOnClickListener(listener);
    }

    private void addTocart(){
        String url=getResources().getString(R.string.ip);
        RequestParams params = new RequestParams (url);
        params.addBodyParameter("","");
        x.http().post(params, new Callback.CommonCallback<String>(){
            @Override
            public void onSuccess(String result) {
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

    private void buyGift(){
        if(userInfo.getAccess_token()==null){
            Toast.makeText(this,"请先注册登录",Toast.LENGTH_SHORT).show();
            return;
        }
        String url=getResources().getString(R.string.ip);
        RequestParams params = new RequestParams (url);
        params.addBodyParameter("access_token",userInfo.getAccess_token());
        params.addBodyParameter("goodid",gift.getId()+"");
        params.addBodyParameter("userid", userInfo.getUsername()+"");
        params.addBodyParameter("price",gift.getPrice()+"");
        params.addBodyParameter("num","1");

        x.http().post(params, new Callback.CommonCallback<String>(){
            @Override
            public void onSuccess(String result) {
                Log.d("提交订单返回值",result);
               // Toast.makeText(x.app(), result, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

                Toast.makeText(x.app(), ex.getMessage(), Toast.LENGTH_SHORT).show();
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


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     * 获取广告图片列表
     */
    private void getViewImage() {
        System.out.println(gift.getId());
        String url=getResources().getString(R.string.ip)+"goodInfo?accountGood="+gift.getId();
        System.out.println(url);
        RequestParams params = new RequestParams(url);
        x.http().get(params, new Callback.CommonCallback<String>() {

            @Override
            public void onSuccess(String result) {
                System.out.println(result);
                try {
                    JSONObject ja=new JSONArray(result).getJSONObject(0);
                    gift.setPrice(ja.getString("price"));
                    gift.setDecr(ja.getString("description"));
                    gift.setName(ja.getString("name"));
                    if(ja.getString("photoAddr2").compareTo("null")!=0){
                        gift.setImgUrl2(ja.getString("photoAddr2"));
                    }
                    gift.setImgUrl1(getResources().getString(R.string.ip)+ja.getString("photoAddr1"));
                    price.setText("￥："+gift.getPrice());
                    name.setText(gift.getName());
                    decs.setText(gift.getDecr());
                    if (viewContainer == null) {
                        viewContainer = new ArrayList<View>();
                    }

                    viewContainer.clear();
                    ImageView iv=new ImageView(ProductDetailsThirdLayerActivity.this);
                    NetworkManager.display(iv,gift.getImgUrl1());
                    viewContainer.add(iv);
                    if(gift.getImgUrl2()!=null){
                        ImageView iv1=new ImageView(ProductDetailsThirdLayerActivity.this);
                        NetworkManager.display(iv,gift.getImgUrl2());
                        viewContainer.add(iv1);
                    }
                    pagerAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Toast.makeText(ProductDetailsThirdLayerActivity.this,"网络异常",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });



    }

    /**
     * 获取广告位的图片资源url数组
     * 每次从服务器获得url数组先做永久性存储，获取时先从本地显示之前的缓存，等获取成功之后再调用notifyDataSetChanged()
     *
     * @return url数组
     */
    void getGiftInfo(){

    }

}
