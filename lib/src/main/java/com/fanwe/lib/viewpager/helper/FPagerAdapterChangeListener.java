package com.fanwe.lib.viewpager.helper;

import android.support.v4.view.ViewPager;

/**
 * PagerAdapter变化监听
 */
public abstract class FPagerAdapterChangeListener extends FViewPagerHolder implements ViewPager.OnAdapterChangeListener
{
    @Override
    protected void onInit(ViewPager viewPager)
    {
        viewPager.addOnAdapterChangeListener(this);
    }

    @Override
    protected void onRelease(ViewPager viewPager)
    {
        viewPager.removeOnAdapterChangeListener(this);
    }
}
