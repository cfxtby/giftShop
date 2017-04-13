package com.example.tby.myapplication.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.Toast;

import cn.trinea.android.view.autoscrollviewpager.AutoScrollViewPager;

import com.example.R;
import com.example.tby.myapplication.activity.LoginActivity;
import com.example.tby.myapplication.activity.MainActivity;
import com.example.tby.myapplication.activity.ProductDetailsSecondLayerActivity;
import com.example.tby.myapplication.activity.ProductDetailsThirdLayerActivity;
import com.example.tby.myapplication.model.HomeFloorData;
import com.example.tby.myapplication.model.ProductData;
import com.example.tby.myapplication.network.NetworkManager;
import com.example.tby.myapplication.noscrollview.NoScrollListView;
import com.example.tby.myapplication.userInfo;
import com.viewpagerindicator.CirclePageIndicator;
import com.example.tby.myapplication.adapter.HomeListAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

/**
 * 首页Fragment
 */
public class HomeFragment extends Fragment {

    /**
     * 广告自动循环切换的时间间隔
     */
    private static final int AUTO_SCROLL_INTERVAL = 3000;//每经过3s，进行一次切换
    private AutoScrollViewPager viewPager;//自动切换的viewpager
    private CirclePageIndicator indicator;//
    private PagerAdapter pagerAdapter;
    private ArrayList<View> viewContainer;
    /**
     * 广告图片的url数组
     */
    private ArrayList<String> urls;
    /**
     * 商品列表部分
     */
    private NoScrollListView listView;
    private HomeListAdapter listAdapter;
    private ArrayList<HomeFloorData> floorDatas=new ArrayList<HomeFloorData>();
    /**
     * 顶部搜索
     */
    private EditText etSearch;
    private ImageButton ibSearch;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        initView(view);
        return view;
    }

    /**
     * 初始化视图
     */
    private void initView(View view) {
        // 广告位部分
        viewPager = (AutoScrollViewPager) view
                .findViewById(R.id.home_viewPager);
        getViewImage();
        pagerAdapter = new PagerAdapter() {

            @Override
            public void destroyItem(ViewGroup container, int position,
                                    Object object) {
                ((AutoScrollViewPager) container).removeView(viewContainer
                        .get(position));
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                ((AutoScrollViewPager) container).addView(viewContainer
                        .get(position));
                return viewContainer.get(position);
            }

            @Override
            public boolean isViewFromObject(View arg0, Object arg1) {
                return arg0 == arg1;
            }

            @Override
            public int getCount() {
                return viewContainer.size();
            }
        };
        viewPager.setAdapter(pagerAdapter);
        indicator = (CirclePageIndicator) view
                .findViewById(R.id.home_indicator);
        indicator.setViewPager(viewPager);
        viewPager.setInterval(AUTO_SCROLL_INTERVAL);
        viewPager.startAutoScroll();
        // 商品列表部分
        listView = (NoScrollListView) view.findViewById(R.id.home_lv);
        //floorDatas = getListData();
        listAdapter = new HomeListAdapter(getActivity(), floorDatas,
                R.layout.item_home_lv);
        listView.setAdapter(listAdapter);
        listView.setDivider(null);
        // 手动给ListView内容设置了高度，导致页面进入不在顶端，通过给顶端控件设置焦点的方法使view显示在顶端
        viewPager.setFocusable(true);
        viewPager.setFocusableInTouchMode(true);
        viewPager.requestFocus();
        // 顶部搜索部分
        etSearch = (EditText) view.findViewById(R.id.home_top_et_search);
        ibSearch = (ImageButton) view.findViewById(R.id.home_top_ib_search);
        ibSearch.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(
                        getActivity(),
                        ProductDetailsSecondLayerActivity.class));


                // ////////////////////////////////////
                // /////////向服务器发送搜索请求///////////
                etSearch.getText().toString().trim();
                // ////////////////////////////////////
            }
        });
    }



    /**
     * 获取广告图片列表
     */
    private void getViewImage() {
        if (viewContainer == null) {
            viewContainer = new ArrayList<View>();
        }
        if (urls == null) {
            urls = new ArrayList<String>();
        }
        urls = getImageUrls();
        viewContainer.clear();
        for (String url : urls) {
            ImageView iv=new ImageView(getActivity());
            NetworkManager.display(iv,url);
            viewContainer.add(iv);
        }
    }

    /**
     * 获取广告位的图片资源url数组
     * 每次从服务器获得url数组先做永久性存储，获取时先从本地显示之前的缓存，等获取成功之后再调用notifyDataSetChanged()
     *
     * @return url数组
     */
    private ArrayList<String> getImageUrls() {
        ArrayList<String> list = new ArrayList<String>();
        // //////////////////////////////////////
        // ////////////////假数据/////////////////
        list.add("http://b.hiphotos.baidu.com/image/pic/item/14ce36d3d539b6006bae3d86ea50352ac65cb79a.jpg");
        list.add("http://c.hiphotos.baidu.com/image/pic/item/37d12f2eb9389b503564d2638635e5dde7116e63.jpg");
        list.add("http://g.hiphotos.baidu.com/image/pic/item/cf1b9d16fdfaaf517578b38e8f5494eef01f7a63.jpg");
        list.add("http://f.hiphotos.baidu.com/image/pic/item/77094b36acaf2eddce917bd88e1001e93901939a.jpg");
        list.add("http://g.hiphotos.baidu.com/image/pic/item/f703738da97739124dd7b750fb198618367ae20a.jpg");
        // //////////////////////////////////////
        return list;
    }

    /**
     * 获取楼层列表的数据
     *
    private ArrayList<HomeFloorData> getListData() {

        // ///////////////////////////////////////
        // /////////////////假数据/////////////////
        String url = R.string.ip+"welcome";//"http://b.hiphotos.baidu.com/image/pic/item/14ce36d3d539b6006bae3d86ea50352ac65cb79a.jpg";

        // ///////////////////////////////////////

    }

    /**
     * 通知ViewPager广告位数据源改变
     */
    public void notifyDataSetChanged() {
        if (pagerAdapter != null)
            pagerAdapter.notifyDataSetChanged();
        if (indicator != null)
            indicator.notifyDataSetChanged();
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


                if (result != null) {
                    System.out.println(result);
                    if(result.compareTo("ERROR")==0){
                       // Toast.makeText(LoginActivity.this,"账号或密码错误",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    try {
                        JSONArray jo=new JSONArray(result);
                        ArrayList<ProductData> products;
                        HomeFloorData floor;
                        ProductData product;


                        for (int j = 0; j < 2; j++) {
                            products = new ArrayList<ProductData>();
                            for (int i = 0; i < 4; i++) {
                                product = new ProductData();
                                product.setId(i);
                                //product.setImgUrl(imgUrl);
                                product.setInfo("上岛咖啡上岛咖啡上岛咖啡上岛咖啡上岛咖啡");
                                product.setPrice(i * 2);
                                products.add(product);
                            }
                            floor = new HomeFloorData();
                            floor.setFloor(j + "F");
                            floor.setProducts(products);
                            floorDatas.add(floor);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    //Toast.makeText(LoginActivity.this,"登陆成功",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                //ld.dismiss();
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




}
