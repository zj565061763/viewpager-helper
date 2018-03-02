package com.fanwe.lib.viewpager.helper;

import android.support.annotation.CallSuper;
import android.support.v4.view.ViewPager;

/**
 * PagerAdapter变化监听
 */
public abstract class FPagerAdapterChangeListener extends FViewPagerHolder implements ViewPager.OnAdapterChangeListener
{
    @CallSuper
    @Override
    protected void onInit(ViewPager viewPager)
    {
        viewPager.addOnAdapterChangeListener(this);
    }

    @CallSuper
    @Override
    protected void onRelease(ViewPager viewPager)
    {
        viewPager.removeOnAdapterChangeListener(this);
    }
}
