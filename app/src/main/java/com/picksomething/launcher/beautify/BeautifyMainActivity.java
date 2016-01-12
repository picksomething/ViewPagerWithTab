package com.picksomething.launcher.beautify;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.picksomething.launcher.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class BeautifyMainActivity extends AppCompatActivity {

    private static final int TAB_WIDTH = 48;

    @Bind(R.id.view_pager)
    ViewPager viewPager;

    @Bind(R.id.wallpaper_title)
    TextView wallpaperTitle;

    @Bind(R.id.theme_title)
    TextView themeTitle;

    @Bind(R.id.font_title)
    TextView fontTitle;

    @Bind(R.id.tab_indicator)
    ImageView indicatorLine;

    private int currentIndex;
    private float density;
    private int tabWidthPx;

    private BeautifyFragmentAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_beautify_main);
        ButterKnife.bind(this);

        setupViews();

        initTabLineWidth();
    }

    private void setupViews() {
        mAdapter = new BeautifyFragmentAdapter(getSupportFragmentManager());

        Bundle wallpaperData = new Bundle();
        wallpaperData.putInt(BeautifyMainFragment.PAGES_TYPE, BeautifyMainFragment.TYPE_WALLPAPER);
        BeautifyMainFragment wallpaper = new BeautifyMainFragment();
        wallpaper.setArguments(wallpaperData);
        mAdapter.addFragment(wallpaper);

        Bundle themeData = new Bundle();
        themeData.putInt(BeautifyMainFragment.PAGES_TYPE, BeautifyMainFragment.TYPE_THEME);
        BeautifyMainFragment theme = new BeautifyMainFragment();
        theme.setArguments(themeData);
        mAdapter.addFragment(theme);

        Bundle fontData = new Bundle();
        fontData.putInt(BeautifyMainFragment.PAGES_TYPE, BeautifyMainFragment.TYPE_FONT);
        BeautifyMainFragment font = new BeautifyMainFragment();
        font.setArguments(fontData);
        mAdapter.addFragment(font);

        viewPager.setAdapter(mAdapter);
        viewPager.setCurrentItem(0);
        viewPager.addOnPageChangeListener(onPageChangeListener);
    }

    private void initTabLineWidth() {
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        density = metrics.density;
        LinearLayout.LayoutParams indicatorLineLayoutParams = (LinearLayout.LayoutParams)
                indicatorLine.getLayoutParams();
        indicatorLineLayoutParams.width = (int) (48 * density);
        indicatorLine.setLayoutParams(indicatorLineLayoutParams);
        tabWidthPx = (int) (density * TAB_WIDTH);
    }


    ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {

        /**
         * position:当前页面，以及你点击滑动的页面
         * offset:当前页面偏移的百分比
         * offsetPixels:当前页面偏移的像素位置
         */
        @Override
        public void onPageScrolled(int position, float offset, int offsetPixels) {
            LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams)
                    indicatorLine.getLayoutParams();

            Log.d("offset:", offset + "");
            /**
             * 利用currentIndex(当前所在页面)和position(下一个页面)以及offset来
             * 设置indicatorLine的左边距 滑动场景：
             * 记3个页面,
             * 从左到右分别为0,1,2
             * 0->1; 1->2; 2->1; 1->0
             */

            if (currentIndex == 0 && position == 0) { // 0->1
                lp.leftMargin = (int) (offset * tabWidthPx
                        + currentIndex * tabWidthPx);

            } else if (currentIndex == 1 && position == 0) { // 1->0
                lp.leftMargin = (int) (-(1 - offset) * tabWidthPx
                        + currentIndex * tabWidthPx);

            } else if (currentIndex == 1 && position == 1) { // 1->2
                lp.leftMargin = (int) (offset * tabWidthPx
                        + currentIndex * tabWidthPx);
            } else if (currentIndex == 2 && position == 1) { // 2->1
                lp.leftMargin = (int) (-(1 - offset) * tabWidthPx
                        + currentIndex * tabWidthPx);
            }
            indicatorLine.setLayoutParams(lp);
        }

        @Override
        public void onPageSelected(int position) {
            resetTextView();
            switch (position) {
                case 0:
                    wallpaperTitle.setTextColor(ContextCompat.getColor(
                            BeautifyMainActivity.this, R.color.bm_tab_text_selected));
                    break;
                case 1:
                    themeTitle.setTextColor(ContextCompat.getColor(
                            BeautifyMainActivity.this, R.color.bm_tab_text_selected));
                    break;
                case 2:
                    fontTitle.setTextColor(ContextCompat.getColor(
                            BeautifyMainActivity.this, R.color.bm_tab_text_selected));
                    break;
            }
            currentIndex = position;
        }

        /**
         * state:滑动中的状态 有三种状态（0，1，2） 1：正在滑动 2：滑动完毕 0：什么都没做。
         */
        @Override
        public void onPageScrollStateChanged(int state) {


        }
    };

    /**
     * 重置颜色
     */
    private void resetTextView() {
        wallpaperTitle.setTextColor(ContextCompat.getColor(this, R.color.bm_tab_text));
        themeTitle.setTextColor(ContextCompat.getColor(this, R.color.bm_tab_text));
        fontTitle.setTextColor(ContextCompat.getColor(this, R.color.bm_tab_text));
    }
}
