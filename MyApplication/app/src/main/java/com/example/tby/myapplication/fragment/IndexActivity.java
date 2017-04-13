package com.example.tby.myapplication.fragment;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.InputType;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import com.example.R;
import com.example.tby.myapplication.activity.ProductDetailsThirdLayerActivity;
import com.example.tby.myapplication.adapter.gridViewAdapter;
import com.example.tby.myapplication.network.NetworkManager;
import com.example.tby.myapplication.utils.beans;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.adapter.StaticPagerAdapter;
import com.jude.rollviewpager.hintview.ColorPointHintView;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class IndexActivity extends Fragment implements OnClickListener {
	public static final String TAG = IndexActivity.class.getSimpleName();

	public ArrayList<beans> welList1;
	public ArrayList<beans> welList2;

	/**
	 * 顶部搜索
	 */
	private EditText etSearch;
	private ImageButton ibSearch;

	private ArrayList<bean>list=new ArrayList<>();

	private GridView middleGrid;
	private  GridView gv,gv1;
	private GridAdapter adapter1,adapter2;

	private View view;
	private LayoutInflater li;
	private RollPagerView mpv;



	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.activity_index, container, false);
		this.view=view;
		this.li=inflater;
		welList1=new ArrayList<beans>();
		welList2=new ArrayList<beans>();
		getWelList();
		findViewById();
		initView();
		return view;
	}


	protected void findViewById() {
		// TODO Auto-generated method stub
		middleGrid = (GridView) view.findViewById(R.id.grid_main);
	}

	protected void initView() {
		int[] imgid={R.drawable.shortcuts_icon_promotion,R.drawable.shortcuts_icon_recharge,
				R.drawable.shortcuts_icon_groupbuy,R.drawable.shortcuts_icon_lottery,
				R.drawable.shortcuts_icon_order,R.drawable.shortcuts_icon_history,
				R.drawable.shortcuts_icon_life_journey,R.drawable.shortcuts_icon_collect
		};
		String[] text={"促销卖场","充值","团购","彩票",
				"订单","历史","摇一摇","关注"};
		ArrayList<Map<String,Object>> array=new ArrayList<>();
		for(int i=0;i<imgid.length;i++){
			Map<String,Object> map=new HashMap<String,Object>();
			map.put("image",imgid[i]);
			map.put("text",text[i]);
			array.add(map);
		}
		gridViewAdapter grid=new gridViewAdapter(this.getContext(),array);
		middleGrid.setAdapter(grid);
		middleGrid.setOnItemClickListener(new gridListener());

		mpv = (RollPagerView) view.findViewById(R.id.roll_view_pager);

		//设置播放时间间隔
		mpv.setPlayDelay(5000);
		//设置透明度
		mpv.setAnimationDurtion(500);
		//设置适配器
		mpv.setAdapter(new TestNormalAdapter());

		//设置指示器（顺序依次）
		//自定义指示器图片
		//设置圆点指示器颜色
		//设置文字指示器
		//隐藏指示器
		//mRollViewPager.setHintView(new IconHintView(this, R.drawable.point_focus, R.drawable.point_normal));
		mpv.setHintView(new ColorPointHintView(this.getContext(), Color.YELLOW,Color.WHITE));

		// ======= 初始化ViewPager ========

		//mBarPopupWindow = new HomeSearchBarPopupWindow(this.getContext(),
		//		LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		gv1= (GridView) view.findViewById(R.id.listView1);
		adapter1=new GridAdapter(welList1);
		adapter2=new GridAdapter(welList2);
		gv1.setAdapter(adapter1);
		gv= (GridView) view.findViewById(R.id.listView2);
		horizontal_layout();
		gv.setAdapter(adapter2);
		gv.setOnItemClickListener(new welGridListener(welList1));
		gv1.setOnItemClickListener(new welGridListener(welList2));
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		default:
			break;
		}
	}

	private void getWelList(){
		String url=getResources().getString(R.string.ip)+"welcome";
		RequestParams params = new RequestParams(url);
		String s=params.getCharset();
		System.out.println(s);
		x.http().get(params, new Callback.CommonCallback<String>() {

			@Override
			public void onSuccess(String result) {
				System.out.println(result);
				try {
					JSONArray ja=new JSONArray(result);
					int i=0;
					for(;i<ja.length()/2;i++){
						JSONObject jo=ja.getJSONObject(i);
						beans b=new beans();
						b.setId(jo.getLong("accountGood"));
						b.setPrice("￥"+jo.getString("price"));
						b.setImgUrl1(jo.getString("photoAddr"));
						welList1.add(b);
					}
					for(;i<ja.length();i++){
						JSONObject jo=ja.getJSONObject(i);
						beans b=new beans();
						b.setId(jo.getLong("accountGood"));
						b.setPrice("￥"+jo.getString("price"));
						b.setImgUrl1(jo.getString("photoAddr"));
						welList2.add(b);
					}


					adapter1.notifyDataSetChanged();
					adapter2.notifyDataSetChanged();
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

			@Override
			public void onError(Throwable ex, boolean isOnCallback) {
				Toast.makeText(getContext(),"网络异常",Toast.LENGTH_SHORT);
			}

			@Override
			public void onCancelled(CancelledException cex) {

			}

			@Override
			public void onFinished() {

			}
		});
	}

	class gridListener implements OnItemClickListener{

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			//System.out.println("此次点击的是第"+position+"个位置");
			Toast.makeText(getContext(),"暂不支持该功能",Toast.LENGTH_SHORT).show();
		}
	}
	class TestNormalAdapter extends StaticPagerAdapter {
		private int[] imgs = {
				R.drawable.image02,
				R.drawable.image03,
				R.drawable.image04
		};

		@Override
		public View getView(ViewGroup container, int position) {

			ImageView view = new ImageView(container.getContext());
			view.setImageResource(imgs[position]);
			System.out.println(position);
			//ImageLoader.getInstance().displayImage("drawable://"+imgs[position],view);

			view.setScaleType(ScaleType.CENTER_CROP);
			view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
			//container.addView(view);
			return view;
		}

		@Override
		public int getCount() {
			return imgs.length;
		}
	}

	class GridAdapter extends BaseAdapter {
		//ImageLoader imageLoader = new ImageLoader(requestQueue, new BitmapCache());
		ArrayList<beans> wel;
		GridAdapter(ArrayList<beans> wel){
			this.wel=wel;
		}

		@Override
		public int getCount() {
			return wel.size();
		}

		@Override
		public Object getItem(int position) {
			return wel.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		class ViewHolder {
			TextView price;
			ImageView imageView;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder viewHolder = null;
			if (convertView == null) {
				viewHolder = new ViewHolder();
				convertView = li.inflate(R.layout.bottomgrid, null);
				viewHolder.price = (TextView) convertView.findViewById(R.id.textb);
				viewHolder.imageView = (ImageView) convertView.findViewById(R.id.ivb);
				convertView.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder) convertView.getTag();
			}

			viewHolder.price.setText(wel.get(position).getPrice());
			//ImageLoader.getInstance().displayImage("drawable://"+list.get(position).img,viewHolder.imageView);
			NetworkManager.display(viewHolder.imageView,getResources().getString(R.string.ip)+wel.get(position).getImgUrl1());
			return convertView;
		}


	}
	class bean{
		String text;
		int img;
	}
	public void horizontal_layout(){
		int size =4;
		System.out.println(size);
		DisplayMetrics dm = new DisplayMetrics();
		getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
		float density = dm.density;
		int allWidth = (int) (110 * size * density);
		int itemWidth = (int) (100 * density);
		LayoutParams params = new LayoutParams(
				allWidth, LayoutParams.FILL_PARENT);
		gv.setLayoutParams(params);// 设置GirdView布局参数
		gv.setColumnWidth(itemWidth);// 列表项宽
		gv.setHorizontalSpacing(40);// 列表项水平间距
		gv.setStretchMode(GridView.NO_STRETCH);
		gv.setNumColumns(size);//总长度
		gv1.setLayoutParams(params);// 设置GirdView布局参数
		gv1.setColumnWidth(itemWidth);// 列表项宽
		gv1.setHorizontalSpacing(40);// 列表项水平间距
		gv1.setStretchMode(GridView.NO_STRETCH);
		gv1.setNumColumns(size);//总长度
	}
	class welGridListener implements OnItemClickListener{
		ArrayList<beans> list;
		welGridListener(ArrayList<beans> list){
			this.list=list;
		}
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			Intent in=new Intent();
			in.setClass(getContext(), ProductDetailsThirdLayerActivity.class);
			System.out.println(list.get(position).getId());
			in.putExtra("ID",list.get(position).getId());
			startActivity(in);
		}
	}

}
