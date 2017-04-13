package com.example.tby.myapplication.network;

import org.json.JSONObject;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import android.content.Context;
import android.widget.ImageView;



/**
 * <b>网络请求操作单例类</b>
 * <p>
 * <br/>
 * 使用getInstance方法获得单例类 <br/>
 * 在程序发送网络请求之前需调用init方法 <br/>
 * 调用post方法发送网络请求 <br/>
 * 在程序退出时需调用remove方法
 */
public class NetworkManager {

    private Context mContext;

    /**
     * 网络请求队列
     */

    /**
     * 静态内部类的方法实现单例模式
     */
    private static class Holder {
        private static final NetworkManager INSTANCE = new NetworkManager();
    }

    public static void display(ImageView imageView, String iconUrl) {
        ImageOptions imageOptions = new ImageOptions.Builder()
                .setIgnoreGif(true)//是否忽略gif图。false表示不忽略。不写这句，默认是true
                .setImageScaleType(ImageView.ScaleType.CENTER_CROP)
                .setUseMemCache(true)
                //.setFailureDrawableId(R.mipmap.loadfailed)
                //.setLoadingDrawableId(R.mipmap.loading)
                .build();
        x.image().bind(imageView, iconUrl,imageOptions);
    }

}
