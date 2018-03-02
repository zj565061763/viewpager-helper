package com.fanwe.lib.viewpager.helper;

import android.support.v4.view.ViewPager;

/**
 * 页面变化监听
 */
public abstract class FPagerChangeListener extends FViewPagerHolder implements ViewPager.OnPageChangeListener
{
    @Override
    protected void onInit(ViewPager viewPager)
    {
        viewPager.addOnPageChangeListener(this);
    }

    @Override
    protected void onRelease(ViewPager viewPager)
    {
        viewPager.removeOnPageChangeListener(this);
    }
}
