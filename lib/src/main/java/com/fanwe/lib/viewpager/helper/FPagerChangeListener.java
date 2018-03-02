package com.fanwe.lib.viewpager.helper;

import android.support.v4.view.ViewPager;

/**
 * Created by zhengjun on 2018/3/2.
 */
public abstract class FPagerChangeListener extends FViewPagerHolder implements ViewPager.OnPageChangeListener
{
    @Override
    protected void onRelease(ViewPager viewPager)
    {
        viewPager.removeOnPageChangeListener(this);
    }

    @Override
    protected void onInit(ViewPager viewPager)
    {
        viewPager.addOnPageChangeListener(this);
    }
}
