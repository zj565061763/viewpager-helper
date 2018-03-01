package com.fanwe.viewpager_helper;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.fanwe.lib.viewpager.helper.FPercentPageChangeListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
{
    public static final String TAG = MainActivity.class.getSimpleName();

    private ViewPager mViewPager;
    private List<String> mListModel = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mViewPager = findViewById(R.id.viewpager);
        mViewPager.addOnPageChangeListener(new FPercentPageChangeListener()
        {
            @Override
            public void onShowPercent(int position, float showPercent, boolean isEnter, boolean isMoveLeft)
            {
                if (isEnter)
                {
                    Log.i(TAG, position + " (" + showPercent + ") " + isMoveLeft);
                } else
                {
                    Log.e(TAG, position + " (" + showPercent + ") " + isMoveLeft);
                }
            }
        });

        for (int i = 0; i < 10; i++)
        {
            mListModel.add(String.valueOf(i));
        }
        mViewPager.setAdapter(mPagerAdapter);
    }

    private PagerAdapter mPagerAdapter = new PagerAdapter()
    {
        @Override
        public int getItemPosition(Object object)
        {
            return POSITION_NONE;
        }

        @Override
        public int getCount()
        {
            return mListModel.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object)
        {
            return view == object;
        }

        public Object instantiateItem(ViewGroup container, final int pageIndex)
        {
            Button button = new Button(MainActivity.this);
            button.setText(String.valueOf(pageIndex));
            container.addView(button);
            return button;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object)
        {
            container.removeView((View) object);
        }
    };
}
