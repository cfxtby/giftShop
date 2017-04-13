package com.example.tby.myapplication.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.R;
import com.example.tby.myapplication.model.ProductData;
import com.example.tby.myapplication.network.NetworkManager;
import com.mtxc.universallistview.UniversalAdapter;
import com.mtxc.universallistview.ViewHolder;

public class PdSecondLayerAdapter extends UniversalAdapter<ProductData> {

    public PdSecondLayerAdapter(Context context, ArrayList<ProductData> datas,
                                int itemLayoutId) {
        super(context, datas, itemLayoutId);
    }

    @Override
    public void updateItem(ViewHolder viewHolder, ProductData data) {
        ImageView imageView = viewHolder
                .getView(R.id.iv_pd_second_layer);
        TextView tv_price = viewHolder.getView(R.id.tv_item_pd_price);
        TextView tv_info = viewHolder.getView(R.id.tv_item_pd_info);

        NetworkManager.display(imageView, data.getImgUrl());
        tv_price.setText("￥" + data.getPrice());
        tv_info.setText(data.getInfo());
    }

}
