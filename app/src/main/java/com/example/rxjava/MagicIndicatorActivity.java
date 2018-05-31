package com.example.rxjava;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 作者： ZlyjD.
 * 时间：2018/5/25.
 */

public class MagicIndicatorActivity extends AppCompatActivity {
    private MagicIndicator magicIndicator;
    private ViewPager mViewPager;
    private CommonNavigator commonNavigator;
    private List<String> mTitleDataList = new ArrayList<>();

    private static final String[] CHANNELS = new String[]{"KITKAT", "NOUGAT", "DONUT", "1212", "4546"};
    private List<String> mDataList = Arrays.asList(CHANNELS);
    private ExamplePagerAdapter mExamplePagerAdapter = new ExamplePagerAdapter(mDataList);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_magic);
        magicIndicator = (MagicIndicator) findViewById(R.id.magic_indicator);
        mViewPager = (ViewPager) findViewById(R.id.view_pager);

        commonNavigator = new CommonNavigator(this);
        initTitleData();
        initMagic();
        commonNavigator.setAdjustMode(true);
        mViewPager.setAdapter(mExamplePagerAdapter);
        magicIndicator.setNavigator(commonNavigator);

        ViewPagerHelper.bind(magicIndicator, mViewPager);
    }

    private void initTitleData() {
        mTitleDataList.add("第一第一");
        mTitleDataList.add("第二ss");
        mTitleDataList.add("第三");
        mTitleDataList.add("第四aaa");
        mTitleDataList.add("第五");
    }

    private void initMagic() {
        commonNavigator.setAdapter(new CommonAdapter());
    }

    class CommonAdapter extends CommonNavigatorAdapter {

        @Override
        public int getCount() {
            return mTitleDataList == null ? 0 : mTitleDataList.size();
        }

        @Override
        public IPagerTitleView getTitleView(Context context, final int index) {
            ColorTransitionPagerTitleView colorTransitionPagerTitleView = new ColorTransitionPagerTitleView(context);
            colorTransitionPagerTitleView.setNormalColor(Color.RED);
            colorTransitionPagerTitleView.setSelectedColor(Color.BLACK);
            colorTransitionPagerTitleView.setText(mTitleDataList.get(index));
            colorTransitionPagerTitleView.setTextSize(10);
            colorTransitionPagerTitleView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mViewPager.setCurrentItem(index);
                }
            });
            return colorTransitionPagerTitleView;
        }

        @Override
        public IPagerIndicator getIndicator(Context context) {
            LinePagerIndicator indicator = new LinePagerIndicator(context);
            indicator.setMode(LinePagerIndicator.MODE_WRAP_CONTENT);
            indicator.setColors(Color.RED);
            return indicator;
        }
    }

    /**
     * View_Pager
     * adapter
     */
    class ExamplePagerAdapter extends PagerAdapter {
        private List<String> mDataList;

        public ExamplePagerAdapter(List<String> dataList) {
            mDataList = dataList;
        }

        @Override
        public int getCount() {
            return mDataList == null ? 0 : mDataList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            TextView textView = new TextView(container.getContext());
            textView.setText(mDataList.get(position));
            textView.setGravity(Gravity.CENTER);
            textView.setTextColor(Color.BLACK);
            textView.setTextSize(24);
            container.addView(textView);
            return textView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public int getItemPosition(Object object) {
            TextView textView = (TextView) object;
            String text = textView.getText().toString();
            int index = mDataList.indexOf(text);
            if (index >= 0) {
                return index;
            }
            return POSITION_NONE;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mDataList.get(position);
        }
    }
}
