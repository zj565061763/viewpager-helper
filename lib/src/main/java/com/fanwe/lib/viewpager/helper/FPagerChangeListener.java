package com.fanwe.lib.viewpager.helper;

import android.support.annotation.CallSuper;
import android.support.v4.view.ViewPager;

/**
 * 页面变化监听
 */
public abstract class FPagerChangeListener extends FViewPagerHolder implements ViewPager.OnPageChangeListener
{
    @CallSuper
    @Override
    protected void onInit(ViewPager viewPager)
    {
        viewPager.addOnPageChangeListener(this);
    }

    @CallSuper
    @Override
    protected void onRelease(ViewPager viewPager)
    {
        viewPager.removeOnPageChangeListener(this);
    }
}
