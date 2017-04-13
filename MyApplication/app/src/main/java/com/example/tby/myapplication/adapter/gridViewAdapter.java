package com.example.tby.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


import com.example.R;
import com.example.tby.myapplication.network.NetworkManager;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by tby on 2017/4/7.
 */

public class gridViewAdapter extends BaseAdapter {
    ArrayList<Map<String,Object>> list;
    Context context;
    public gridViewAdapter(Context context, ArrayList<Map<String,Object>> list){
        this.context=context;
        this.list=list;
    }


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {

        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.index_class_item, null);
        }

        ImageButton imageView = (ImageButton) convertView.findViewById(R.id.index_btn);
        TextView textView = (TextView) convertView.findViewById(R.id.index_text);

        Map<String, Object> map = list.get(position);
        //NetworkManager.display(imageView,"drawable://" + (Integer) map.get("image"));
       // imageView.setImageResource((Integer) map.get("image"));
        imageView.setImageResource((Integer) map.get("image"));
        //ImageLoader.getInstance().displayImage("drawable://" + map.get("image"), imageView);
        //imageView.setImageResource((Integer) map.get("image"));
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,"暂不支持该功能",Toast.LENGTH_SHORT).show();
                System.out.println("点击了："+position);
            }
        });
        textView.setText(map.get("text") + "");
        return convertView;
    }
}
