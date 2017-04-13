package com.example.tby.myapplication.adapter;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;

import com.example.R;
import com.example.tby.myapplication.model.SecondCategoryData;
import com.example.tby.myapplication.network.NetworkManager;
import com.mtxc.universallistview.UniversalAdapter;
import com.mtxc.universallistview.ViewHolder;

public class CategoryRightGridAdapter extends UniversalAdapter<SecondCategoryData> {

    public CategoryRightGridAdapter(Context context,
                                    List<SecondCategoryData> datas, int itemLayoutId) {
        super(context, datas, itemLayoutId);
    }

    @Override
    public void updateItem(ViewHolder holder, SecondCategoryData data) {
        ImageView iv = holder.getView(R.id.item_category_right_iv);
        NetworkManager.display(iv, data.getImgUrl());
        holder.setTextViewText(R.id.item_category_right_tv, data.getName());
        // 设置图片宽高相等
        DisplayMetrics dm = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(dm);
        LayoutParams params = iv.getLayoutParams();
        int width = dm.widthPixels / 4 - (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 13, dm);
        params.width = width;
        params.height = width;
        iv.setLayoutParams(params);
    }

}
