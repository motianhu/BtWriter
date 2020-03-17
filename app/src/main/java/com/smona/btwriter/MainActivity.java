package com.smona.btwriter;

import android.Manifest;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.smona.base.ui.fragment.BaseFragment;
import com.smona.btwriter.language.BaseLanguageActivity;
import com.smona.btwriter.main.MainFragmentAdapter;
import com.smona.btwriter.main.fragment.HomeFragment;
import com.smona.btwriter.main.fragment.MineFragment;
import com.smona.btwriter.main.fragment.ParamFragment;
import com.smona.btwriter.util.ARouterPath;
import com.smona.btwriter.util.CommonUtil;
import com.smona.btwriter.widget.NoScrollViewPager;

import java.util.ArrayList;
import java.util.List;

@Route(path = ARouterPath.PATH_TO_MAIN)
public class MainActivity extends BaseLanguageActivity {

    private TabLayout tabs;
    private NoScrollViewPager viewpager;
    private List<String> titles = new ArrayList<>();
    private List<Integer> resIds = new ArrayList<>();
    private List<BaseFragment> fragments = new ArrayList<>();
    private MainFragmentAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        initData();
        refreshragments();
        initPermissions();
    }

    private void initPermissions() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                1000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void initViews() {
        tabs = findViewById(R.id.tabs);
        viewpager = findViewById(R.id.viewpager);
        pagerAdapter = new MainFragmentAdapter(getSupportFragmentManager(), fragments, titles);
        viewpager.setAdapter(pagerAdapter);
        viewpager.setNeedScroll(false);
        tabs.setSelectedTabIndicatorHeight(0);
        tabs.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewpager) {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                super.onTabSelected(tab);
            }
        });
    }

    private void initData() {
        fragments.add(new HomeFragment());
        fragments.add(new ParamFragment());
        fragments.add(new MineFragment());

        titles.add(getString(R.string.home));
        titles.add(getString(R.string.param));
        titles.add(getString(R.string.mine));

        resIds.add(R.drawable.tab_icon_home);
        resIds.add(R.drawable.tab_icon_param);
        resIds.add(R.drawable.tab_icon_mine);
    }

    private void refreshragments() {
        tabs.setupWithViewPager(viewpager, true);
        pagerAdapter.updateFragments(fragments);
        viewpager.setOffscreenPageLimit(fragments.size());
        tabs.setTabMode(TabLayout.MODE_FIXED);

        for (int i = 0; i < fragments.size(); i++) {
            TabLayout.Tab tab = tabs.getTabAt(i);
            if (tab != null) {
                tab.setCustomView(getTabView(titles.get(i), resIds.get(i), i == 0));
            }
        }
        int index = 0;
        viewpager.setCurrentItem(index);
    }

    // Tab自定义view
    public View getTabView(String title, int resId, boolean isSelected) {
        View v = LayoutInflater.from(getApplicationContext()).inflate(R.layout.tab_layout_item, null);
        TextView textView = v.findViewById(R.id.textview);
        textView.setText(title);
        ImageView imageView = v.findViewById(R.id.imageview);
        imageView.setImageResource(resId);
        imageView.setSelected(isSelected);
        v.setSelected(isSelected);
        return v;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @SuppressLint("MissingSuperCall")
    @Override
    protected void onSaveInstanceState(Bundle outState) {
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
    }

    public void switchSettingFragment() {
        viewpager.setCurrentItem(2);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            int curPos = viewpager.getCurrentItem();
            if (fragments.get(curPos).backpressed()) {
                return true;
            }
            exit();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    //退出时的时间
    private long mExitTime;

    public void exit() {
        if ((System.currentTimeMillis() - mExitTime) > 2000) {
            CommonUtil.showShort(this, getString(R.string.app_exit_tip));
            mExitTime = System.currentTimeMillis();
        } else {
            finish();
        }
    }

}
