package com.fanwe.lib.viewpager.helper;

import android.support.v4.view.ViewPager;

/**
 * Created by zhengjun on 2018/3/1.
 */
public abstract class FViewPagerSelectChangeListener implements ViewPager.OnPageChangeListener
{
    private int mLastSelected;

    public void setSelected(int position)
    {
        if (position < 0)
        {
            return;
        }

        onSelectedChanged(mLastSelected, false);
        onSelectedChanged(position, true);

        mLastSelected = position;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
    {

    }

    @Override
    public void onPageSelected(int position)
    {
        setSelected(position);
    }

    @Override
    public void onPageScrollStateChanged(int state)
    {

    }

    protected abstract void onSelectedChanged(int position, boolean selected);
}
