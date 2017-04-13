package com.example.tby.myapplication.adapter;

import java.util.List;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.SparseBooleanArray;
import android.util.SparseIntArray;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.R;
import com.example.tby.myapplication.model.ProductData;
import com.example.tby.myapplication.network.NetworkManager;
import com.mtxc.universallistview.UniversalAdapter;
import com.mtxc.universallistview.ViewHolder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class CartListAdapter extends UniversalAdapter<ProductData> {

    private SparseBooleanArray isChecked;
    private SparseIntArray nums;
    private OnPriceChangedListener onPriceChangedListener;

    public CartListAdapter(Context context, List<ProductData> datas,
                           int itemLayoutId) {
        super(context, datas, itemLayoutId);
        isChecked = new SparseBooleanArray();
        nums = new SparseIntArray();
    }

    @Override
    public void updateItem(final ViewHolder holder, final ProductData data) {
        CheckBox cb = holder.getView(R.id.item_cart_cb);
        cb.setChecked(isChecked.get(holder.getPosition()));
        ImageView iv = holder.getView(R.id.item_cart_iv);
        NetworkManager.display(iv, data.getImgUrl());
        holder.setTextViewText(R.id.item_cart_tv_info, data.getInfo());
        holder.setTextViewText(R.id.item_cart_tv_price, "￥" + data.getPrice());
        final EditText etNum = holder.getView(R.id.item_cart_et_num);
        final Button btnSub = holder.getView(R.id.item_cart_btn_sub);
        Button btnAdd = holder.getView(R.id.item_cart_btn_add);
        cb.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                getIsChecked().put(holder.getPosition(), isChecked);
                if (onPriceChangedListener != null) {
                    onPriceChangedListener.onPriceChanged(calculatePrice());
                }
            }
        });
        btnSub.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                int num = Integer.parseInt(etNum.getText().toString());
                etNum.setText((--num) + "");
                getNums().put(holder.getPosition(), num);
                if (onPriceChangedListener != null) {
                    onPriceChangedListener.onPriceChanged(calculatePrice());
                }
            }
        });
        btnAdd.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                int num = Integer.parseInt(etNum.getText().toString());
                etNum.setText((++num) + "");
                getNums().put(holder.getPosition(), num);
                if (onPriceChangedListener != null) {
                    onPriceChangedListener.onPriceChanged(calculatePrice());
                }
            }
        });
        etNum.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                int num = Integer.parseInt(s.toString());
                if (num == 1) {
                    btnSub.setEnabled(false);
                } else {
                    btnSub.setEnabled(true);
                }
                getNums().put(holder.getPosition(), num);
                if (onPriceChangedListener != null) {
                    onPriceChangedListener.onPriceChanged(calculatePrice());
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    public SparseBooleanArray getIsChecked() {
        return isChecked;
    }

    public SparseIntArray getNums() {
        return nums;
    }

    /**
     * 总价改变监听器
     */
    public interface OnPriceChangedListener {
        void onPriceChanged(float price);
    }

    public void setOnPriceChangedListener(
            OnPriceChangedListener onPriceChangedListener) {
        this.onPriceChangedListener = onPriceChangedListener;
    }

    public JSONArray getOrders(){
        JSONArray ja=new JSONArray();
        for(int i=0;i<datas.size();i++){
            if (isChecked.get(i) && nums.get(i)>0) {
                JSONObject jo=new JSONObject();
                try {
                    jo.put("ID",datas.get(i).getId());
                    jo.put("num",nums.get(i));
                    ja.put(jo);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        return ja;
    }

    /**
     * 计算总价
     */
    public float calculatePrice() {
        float price = 0;
        for (int i = 0; i < datas.size(); i++) {
            if (isChecked.get(i)) {
                price += (nums.get(i)) * (datas.get(i).getPrice());
            }
        }
        return price;
    }

}
