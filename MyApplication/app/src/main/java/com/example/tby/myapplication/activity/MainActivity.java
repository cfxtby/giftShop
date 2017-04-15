package com.example.tby.myapplication.activity;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

import com.example.R;
import com.example.tby.myapplication.fragment.IndexActivity;
import com.example.tby.myapplication.utils.FragmentTag;
import com.example.tby.myapplication.utils.LogType;
import com.example.tby.myapplication.utils.LogUtil;

import org.xutils.BuildConfig;
import org.xutils.x;


/**
 * 主activity
 */
public class MainActivity extends Activity implements OnClickListener {
    /**
     * 当前Fragment的key
     */
    private FragmentTag mCurrentTag;
    /**
     * 当前Fragment
     */
    private Fragment mCurrentFragment;
    /**
     * 选项卡按钮数组
     */
    private ArrayList<ImageButton> mBtnTabs;

    @SuppressLint("InlinedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 初始化网络模块
      //  x.Ext.setDebug(BuildConfig.DEBUG); // 是否输出debug日志, 开启debug会影响性能.
        //NetworkManager.getInstance().init(getApplicationContext());
        ///应该是从网上开始下载当前的图片
        initView();
        if (savedInstanceState == null) {
            // 记录当前Fragment，首次进入初始化界面，进入homeFragment
            mCurrentTag = FragmentTag.TAG_HOME;
            mCurrentFragment = new IndexActivity(); //new HomeFragment();
            getFragmentManager()
                    .beginTransaction()
                    .add(R.id.main_fragment, mCurrentFragment,
                            mCurrentTag.getTag()).commit();
            ((TransitionDrawable) mBtnTabs.get(0).getDrawable())
                    .startTransition(200);
        }

    }
/*
    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 退出时移除队列中的所有网络请求
        NetworkManager.getInstance().remove();
    }*/

    /**
     * 初始化视图
     */
    private void initView() {
        // 添加选项卡按钮
        mBtnTabs = new ArrayList<ImageButton>();
        mBtnTabs.add((ImageButton) findViewById(R.id.main_btn_home));
        mBtnTabs.add((ImageButton) findViewById(R.id.main_btn_cart));
        mBtnTabs.add((ImageButton) findViewById(R.id.main_btn_my));
        for (int i = 0; i < mBtnTabs.size(); i++) {
            mBtnTabs.get(i).setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_btn_home:
                // 切换首页选项卡
                switchFragment(FragmentTag.TAG_HOME);
                break;
            case R.id.main_btn_cart:
                // 切换购物车选项卡
                switchFragment(FragmentTag.TAG_CART);
                break;
            case R.id.main_btn_my:
                // 切换我的信息选项卡
                switchFragment(FragmentTag.TAG_MY);
                break;
        }
    }

    /**
     * 切换Fragment
     *
     * @param to 目标Fragment
     */
    private void switchFragment(FragmentTag to) {
        if (!mCurrentTag.equals(to)) {
            Fragment currentF = getFragmentManager().findFragmentByTag(
                    mCurrentTag.getTag());
            Fragment toF = getFragmentManager().findFragmentByTag(to.getTag());
            if (null == toF) { // 先判断是否被add过
                try {
                    // 未add过，使用反射新建一个Fragment并add到FragmentManager中
                    toF = (Fragment) Class.forName(to.getTag()).newInstance();
                    getFragmentManager().beginTransaction().hide(currentF)
                            .add(R.id.main_fragment, toF, to.getTag()).commit(); // 隐藏当前的fragment，add下一个到Activity中
                    // 切换按钮动画
                    switchAnimation(to.ordinal());
                    // 更新当前Fragment
                    mCurrentTag = to;
                    mCurrentFragment = toF;
                } catch (Exception e) {
                    LogUtil.log(LogType.ERROR, this.getClass(),
                            "根据Tag创建Fragment实例出错");
                }
            } else {
                // add过，直接hide当前，并show出目标Fragment
                getFragmentManager().beginTransaction().hide(currentF)
                        .show(toF).commit(); // 隐藏当前的fragment，显示下一个
                // 切换按钮动画
                switchAnimation(to.ordinal());
                // 更新当前Fragment
                mCurrentTag = to;
                mCurrentFragment = toF;
            }
        }
    }

    /**
     * 切换Fragment时选项卡按钮的动画
     *
     * @param to 目标选项卡的下标
     */
    private void switchAnimation(int to) {
        ((TransitionDrawable) mBtnTabs.get(mCurrentTag.ordinal()).getDrawable())
                .reverseTransition(200);
        ((TransitionDrawable) mBtnTabs.get(to).getDrawable())
                .startTransition(200);
    }

    /**
     * 获取当前显示的Fragment
     *
     * @return 当前Fragment
     */
    public Fragment getCurrentFragment() {
        return mCurrentFragment;
    }

    /**
     * 点击MyFragment中查看所有收藏的处理事件
     *
     * @param view 被点击的view
     */
    public void showAllCollection(View view) {
    }

    /**
     * 点击MyFragment中查看所有订单的处理事件
     *
     * @param view 被点击的view
     */
    public void showAllOrder(View view) {
    }

}