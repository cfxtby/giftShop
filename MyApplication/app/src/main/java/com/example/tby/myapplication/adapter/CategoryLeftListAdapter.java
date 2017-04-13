package com.example.tby.myapplication.adapter;

import java.util.List;

import android.content.Context;

import com.example.R;
import com.example.tby.myapplication.model.CategoryData;
import com.mtxc.universallistview.UniversalAdapter;
import com.mtxc.universallistview.ViewHolder;

public class CategoryLeftListAdapter extends UniversalAdapter<CategoryData> {

    public CategoryLeftListAdapter(Context context, List<CategoryData> datas,
                                   int itemLayoutId) {
        super(context, datas, itemLayoutId);
    }

    @Override
    public void updateItem(ViewHolder holder, CategoryData data) {
        holder.setTextViewText(R.id.item_category_left_tv, data.getName());
    }

}